package piezas;
public class Torre extends Pieza {

    public Torre(boolean blanca) {
        super(blanca, blanca ? "♖" : "♜");
    }

    @Override
    public boolean movimientoValido(int fo, int co, int fd, int cd) {

        return fo == fd || co == cd;

    }

}