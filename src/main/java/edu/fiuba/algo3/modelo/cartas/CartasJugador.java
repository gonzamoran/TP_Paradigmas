package edu.fiuba.algo3.modelo.cartas;

import edu.fiuba.algo3.modelo.ProveedorDeDatos;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.ContextoCartaDesarrollo;
import edu.fiuba.algo3.modelo.excepciones.NoSePuedeJugarEstaCartaException;
import edu.fiuba.algo3.modelo.tiposRecurso.Grano;
import edu.fiuba.algo3.modelo.tiposRecurso.Lana;
import edu.fiuba.algo3.modelo.tiposRecurso.Piedra;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CartasJugador {

    private ArrayList<CartasDesarrollo> cartasDesarrollo;
    private List<Recurso> recursos;
    private boolean tieneLaGranCaballeria = false; // hacerlo de otra forma
    private boolean tieneLaGranRutaComercial = false;

    public CartasJugador() {
        this.cartasDesarrollo = new ArrayList<>();
        this.recursos = new ArrayList<>();
    }

    /*
     * Metodo que busca un recurso en la lista de recursos.
     */
    private Recurso buscarRecurso(Recurso recursoBuscado) {
        for (Recurso recurso : this.recursos) {
            if (recurso.getClass().equals(recursoBuscado.getClass())) {
                return recurso;
            }
        }
        return null;
    }

    public void agregarRecursos(Recurso recurso) {
        if (recurso == null) {
            return;
        }
        var agregado = false;
        for (Recurso recursoActual : this.recursos) {
            if (recursoActual.getClass().equals(recurso.getClass())) {
                recursoActual.sumar(recurso);
                agregado = true;
                break;
            }
        }
        if (!agregado) {
            this.recursos.add(recurso);
        }
    }

    public void agregarCartaDesarrollo(CartasDesarrollo carta) {
        this.cartasDesarrollo.add(carta);
    }

    public int obtenerCantidadCartasRecurso(Recurso recurso) {
        Recurso actual = this.buscarRecurso(recurso);
        if (actual != null) {
            return actual.obtenerCantidad();
        }
        return 0;
    }

    public int cantidadTotalCartasRecurso() {
        int total = 0;
        for (Recurso recurso : this.recursos) {
            total += recurso.obtenerCantidad();
        }
        return total;
    }

    public Recurso conseguirRecursoAleatorio() {

        int total = this.cantidadTotalCartasRecurso();

        if (total == 0) {
            return null;
        }

        int indice = new Random().nextInt(total);

        for (Recurso recurso : recursos) {
            int cant = recurso.obtenerCantidad();

            if (indice < cant) {
                return recurso;
            }
            indice -= cant;
        }
        return null;
    }

    public Recurso removerRecursoAleatorio() {
        Recurso recurso = conseguirRecursoAleatorio();
        recurso.restar(1);
        Recurso robado = recurso.obtenerCopia(1);
        return robado;
    }

    // Solo se llama cuando el jugador tiene 7 cartas o más.
    public ArrayList<Recurso> descarteCartas() {
        ArrayList<Recurso> descarte = new ArrayList<>();
        int cantCartasDescarte = this.cantidadTotalCartasRecurso() / 2;
        for (int i = 0; i < cantCartasDescarte; i++) {
            Recurso recurso = conseguirRecursoAleatorio();
            if (recurso == null) {
                break;
            }

            Recurso cartaDescartada = recurso.obtenerCopia(1);


            descarte.add(cartaDescartada);

            recurso.restar(1);
        }
        return descarte;
    }

    public boolean puedeDescartarse() {
        return this.cantidadTotalCartasRecurso() > 7;
    }

    public void removerRecurso(Recurso recurso) {
        Recurso actual = this.buscarRecurso(recurso);
        if (actual != null) {
            actual.restar(recurso.obtenerCantidad());
        }
    }

    public boolean poseeRecursosParaCartaDesarrollo() {
        var costoCartaDesarrollo = new ArrayList<Recurso>(List.of(new Lana(1), new Piedra(1), new Grano(1)));

        for (Recurso requisito : costoCartaDesarrollo) {
            Recurso recursoJugador = this.buscarRecurso(requisito);
            if (recursoJugador == null || recursoJugador.obtenerCantidad() < requisito.obtenerCantidad()) {
                return false;
            }
        }
        return true;
    }

    public void pagarCartaDesarrollo() {
        var costoCartaDesarrollo = new ArrayList<Recurso>(List.of(new Lana(1), new Piedra(1), new Grano(1)));

        for (Recurso requisito : costoCartaDesarrollo) {
            Recurso recursoJugador = this.buscarRecurso(requisito);
            recursoJugador.restar(requisito.obtenerCantidad());
        }

    }

    public Recurso vaciarRecurso(Recurso recurso) {
        Recurso actual = this.buscarRecurso(recurso);
        if (actual == null) {
            return null;
        }
        int cantidad = actual.obtenerCantidad();
        actual.restar(cantidad);
        return recurso.obtenerCopia(cantidad);
    }

    public void usarCartaDesarrollo(CartasDesarrollo carta, ContextoCartaDesarrollo contexto,
            ProveedorDeDatos proveedor) {
        if (this.cartasDesarrollo.isEmpty() || !cartasDesarrollo.contains(carta)) {
            throw new NoSePuedeJugarEstaCartaException();
        }
        CartasDesarrollo cartaAUsar = null;
        for (CartasDesarrollo cartaDesarrollo : cartasDesarrollo) {
            if (cartaDesarrollo.equals(carta)) {
                cartaAUsar = cartaDesarrollo;
                break;
            }
        }

        if (!cartaAUsar.esJugable(contexto)) {
            throw new NoSePuedeJugarEstaCartaException();
        }
        cartasDesarrollo.remove(cartaAUsar);
        cartaAUsar.usar(contexto, proveedor);
    }

    public int obtenerPVdeCartas() {
        int cartasConPuntos = 0;
        for (CartasDesarrollo carta : cartasDesarrollo) {
            cartasConPuntos += carta.conseguirPV();
        }
        if (this.tieneLaGranCaballeria) {
            cartasConPuntos += 2;
        }
        if (this.tieneLaGranRutaComercial){
            cartasConPuntos += 2;
        }
        return cartasConPuntos;
    }

    public int obtenerCantidadCartasDesarrollo() {
        return cartasDesarrollo.size();
    }

    public ArrayList<CartasDesarrollo> obtenerCartasDesarrollo() {
        return this.cartasDesarrollo;
    }

    public void otorgarGranCaballeria() {
        this.tieneLaGranCaballeria = true;
    }

    public void pierdeGranCaballeria() {
        this.tieneLaGranCaballeria = false;
    }

    public void otorgarGranRutaComercial(){
        this.tieneLaGranRutaComercial = true;
    }

    public void quitarCartaGranRutaComercial(){
        this.tieneLaGranRutaComercial = false;
    }
}