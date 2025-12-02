package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.cartas.*;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.*;
import edu.fiuba.algo3.modelo.ProveedorDeDatos;

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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.*;


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
        new CartaDescubrimiento(), new CartaDescubrimiento(),
        new CartaCaballero(), new CartaCaballero(), new CartaCaballero(),
        new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), 
        new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), 
        new CartaCaballero(), new CartaCaballero()
        ));
    }
    
    @Test
    public void test01ComprarCartaDesarrolloConsumeRecursos() {
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
        Jugador jugador = new Jugador("Azul");
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(this.cartas);

        assertThrows(RecursosInsuficientesException.class,
               () -> caso.comprarCartaDesarrollo(new MazoTrucado(new CartaPuntoVictoria()), jugador, turnoActual));
    }

    @Test
    public void test03ComprarCartaDesarrolloPuntosVictoriaSeAgregaCorrectamente() {
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
        Jugador jugador = new Jugador("Azul");

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        caso.comprarCartaDesarrollo(new MazoTrucado(new CartaPuntoVictoria()), jugador, turnoActual);

        ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador, new ArrayList<>(), turnoActual, new Tablero());

        assertThrows(NoSePuedeJugarEstaCartaException.class, () -> {
            caso.usarCartaDesarrollo(new CartaPuntoVictoria(0), jugador, contexto, new ProveedorDeDatos());
        });
    }
    
    @Test
    public void test05InicializacionDelMazoDeCartasDeDesarrolloTieneTodasLasCartas() {
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        MazoCartasDesarrollo mazo = caso.inicializarMazoDeCartasDeDesarrollo();

       MazoCartasDesarrollo mazoEsperado = new MazoCartasDesarrollo(cartas);

        assertEquals(mazoEsperado, mazo);
    }


    @Test
    public void test06ElMazoDeCartasDeDesarrolloSeAgotaDespuesDeSacarTodasLasCartas() {

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
    
        var jugador = new Jugador("Azul");
        var jugadores = new ArrayList<Jugador>();
        ProveedorDeDatos proveedor = new ProveedorDeDatos();
        jugadores.add(jugador);
        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));

        CartaMonopolio carta = new CartaMonopolio();

        ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual, new Tablero());

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        caso.comprarCartaDesarrollo(new MazoTrucado(carta), jugador, turnoActual);

        assertThrows(NoSePuedeJugarEstaCartaException.class, () -> {
            caso.usarCartaDesarrollo(carta, jugador, contexto, proveedor);
        });
    }

    @Test
    public void test08JugadorPuedeUsarCartaLuegoDeEsperarUnTurno() {
        
        var jugador = new Jugador("Azul");
        var jugador2 = new Jugador("Rojo");
        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        when(proveedorMockeado.pedirRecursoAlUsuario()).thenReturn(new Madera());
        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador);
        jugadores.add(jugador2);

        Tablero tablero = new Tablero();

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));
        
        CartaMonopolio carta = new CartaMonopolio();
        ContextoCartaDesarrollo contextoAntiguo = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual, tablero);
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        caso.comprarCartaDesarrollo(new MazoTrucado(carta), jugador, turnoActual);

        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual + 1, tablero);

        assertDoesNotThrow(() -> {
            caso.usarCartaDesarrollo(carta, jugador, contextoNuevo, proveedorMockeado);
        }); //null
    }
    

    @Test
    public void test09CartaDeMonopolioRobaTodosLosRecursosATodosLosJugadores() {
        
        var jugador1 = new Jugador("Azul");
        var jugador2 = new Jugador("Rojo");
        var jugador3 = new Jugador("Verde");
        Tablero tablero = new Tablero();
        jugador1.agregarRecurso(new Lana(1));
        jugador1.agregarRecurso(new Piedra(1));
        jugador1.agregarRecurso(new Grano(1));

        jugador2.agregarRecurso(new Madera(2));
        jugador3.agregarRecurso(new Madera(3));

        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        jugadores.add(jugador3);

        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        when(proveedorMockeado.pedirRecursoAlUsuario()).thenReturn(new Madera());

        ContextoCartaDesarrollo contextoAntiguo = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual, tablero);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        caso.comprarCartaDesarrollo(new MazoTrucado(new CartaMonopolio()), jugador1, turnoActual);

        ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual + 1, tablero);
        caso.usarCartaDesarrollo(new CartaMonopolio(1), jugador1, contexto, proveedorMockeado);

        int cantidadEsperadaJugador1 = 5;
        int cantidadEsperadaJugador2 = 0;
        int cantidadEsperadaJugador3 = 0;

        assertEquals(cantidadEsperadaJugador1, jugador1.obtenerCantidadRecurso(new Madera()));
        assertEquals(cantidadEsperadaJugador2, jugador2.obtenerCantidadRecurso(new Madera()));
        assertEquals(cantidadEsperadaJugador3, jugador3.obtenerCantidadRecurso(new Madera()));
    }

    @Test
    public void test10CartaDeMonopolioRobaSoloElRecursoEspecificado() {
        
        var jugador1 = new Jugador("Azul");
        var jugador2 = new Jugador("Rojo");
        Tablero tablero = new Tablero();
        jugador1.agregarRecurso(new Lana(1));
        jugador1.agregarRecurso(new Piedra(1));
        jugador1.agregarRecurso(new Grano(1));

        jugador2.agregarRecurso(new Madera(2));
        jugador2.agregarRecurso(new Lana(3));

        ArrayList<Jugador> jugadores = new ArrayList<Jugador>(List.of(jugador1, jugador2));

        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        
        when(proveedorMockeado.pedirRecursoAlUsuario()).thenReturn(new Madera(1));
        ContextoCartaDesarrollo contextoAntiguo = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual, tablero);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        caso.comprarCartaDesarrollo(new MazoTrucado(new CartaMonopolio()), jugador1, turnoActual);

        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual + 1, tablero);
        
        caso.usarCartaDesarrollo(new CartaMonopolio(1), jugador1, contextoNuevo, proveedorMockeado);

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

    @Test
    public void test11CartaDeCaballeroRobaAUnJugadorCorrectamente(){
        Tablero tablero = new Tablero();
        Jugador jugador1 = new Jugador("Azul");
        Jugador victima = new Jugador("Rojo");
        CartasDesarrollo carta = new CartaCaballero();

        jugador1.agregarRecurso(new Lana(1));
        jugador1.agregarRecurso(new Piedra(1));
        jugador1.agregarRecurso(new Grano(1));        

        victima.agregarRecurso(new Madera(1));
        victima.agregarRecurso(new Ladrillo(1));
        victima.agregarRecurso(new Grano(1));
        victima.agregarRecurso(new Lana(1));
        victima.agregarRecurso(new Piedra(1));
        
        var jugadores = new ArrayList<Jugador>(List.of(jugador1, victima));
        
        tablero.colocarEn(new Coordenadas(2,2), new Poblado(), victima);
        
        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        when(proveedorMockeado.pedirCoordenadasAlUsuario()).thenReturn((new Coordenadas(2,2)));

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        MazoCartasDesarrollo mazo = new MazoTrucado(new CartaCaballero());
        
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador1, turnoActual);

        //turno 2
        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual + 1, tablero); //incluyendo destinoLadron
        
        caso.usarCartaDesarrollo(carta, jugador1 ,contextoNuevo, proveedorMockeado);

        assertEquals(1, jugador1.obtenerCantidadCartasRecurso());
        assertEquals(0, victima.obtenerCantidadCartasRecurso());

        assertEquals(1, jugador1.obtenerCantidadCaballerosUsados());
    }
    @Test
    public void test12CartaCaballeroCon2JugadoresRobaSoloAUno(){
        Tablero tablero = new Tablero();
        Jugador jugador1 = new Jugador("Azul");
        Jugador jugador2 = new Jugador("Verde");
        Jugador victima = new Jugador("Rojo");
        CartasDesarrollo carta = new CartaCaballero();

        jugador1.agregarRecurso(new Lana(1));
        jugador1.agregarRecurso(new Piedra(1));
        jugador1.agregarRecurso(new Grano(1));        

        jugador2.agregarRecurso(new Lana(4));
        jugador2.agregarRecurso(new Madera(1));
        jugador2.agregarRecurso(new Ladrillo(1));
        jugador2.agregarRecurso(new Grano(1));

        victima.agregarRecurso(new Madera(1));
        victima.agregarRecurso(new Ladrillo(1));
        victima.agregarRecurso(new Grano(1));
        victima.agregarRecurso(new Lana(1));
        victima.agregarRecurso(new Piedra(1));
        
        var jugadores = new ArrayList<Jugador>(List.of(jugador1, jugador2 ,victima));
        
        tablero.colocarEn(new Coordenadas(2,2), new Poblado(), victima);
        tablero.colocarEn(new Coordenadas(3,3), new Poblado(), jugador2);

        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        when(proveedorMockeado.pedirCoordenadasAlUsuario()).thenReturn((new Coordenadas(2,2)));

        when(proveedorMockeado.pedirJugadorARobar(jugadores)).thenReturn(victima);
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        MazoCartasDesarrollo mazo = new MazoTrucado(new CartaCaballero());
        
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador1, turnoActual);

        //turno 2
        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual + 1, tablero); //incluyendo destinoLadron
        
        caso.usarCartaDesarrollo(carta, jugador1 ,contextoNuevo, proveedorMockeado);

        assertEquals(1, jugador1.obtenerCantidadCartasRecurso());
        assertEquals(1, victima.obtenerCantidadCartasRecurso());
        assertEquals(3, jugador2.obtenerCantidadCartasRecurso());
        assertEquals(1, jugador1.obtenerCantidadCaballerosUsados());
    }

    @Test
    public void test13Tener3oMasCartasPoseeGranCaballeria(){
        
    }
   
   
   
   
   
    @Test
    public void test14CartaDescubrimientoAgregaLosRecursosCorrectamente(){
        Jugador jugador = new Jugador("Azul");
        Tablero tablero = new Tablero();

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1)); 

        ArrayList<Jugador> jugadores = new ArrayList<>(List.of(jugador));

        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        
        ArrayList<Recurso> recursos = new ArrayList<>();
        recursos.add(new Madera(1));
        recursos.add(new Ladrillo(1));

        when(proveedorMockeado.pedirGrupoRecursosAlUsuario(2)).thenReturn(recursos);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        MazoCartasDesarrollo mazo = new MazoTrucado(new CartaDescubrimiento());
        CartaDescubrimiento carta = new CartaDescubrimiento(turnoActual);
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador, turnoActual);

        //turno 2
        ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual + 1, tablero);

        caso.usarCartaDesarrollo(carta, jugador, contexto, proveedorMockeado);

        assertEquals(1, jugador.obtenerCantidadRecurso(new Ladrillo()));
        assertEquals(1, jugador.obtenerCantidadRecurso(new Madera()));
    }



    /*
    Pruebo que el jugador1 compre una carta de descubrimiento y que en el proximo turno pueda comprar un poblado reclamando los recursos.
    */ 
    @Test
    public void test15comprarCartaDescubrimientoYConstruir(){
        Jugador jugador = new Jugador("Azul");
        Tablero tablero = new Tablero();

        ArrayList<Jugador> jugadores = new ArrayList<>(List.of(jugador));
        jugador.agregarRecurso(new Lana(2));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(2)); 

        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        
        ArrayList<Recurso> recursos = new ArrayList<>();
        recursos.add(new Madera(1));
        recursos.add(new Ladrillo(1));

        when(proveedorMockeado.pedirGrupoRecursosAlUsuario(2)).thenReturn(recursos);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        MazoCartasDesarrollo mazo = new MazoTrucado(new CartaDescubrimiento());
        
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador, turnoActual);

        ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual + 1, tablero);
        CartaDescubrimiento carta = new CartaDescubrimiento(turnoActual);

        caso.usarCartaDesarrollo(carta, jugador, contexto, proveedorMockeado);

        tablero.colocarEn(new Coordenadas(2,2), new Poblado(), jugador);

        assertEquals(0, jugador.obtenerCantidadCartasRecurso());
    }
} 