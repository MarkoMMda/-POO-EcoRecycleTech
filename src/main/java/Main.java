import modelo.PlantaReciclaje;
import vista.VistaPlanta;
import controlador.ControladorPlanta;

import javax.swing.SwingUtilities;

/**
 * Clase principal que arranca el programa de la planta de reciclaje.
 */
public class Main {
    /**
     * Metodo main que inicia la aplicacion y la interfaz grafica.
     * @param args Argumentos de consola.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PlantaReciclaje modelo = new PlantaReciclaje();

            VistaPlanta vista = new VistaPlanta();

            new ControladorPlanta(modelo, vista);

            vista.setVisible(true);
        });
    }
}