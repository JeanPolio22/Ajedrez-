package Piezas;

public class Peon extends Pieza{


    //Constructor.
    public Peon(boolean blanca,int fila,int columna){

        super(blanca,fila,columna);

    }


    //Devuelve el símbolo del peón.
    @Override
    public String getSimbolo(){

        if(esBlanca()){

            return "♙";

        }

        return "♟";

    }


}