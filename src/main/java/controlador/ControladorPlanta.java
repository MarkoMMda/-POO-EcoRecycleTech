package controlador;

import modelo.*;
import vista.VistaPlanta;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que conecta la logica de la planta con la interfaz grafica.
 */
public class ControladorPlanta {
    private PlantaReciclaje modelo;
    private VistaPlanta vista;

    /**
     * Constructor que asocia el modelo y la vista, y activa los botones.
     * @param modelo Logica de la planta.
     * @param vista Ventana de la aplicacion.
     */
    public ControladorPlanta(PlantaReciclaje modelo, VistaPlanta vista) {
        this.modelo = modelo;
        this.vista = vista;

        cargarEstado();
        actualizarInterfaz();

        this.vista.getBtnSimularEntrada().addActionListener(e -> simularEntradaResiduo());
        this.vista.getBtnProcesar().addActionListener(e -> procesarResiduo());
        this.vista.getBtnVaciar().addActionListener(e -> vaciarContenedores());

        this.vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarEstado();
            }
        });
    }

    /**
     * Genera un residuo nuevo y lo pone en la cinta transportadora.
     */
    private void simularEntradaResiduo() {
        Residuo nuevo = modelo.agregarResiduoACinta();
        vista.agregarAlerta("ENTRADA: Generado " + nuevo.getTipo() + " [ID: " + nuevo.getID() + "] - Peso: " + nuevo.getPeso() + " kg.");
        actualizarInterfaz();
    }

    /**
     * Coge el residuo de la cinta y lo mete en su contenedor correspondiente.
     */
    private void procesarResiduo() {
        if (modelo.getCintaTransportadora().isEmpty()) {
            vista.agregarAlerta("La cinta está vacía. No hay nada que clasificar.");
            return;
        }

        Residuo residuo = modelo.getCintaTransportadora().get(0);

        if (!residuo.esReciclable()) {
            modelo.getCintaTransportadora().remove(0);
            vista.agregarAlerta("RECHAZADO: El residuo " + residuo.getTipo() + " [ID: " + residuo.getID() + "] no es reciclable (Tóxico).");
            actualizarInterfaz();
            return;
        }

        Contenedor contenedorDestino = null;
        for (Contenedor c : modelo.getContenedores()) {
            if (c.getTipoMaterial().equals(residuo.getTipo())) {
                contenedorDestino = c;
                break;
            }
        }

        if (contenedorDestino != null) {
            boolean insertadoConExito = contenedorDestino.agregarResiduo(residuo);
            if (insertadoConExito) {
                modelo.getCintaTransportadora().remove(0);
                vista.agregarAlerta("CLASIFICADO: " + residuo.getTipo() + " [ID: " + residuo.getID() + "] enviado a su depósito.");

                registrarEnLog(residuo);
            } else {
                vista.agregarAlerta("¡ALERTA! El contenedor de " + residuo.getTipo() + " está lleno. ¡Vacíalo!");
            }
        }
        actualizarInterfaz();
    }

    /**
     * Vacia todos los contenedores de la planta.
     */
    private void vaciarContenedores() {
        for (Contenedor c : modelo.getContenedores()) {
            c.vaciarContenedor();
        }
        vista.agregarAlerta("SISTEMA: Todos los depósitos han sido vaciados por el operario.");
        actualizarInterfaz();
    }

    /**
     * Actualiza el texto de la cinta y las barras de progreso en la ventana.
     */
    private void actualizarInterfaz() {
        StringBuilder sb = new StringBuilder();
        if (modelo.getCintaTransportadora().isEmpty()) {
            sb.append("Cinta libre / Vacía");
        } else {
            for (Residuo r : modelo.getCintaTransportadora()) {
                sb.append("[").append(r.getTipo()).append(" | ").append(r.getPeso()).append("kg] ➔ ");
            }
            sb.append("FIN");
        }
        vista.getTxtCinta().setText(sb.toString());

        for (Contenedor c : modelo.getContenedores()) {
            JProgressBar barra = vista.getBarrasContenedores().get(c.getTipoMaterial());
            if (barra != null) {
                int porcentaje = (int) c.getPorcentajeLlenado();
                barra.setValue(porcentaje);
                barra.setString(String.format("%.1f / %.1f kg (%d%%)", c.getLlenadoActual(), c.getCapacidadMaxima(), porcentaje));
            }
        }
    }

    /**
     * Escribe la informacion del residuo procesado en un archivo de texto de registro.
     * @param r El residuo que se acaba de clasificar con exito.
     */
    private void registrarEnLog(Residuo r) {
        try (FileWriter fw = new FileWriter("recycle.log", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            out.printf("[%s] ID: %s | Tipo: %s | Peso: %.2f kg\n",
                    ahora.format(formatter), r.getID(), r.getTipo(), r.getPeso());

        } catch (IOException e) {
            System.err.println("Error crítico al escribir en recycle.log: " + e.getMessage());
        }
    }

    /**
     * Guarda el peso actual de los contenedores en un archivo JSON.
     */
    private void guardarEstado() {
        try (FileWriter fw = new FileWriter("estado_planta.json");
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write("{\n");
            for (int i = 0; i < modelo.getContenedores().size(); i++) {
                Contenedor c = modelo.getContenedores().get(i);
                bw.write("  \"" + c.getTipoMaterial() + "\": " + c.getLlenadoActual());
                if (i < modelo.getContenedores().size() - 1) {
                    bw.write(",\n");
                } else {
                    bw.write("\n");
                }
            }
            bw.write("}");

        } catch (IOException e) {
            System.err.println("Error crítico al guardar estado_planta.json: " + e.getMessage());
        }
    }

    /**
     * Carga los datos de los contenedores desde el archivo JSON si existe.
     */
    private void cargarEstado() {
        File archivo = new File("estado_planta.json");
        if (!archivo.exists()) return;

        try (FileReader fr = new FileReader(archivo);
             BufferedReader br = new BufferedReader(fr)) {

            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.contains(":") && !linea.contains("{") && !linea.contains("}")) {
                    String[] partes = linea.split(":");
                    String material = partes[0].replace("\"", "").trim();
                    double llenado = Double.parseDouble(partes[1].replace(",", "").trim());

                    for (Contenedor c : modelo.getContenedores()) {
                        if (c.getTipoMaterial().equals(material)) {
                            c.setLlenadoActual(llenado);
                        }
                    }
                }
            }
            vista.agregarAlerta("SISTEMA: Estado anterior cargado de forma exitosa desde estado_planta.json.");
        } catch (Exception e) {
            System.err.println("Error al leer archivo de restauración estado_planta.json: " + e.getMessage());
        }
    }
}