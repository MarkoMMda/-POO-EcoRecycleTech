package modelo;

public class ResiduoOrganico extends Residuo {
    public ResiduoOrganico(String id, double peso, boolean esToxico) {
        super(id, peso, esToxico);
    }

    @Override
    public String getTipo() {
        return "Orgánico";
    }

    @Override
    public boolean esReciclable() {
        return !this.esToxico;
    }
}