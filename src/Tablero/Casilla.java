package tablero;
import Piezas.Pieza;
public class Casilla {

    private Pieza pieza;

    public Casilla() {
        pieza = null;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    public boolean estaVacia() {
        return pieza == null;
    }
}