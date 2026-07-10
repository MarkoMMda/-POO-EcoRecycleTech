package vista;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Ventana grafica de la aplicacion que muestra el estado de la planta.
 */
public class VistaPlanta extends JFrame {

    private JButton btnSimularEntrada;
    private JButton btnProcesar;
    private JButton btnVaciar;

    private JTextArea txtCinta;
    private JTextArea txtAlertas;

    private JProgressBar barraPlastico;
    private JProgressBar barraVidrio;
    private JProgressBar barraPapel;
    private JProgressBar barraOrganico;

    /**
     * Constructor que crea la ventana, los paneles y coloca los componentes.
     */
    public VistaPlanta() {
        setTitle("PRÁCTICA POO - Planta de Reciclaje EcoRecycle");
        setSize(750, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();
        btnSimularEntrada = new JButton("Generar Residuo");
        btnProcesar = new JButton("Clasificar Siguiente");
        btnVaciar = new JButton("Vaciar Todo");

        panelBotones.add(btnSimularEntrada);
        panelBotones.add(btnProcesar);
        panelBotones.add(btnVaciar);
        add(panelBotones, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridLayout(2, 1));

        JPanel panelCinta = new JPanel(new BorderLayout());
        panelCinta.add(new JLabel(" ESTADO DE LA CINTA TRANSPORTADORA"), BorderLayout.NORTH);
        txtCinta = new JTextArea(4, 30);
        txtCinta.setEditable(false);
        panelCinta.add(new JScrollPane(txtCinta), BorderLayout.CENTER);
        panelCentral.add(panelCinta);

        JPanel panelAlertas = new JPanel(new BorderLayout());
        panelAlertas.add(new JLabel(" LOG DE OPERACIONES Y ALERTAS"), BorderLayout.NORTH);
        txtAlertas = new JTextArea(6, 30);
        txtAlertas.setEditable(false);
        panelAlertas.add(new JScrollPane(txtAlertas), BorderLayout.CENTER);
        panelCentral.add(panelAlertas);

        add(panelCentral, BorderLayout.CENTER);

        JPanel panelContenedores = new JPanel(new GridLayout(4, 1));

        JPanel pPlastico = new JPanel();
        pPlastico.add(new JLabel("Contenedor Plástico: "));
        barraPlastico = new JProgressBar(0, 100);
        barraPlastico.setStringPainted(true);
        pPlastico.add(barraPlastico);
        panelContenedores.add(pPlastico);

        JPanel pVidrio = new JPanel();
        pVidrio.add(new JLabel("Contenedor Vidrio: "));
        barraVidrio = new JProgressBar(0, 100);
        barraVidrio.setStringPainted(true);
        pVidrio.add(barraVidrio);
        panelContenedores.add(pVidrio);

        JPanel pPapel = new JPanel();
        pPapel.add(new JLabel("Contenedor Papel: "));
        barraPapel = new JProgressBar(0, 100);
        barraPapel.setStringPainted(true);
        pPapel.add(barraPapel);
        panelContenedores.add(pPapel);

        JPanel pOrganico = new JPanel();
        pOrganico.add(new JLabel("Contenedor Orgánico: "));
        barraOrganico = new JProgressBar(0, 100);
        barraOrganico.setStringPainted(true);
        pOrganico.add(barraOrganico);
        panelContenedores.add(pOrganico);

        add(panelContenedores, BorderLayout.SOUTH);
    }

    /**
     * @return El boton de generar residuo.
     */
    public JButton getBtnSimularEntrada() { return btnSimularEntrada; }
    /**
     * @return El boton de clasificar el siguiente residuo.
     */
    public JButton getBtnProcesar() { return btnProcesar; }
    /**
     * @return El boton de vaciar los contenedores.
     */
    public JButton getBtnVaciar() { return btnVaciar; }

    /**
     * @return El cuadro de texto de la cinta.
     */
    public JTextArea getTxtCinta() { return txtCinta; }

    /**
     * Organiza las barras de progreso en un mapa para facilitar su actualizacion.
     * @return Mapa con las referencias de las barras por cada tipo.
     */
    public Map<String, JProgressBar> getBarrasContenedores() {
        Map<String, JProgressBar> mapa = new HashMap<>();
        mapa.put("Plástico", barraPlastico);
        mapa.put("Vidrio", barraVidrio);
        mapa.put("Papel/Cartón", barraPapel);
        mapa.put("Orgánico", barraOrganico);
        return mapa;
    }

    /**
     * Escribe un mensaje en el panel de texto de alertas del sistema.
     * @param mensaje Texto que se va a mostrar.
     */
    public void agregarAlerta(String mensaje) {
        txtAlertas.append(mensaje + "\n");
        txtAlertas.setCaretPosition(txtAlertas.getDocument().getLength());
    }
}