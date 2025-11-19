package edu.fiuba.algo3.modelo;
import java.util.*;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;

import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.tiposRecurso.*;

public class Jugador {
    //private final Color color; 
    private final String nombre;
    private int PUNTOS_VICTORIA_INICIALES = 0;
    private Map<Recurso, Integer> recursos;
    //private final List<CartaDesarrollo> cartasDeDesarrollo;
    //private Turno turno = new turnoJugador(); //podria manejarlo juego?
    private int puntosDeVictoria;
    
    public Jugador(String nombre){
        //this.color = color;
        this.nombre = nombre;
        this.recursos = new HashMap<>();    // <Recurso, Integer> || <"Madera", Integer> OJOOOO
        inicializarRecursos();
        //this.cartasDeDesarrollo = new List<CartaDesarrollo>();
        this.puntosDeVictoria = PUNTOS_VICTORIA_INICIALES;
    }
    
    private void inicializarRecursos(){
        recursos.put(new Madera("Madera", 0), 0);
        recursos.put(new Ladrillo("Ladrillo", 0), 0);
        recursos.put(new Grano("Grano", 0), 0);
        recursos.put(new Lana("Lana", 0), 0);
        recursos.put(new Piedra("Piedra", 0), 0);
    }

    // OJOOO chequear el Map
    public boolean puedeConstruirCarretera(){
        return recursos.get(Madera.class) >= 1 && recursos.get(Ladrillo.class) >=1;
    }

    // OJOOO chequear el Map
    public void construirCarretera(){
        if(puedeConstruirCarretera()){
            recursos.put(Madera.class, (recursos.get(Madera.class) - 1));
            recursos.put(Recurso.Ladrillo, recursos.get(Recurso.Ladrillo) - 1);
        }
    }

    public void agregarRecurso(Recurso recurso, int cantidad){
        recursos.put(recurso, recursos.get(recurso) + cantidad);
    }

    public int getCantidadRecurso(Recurso recurso){
        return recursos.get(recurso);
    }
    
    //public Color getColor(){
    //    return color;
    //}

    public String getNombre(){
        return nombre;
    }

    
}