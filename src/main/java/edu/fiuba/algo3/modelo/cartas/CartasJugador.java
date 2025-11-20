package edu.fiuba.algo3.modelo.cartas;

import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tiposRecurso.Grano;
import edu.fiuba.algo3.modelo.tiposRecurso.Ladrillo;
import edu.fiuba.algo3.modelo.tiposRecurso.Lana;
import edu.fiuba.algo3.modelo.tiposRecurso.Madera;
import edu.fiuba.algo3.modelo.tiposRecurso.Piedra;

import java.util.HashMap;
import java.util.List;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Random;
import java.util.Map;

public class CartasJugador {

    private Map<Class<? extends Recurso>, Recurso> recursos;

    // private List<cartasDeDesarrollo> desarrollo;

    // Crear constructor.
    public CartasJugador() {
        this.recursos = new HashMap<Class<? extends Recurso>, Recurso>();
        inicializarRecursos();
    }

    public void inicializarRecursos() {
        recursos.put(Madera.class, new Madera("Madera", 0));
        recursos.put(Ladrillo.class, new Ladrillo("Ladrillo", 0));
        recursos.put(Grano.class, new Grano("Grano", 0));
        recursos.put(Lana.class, new Lana("Lana", 0));
        recursos.put(Piedra.class, new Piedra("Piedra", 0));
    }

    public void agregarRecursos(Recurso recurso) {
        Recurso actual = this.recursos.get(recurso.getClass());
        actual.sumar(recurso);
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

    /*
     * Metodo a revisar, no se que tan bien esta esto
     */
    public Map.Entry<Class<? extends Recurso>, Recurso> conseguirRecursoAleatorio() {
        if (recursos.isEmpty()) {
            return null;
        }
        List<Map.Entry<Class<? extends Recurso>, Recurso>> entries = new ArrayList<>(recursos.entrySet());
        Random random = new Random();
        for (int i = 0; i < entries.size(); i++) {
            var entrada = entries.get(random.nextInt(entries.size()));
            Recurso recurso = (Recurso) entrada.getValue();
            if (recurso.obtenerCantidad() > 0){
                return entrada;
            }
        }
        return null;
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