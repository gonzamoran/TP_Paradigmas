package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.acciones.CasoDeUsoSacarCartasDelMazoDeDesarrollo;
import edu.fiuba.algo3.modelo.cartas.*;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.*;
import edu.fiuba.algo3.modelo.construcciones.*;
import edu.fiuba.algo3.modelo.excepciones.CoordenadasInvalidasException;
import edu.fiuba.algo3.modelo.excepciones.NoEsPosibleConstruirException;
import edu.fiuba.algo3.modelo.excepciones.NoSePuedeJugarEstaCartaException;
import edu.fiuba.algo3.modelo.excepciones.RecursosInsuficientesException;
import edu.fiuba.algo3.modelo.tablero.*;
import edu.fiuba.algo3.modelo.tiposRecurso.*;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CasoDeUsoSacarCartasDelMazoDeDesarrolloTest {
    private ArrayList<CartasDesarrollo> cartas;
    private int turnoActual = 1;
    private MazoCartasDesarrollo mazoEntero;

    @BeforeEach
    public void setUp() {
        this.cartas = new ArrayList<CartasDesarrollo>(List.of(
        new CartaPuntoVictoria(), new CartaPuntoVictoria(), new CartaPuntoVictoria(),
        new CartaPuntoVictoria(), new CartaPuntoVictoria(),
        new CartaMonopolio(), new CartaMonopolio(),
        new CartaConstruccionCarretera(), new CartaConstruccionCarretera(),
        new CartaDescubrimiento(), new CartaDescubrimiento(),
        new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(),
        new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), 
        new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), 
        new CartaCaballero(), new CartaCaballero()
        ));
        this.mazoEntero = new MazoCartasDesarrollo(cartas);
    }
    
    @Test
    public void test01ComprarCartaDesarrolloConsumeRecursos() {
        Jugador jugador = new Jugador("Azul");

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        ArrayList<CartasDesarrollo> cartas = new ArrayList<>(List.of(new CartaPuntoVictoria()));
        caso.comprarCartaDesarrollo(new MazoTrucado(cartas), jugador, turnoActual);

        assertEquals(1, jugador.contarCartasDeDesarrollo());
    }

    @Test
    public void test02ComprarCartaDesarrolloSinRecursosLanzaExcepcion() {
        Jugador jugador = new Jugador("Azul");
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();;

        assertThrows(RecursosInsuficientesException.class,
               () -> caso.comprarCartaDesarrollo(mazoEntero, jugador, turnoActual));
    }

    @Test
    public void test03ComprarCartaDesarrolloPuntosVictoriaSeAgregaCorrectamente() {
        Jugador jugador = new Jugador("Azul");

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));

        ArrayList<CartasDesarrollo> cartas = new ArrayList<>(List.of(new CartaPuntoVictoria()));
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        caso.comprarCartaDesarrollo(new MazoTrucado(cartas), jugador, turnoActual);

        int puntosDeVictoriaEsperados = 1;

        assertEquals(puntosDeVictoriaEsperados, jugador.calculoPuntosVictoria());
    }

    @Test
    public void test04CartaDesarrolloPuntosVictoriaNoEsJugable() {
        Jugador jugador = new Jugador("Azul");
        Tablero tablero =  new Tablero();
        Ladron ladron = new Ladron(tablero.obtenerDesierto());

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        ArrayList<CartasDesarrollo> cartasTrucada = new ArrayList<>(List.of(new CartaPuntoVictoria()));
        caso.comprarCartaDesarrollo(new MazoTrucado(cartasTrucada), jugador, turnoActual);

        ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador, new ArrayList<>(), turnoActual, tablero, ladron);

        assertThrows(NoSePuedeJugarEstaCartaException.class, () -> {
            caso.usarCartaDesarrollo(new CartaPuntoVictoria(0), jugador, contexto);
        });
    }

    @Test
    public void test05ElMazoDeCartasDeDesarrolloSeAgotaDespuesDeSacarTodasLasCartas() {

        var jugador = new Jugador("Azul");
        jugador.agregarRecurso(new Lana(26));
        jugador.agregarRecurso(new Piedra(26));
        jugador.agregarRecurso(new Grano(26));

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();

        int totalCartas = 25;
        for (int i = 0; i < totalCartas; i++) {
            caso.comprarCartaDesarrollo(mazoEntero, jugador, turnoActual);
        }
        assertTrue(mazoEntero.estaVacio());
    }

    @Test
    public void test06JugadorNoPuedeUsarCartaQueAcabaDeComprar() {
    
        var jugador = new Jugador("Azul");
        Tablero tablero = new Tablero();
        Ladron ladron = new Ladron(tablero.obtenerDesierto());
        
        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador); 
        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));

        ArrayList<CartasDesarrollo> cartas = new ArrayList<>(List.of(new CartaMonopolio()));

        ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual,tablero, ladron);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        caso.comprarCartaDesarrollo(new MazoTrucado(cartas), jugador, turnoActual);

        assertThrows(NoSePuedeJugarEstaCartaException.class, () -> {
            caso.usarCartaDesarrollo(cartas.get(0), jugador, contexto);
        });
    }

    @Test
    public void test07JugadorPuedeUsarCartaLuegoDeEsperarUnTurno() {
        
        var jugador = new Jugador("Azul");
        var jugador2 = new Jugador("Rojo");
        Tablero tablero = new Tablero();
        Ladron ladron = new Ladron(tablero.obtenerDesierto());

        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador);
        jugadores.add(jugador2);

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));
        
        ArrayList<CartasDesarrollo> cartasTrucada = new ArrayList<>(List.of(new CartaMonopolio()));
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        caso.comprarCartaDesarrollo(new MazoTrucado(cartasTrucada), jugador, turnoActual);

        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual + 1, tablero, ladron);

        assertDoesNotThrow(() -> {
            caso.usarCartaDesarrollo(cartasTrucada.get(0), jugador, contextoNuevo);
        });
    }
    

    @Test
    public void test08CartaDeMonopolioRobaTodosLosRecursosATodosLosJugadores() {
        
        var jugador1 = new Jugador("Azul");
        var jugador2 = new Jugador("Rojo");
        var jugador3 = new Jugador("Verde");
        Tablero tablero = new Tablero();
        Ladron ladron = new Ladron(tablero.obtenerDesierto());

        jugador1.agregarRecurso(new Lana(1));
        jugador1.agregarRecurso(new Piedra(1));
        jugador1.agregarRecurso(new Grano(1));

        jugador2.agregarRecurso(new Madera(2));
        jugador3.agregarRecurso(new Madera(3));

        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        jugadores.add(jugador3);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        caso.comprarCartaDesarrollo(new MazoTrucado(new ArrayList<>(List.of(new CartaMonopolio()))), jugador1, turnoActual);

        ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual + 1, tablero, ladron);
        contexto.establecerRecursoElegido(new Madera());
        caso.usarCartaDesarrollo(new CartaMonopolio(1), jugador1, contexto);

        int cantidadEsperadaJugador1 = 5;
        int cantidadEsperadaJugador2 = 0;
        int cantidadEsperadaJugador3 = 0;

        assertEquals(cantidadEsperadaJugador1, jugador1.obtenerCantidadRecurso(new Madera()));
        assertEquals(cantidadEsperadaJugador2, jugador2.obtenerCantidadRecurso(new Madera()));
        assertEquals(cantidadEsperadaJugador3, jugador3.obtenerCantidadRecurso(new Madera()));
    }

    @Test
    public void test9CartaDeMonopolioRobaSoloElRecursoEspecificado() {
        
        var jugador1 = new Jugador("Azul");
        var jugador2 = new Jugador("Rojo");
        Tablero tablero = new Tablero();
        Ladron ladron = new Ladron(tablero.obtenerDesierto());
        
        jugador1.agregarRecurso(new Lana(1));
        jugador1.agregarRecurso(new Piedra(1));
        jugador1.agregarRecurso(new Grano(1));

        jugador2.agregarRecurso(new Madera(2));
        jugador2.agregarRecurso(new Lana(3));

        ArrayList<Jugador> jugadores = new ArrayList<Jugador>(List.of(jugador1, jugador2));

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        caso.comprarCartaDesarrollo(new MazoTrucado(new ArrayList<>(List.of(new CartaMonopolio()))), jugador1, turnoActual);

        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual + 1, tablero, ladron);
        contextoNuevo.establecerRecursoElegido(new Madera(1));
        
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

    @Test
    public void test10CartaDeCaballeroRobaAUnJugadorCorrectamente(){
        Tablero tablero = new Tablero();
        Ladron ladron = new Ladron(tablero.obtenerDesierto());
        Jugador jugador1 = new Jugador("Azul");
        Jugador victima = new Jugador("Rojo");
        CartasDesarrollo carta = new CartaCaballero();

        jugador1.agregarRecurso(new Lana(1));
        jugador1.agregarRecurso(new Piedra(1));
        jugador1.agregarRecurso(new Grano(1));        

        victima.agregarRecurso(new Piedra(1));
        
        var jugadores = new ArrayList<Jugador>(List.of(jugador1, victima));
        
        tablero.colocarConstruccionInicial(new Poblado(), new Coordenadas(2,2), victima);
        
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(new ArrayList<>(List.of(new CartaCaballero())));
        
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador1, turnoActual);

        //turno 2
        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual + 1, tablero, ladron);
        contextoNuevo.establecerCoordenadasDestino(new Coordenadas(2,2));
        contextoNuevo.establecerJugadorObjetivo(victima);
        
        caso.usarCartaDesarrollo(carta, jugador1 ,contextoNuevo);

        assertEquals(1, jugador1.obtenerCantidadCartasRecurso());
        assertEquals(0, victima.obtenerCantidadCartasRecurso());

        assertEquals(1, jugador1.obtenerCantidadCaballerosUsados());
    }
    @Test
    public void test11CartaCaballeroCon2JugadoresRobaSoloAUno(){
        Tablero tablero = new Tablero();
        Ladron ladron = new Ladron(tablero.obtenerDesierto());
        Jugador jugador1 = new Jugador("Azul");
        Jugador jugador2 = new Jugador("Verde");
        Jugador victima = new Jugador("Rojo");
        CartasDesarrollo carta = new CartaCaballero();

        jugador1.agregarRecurso(new Lana(1));
        jugador1.agregarRecurso(new Piedra(1));
        jugador1.agregarRecurso(new Grano(1));        

        jugador2.agregarRecurso(new Lana(3));

        victima.agregarRecurso(new Piedra(1));
        
        var jugadores = new ArrayList<Jugador>(List.of(jugador1, jugador2 ,victima));
        
        tablero.colocarConstruccionInicial( new Poblado(),new Coordenadas(2,2), victima);
        tablero.colocarConstruccionInicial( new Poblado(),new Coordenadas(3,3), jugador2);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(new ArrayList<>(List.of(new CartaCaballero())));
        
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador1, turnoActual);

        //turno 2
        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual + 1, tablero, ladron);
        contextoNuevo.establecerCoordenadasDestino(new Coordenadas(2,2));
        contextoNuevo.establecerJugadorObjetivo(victima);
        
        caso.usarCartaDesarrollo(carta, jugador1 ,contextoNuevo);

        assertEquals(1, jugador1.obtenerCantidadCartasRecurso());
        assertEquals(0, victima.obtenerCantidadCartasRecurso());
        assertEquals(3, jugador2.obtenerCantidadCartasRecurso());
        assertEquals(1, jugador1.obtenerCantidadCaballerosUsados());
    }

    @Test
    public void test12Usar3oMasCartasDeCaballeroOtorgaGranCaballeria(){
        Jugador jugador1 = new Jugador("Azul");
        Jugador victima = new Jugador("Rojo");
        Tablero tablero = new Tablero();
        Ladron ladron = new Ladron(tablero.obtenerDesierto());
        
        jugador1.agregarRecurso(new Lana(3));
        jugador1.agregarRecurso(new Piedra(3));
        jugador1.agregarRecurso(new Grano(3));

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(new ArrayList<>(List.of(new CartaCaballero(), new CartaCaballero(), new CartaCaballero())));

        caso.comprarCartaDesarrollo(mazo, jugador1, turnoActual);
        caso.comprarCartaDesarrollo(mazo, jugador1, turnoActual);
        caso.comprarCartaDesarrollo(mazo, jugador1, turnoActual);

        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador1, new ArrayList<>(), turnoActual + 1, tablero, ladron);
        contextoNuevo.establecerCoordenadasDestino(new Coordenadas(2,2));
        contextoNuevo.establecerJugadorObjetivo(victima);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador1 ,contextoNuevo);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador1 ,contextoNuevo);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador1 ,contextoNuevo);

        assertEquals(2, jugador1.calculoPuntosVictoria());
    }

    @Test
    public void test13ElJugadorConMasCaballerosLeRobaAlOtroLaGranCaballeria(){
        Jugador jugador1 = new Jugador("Azul");
        Jugador jugador2 = new Jugador("Rojo");
        Tablero tablero = new Tablero();
        Ladron ladron = new Ladron(tablero.obtenerDesierto());
        jugador1.agregarRecurso(new Lana(3));
        jugador1.agregarRecurso(new Piedra(3));
        jugador1.agregarRecurso(new Grano(3));
        jugador2.agregarRecurso(new Lana(4));
        jugador2.agregarRecurso(new Piedra(4));
        jugador2.agregarRecurso(new Grano(4));

        var jugadores = new ArrayList<Jugador>(List.of(jugador1, jugador2));

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(new ArrayList<>(List.of(
            new CartaCaballero(), new CartaCaballero(), new CartaCaballero(),
            new CartaCaballero(), new CartaCaballero(), new CartaCaballero(),
            new CartaCaballero())));

        caso.comprarCartaDesarrollo(mazo, jugador1, turnoActual);
        caso.comprarCartaDesarrollo(mazo, jugador1, turnoActual);
        caso.comprarCartaDesarrollo(mazo, jugador1, turnoActual);

        caso.comprarCartaDesarrollo(mazo, jugador2, turnoActual);
        caso.comprarCartaDesarrollo(mazo, jugador2, turnoActual);
        caso.comprarCartaDesarrollo(mazo, jugador2, turnoActual);
        caso.comprarCartaDesarrollo(mazo, jugador2, turnoActual);

        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual + 1, tablero, ladron);
        contextoNuevo.establecerCoordenadasDestino(new Coordenadas(2,2));
        contextoNuevo.establecerJugadorObjetivo(jugador2);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador1 ,contextoNuevo);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador1 ,contextoNuevo);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador1 ,contextoNuevo);

        assertEquals(2, jugador1.calculoPuntosVictoria());
        
        contextoNuevo = new ContextoCartaDesarrollo(jugador2, jugadores, turnoActual + 1, tablero, ladron);
        contextoNuevo.establecerCoordenadasDestino(new Coordenadas(2,2));
        contextoNuevo.establecerJugadorObjetivo(jugador1);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador2 ,contextoNuevo);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador2 ,contextoNuevo);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador2 ,contextoNuevo);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador2 ,contextoNuevo);

        assertEquals(2, jugador2.calculoPuntosVictoria());
        assertEquals(0, jugador1.calculoPuntosVictoria());
    }
   
    @Test
    public void test15CartaDescubrimientoAgregaLosRecursosCorrectamente(){
        Jugador jugador = new Jugador("Azul");
        Tablero tablero = new Tablero();
        Ladron ladron = new Ladron(tablero.obtenerDesierto());
        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1)); 

        ArrayList<Jugador> jugadores = new ArrayList<>(List.of(jugador));
        ArrayList<CartasDesarrollo> cartas = new ArrayList<>(List.of(new CartaDescubrimiento()));

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(cartas);
        CartaDescubrimiento carta = new CartaDescubrimiento(turnoActual);
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador, turnoActual);

        //turno 2
        ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual + 1, tablero, ladron);
        ArrayList<Recurso> recursos = new ArrayList<>();
        recursos.add(new Madera(1));
        recursos.add(new Ladrillo(1));
        contexto.establecerRecursosElegidos(recursos);

        caso.usarCartaDesarrollo(carta, jugador, contexto);

        assertEquals(1, jugador.obtenerCantidadRecurso(new Ladrillo()));
        assertEquals(1, jugador.obtenerCantidadRecurso(new Madera()));
    }

    @Test
    public void test16comprarCartaDescubrimientoYConstruir(){
        Jugador jugador = new Jugador("Azul");
        Tablero tablero = new Tablero();
        Ladron ladron = new Ladron(tablero.obtenerDesierto());
        ArrayList<Jugador> jugadores = new ArrayList<>(List.of(jugador));
        jugador.agregarRecurso(new Lana(2));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(2)); 

        tablero.colocarConstruccionInicial(new Poblado(), new Coordenadas(2,0), jugador);
        tablero.construirCarreteraGratis(new Coordenadas(2,0), new Coordenadas(2,1), jugador);
        tablero.construirCarreteraGratis(new Coordenadas(2,1),new Coordenadas(2,2),jugador);
        
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(new ArrayList<>(List.of(new CartaDescubrimiento())));
        
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador, turnoActual);

        ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual + 1, tablero, ladron);
        CartaDescubrimiento carta = new CartaDescubrimiento(turnoActual);
        ArrayList<Recurso> recursos = new ArrayList<>();
        recursos.add(new Madera(1));
        recursos.add(new Ladrillo(1));
        contexto.establecerRecursosElegidos(recursos);

        caso.usarCartaDesarrollo(carta, jugador, contexto);

        tablero.colocarEn(new Coordenadas(2,2), new Poblado(), jugador);

        assertEquals(0, jugador.obtenerCantidadCartasRecurso());
    }

    @Test
    public void test17UsarCartaConstruccionCarreteraAgregaDosCarreterasValidas(){
        Jugador jugador = new Jugador("Azul");
        Tablero tablero = new Tablero();
        Ladron ladron = new Ladron(tablero.obtenerDesierto());

        jugador.agregarRecurso(new Madera(1));
        jugador.agregarRecurso(new Ladrillo(1));
        jugador.agregarRecurso(new Lana(2));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(2)); 

        ArrayList<Jugador> jugadores = new ArrayList<>(List.of(jugador));
        ArrayList<CartasDesarrollo> cartas = new ArrayList<>(List.of(new CartaConstruccionCarretera()));
        tablero.colocarConstruccionInicial(new Poblado(),new Coordenadas(3,3), jugador);
      //tablero.construirCarreteraGratis(new Coordenadas(3,2),new Coordenadas(3,3),jugador);


        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(cartas);

        caso.comprarCartaDesarrollo(mazo, jugador, turnoActual);
        
        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual + 1, tablero, ladron);

        ArrayList<List<Coordenadas>> coordenadasCarreteras = new ArrayList<List<Coordenadas>>();
        coordenadasCarreteras.add( new ArrayList<Coordenadas>(List.of(new Coordenadas(3,3), new Coordenadas(3,4))));
        coordenadasCarreteras.add( new ArrayList<Coordenadas>(List.of(new Coordenadas(3,4), new Coordenadas(3,5))));

        contextoNuevo.establecerCoordenadasCarreteras(coordenadasCarreteras);

        caso.usarCartaDesarrollo(new CartaConstruccionCarretera(), jugador ,contextoNuevo);
        assertTrue(tablero.estaConstruidoCon(new Carretera(), new Coordenadas(3,3), jugador));
        assertTrue(tablero.estaConstruidoCon(new Carretera(), new Coordenadas(3,4), jugador));
        assertTrue(tablero.estaConstruidoCon(new Carretera(), new Coordenadas(3,4), jugador));
        assertTrue(tablero.estaConstruidoCon(new Carretera(), new Coordenadas(3,5), jugador));
    }


    @Test
    public void test18UsarCartaConstruccionCarreteraConCoordenadasInvalidasLanzaExcepcion(){
        Jugador jugador = new Jugador("Azul");
        Tablero tablero = new Tablero();
        Ladron ladron = new Ladron(tablero.obtenerDesierto());

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1)); 

        ArrayList<Jugador> jugadores = new ArrayList<>(List.of(jugador));
        ArrayList<CartasDesarrollo> cartas = new ArrayList<>(List.of(new CartaConstruccionCarretera()));

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(cartas);
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador, turnoActual);

        //turno 2
        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual + 1, tablero, ladron); //incluyendo destinoLadron
        ArrayList<List<Coordenadas>> coordenadasCarreteras = new ArrayList<List<Coordenadas>>();
        coordenadasCarreteras.add( new ArrayList<Coordenadas>(List.of(new Coordenadas(8,8), new Coordenadas(8,9))));
        coordenadasCarreteras.add( new ArrayList<Coordenadas>(List.of(new Coordenadas(9,9), new Coordenadas(9,10))));
        contextoNuevo.establecerCoordenadasCarreteras(coordenadasCarreteras);
        
        assertThrows(CoordenadasInvalidasException.class, () -> {
            caso.usarCartaDesarrollo(new CartaConstruccionCarretera(), jugador ,contextoNuevo);
        });
    }

    @Test
    public void test19UsarCartaConstruccionCarreteraSinPobladoCercaLanzaExcepcion(){
        Jugador jugador = new Jugador("Azul");
        Tablero tablero = new Tablero();
        Ladron ladron = new Ladron(tablero.obtenerDesierto());

        jugador.agregarRecurso(new Lana(2));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(2));
        jugador.agregarRecurso(new Madera(1));
        jugador.agregarRecurso(new Ladrillo(1)); 


        ArrayList<Jugador> jugadores = new ArrayList<>(List.of(jugador));
        ArrayList<CartasDesarrollo> cartas = new ArrayList<>(List.of(new CartaConstruccionCarretera()));

        tablero.colocarConstruccionInicial( new Poblado(), new Coordenadas(1,1), jugador);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(cartas);
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador, turnoActual);

        //turno 2
        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual + 1, tablero, ladron); //incluyendo destinoLadron
        ArrayList<List<Coordenadas>> coordenadasCarreteras = new ArrayList<List<Coordenadas>>();
        coordenadasCarreteras.add( new ArrayList<Coordenadas>(List.of(new Coordenadas(3,3), new Coordenadas(3,4))));
        coordenadasCarreteras.add( new ArrayList<Coordenadas>(List.of(new Coordenadas(3,4), new Coordenadas(3,5))));
        contextoNuevo.establecerCoordenadasCarreteras(coordenadasCarreteras);
        
        assertThrows(NoEsPosibleConstruirException.class, () -> {
            caso.usarCartaDesarrollo(new CartaConstruccionCarretera(), jugador ,contextoNuevo);
        });
    }

    @Test
    public void test20UsarCartaConstruccionCarreteraConPobladoDeOtroJugadorLanzaExcepcion(){
        Jugador jugador1 = new Jugador("Azul");
        Jugador jugador2 = new Jugador("Rojo");
        Tablero tablero = new Tablero();
        Ladron ladron = new Ladron(tablero.obtenerDesierto());

        jugador1.agregarRecurso(new Lana(1));
        jugador1.agregarRecurso(new Piedra(1));
        jugador1.agregarRecurso(new Grano(1));

        jugador2.agregarRecurso(new Madera(1));
        jugador2.agregarRecurso(new Ladrillo(1));
        jugador2.agregarRecurso(new Grano(1));
        jugador2.agregarRecurso(new Lana(1));


        ArrayList<Jugador> jugadores = new ArrayList<>(List.of(jugador1, jugador2));
        ArrayList<CartasDesarrollo> cartas = new ArrayList<>(List.of(new CartaConstruccionCarretera()));

        tablero.colocarConstruccionInicial(new Poblado(), new Coordenadas(1,1), jugador2);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(cartas);
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador1, turnoActual);

        //turno 2
        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual + 1, tablero, ladron); //incluyendo destinoLadron
        ArrayList<List<Coordenadas>> coordenadasCarreteras = new ArrayList<List<Coordenadas>>();
        coordenadasCarreteras.add( new ArrayList<Coordenadas>(List.of(new Coordenadas(1,1), new Coordenadas(1,2))));
        coordenadasCarreteras.add( new ArrayList<Coordenadas>(List.of(new Coordenadas(1,2), new Coordenadas(1,3))));
        contextoNuevo.establecerCoordenadasCarreteras(coordenadasCarreteras);
        
        assertThrows(NoEsPosibleConstruirException.class, () -> {
            caso.usarCartaDesarrollo(new CartaConstruccionCarretera(), jugador1 ,contextoNuevo);
        });
    }

    @Test
    public void test21UsarCartaConstruccionDeCarreterasConUnaDeEllasMalColocadaNoDejaNadaConstruido(){
        Jugador jugador = new Jugador("Azul");
        Tablero tablero = new Tablero();
        Ladron ladron = new Ladron(tablero.obtenerDesierto());

        jugador.agregarRecurso(new Madera(1));
        jugador.agregarRecurso(new Ladrillo(1));
        jugador.agregarRecurso(new Lana(2));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(2)); 

        ArrayList<Jugador> jugadores = new ArrayList<>(List.of(jugador));
        ArrayList<CartasDesarrollo> cartas = new ArrayList<>(List.of(new CartaConstruccionCarretera()));
        tablero.colocarConstruccionInicial(new Poblado(),new Coordenadas(3,3), jugador);


        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(cartas);

        caso.comprarCartaDesarrollo(mazo, jugador, turnoActual);
        
        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual + 1, tablero, ladron);

        ArrayList<List<Coordenadas>> coordenadasCarreteras = new ArrayList<List<Coordenadas>>();
        coordenadasCarreteras.add( new ArrayList<Coordenadas>(List.of(new Coordenadas(3,3), new Coordenadas(3,4))));
        coordenadasCarreteras.add( new ArrayList<Coordenadas>(List.of(new Coordenadas(4,4), new Coordenadas(4,5))));

        contextoNuevo.establecerCoordenadasCarreteras(coordenadasCarreteras);

        assertThrows(NoEsPosibleConstruirException.class, () -> caso.usarCartaDesarrollo(new CartaConstruccionCarretera(), jugador ,contextoNuevo));
        assertFalse(tablero.estaConstruidoCon(new Carretera(), new Coordenadas(3,3), jugador));
        assertFalse(tablero.estaConstruidoCon(new Carretera(), new Coordenadas(3,4), jugador));
        assertFalse(tablero.estaConstruidoCon(new Carretera(), new Coordenadas(4,4), jugador));
        assertFalse(tablero.estaConstruidoCon(new Carretera(), new Coordenadas(4,5), jugador));
    }
}