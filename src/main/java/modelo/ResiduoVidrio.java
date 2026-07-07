package modelo;

public class ResiduoVidrio extends Residuo {
    public ResiduoVidrio(String id, double peso, boolean esToxico) {
        super(id, peso, esToxico);
    }

    @Override
    public String getTipo() {
        return "Vidrio";
    }

    @Override
    public boolean esReciclable() {
        return true;
    }
}