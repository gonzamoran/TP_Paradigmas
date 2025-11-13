package edu.fiuba.algo3.modelo;

import java.util.List;
import java.util.ArrayList;

public class Vertice {
    private Coordenadas coordenadas;
    private List<Hexagono> hexagonos;
    private Construccion construccion;

    public Vertice(int x, int y) {
        this.hexagonos = new ArrayList<>();
        this.coordenadas = new Coordenadas(x, y);
    }

    public Coordenadas obtenerCoordenadas() {
        return coordenadas;
    }

    public void agregarHexagono(Hexagono hexagono) {
        if (!hexagonos.contains(hexagono)) {
            hexagonos.add(hexagono);
        }
    }

    public List<Coordenadas> obtenerCoordenadasAdyacentes() {
        int x = this.coordenadas.obtenerCoordenadaX();
        int y = this.coordenadas.obtenerCoordenadaY();

        List<Coordenadas> coordsAdyacentes = new ArrayList<>();

        int suma = x + y;
        if (suma % 2 == 0) {
            coordsAdyacentes.add(new Coordenadas(x - 1, y));
            coordsAdyacentes.add(new Coordenadas(x, y - 1));
            coordsAdyacentes.add(new Coordenadas(x, y + 1));
        } else {
            coordsAdyacentes.add(new Coordenadas(x, y - 1));
            coordsAdyacentes.add(new Coordenadas(x, y + 1));
            coordsAdyacentes.add(new Coordenadas(x + 1, y));
        }

        return coordsAdyacentes;
    }
}
