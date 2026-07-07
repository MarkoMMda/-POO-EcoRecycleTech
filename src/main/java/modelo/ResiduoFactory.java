package modelo;

import java.util.Random;
import java.util.UUID;

/**
 * Clase para fabricar residuos aleatorios.
 */
public class ResiduoFactory {
    private static final Random random = new Random();

    /**
     * Crea un residuo al azar con peso, ID y tipo.
     * @return Un residuo nuevo.
     */
    public static Residuo generarResiduoAleatorio() {
        String idAleatorio = UUID.randomUUID().toString().substring(0, 8);
        double pesoAleatorio = 0.1 + (4.9 * random.nextDouble());
        pesoAleatorio = Math.round(pesoAleatorio * 100.0) / 100.0;
        boolean esToxicoAleatorio = random.nextDouble() < 0.20;
        int tipo = random.nextInt(4);
        switch (tipo) {
            case 0:
                return new ResiduoPlastico(idAleatorio, pesoAleatorio, esToxicoAleatorio);
            case 1:
                return new ResiduoVidrio(idAleatorio, pesoAleatorio, esToxicoAleatorio);
            case 2:
                return new ResiduoPapel(idAleatorio, pesoAleatorio, esToxicoAleatorio);
            case 3:
                return new ResiduoOrganico(idAleatorio, pesoAleatorio, esToxicoAleatorio);
            default:
                return new ResiduoPapel(idAleatorio, pesoAleatorio, esToxicoAleatorio);
        }
    }
}