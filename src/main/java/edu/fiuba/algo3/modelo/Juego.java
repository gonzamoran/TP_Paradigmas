package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;

import java.util.List;

public class Juego {
    private List<Jugador> jugadores;
    private Tablero tablero;
    private final Dados dados;
    private Mazo mazo;
    private Jugador jugadorActual;

    public Juego(){
        this.tablero = new Tablero();
        this.dados = new Dados();
        this.mazo = new Mazo();
        this.jugadores = List.of();
    }

    public void lanzarDados(){
        // return dados.lanzar();

    }

    public void construirEnCoordenada(int x, int y) {
        // transformar la coordenada de String a Coordenadas
    }
    
    // cada jugador coloca 1 poblado y 1 camino en orden, luego en orden inverso
    public void colocacionInicial(){
        for(Jugador jugador : jugadores){
            // pedir coordenada al jugador
            // colocarConstruccionInicial(coordenada);
            // pedir coordenada al jugador
            // colocar caminoInicial(coordenada);
        }
        for(int i = jugadores.size() -1; i >=0 ; i--){
            Jugador jugador = jugadores.get(i);
            // pedir coordenada al jugador
            // colocarConstruccionInicial(coordenada);
            // pedir coordenada al jugador
            // colocar caminoInicial(coordenada);

        }
    }
    
    public void colocarConstruccionInicial(int x, int y, Jugador jugador) {
        tablero.colocarConstruccionInicial(x, y, this.jugadorActual);
        
    }
}
