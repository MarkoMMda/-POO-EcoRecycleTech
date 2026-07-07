package modelo;

public class ResiduoPapel extends Residuo {
    public ResiduoPapel(String id, double peso, boolean esToxico) {
        super(id, peso, esToxico);
    }

    @Override
    public String getTipo() {
        return "Papel/Cartón";
    }

    @Override
    public boolean esReciclable() {
        return !this.esToxico;
    }
}