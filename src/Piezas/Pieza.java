package piezas;

public abstract class Pieza {

    protected boolean blanca;
    protected String simbolo;

    public Pieza(boolean blanca, String simbolo) {
        this.blanca = blanca;
        this.simbolo = simbolo;
    }

    public boolean esBlanca() {
        return blanca;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public abstract boolean movimientoValido(int filaOrigen, int colOrigen,
                                             int filaDestino, int colDestino);
}