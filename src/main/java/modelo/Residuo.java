package modelo;

/**
 * Clase base para todos los residuos de la planta.
 */
public abstract class Residuo implements IResiduo {
    /** ID del residuo */
    protected String id;
    /** Peso en kilos */
    protected double peso;
    /** Si es tóxico o no */
    protected boolean esToxico;

    /**
     * Constructor para guardar los datos básicos del residuo.
     * @param id Código único.
     * @param peso Peso en kg.
     * @param esToxico true si es peligroso.
     */
    public Residuo(String id, double peso, boolean esToxico) {
        this.id = id;
        this.peso = peso;
        this.esToxico = esToxico;
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public double getPeso() {
        return this.peso;
    }

    /**
     * Comprueba si el residuo se puede reciclar o no.
     * @return true si es reciclable, false si se descarta.
     */
    public abstract boolean esReciclable();
}