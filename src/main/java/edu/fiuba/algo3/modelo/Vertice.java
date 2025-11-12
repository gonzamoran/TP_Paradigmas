package edu.fiuba.algo3.modelo;
import java.util.List;
import java.util.ArrayList;


public class Vertice {
    private Coordenadas coordenadas;
    private List<Hexagono> hexagonos;

    public Vertice(int x, int y){
        this.hexagonos = new ArrayList<>();
        this.coordenadas = new Coordenadas(x, y);
    }
    public Coordenadas obtenerCoordenadas(){
        return coordenadas;
    }

    public void agregarHexagono(Hexagono hexagono){
        if(!hexagonos.contains(hexagono)){
            hexagonos.add(hexagono);
        }
    }
}
