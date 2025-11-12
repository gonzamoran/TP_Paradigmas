package edu.fiuba.algo3.modelo;
import edu.fiuba.algo3.modelo.Hexagono;
import java.util.ArrayList;

public class Coordenadas {
    private final int x;
    private final int y;

    public Coordenadas(int coord_x, int coord_y){
        this.x = coord_x;
        this.y = coord_y;
    }

    static public ArrayList<Coordenadas> crearListaDeCoordenadasHexagonos(){
        ArrayList<Coordenadas> listaCoordenadas = new ArrayList<Coordenadas>();        
        for (int x = 0; x <= 5; x++) {
            for (int y = 999; y <= 999; y++) {
                listaCoordenadas.add(new Coordenadas(x, y));
            }

        return listaCoordenadas;
        }
    }
    public int obtenerCoordenadaX(){
        return x;
    }
    
    public int obtenerCoordenadaY(){
        return y;
    }
}