package edu.fiuba.algo3.modelo.cartas;

import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tiposRecurso.Grano;
import edu.fiuba.algo3.modelo.tiposRecurso.Ladrillo;
import edu.fiuba.algo3.modelo.tiposRecurso.Lana;
import edu.fiuba.algo3.modelo.tiposRecurso.Madera;
import edu.fiuba.algo3.modelo.tiposRecurso.Piedra;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartaPuntoVictoria;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;

import java.util.HashMap;
import java.util.List;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Random;
import java.util.Map;
import java.util.Collections;

public class CartasJugador {

    private List<CartasDesarrollo> cartasDesarrollo;
    private Map<Class<? extends Recurso>, Recurso> recursos;


    // Crear constructor.
    public CartasJugador() {
        this.recursos = new HashMap<Class<? extends Recurso>, Recurso>();
        inicializarRecursos();
        this.cartasDesarrollo = new ArrayList<>();
    }

    public void inicializarRecursos() {
        recursos.put(Madera.class, new Madera(0));
        recursos.put(Ladrillo.class, new Ladrillo(0));
        recursos.put(Grano.class, new Grano(0));
        recursos.put(Lana.class, new Lana(0));
        recursos.put(Piedra.class, new Piedra(0));
    }

    public void agregarRecursos(Recurso recurso) {
        Recurso actual = this.recursos.get(recurso.getClass());
        actual.sumar(recurso);
    }

    public void agregarCartaDesarrollo(CartasDesarrollo carta){
        this.cartasDesarrollo.add(carta);
    }

    public int obtenerCantidadCartasRecurso(Recurso recurso) {
        Recurso actual = recursos.get(recurso.getClass());
        int cantidadactual = actual.obtenerCantidad();
        return cantidadactual;
    }

    public int cantidadTotalCartasRecurso() {
        int cantidadCartas = 0;
        for (Recurso rec : recursos.values()) {
            cantidadCartas = cantidadCartas + obtenerCantidadCartasRecurso(rec);
        }
        return cantidadCartas;
    }

    public Map.Entry<Class<? extends Recurso>, Recurso> conseguirRecursoAleatorio() {
        List<Map.Entry<Class<? extends Recurso>, Recurso>> entries = new ArrayList<>(recursos.entrySet());
        Random random = new Random();
        Collections.shuffle(entries, random);
        for (Map.Entry<Class<? extends Recurso>, Recurso> entrada : entries) {
            Recurso recurso = entrada.getValue();
            if (recurso.obtenerCantidad() > 0) {
                return entrada;
            }
        }
        return null;
    }

    public Recurso removerRecursoAleatorio() {
        Map.Entry<Class<? extends Recurso>, Recurso> entrada = conseguirRecursoAleatorio();
        Recurso recurso = entrada.getValue();
        recurso.restar(1);
        Recurso robado = recurso.obtenerCopia(1);
        return robado;
    }

    // Solo se llama cuando el jugador tiene 7 cartas o más.
    public ArrayList<Recurso> descarteCartas() {
        ArrayList<Recurso> descarte = new ArrayList<>();
        int cantCartasDescarte = (int) Math.floor(this.cantidadTotalCartasRecurso() / 2.0);
        for (int i = 0; i < cantCartasDescarte; i++) {
            Map.Entry<Class<? extends Recurso>, Recurso> entrada = conseguirRecursoAleatorio();
            if (entrada == null){
                break;
            }

            Recurso recurso = entrada.getValue();
            int cantidad = recurso.obtenerCantidad();

            // Despues hay que ver si lo ponemos como re
            descarte.add(recurso);

            recurso.restar(1);
        }
        return descarte; //podria devolver o no, por ahora no lo necesitamos
    }

    public boolean puedeDescartarse() {
        return this.cantidadTotalCartasRecurso() >= 7;
    }

    public void removerRecurso(Recurso recurso) {
        Recurso actual = this.recursos.get(recurso.getClass());
        actual.restar(recurso.obtenerCantidad());
    }

    public boolean poseeRecursosParaCartaDesarrollo(){
        return recursos.get(Lana.class).obtenerCantidad() >= 1 &&  recursos.get(Piedra.class).obtenerCantidad() >= 1 &&  recursos.get(Grano.class).obtenerCantidad() >= 1;
    }

    public void pagarCartaDesarrollo(){
        if (this.poseeRecursosParaCartaDesarrollo()){
            recursos.get(Lana.class).restar(1);
            recursos.get(Piedra.class).restar(1);
            recursos.get(Grano.class).restar(1);
        }
        else{
            throw new IllegalStateException("No cumple con los recursos necesarios");
        }
    }

    public List<CartasDesarrollo> obtenerCartasDesarrollos(){
        return cartasDesarrollo;
    }

    public int contarCartasDePuntosDeVictoria(){
        int cartasConPuntos = 0;
        for (CartasDesarrollo carta : cartasDesarrollo) {
            if (carta instanceof CartaPuntoVictoria){ //cambiar por un metodo bien hecho
                cartasConPuntos += 1;
            }
        }
        return cartasConPuntos;
    }

    public int obtenerCantidadCartasDesarrollo() {
        return cartasDesarrollo.size();
    }
    // verificar si un jugador puede construir. Hay que verlo.
    // public boolean tieneRecursos() {

    // }

    // Sacar recursos del jugador.
    // public void gastarRecurso(Recurso recurso, int cantidad);

    // {
    //     // for
    //     Recurso actual = recursos.getKey(recurso);
    //     int cantidadAGastar = entry.getValue();

    //     recursos.add(recurso, obtenerCantidadCartasRecurso(recurso) - cantidadAGastar);
    // }
}