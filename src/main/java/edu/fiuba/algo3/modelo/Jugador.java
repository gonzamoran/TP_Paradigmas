package edu.fiuba.algo3.modelo;
import java.util.*;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;

public class Jugador {
    //private final Color color; 
    private final String nombre;
    private int PUNTOS_VICTORIA_INICIALES = 0;
    private final Dictionary <Recurso, Integer> recursos;
    private final List<CartaDesarrollo> cartasDeDesarrollo;
    private Turno turno = new turnoJugador(); //podria manejarlo juego?
    private int puntosDeVictoria;
    
    public Jugador(String nombre){
        //this.color = color;
        this.nombre = nombre;
        this.recursos = new Dictionary<Recurso, Integer>();
        this.cartasDeDesarrollo = new List<CartaDesarrollo>();
        this.puntosDeVictoria = PUNTOS_VICTORIA_INICIALES;
    }
    
    //public Color getColor(){
    //    return color;
    //}


    public String getNombre(){
        return nombre;
    }

    
}