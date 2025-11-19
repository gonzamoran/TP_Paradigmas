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
        actual.obtenerCantidad();
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
    public static <K, V> Map.Entry<K, V> getRandomEntry(Map<K, V> map) {
        if (map.isEmpty()) {
            return null;
        }
        List<Map.Entry<K, V>> entries = new ArrayList<>(map.entrySet());
        Random random = new Random();
        return entries.get(random.nextInt(entries.size()));
    }

    // Solo se llama cuando el jugador tien 8 cartas o más.
    public ArrayList<Recurso> descarteCartas() {
        ArrayList<Recurso> descarte = new ArrayList<>();
        int cantCartasDescarte = (this.cantidadTotalCartasRecurso() / 2);

        for (int i = 0; i < cantCartasDescarte; i++) {
            Map.Entry<Recurso, Integer> entry = getRandomEntry(recursos);

            Recurso tipo = entry.getKey();
            int cantidad = entry.getValue();

            // Despues hay que ver si lo ponemos como re
            descarte.add(tipo);

            recursos.put(tipo, cantidad - 1);
        }
        return descarte;
    }

    // verificar si un jugador puede construir. Hay que verlo.
    public boolean tieneRecursos() {

    }

    // Sacar recursos del jugador.
    public void gastarRecurso(Recurso recurso, int cantidad);

    {
        // for
        Recurso actual = recursos.getKey(recurso);
        int cantidadAGastar = entry.getValue();

        recursos.add(recurso, obtenerCantidadCartasRecurso(recurso) - cantidadAGastar);
    }
}