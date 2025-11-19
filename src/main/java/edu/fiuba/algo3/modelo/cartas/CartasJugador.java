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
import java.util.Map;
import java.util.Random;

public class CartasJugador {

    private Map<Recurso, Integer> recursos;
    // private List<cartasDeDesarrollo> desarrollo;

    // Crear constructor.
    public CartasJugador() {
        inicializarRecursos();
    }

    public void inicializarRecursos() {
        recursos.put(new Madera("Madera", 0), 0);
        recursos.put(new Ladrillo("Ladrillo", 0), 0);
        recursos.put(new Grano("Grano", 0), 0);
        recursos.put(new Lana("Lana", 0), 0);
        recursos.put(new Piedra("Piedra", 0), 0);
    }

    public void agregarRecurso(Recurso recurso, int cantidad) {
        recursos.put(recurso, obtenerCantidadCartasRecurso(recurso) + cantidad);
    }

    public int obtenerCantidadCartasRecurso(Recurso recurso) {
        return recursos.get(recurso);
    }

    public int cantidadTotalCartasRecurso() {
        int cantidadCartas = 0;
        for (Recurso rec : recursos.keySet()) {
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
    public ArrayList<CartasRecurso> descarteCartas() {
        ArrayList<CartasRecurso> descarte = new ArrayList<>();
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
}
