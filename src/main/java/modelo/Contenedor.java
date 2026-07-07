package modelo;

/**
 * Clase que representa un contenedor de la planta con un límite de kg.
 */
public class Contenedor {
    private String tipoMaterial;
    private double capacidadMaxima;
    private double llenadoActual;

    /**
     * Crea un contenedor vacío para un material.
     * @param tipoMaterial Tipo de residuo que acepta.
     * @param capacidadMaxima Límite de kg.
     */
    public Contenedor(String tipoMaterial, double capacidadMaxima) {
        this.tipoMaterial = tipoMaterial;
        this.capacidadMaxima = capacidadMaxima;
        this.llenadoActual = 0.0;
    }

    /**
     * Mete un residuo en el contenedor si es del mismo tipo y hay espacio.
     * @param residuo El residuo a meter.
     * @return true si se pudo guardar, false si no.
     */
    public boolean agregarResiduo(Residuo residuo) {
        if (residuo.getTipo().equals(this.tipoMaterial)) {
            if (this.llenadoActual + residuo.getPeso() <= this.capacidadMaxima) {
                this.llenadoActual += residuo.getPeso();
                return true;
            } else {
                System.out.println("El contenedor de " + tipoMaterial + " está lleno.");
                return false;
            }
        }
        return false;
    }

    /**
     * Pone el peso del contenedor a 0.
     */
    public void vaciarContenedor() {
        this.llenadoActual = 0.0;
    }

    /**
     * @return Tipo de material del contenedor.
     */
    public String getTipoMaterial() {
        return tipoMaterial;
    }

    /**
     * @return Kilos máximos permitidos.
     */
    public double getCapacidadMaxima() {
        return capacidadMaxima;
    }

    /**
     * @return Kilos que hay guardados ahora mismo.
     */
    public double getLlenadoActual() {
        return llenadoActual;
    }

    /**
     * Calcula el porcentaje de ocupación.
     * @return Un número del 0 al 100.
     */
    public double getPorcentajeLlenado() {
        return (this.llenadoActual / this.capacidadMaxima) * 100;
    }

    /**
     * Cambia el peso actual a mano para cargar datos.
     * @param llenadoActual Nuevo peso en kilos.
     */
    public void setLlenadoActual(double llenadoActual) {
        this.llenadoActual = llenadoActual;
    }
}