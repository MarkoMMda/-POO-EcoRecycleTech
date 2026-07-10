package modelo;

import java.util.ArrayList;
import java.util.List;

public class PlantaReciclaje {

    private List<Residuo> cintaTransportadora;

    private List<Contenedor> contenedores;

    public PlantaReciclaje() {
        this.cintaTransportadora = new ArrayList<>();
        this.contenedores = new ArrayList<>();

        contenedores.add(new Contenedor("Plástico", 50.0));
        contenedores.add(new Contenedor("Vidrio", 50.0));
        contenedores.add(new Contenedor("Papel/Cartón", 50.0));
        contenedores.add(new Contenedor("Orgánico", 50.0));
    }

    public Residuo agregarResiduoACinta() {
        Residuo nuevoResiduo = ResiduoFactory.generarResiduoAleatorio();
        cintaTransportadora.add(nuevoResiduo);
        return nuevoResiduo;
    }

    public String procesarSiguienteResiduo() {
        if (cintaTransportadora.isEmpty()) {
            return "La cinta está vacía.";
        }

        Residuo residuoAProcesar = cintaTransportadora.remove(0);

        if (!residuoAProcesar.esReciclable()) {
            return "Residuo [" + residuoAProcesar.getID() + "] (" + residuoAProcesar.getTipo() + ") RECHAZADO por toxicidad.";
        }

        for (Contenedor c : contenedores) {
            if (c.getTipoMaterial().equals(residuoAProcesar.getTipo())) {
                boolean exito = c.agregarResiduo(residuoAProcesar);
                if (exito) {
                    return "Residuo [" + residuoAProcesar.getID() + "] (" + residuoAProcesar.getTipo() + ") procesado correctamente.";
                } else {
                    cintaTransportadora.add(0, residuoAProcesar);
                    return "¡ALERTA! El contenedor de " + c.getTipoMaterial() + " está LLENO.";
                }
            }
        }
        return "Error: No se encontró contenedor para " + residuoAProcesar.getTipo();
    }

    public List<Residuo> getCintaTransportadora() {
        return cintaTransportadora;
    }

    public List<Contenedor> getContenedores() {
        return contenedores;
    }
}