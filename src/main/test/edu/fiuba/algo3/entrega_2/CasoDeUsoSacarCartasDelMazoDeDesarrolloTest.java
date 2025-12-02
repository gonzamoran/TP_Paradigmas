package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.construcciones.Ciudad;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.cartas.*;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.*;

import edu.fiuba.algo3.modelo.acciones.CasoDeUsoSacarCartasDelMazoDeDesarrollo;

import edu.fiuba.algo3.modelo.excepciones.RecursosInsuficientesException;
import edu.fiuba.algo3.modelo.excepciones.NoSePuedeJugarEstaCartaException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.*;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class CasoDeUsoSacarCartasDelMazoDeDesarrolloTest {
    private ArrayList<CartasDesarrollo> cartas;
    private int turnoActual = 1;

    @BeforeEach
    public void setUp() {
        this.cartas = new ArrayList<CartasDesarrollo>(List.of(
        new CartaPuntoVictoria(), new CartaPuntoVictoria(), new CartaPuntoVictoria(),
        new CartaPuntoVictoria(),
        new CartaPuntoVictoria(),
        new CartaMonopolio(), new CartaMonopolio(),
        //new CartaConstruccionCarretera(), new CartaConstruccionCarretera(),
        // new CartaDescubrimiento(), new CartaDescubrimiento(),
        new CartaCaballero(), new CartaCaballero(), new CartaCaballero(),
        new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), 
        new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), 
        new CartaCaballero(), new CartaCaballero()
        ));
    }
    @Test
    public void test01ComprarCartaDesarrolloConsumeRecursos() {
        setUp();
        Jugador jugador = new Jugador("Azul");

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(this.cartas);
        caso.comprarCartaDesarrollo(new MazoTrucado(new CartaPuntoVictoria(0)), jugador, turnoActual);

        assertEquals(1, jugador.contarCartasDeDesarrollo());
    }

    @Test
    public void test02ComprarCartaDesarrolloSinRecursosLanzaExcepcion() {
        setUp();
        Jugador jugador = new Jugador("Azul");
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(this.cartas);

        assertThrows(RecursosInsuficientesException.class,
               () -> caso.comprarCartaDesarrollo(new MazoTrucado(new CartaPuntoVictoria()), jugador, turnoActual));
    }

    @Test
    public void test03ComprarCartaDesarrolloPuntosVictoriaSeAgregaCorrectamente() {
        setUp();
        Jugador jugador = new Jugador("Azul");

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        caso.comprarCartaDesarrollo(new MazoTrucado(new CartaPuntoVictoria()), jugador, turnoActual);

        int puntosDeVictoriaEsperados = 1;

        assertEquals(puntosDeVictoriaEsperados, jugador.calculoPuntosVictoria());
    }

    @Test
    public void test04CartaDesarrolloPuntosVictoriaNoEsJugable() {
        setUp();
        Jugador jugador = new Jugador("Azul");

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        caso.comprarCartaDesarrollo(new MazoTrucado(new CartaPuntoVictoria()), jugador, turnoActual);

        ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador, new ArrayList<>(), turnoActual, null, new Tablero());

        assertThrows(NoSePuedeJugarEstaCartaException.class, () -> {
            caso.usarCartaDesarrollo(new CartaPuntoVictoria(0), jugador, contexto);
        });
    }
    
    @Test
    public void test04InicializacionDelMazoDeCartasDeDesarrolloTieneTodasLasCartas() {
        setUp();

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        MazoCartasDesarrollo mazo = caso.inicializarMazoDeCartasDeDesarrollo();

       MazoCartasDesarrollo mazoEsperado = new MazoCartasDesarrollo(cartas);

        assertEquals(mazoEsperado, mazo);
    }


    @Test
    public void test06ElMazoDeCartasDeDesarrolloSeAgotaDespuesDeSacarTodasLasCartas() {
        setUp();
        var mazo = new MazoCartasDesarrollo(cartas);
        var jugador = new Jugador("Azul");
        jugador.agregarRecurso(new Lana(26));
        jugador.agregarRecurso(new Piedra(26));
        jugador.agregarRecurso(new Grano(26));

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);

        int totalCartas = 25;
        for (int i = 0; i < totalCartas; i++) {
            caso.comprarCartaDesarrollo(mazo, jugador, turnoActual);
        }

        assertTrue(mazo.estaVacio());
    }

    @Test
    public void test07JugadorNoPuedeUsarCartaQueAcabaDeComprar() {
        setUp();
        var jugador = new Jugador("Azul");
        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));

        ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador, new ArrayList<>(), turnoActual, null, new Tablero());

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        caso.comprarCartaDesarrollo(new MazoTrucado(new CartaMonopolio()), jugador, turnoActual);

        assertThrows(NoSePuedeJugarEstaCartaException.class, () -> {
            caso.usarCartaDesarrollo(new CartaMonopolio(),jugador, contexto);
        });
    }

    @Test
    public void test08JugadorPuedeUsarCartaLuegoDeEsperarUnTurno() {
        setUp();
        var jugador = new Jugador("Azul");
        Tablero tablero = new Tablero();

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));
        Recurso recursoSolicitado = null;
        
        ContextoCartaDesarrollo contextoAntiguo = new ContextoCartaDesarrollo(jugador, new ArrayList<>(), turnoActual, recursoSolicitado, tablero);
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        caso.comprarCartaDesarrollo(new MazoTrucado(new CartaMonopolio()), jugador, turnoActual);

        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador, new ArrayList<>(), turnoActual + 1, recursoSolicitado, tablero);

        assertDoesNotThrow(() -> {
            caso.usarCartaDesarrollo(new CartaMonopolio(1),jugador,  contextoNuevo);
        });
    }


    @Test
    public void test09CartaDeMonopolioRobaTodosLosRecursosATodosLosJugadores() {
        setUp();
        var jugador1 = new Jugador("Azul");
        var jugador2 = new Jugador("Rojo");
        var jugador3 = new Jugador("Verde");
        Tablero tablero = new Tablero();
        jugador1.agregarRecurso(new Lana(1));
        jugador1.agregarRecurso(new Piedra(1));
        jugador1.agregarRecurso(new Grano(1));

        jugador2.agregarRecurso(new Madera(2));
        jugador3.agregarRecurso(new Madera(3));

        ArrayList<Jugador> jugadoresAfectados = new ArrayList<Jugador>(List.of(jugador2, jugador3));
        Recurso recursoSolicitado = new Madera(1);

        ContextoCartaDesarrollo contextoAntiguo = new ContextoCartaDesarrollo(jugador1, jugadoresAfectados, turnoActual, recursoSolicitado, tablero);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        caso.comprarCartaDesarrollo(new MazoTrucado(new CartaMonopolio()), jugador1, turnoActual);

        ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador1, jugadoresAfectados, turnoActual + 1, recursoSolicitado, tablero);
        
        caso.usarCartaDesarrollo(new CartaMonopolio(1), jugador1, contexto);

        int cantidadEsperadaJugador1 = 5;
        int cantidadEsperadaJugador2 = 0;
        int cantidadEsperadaJugador3 = 0;

        assertEquals(cantidadEsperadaJugador1, jugador1.obtenerCantidadRecurso(new Madera()));
        assertEquals(cantidadEsperadaJugador2, jugador2.obtenerCantidadRecurso(new Madera()));
        assertEquals(cantidadEsperadaJugador3, jugador3.obtenerCantidadRecurso(new Madera()));
    }

    @Test
    public void test10CartaDeMonopolioRobaSoloElRecursoEspecificado() {
        setUp();
        var jugador1 = new Jugador("Azul");
        var jugador2 = new Jugador("Rojo");
        Tablero tablero = new Tablero();
        jugador1.agregarRecurso(new Lana(1));
        jugador1.agregarRecurso(new Piedra(1));
        jugador1.agregarRecurso(new Grano(1));

        jugador2.agregarRecurso(new Madera(2));
        jugador2.agregarRecurso(new Lana(3));

        ArrayList<Jugador> jugadoresAfectados = new ArrayList<Jugador>(List.of(jugador2));
        Recurso recursoSolicitado = new Madera(1);

        ContextoCartaDesarrollo contextoAntiguo = new ContextoCartaDesarrollo(jugador1, jugadoresAfectados, turnoActual, recursoSolicitado, tablero);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        caso.comprarCartaDesarrollo(new MazoTrucado(new CartaMonopolio()), jugador1, turnoActual);

        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador1, jugadoresAfectados, turnoActual + 1, recursoSolicitado, tablero);
        
        caso.usarCartaDesarrollo(new CartaMonopolio(1), jugador1, contextoNuevo);

        ArrayList<Recurso> recursosEsperadosJugador1 = new ArrayList<Recurso>(List.of(new Madera(2), new Lana(0)));
        ArrayList<Recurso> recursosEsperadosJugador2 = new ArrayList<Recurso>(List.of(new Lana(3), new Madera(0)));

        ArrayList<Recurso> recursosJugador1 = new ArrayList<Recurso>(List.of(
            new Madera(jugador1.obtenerCantidadRecurso(new Madera())),
            new Lana(jugador1.obtenerCantidadRecurso(new Lana()))
        ));
        ArrayList<Recurso> recursosJugador2 = new ArrayList<Recurso>(List.of(
            new Madera(jugador2.obtenerCantidadRecurso(new Madera())),
            new Lana(jugador2.obtenerCantidadRecurso(new Lana()))
        ));
        assertTrue(recursosEsperadosJugador1.containsAll(recursosJugador1));
        assertTrue(recursosJugador1.containsAll(recursosEsperadosJugador1));
        assertTrue(recursosEsperadosJugador2.containsAll(recursosJugador2));
        assertTrue(recursosJugador2.containsAll(recursosEsperadosJugador2));
    }

    /*
    @Test
    public void testCasoDeUsoCartaCaballero(){

        setUp();
        Tablero tablero = new Tablero();
        Jugador jugador1 = new Jugador("Azul");
        Jugador victima = new Jugador("Rojo");

        jugador1.agregarRecurso(new Lana(1));
        jugador1.agregarRecurso(new Piedra(1));
        jugador1.agregarRecurso(new Grano(1));        

        victima.agregarRecurso(new Madera(1));

        ArrayList<Jugador> jugadoresAfectados = new ArrayList<Jugador>(List.of(victima));

        Coordenadas destinoLadron = new Coordenadas(2,2);
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        MazoCartasDesarrollo mazo = new MazoTrucado(new CartaCaballero());
        
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador1, turnoActual);

        //turno 2
        ArrayList<CartasDesarrollo> disponibles = jugador1.obtenerCartasDesarrollo();
        CartasDesarrollo cartaElegida = disponibles.get(0);
        
        new ContextoCartaDesarrollo(jugador1, turnoActual + 1, tablero);


        contexto.agregarDestinoLadron(destinoLadron);

        
        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador1, jugadoresAfectados, turnoActual + 1, null, tablero); //incluyendo destinoLadron
        
        caso.usarCartaDesarrollo(cartaElegida, jugador1, contextoNuevo);

        assertEquals(1, jugador1.obtenerCantidadCartasRecurso());
        assertEquals(0, victima.obtenerCantidadCartasRecurso());

        assertEquals(1, jugador1.obtenerCantidadCaballerosUsados());
    }
    */
}