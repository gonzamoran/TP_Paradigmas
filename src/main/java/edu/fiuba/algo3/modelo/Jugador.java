package edu.fiuba.algo3.modelo;

import java.util.*;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.excepciones.IntercambioInvalidoException;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.construcciones.Construccion;
import edu.fiuba.algo3.modelo.cartas.CartasJugador;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.ContextoCartaDesarrollo;
import edu.fiuba.algo3.modelo.excepciones.RecursosInsuficientesException;

public class Jugador {
    private final String color;
    private CartasJugador mazos;
    private ArrayList<Construccion> construccionesJugador;
    // private List<CartaDesarrollo> cartasDeDesarrollo;
    private int puntosDeVictoria;
    private int caballerosJugador;

    public Jugador(String color) {
        this.color = color;
        // this.cartasDeDesarrollo = new List<CartaDesarrollo>();
        this.puntosDeVictoria = 0;
        this.caballerosJugador = 0;
        this.construccionesJugador = new ArrayList<>();
        this.mazos = new CartasJugador();
    }

    public void agregarRecurso(Recurso recurso) {
        mazos.agregarRecursos(recurso);
    }

    public Recurso removerRecursoAleatorio() {
        return mazos.removerRecursoAleatorio();
    }

    public void intercambiar(ArrayList<Recurso> recursosAEntregar, ArrayList<Recurso> recursosARecibir,
            Jugador jugador2) {
        if (!this.poseeRecursosParaIntercambiar(recursosAEntregar)
                || !jugador2.poseeRecursosParaIntercambiar(recursosARecibir)) {
            throw new IntercambioInvalidoException();
        }
        for (Recurso recurso : recursosAEntregar) {
            this.removerRecurso(recurso);
            jugador2.agregarRecurso(recurso);
        }
        for (Recurso recurso : recursosARecibir) {
            jugador2.removerRecurso(recurso);
            this.agregarRecurso(recurso);
        }
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

    public void sumarCaballero() {
        this.caballerosJugador += 1;
    }

    public int obtenerCantidadCaballerosUsados(){
        return this.caballerosJugador;
    }


    public int calculoPuntosVictoria() {
        int sumaTotal = 0;
        for (Construccion c : construccionesJugador) {
            sumaTotal += c.obtenerPuntosDeVictoria();
        }
        sumaTotal += mazos.obtenerPVdeCartas();
        this.puntosDeVictoria = sumaTotal;
        return puntosDeVictoria;
    }

    public boolean puedeDescartarse() {
        return mazos.puedeDescartarse();
    }

    public void descartarse() {
        if (!this.puedeDescartarse()) {
            return;
        }
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

    public boolean poseeRecursosParaIntercambiar(ArrayList<Recurso> recursosAEntregar) {
        for (Recurso recurso : recursosAEntregar) {
            if (this.obtenerCantidadRecurso(recurso) < recurso.obtenerCantidad()) {
                return false;
            }
        }
        return true;
    }

    public Recurso vaciarRecurso(Recurso recurso) {
        return mazos.vaciarRecurso(recurso);
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Jugador jugador = (Jugador) obj;
        return this.color == jugador.color;
    }

    public int contarCartasDeDesarrollo() {
        return mazos.obtenerCantidadCartasDesarrollo();
    }

    public void comprarCartaDesarrollo(CartasDesarrollo carta, int turnoActual) {
        if (!mazos.poseeRecursosParaCartaDesarrollo()) {
            throw new RecursosInsuficientesException();
        }
        mazos.pagarCartaDesarrollo();
        CartasDesarrollo cartaComprada = carta.comprarCarta(turnoActual);
        mazos.agregarCartaDesarrollo(cartaComprada);
    }

    public void usarCartaDesarrollo(CartasDesarrollo carta, ContextoCartaDesarrollo contexto, ProveedorDeDatos proveedor) {
        mazos.usarCartaDesarrollo(carta, contexto, proveedor);
    }

    public void otorgarGranCaballeria(ArrayList<Jugador> jugadores) {
        for (Jugador jugador : jugadores) {
            if (jugador != this) {
                jugador.pierdeGranCaballeria();
            }
        }
        mazos.otorgarGranCaballeria();
    }
    public void pierdeGranCaballeria(){
        mazos.pierdeGranCaballeria();
    }
    
    // public void otorgarGranRutaComercial(){
    //     mazos.otorgarGranRutaComercial();
    // }
    // public ArrayList<CartasDesarrollo> obtenerCartasDesarrollo(){
    //     return mazos.obtenerCartasDesarrollo();
    // }
}