package edu.fiuba.algo3.modelo;

import java.util.*;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.construcciones.Construccion;
import edu.fiuba.algo3.modelo.cartas.CartasJugador;

public class Jugador {
    private final String color;
    private CartasJugador mazos;
    private ArrayList<Construccion> construccionesJugador; 
    // private final List<CartaDesarrollo> cartasDeDesarrollo;
    private int puntosDeVictoria;

    public Jugador(String color) {
        this.color = color;
        // this.cartasDeDesarrollo = new List<CartaDesarrollo>();
        this.puntosDeVictoria = 0;
        this.construccionesJugador = new ArrayList<>();
        this.mazos = new CartasJugador();
    }

    public void agregarRecurso(Recurso recurso) {
        mazos.agregarRecursos(recurso);
    }

    public Recurso removerRecursoAleatorio() {
        return mazos.removerRecursoAleatorio();
    }

    public void removerRecurso(Recurso recurso) {
        mazos.removerRecurso(recurso);
    }

    public int obtenerCantidadRecurso(Recurso recurso) {
        return mazos.obtenerCantidadCartasRecurso(recurso);
    }

    public int obtenerCantidadCartasRecurso() {
        return mazos.cantidadTotalCartasRecurso();
    }

    public String getColor() {
        return this.color;
    }

    public void agregarConstruccion(Construccion construccion) {
        construccionesJugador.add(construccion);
    }

    public void removerConstruccion(Construccion construccion) {
        construccionesJugador.remove(construccion);
    }

    public int calculoPuntosVictoria(){
        int sumaTotal = 0;
        for (Construccion c : construccionesJugador) {
            sumaTotal += c.obtenerPuntosDeVictoria();
        }
        this.puntosDeVictoria = sumaTotal;
        return puntosDeVictoria;
    }

    public boolean puedeDescartarse() {
        return mazos.puedeDescartarse();
    }

    public void descartarse(){
        mazos.descarteCartas();
    }

    public boolean tieneRecursos() {
        return this.obtenerCantidadCartasRecurso() > 0;
    }

    public boolean poseeRecursosParaConstruir(Construccion construccion) {
        var recursosNecesarios = construccion.obtenerRecursosNecesarios();
        for (Recurso recurso : recursosNecesarios) {
            if (this.obtenerCantidadRecurso(recurso) < recurso.obtenerCantidad()) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Jugador jugador = (Jugador) obj;
        return this.color == jugador.color;
    }
    // OJOOO chequear el Map
    // public boolean puedeConstruirCarretera() {
    //     return recursos.get(Madera.class) >= 1 && recursos.get(Ladrillo.class) >= 1;
    // }

    // // OJOOO chequear el Map
    // public void construirCarretera() {
    //     if (puedeConstruirCarretera()) {
    //         recursos.put(Madera.class, (recursos.get(Madera.class) - 1));
    //         recursos.put(Recurso.Ladrillo, recursos.get(Recurso.Ladrillo) - 1);
    //     }
    // }

}