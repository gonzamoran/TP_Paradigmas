package edu.fiuba.algo3.modelo.acciones;

import java.util.ArrayList;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.construcciones.Construccion;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;

public class CasoDeUsoColocacionInicial {
    private Tablero tablero;

    public CasoDeUsoColocacionInicial(Tablero tablero) {
        this.tablero = tablero;
    }

    public ArrayList<Recurso> colocarConstruccionInicial(Coordenadas coordenadas, Construccion construccion, Jugador jugador) {
        var recursos = tablero.colocarConstruccionInicial(construccion, coordenadas, jugador);
        return recursos;
    }

    public void colocarCarreteraInicial(Coordenadas coordenada1, Coordenadas coordenada2, Jugador jugador) {

        tablero.construirCarreteraGratis(coordenada1, coordenada2, jugador);
    }
}
