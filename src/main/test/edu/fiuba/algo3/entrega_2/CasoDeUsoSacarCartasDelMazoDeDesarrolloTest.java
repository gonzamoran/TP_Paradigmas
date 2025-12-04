package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Ladron;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.Carretera;
import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.cartas.*;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.*;
import edu.fiuba.algo3.modelo.ProveedorDeDatos;

import edu.fiuba.algo3.modelo.acciones.CasoDeUsoSacarCartasDelMazoDeDesarrollo;

import edu.fiuba.algo3.modelo.excepciones.*;

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
        caso.comprarCartaDesarrollo(new MazoTrucado(cartas), jugador, turnoActual);

        ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador, new ArrayList<>(), turnoActual, tablero, ladron);

        assertThrows(NoSePuedeJugarEstaCartaException.class, () -> {
            caso.usarCartaDesarrollo(new CartaPuntoVictoria(0), jugador, contexto, new ProveedorDeDatos());
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
        ProveedorDeDatos proveedor = new ProveedorDeDatos();
        jugadores.add(jugador); 
        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));

        ArrayList<CartasDesarrollo> cartas = new ArrayList<>(List.of(new CartaMonopolio()));

        ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual,tablero, ladron);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        caso.comprarCartaDesarrollo(new MazoTrucado(cartas), jugador, turnoActual);

        assertThrows(NoSePuedeJugarEstaCartaException.class, () -> {
            caso.usarCartaDesarrollo(cartas.get(0), jugador, contexto, proveedor);
        });
    }

    @Test
    public void test07JugadorPuedeUsarCartaLuegoDeEsperarUnTurno() {
        
        var jugador = new Jugador("Azul");
        var jugador2 = new Jugador("Rojo");
        Tablero tablero = new Tablero();
        Ladron ladron = new Ladron(tablero.obtenerDesierto());

        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        when(proveedorMockeado.pedirRecursoAlUsuario()).thenReturn(new Madera());
        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador);
        jugadores.add(jugador2);

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));
        
        ArrayList<CartasDesarrollo> cartas = new ArrayList<>(List.of(new CartaMonopolio()));
        ContextoCartaDesarrollo contextoAntiguo = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual, tablero, ladron);
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        caso.comprarCartaDesarrollo(new MazoTrucado(cartas), jugador, turnoActual);

        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual + 1, tablero, ladron);

        assertDoesNotThrow(() -> {
            caso.usarCartaDesarrollo(cartas.get(0), jugador, contextoNuevo, proveedorMockeado);
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

        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        when(proveedorMockeado.pedirRecursoAlUsuario()).thenReturn(new Madera());

        ContextoCartaDesarrollo contextoAntiguo = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual, tablero, ladron);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        caso.comprarCartaDesarrollo(new MazoTrucado(new ArrayList<>(List.of(new CartaMonopolio()))), jugador1, turnoActual);

        ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual + 1, tablero, ladron);
        caso.usarCartaDesarrollo(new CartaMonopolio(1), jugador1, contexto, proveedorMockeado);

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

        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        
        when(proveedorMockeado.pedirRecursoAlUsuario()).thenReturn(new Madera(1));
        ContextoCartaDesarrollo contextoAntiguo = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual, tablero, ladron);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        caso.comprarCartaDesarrollo(new MazoTrucado(new ArrayList<>(List.of(new CartaMonopolio()))), jugador1, turnoActual);

        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual + 1, tablero, ladron);
        
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
        
        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        when(proveedorMockeado.pedirCoordenadasAlUsuario()).thenReturn((new Coordenadas(2,2)));

        when(proveedorMockeado.pedirJugadorARobar(any(ArrayList.class))).thenReturn(victima);
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(new ArrayList<>(List.of(new CartaCaballero())));
        
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador1, turnoActual);

        //turno 2
        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual + 1, tablero, ladron);
        
        caso.usarCartaDesarrollo(carta, jugador1 ,contextoNuevo, proveedorMockeado);

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

        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        when(proveedorMockeado.pedirCoordenadasAlUsuario()).thenReturn((new Coordenadas(2,2)));

        when(proveedorMockeado.pedirJugadorARobar(any(ArrayList.class))).thenReturn(victima);
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(new ArrayList<>(List.of(new CartaCaballero())));
        
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador1, turnoActual);

        //turno 2
        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual + 1, tablero, ladron);
        
        caso.usarCartaDesarrollo(carta, jugador1 ,contextoNuevo, proveedorMockeado);

        assertEquals(1, jugador1.obtenerCantidadCartasRecurso());
        assertEquals(0, victima.obtenerCantidadCartasRecurso());
        assertEquals(3, jugador2.obtenerCantidadCartasRecurso());
        assertEquals(1, jugador1.obtenerCantidadCaballerosUsados());
    }

    @Test
    public void test12Usar3oMasCartasDeCaballeroOtorgaGranCaballeria(){
        Jugador jugador1 = new Jugador("Azul");
        Tablero tablero = new Tablero();
        Ladron ladron = new Ladron(tablero.obtenerDesierto());
        
        jugador1.agregarRecurso(new Lana(3));
        jugador1.agregarRecurso(new Piedra(3));
        jugador1.agregarRecurso(new Grano(3));
        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        when(proveedorMockeado.pedirCoordenadasAlUsuario()).thenReturn((new Coordenadas(2,2)));

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(new ArrayList<>(List.of(new CartaCaballero(), new CartaCaballero(), new CartaCaballero())));

        caso.comprarCartaDesarrollo(mazo, jugador1, turnoActual);
        caso.comprarCartaDesarrollo(mazo, jugador1, turnoActual);
        caso.comprarCartaDesarrollo(mazo, jugador1, turnoActual);

        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador1, new ArrayList<>(), turnoActual + 1, tablero, ladron);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador1 ,contextoNuevo, proveedorMockeado);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador1 ,contextoNuevo, proveedorMockeado);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador1 ,contextoNuevo, proveedorMockeado);

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

        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        when(proveedorMockeado.pedirCoordenadasAlUsuario()).thenReturn((new Coordenadas(2,2)));

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
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador1 ,contextoNuevo, proveedorMockeado);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador1 ,contextoNuevo, proveedorMockeado);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador1 ,contextoNuevo, proveedorMockeado);

        assertEquals(2, jugador1.calculoPuntosVictoria());
        
        contextoNuevo = new ContextoCartaDesarrollo(jugador2, jugadores, turnoActual + 1, tablero, ladron);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador2 ,contextoNuevo, proveedorMockeado);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador2 ,contextoNuevo, proveedorMockeado);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador2 ,contextoNuevo, proveedorMockeado);
        caso.usarCartaDesarrollo(new CartaCaballero(), jugador2 ,contextoNuevo, proveedorMockeado);

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

        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        
        ArrayList<Recurso> recursos = new ArrayList<>();
        recursos.add(new Madera(1));
        recursos.add(new Ladrillo(1));

        when(proveedorMockeado.pedirGrupoRecursosAlUsuario(2)).thenReturn(recursos);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(cartas);
        CartaDescubrimiento carta = new CartaDescubrimiento(turnoActual);
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador, turnoActual);

        //turno 2
        ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual + 1, tablero, ladron);

        caso.usarCartaDesarrollo(carta, jugador, contexto, proveedorMockeado);

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
        
        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        
        ArrayList<Recurso> recursos = new ArrayList<>();
        recursos.add(new Madera(1));
        recursos.add(new Ladrillo(1));

        when(proveedorMockeado.pedirGrupoRecursosAlUsuario(2)).thenReturn(recursos);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(new ArrayList<>(List.of(new CartaDescubrimiento())));
        
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador, turnoActual);

        ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual + 1, tablero, ladron);
        CartaDescubrimiento carta = new CartaDescubrimiento(turnoActual);

        caso.usarCartaDesarrollo(carta, jugador, contexto, proveedorMockeado);

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

        Coordenadas origen1 = new Coordenadas(3,3);
        Coordenadas destino1 = new Coordenadas(3,4);
        Coordenadas origen2 = new Coordenadas(3,4);
        Coordenadas destino2 = new Coordenadas(3,5);
        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        when(proveedorMockeado.pedirCoordenadasAlUsuario()).thenReturn(origen1, destino1, origen2, destino2);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(cartas);

        caso.comprarCartaDesarrollo(mazo, jugador, turnoActual);
        
        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual + 1, tablero, ladron);
        caso.usarCartaDesarrollo(new CartaConstruccionCarretera(), jugador ,contextoNuevo, proveedorMockeado);
        assertTrue(tablero.estaConstruidoCon(new Carretera(), origen1, jugador));
        assertTrue(tablero.estaConstruidoCon(new Carretera(), destino1, jugador));
        assertTrue(tablero.estaConstruidoCon(new Carretera(), origen2, jugador));
        assertTrue(tablero.estaConstruidoCon(new Carretera(), destino2, jugador));
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

        Coordenadas origen1 = new Coordenadas(7,7);
        Coordenadas destino1 = new Coordenadas(7,8);
        Coordenadas origen2 = new Coordenadas(-1,1);
        Coordenadas destino2 = new Coordenadas(-3,1);

        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        when(proveedorMockeado.pedirCoordenadasAlUsuario()).thenReturn(origen1, destino1, origen2, destino2);


        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(cartas);
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador, turnoActual);

        //turno 2
        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual + 1, tablero, ladron); //incluyendo destinoLadron
        
        assertThrows(CoordenadasInvalidasException.class, () -> {
            caso.usarCartaDesarrollo(new CartaConstruccionCarretera(), jugador ,contextoNuevo, proveedorMockeado);
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
        
        Coordenadas origen1 = new Coordenadas(3,3);
        Coordenadas destino1 = new Coordenadas(3,4);
        Coordenadas origen2 = new Coordenadas(3,4);
        Coordenadas destino2 = new Coordenadas(3,5);

        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        when(proveedorMockeado.pedirCoordenadasAlUsuario()).thenReturn(origen1, destino1, origen2, destino2);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(cartas);
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador, turnoActual);

        //turno 2
        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual + 1, tablero, ladron); //incluyendo destinoLadron
        
        assertThrows(NoEsPosibleConstruirException.class, () -> {
            caso.usarCartaDesarrollo(new CartaConstruccionCarretera(), jugador ,contextoNuevo, proveedorMockeado);
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
        
        Coordenadas origen1 = new Coordenadas(1,1);
        Coordenadas destino1 = new Coordenadas(1,2);
        Coordenadas origen2 = new Coordenadas(1,2);
        Coordenadas destino2 = new Coordenadas(1,3);

        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        when(proveedorMockeado.pedirCoordenadasAlUsuario()).thenReturn(origen1, destino1, origen2, destino2);

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazo = new MazoTrucado(cartas);
        //turno 1
        caso.comprarCartaDesarrollo(mazo, jugador1, turnoActual);

        //turno 2
        ContextoCartaDesarrollo contextoNuevo = new ContextoCartaDesarrollo(jugador1, jugadores, turnoActual + 1, tablero, ladron); //incluyendo destinoLadron
        
        assertThrows(NoEsPosibleConstruirException.class, () -> {
            caso.usarCartaDesarrollo(new CartaConstruccionCarretera(), jugador1 ,contextoNuevo, proveedorMockeado);
        });
    }
} 