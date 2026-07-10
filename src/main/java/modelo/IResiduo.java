package modelo;

/**
 * Interfaz con los métodos obligatorios para cualquier residuo.
 */
public interface IResiduo {
    /**
     * Devuelve el ID único del residuo.
     * @return El identificador.
     */
    String getID();
    /**
     * Devuelve el peso del residuo.
     * @return El peso en kg.
     */
    double getPeso();
    /**
     * Devuelve el tipo de material.
     * @return El nombre del material.
     */
    String getTipo();
}