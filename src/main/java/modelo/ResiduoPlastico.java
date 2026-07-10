package modelo;

public class ResiduoPlastico extends Residuo {
    public ResiduoPlastico(String id, double peso, boolean esToxico) {
        super(id, peso, esToxico);
    }

    @Override
    public String getTipo() {
        return "Plástico";
    }

    @Override
    public boolean esReciclable() {
        return !this.esToxico;
    }
}