package edu.fiuba.algo3.modelo;

import java.lang.reflect.Array;
import java.util.*;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.excepciones.IntercambioInvalidoException;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.construcciones.Carretera;
import edu.fiuba.algo3.modelo.construcciones.Construccion;
import edu.fiuba.algo3.modelo.cartas.CartasJugador;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.ContextoCartaDesarrollo;
import edu.fiuba.algo3.modelo.excepciones.RecursosInsuficientesException;

public class Jugador {
    private final String color;
    private CartasJugador mazos;
    private ArrayList<Construccion> construccionesJugador;
    private int puntosDeVictoria;
    private int caballerosJugador;

    private int caminoMasLargo;

    public Jugador(String color) {
        this.color = color;
        this.puntosDeVictoria = 0;
        this.caballerosJugador = 0;
        this.construccionesJugador = new ArrayList<>();
        this.mazos = new CartasJugador();
    
        this.caminoMasLargo = 0;
    }

    /*
     * Metodos de manejo recursos y mazo de cartas en la mano.
     */
    public void agregarRecurso(Recurso recurso) {
        mazos.agregarRecursos(recurso);
    }

    public void removerRecurso(Recurso recurso) {
        mazos.removerRecurso(recurso);
    }

    public Recurso removerRecursoAleatorio() {
        return mazos.removerRecursoAleatorio();
    }

    public int obtenerCantidadRecurso(Recurso recurso) {
        return mazos.obtenerCantidadCartasRecurso(recurso);
    }

    public int obtenerCantidadCartasRecurso() {
        return mazos.cantidadTotalCartasRecurso();
    }

    public boolean tieneRecursos() {
        return this.obtenerCantidadCartasRecurso() > 0;
    }

    public boolean puedeDescartarse() {
        return mazos.puedeDescartarse();
    }

    public Recurso vaciarRecurso(Recurso recurso) {
        return mazos.vaciarRecurso(recurso);
    }

    public void descartarse() {
        if (!this.puedeDescartarse()) {
            return;
        }
        mazos.descarteCartas();
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


    /// Metodos de construccion.
    public void agregarConstruccion(Construccion construccion) {
        construccionesJugador.add(construccion);
    }

    public void removerConstruccion(Construccion construccion) {
        construccionesJugador.remove(construccion);
    }


    /// Metodos para las cartas de desarrollo

    public int calculoPuntosVictoria() {
        int sumaTotal = 0;
        for (Construccion c : construccionesJugador) {
            sumaTotal += c.obtenerPuntosDeVictoria();
        }
        sumaTotal += mazos.obtenerPVdeCartas();
        this.puntosDeVictoria = sumaTotal;
        return puntosDeVictoria;
    }

    public void comprarCartaDesarrollo(CartasDesarrollo carta, int turnoActual) {
        if (!mazos.poseeRecursosParaCartaDesarrollo()) {
            throw new RecursosInsuficientesException();
        }
        mazos.pagarCartaDesarrollo();
        CartasDesarrollo cartaComprada = carta.comprarCarta(turnoActual);
        mazos.agregarCartaDesarrollo(cartaComprada);
    }

    public int contarCartasDeDesarrollo() {
        return mazos.obtenerCantidadCartasDesarrollo();
    }

    public void usarCartaDesarrollo(CartasDesarrollo carta, ContextoCartaDesarrollo contexto, ProveedorDeDatos proveedor) {
        mazos.usarCartaDesarrollo(carta, contexto, proveedor);
    }

    public ArrayList<CartasDesarrollo> obtenerCartasDeDesarrollo() {
        return mazos.obtenerCartasDesarrollo();
    }
    /// Metodos para los caballeros y la gran caballeria.
    public int obtenerCantidadCaballerosUsados(){
        return this.caballerosJugador;
    }

    public void sumarCaballero() {
        this.caballerosJugador += 1;
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
    
    public int obtenerCaminoMasLargoDelJugador(){
        return this.caminoMasLargo;
    }

    public void actualizarCaminoMasLargoDelJugador(int valor){
        this.caminoMasLargo = valor;
    }

    public void otorgarGranRutaComercial(){
        mazos.otorgarGranRutaComercial();
    }
    public void quitarCartaGranRutaComercial(){
        mazos.quitarCartaGranRutaComercial();
    }

    ///  Metodos que verifican.
    /// Quizas esto vuela si agregamos el refactor de costo.
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

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Jugador jugador = (Jugador) obj;
        return this.color == jugador.color;
    }

}