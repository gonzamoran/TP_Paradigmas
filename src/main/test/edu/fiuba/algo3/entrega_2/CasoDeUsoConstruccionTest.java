package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.construcciones.Carretera;
import edu.fiuba.algo3.modelo.construcciones.Ciudad;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.Recurso;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.fiuba.algo3.modelo.excepciones.NoEsPosibleConstruirException;
import edu.fiuba.algo3.modelo.excepciones.PosInvalidaParaConstruirException;

import java.lang.invoke.CallSite;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.fiuba.algo3.modelo.acciones.CasoDeUsoConstruccion;

import java.util.ArrayList;
public class CasoDeUsoConstruccionTest {

    @Test
    public void test01ConstruyoUnPobladoYSeConsumenLosRecursosCorrectamente() {
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Jugador 1");
        jugador.agregarRecurso(new Madera(1));
        jugador.agregarRecurso(new Ladrillo(1));
        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Grano(1));
        jugador.agregarRecurso(new Piedra(1));

        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador);

        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);
        caso.construirEn(new Coordenadas(2, 2), new Poblado(), jugador);

        assertTrue(tablero.estaConstruidoCon(new Poblado(), new Coordenadas(2, 2), jugador));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Madera()));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Ladrillo()));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Lana()));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Grano()));
        assertEquals(1, jugador.obtenerCantidadRecurso(new Piedra()));
    }

    @Test
    public void test02ConstruyoUnaCiudadYSeConsumenLosRecursosCorrectamente() {
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Jugador 1");
        jugador.agregarRecurso(new Grano(3));
        jugador.agregarRecurso(new Piedra(3));
        jugador.agregarRecurso(new Madera(1));
        jugador.agregarRecurso(new Ladrillo(1));
        jugador.agregarRecurso(new Lana(1));

        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador);

        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);
        caso.construirEn(new Coordenadas(2, 2), new Poblado(), jugador);
        caso.construirEn(new Coordenadas(2, 2), new Ciudad(), jugador);

        assertTrue(tablero.estaConstruidoCon(new Ciudad(), new Coordenadas(2, 2), jugador));
        assertFalse(tablero.estaConstruidoCon(new Poblado(), new Coordenadas(2, 2), jugador));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Grano()));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Piedra()));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Madera()));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Ladrillo()));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Lana()));
    }

    @Test
    public void test03NoSePuedeConstruirUnaCiudadSiNoHayPobladoPrevio() {
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Jugador 1");
        jugador.agregarRecurso(new Grano(2));
        jugador.agregarRecurso(new Piedra(3));

        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador);
        
        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);

        assertThrows(NoEsPosibleConstruirException.class, () -> caso.construirEn(new Coordenadas(2, 5), new Ciudad(), jugador));
    }

    @Test
    public void test04NoSePuedeConstruirUnPobladoSinRecursosSuficientes() {
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Jugador 1");
        jugador.agregarRecurso(new Madera(1));
        jugador.agregarRecurso(new Ladrillo(1));
        jugador.agregarRecurso(new Lana(1));

        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador);

        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);
        assertThrows(NoEsPosibleConstruirException.class, () -> caso.construirEn(new Coordenadas(2, 2), new Poblado(), jugador));
    }

    @Test
    public void test05MejorarPobladoACiudadAumentaLosPVsDeLaConstruccion() {
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Jugador 1");
        jugador.agregarRecurso(new Madera(1));
        jugador.agregarRecurso(new Ladrillo(1));
        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Grano(3));
        jugador.agregarRecurso(new Piedra(3));

        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador);

        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);
        caso.construirEn(new Coordenadas(2, 2), new Poblado(), jugador);

        assertEquals(1, jugador.calculoPuntosVictoria());
        caso.construirEn(new Coordenadas(2, 2), new Ciudad(), jugador);
        assertEquals(2, jugador.calculoPuntosVictoria());
    }

    @Test
    public void test06ConstruirCarreteraEfectivamenteLoHace() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(10));
        jugador.agregarRecurso(new Ladrillo(10));
        jugador.agregarRecurso(new Piedra(10));
        jugador.agregarRecurso(new Lana(10));
        jugador.agregarRecurso(new Grano(10));

        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador);

        Coordenadas coordenadaExtremo1 = new Coordenadas(2, 1);
        Coordenadas coordenadaExtremo2 = new Coordenadas(2, 2);

        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);
        caso.construirEn(coordenadaExtremo1, new Poblado(), jugador);
        caso.construirCarretera(coordenadaExtremo1, coordenadaExtremo2, jugador);

        assertTrue(tablero.estaConstruidoCon(new Carretera(), coordenadaExtremo1, jugador));
        assertTrue(tablero.estaConstruidoCon(new Carretera(), coordenadaExtremo2, jugador));
    }


    @Test 
    public void test07ConstruirCarreteraSinPobladoNoPermiteConstruir() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(2));
        jugador.agregarRecurso(new Ladrillo(2));

        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador);
        
        Coordenadas coordenadaExtremo1 = new Coordenadas(1, 1);
        Coordenadas coordenadaExtremo2 = new Coordenadas(1, 2);

        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);
        assertThrows(NoEsPosibleConstruirException.class, () -> caso.construirCarretera(coordenadaExtremo1, coordenadaExtremo2, jugador));

    }

    @Test
    public void test08ColocarCarreteraAdyacenteACiudadEstaPermitido() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(10));
        jugador.agregarRecurso(new Ladrillo(10));
        jugador.agregarRecurso(new Piedra(10));
        jugador.agregarRecurso(new Lana(10));
        jugador.agregarRecurso(new Grano(10));

        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador);
        
        Coordenadas coordenadaExtremo1 = new Coordenadas(4, 4);
        Coordenadas coordenadaExtremo2 = new Coordenadas(4, 5);

        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);
        caso.construirEn(coordenadaExtremo1, new Poblado(), jugador);
        caso.construirEn(coordenadaExtremo1, new Ciudad(), jugador);
        caso.construirCarretera(coordenadaExtremo1, coordenadaExtremo2, jugador);

        assertTrue(tablero.estaConstruidoCon(new Carretera(), coordenadaExtremo1, jugador));
        assertTrue(tablero.estaConstruidoCon(new Carretera(), coordenadaExtremo2, jugador));
    }

    @Test
    public void test09ConstruirUnaCarreteraConsumeLosRecursosCorrectamente() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(10));
        jugador.agregarRecurso(new Ladrillo(10));
        jugador.agregarRecurso(new Piedra(10));
        jugador.agregarRecurso(new Lana(10));
        jugador.agregarRecurso(new Grano(10));

        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador);
        
        Coordenadas coordenadaExtremo1 = new Coordenadas(3, 3);
        Coordenadas coordenadaExtremo2 = new Coordenadas(3, 4);

        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);
        caso.construirEn(coordenadaExtremo1, new Poblado(), jugador);
        caso.construirCarretera(coordenadaExtremo1, coordenadaExtremo2, jugador);

        assertEquals(8, jugador.obtenerCantidadRecurso(new Madera()));
        assertEquals(8, jugador.obtenerCantidadRecurso(new Ladrillo()));
        
        /*
        Poblado; 1 madera, 1 ladrillo, 1 lana, 1 grano
        Camino; 1 madera, 1 ladrillo
        Recursos gastados: 2 madera, 2 ladrillo, 1 lana, 1 grano
        */ 
    }

    @Test
    public void test10NoSePuedeConstruirUnaCarreteraSiNoHayRecursosSuficientes() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(1));
        jugador.agregarRecurso(new Ladrillo(1));
        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Grano(1));

        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador);

        Coordenadas coordenadaExtremo1 = new Coordenadas(5, 5);
        Coordenadas coordenadaExtremo2 = new Coordenadas(5, 6);
        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);
        tablero.colocarEn(coordenadaExtremo1, new Poblado(), jugador);
        assertThrows(NoEsPosibleConstruirException.class, () -> caso.construirCarretera(coordenadaExtremo1, coordenadaExtremo2, jugador));
    }

    @Test
    public void test11NoSePuedeConstruirUnaCarreteraEntreCoordenadasNoAdyacentes() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(10));
        jugador.agregarRecurso(new Ladrillo(10));
        jugador.agregarRecurso(new Piedra(10));
        jugador.agregarRecurso(new Lana(10));
        jugador.agregarRecurso(new Grano(10));

        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador);

        Coordenadas coordenadaExtremo1 = new Coordenadas(2, 5);
        Coordenadas coordenadaExtremo2 = new Coordenadas(1, 1);

        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);
        caso.construirEn(coordenadaExtremo1, new Poblado(), jugador);
        assertThrows(PosInvalidaParaConstruirException.class, () -> caso.construirCarretera(coordenadaExtremo1, coordenadaExtremo2, jugador));
    }

    @Test
    public void test12NoSePuedeConstruirUnaCarreteraSiNingunoDeLosExtremosEsAdyacenteAUnaConstruccionPropia() {
        Jugador jugador1 = new Jugador("Jugador 1");
        Jugador jugador2 = new Jugador("Jugador 2");
        Tablero tablero = new Tablero();

        jugador2.agregarRecurso(new Madera(10));
        jugador2.agregarRecurso(new Ladrillo(10));
        jugador2.agregarRecurso(new Lana(10));
        jugador2.agregarRecurso(new Grano(10));

        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);

        jugador1.agregarRecurso(new Madera(10));
        jugador1.agregarRecurso(new Ladrillo(10));
        
        
        Coordenadas coordenadaExtremo1 = new Coordenadas(2, 2);
        Coordenadas coordenadaExtremo2 = new Coordenadas(2, 3);
        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);

        caso.construirEn(coordenadaExtremo1, new Poblado(), jugador2);

        assertThrows(NoEsPosibleConstruirException.class, () -> caso.construirCarretera(coordenadaExtremo1, coordenadaExtremo2, jugador1));
    }

    @Test
    public void test13SePuedeConstruirUnaCarreteraAdyacenteAOtraCarreteraPropia() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(10));
        jugador.agregarRecurso(new Ladrillo(10));
        jugador.agregarRecurso(new Piedra(10));
        jugador.agregarRecurso(new Lana(10));
        jugador.agregarRecurso(new Grano(10));

        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador);
        
        Coordenadas coordenadaExtremo1 = new Coordenadas(3, 1);
        Coordenadas coordenadaExtremo2 = new Coordenadas(3, 2);
        Coordenadas coordenadaExtremo3 = new Coordenadas(3, 3);

        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);
        caso.construirEn(coordenadaExtremo1, new Poblado(), jugador);
        caso.construirCarretera(coordenadaExtremo1, coordenadaExtremo2, jugador);
        caso.construirCarretera(coordenadaExtremo2, coordenadaExtremo3, jugador);

        assertTrue(tablero.estaConstruidoCon(new Carretera(), coordenadaExtremo2, jugador));
        assertTrue(tablero.estaConstruidoCon(new Carretera(), coordenadaExtremo3, jugador));
    }

    @Test
    public void test14ConstruirCarreteraNoEliminaConstruccionAnterior(){
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(10));
        jugador.agregarRecurso(new Ladrillo(10));
        jugador.agregarRecurso(new Piedra(10));
        jugador.agregarRecurso(new Lana(10));
        jugador.agregarRecurso(new Grano(10));

        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador);
        
        Coordenadas coordenadaExtremo1 = new Coordenadas(3, 4);
        Coordenadas coordenadaExtremo2 = new Coordenadas(3, 5);

        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);
        caso.construirEn(coordenadaExtremo1, new Poblado(), jugador);
        caso.construirCarretera(coordenadaExtremo1, coordenadaExtremo2, jugador);

        assertTrue(tablero.estaConstruidoCon(new Poblado(), coordenadaExtremo1, jugador));
        assertTrue(tablero.estaConstruidoCon(new Carretera(), coordenadaExtremo1, jugador));
        assertTrue(tablero.estaConstruidoCon(new Carretera(), coordenadaExtremo2, jugador));

    }

    @Test
    public void test15SeLeOtorgaGranRutaComercialAlJugadorQueConstruyeLaCarreteraMasLarga(){
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        
        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador);

        jugador.agregarRecurso(new Madera(10));
        jugador.agregarRecurso(new Ladrillo(10));
        jugador.agregarRecurso(new Piedra(10));
        jugador.agregarRecurso(new Lana(10));
        jugador.agregarRecurso(new Grano(10));

        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);

        Coordenadas coordenadaPoblado = new Coordenadas(1, 0);
        caso.construirEn(coordenadaPoblado, new Poblado(), jugador);

        for (int i = 0; i < 5; i++) {
            Coordenadas extremo1 = new Coordenadas(1, i);
            Coordenadas extremo2 = new Coordenadas(1, i + 1);
            caso.construirCarretera(extremo1, extremo2, jugador);
        }
        assertEquals(5, jugador.obtenerCaminoMasLargoDelJugador());
        assertEquals(3, jugador.calculoPuntosVictoria());
    }

    @Test
    public void test16UnJugadorInterrumpeLaCarreteraMasLargaDeOtroYLeSacaGranRutaComercial(){
        Jugador jugador1 = new Jugador("Jugador 1");
        Jugador jugador2 = new Jugador("Jugador 2");
        
        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        Tablero tablero = new Tablero();
        jugador1.agregarRecurso(new Madera(7));
        jugador1.agregarRecurso(new Ladrillo(7));
        jugador1.agregarRecurso(new Piedra(1));
        jugador1.agregarRecurso(new Lana(1));
        jugador1.agregarRecurso(new Grano(1));

        jugador2.agregarRecurso(new Madera(7));
        jugador2.agregarRecurso(new Ladrillo(7));
        jugador2.agregarRecurso(new Lana(2));
        jugador2.agregarRecurso(new Grano(2));

        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);
        Coordenadas coordenadaPoblado1 = new Coordenadas(1, 0);
        

        caso.construirEn(coordenadaPoblado1, new Poblado(), jugador1);
        
        
        Coordenadas pobladoJugador2 = new Coordenadas(0, 4);
        Coordenadas extremoA = new Coordenadas(0, 4);
        Coordenadas extremoB = new Coordenadas(0, 3);  
        Coordenadas extremoC = new Coordenadas(1, 3);

        caso.construirEn(pobladoJugador2, new Poblado(), jugador2);
        caso.construirCarretera(extremoA, extremoB, jugador2);
        caso.construirCarretera(extremoB, extremoC, jugador2);
        
        

        for (int i = 0; i < 5; i++) {
            Coordenadas extremo1 = new Coordenadas(1, i);
            Coordenadas extremo2 = new Coordenadas(1, i + 1);
            caso.construirCarretera(extremo1, extremo2, jugador1);
        }

        assertEquals(5, jugador1.obtenerCaminoMasLargoDelJugador());
        assertEquals(3, jugador1.calculoPuntosVictoria());
        assertEquals(1, jugador2.calculoPuntosVictoria());
        
        caso.construirEn(extremoC, new Poblado(), jugador2);
        
        assertEquals(3, jugador1.obtenerCaminoMasLargoDelJugador());
        assertEquals(1, jugador1.calculoPuntosVictoria());
        assertEquals(2, jugador2.obtenerCaminoMasLargoDelJugador());
        assertEquals(2, jugador2.calculoPuntosVictoria());
    }

    @Test
    public void test17JugadorLeRobaGranRutaComercialAOtroJugador(){
        Jugador jugador1 = new Jugador("Jugador 1");
        Jugador jugador2 = new Jugador("Jugador 2");
        
        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        Tablero tablero = new Tablero();
        jugador1.agregarRecurso(new Madera(10));
        jugador1.agregarRecurso(new Ladrillo(10));
        jugador1.agregarRecurso(new Piedra(1));
        jugador1.agregarRecurso(new Lana(1));
        jugador1.agregarRecurso(new Grano(1));

        jugador2.agregarRecurso(new Madera(10));
        jugador2.agregarRecurso(new Ladrillo(10));
        jugador2.agregarRecurso(new Lana(2));
        jugador2.agregarRecurso(new Grano(2));

        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);
        caso.construirEn(new Coordenadas(0, 1), new Poblado(), jugador1);
        caso.construirEn(new Coordenadas(1, 0), new Poblado(), jugador2);

        for (int i = 1; i < 6; i++) {
            Coordenadas extremo1 = new Coordenadas(0, i); 
            Coordenadas extremo2 = new Coordenadas(0, i + 1);
            caso.construirCarretera(extremo1, extremo2, jugador1);
        }
        

        for (int i = 0; i < 5; i++) {
            Coordenadas extremo1 = new Coordenadas(1, i);
            Coordenadas extremo2 = new Coordenadas(1, i + 1);
            caso.construirCarretera(extremo1, extremo2, jugador2);
        }
        
        assertEquals(3, jugador1.calculoPuntosVictoria());
        
        caso.construirCarretera(new Coordenadas(1,3), new Coordenadas(0, 3), jugador2);
        caso.construirEn(new Coordenadas(0,3),new Poblado(), jugador2);

        assertEquals(1, jugador1.calculoPuntosVictoria());
        assertEquals(4, jugador2.calculoPuntosVictoria());
        assertEquals(3, jugador1.obtenerCaminoMasLargoDelJugador());
        assertEquals(5, jugador2.obtenerCaminoMasLargoDelJugador());
    }

    @Test
    public void test18Jugador2LeCortaCaminoAJugador1PeroJugador1SigueSiendoElQueTieneLaCarreteraMasLarga(){
        Jugador jugador1 = new Jugador("Jugador 1");
        Jugador jugador2 = new Jugador("Jugador 2");
        
        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        Tablero tablero = new Tablero();
        jugador1.agregarRecurso(new Madera(10));
        jugador1.agregarRecurso(new Ladrillo(10));
        jugador1.agregarRecurso(new Piedra(1));
        jugador1.agregarRecurso(new Lana(1));
        jugador1.agregarRecurso(new Grano(1));

        jugador2.agregarRecurso(new Madera(10));
        jugador2.agregarRecurso(new Ladrillo(10));
        jugador2.agregarRecurso(new Lana(2));
        jugador2.agregarRecurso(new Grano(2));

        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);
        caso.construirEn(new Coordenadas(0, 1), new Poblado(), jugador1);
        caso.construirEn(new Coordenadas(1, 0), new Poblado(), jugador2);

        for (int i = 1; i < 7; i++) {
            Coordenadas extremo1 = new Coordenadas(0, i); 
            Coordenadas extremo2 = new Coordenadas(0, i + 1);
            caso.construirCarretera(extremo1, extremo2, jugador1);
        }
        caso.construirCarretera(new Coordenadas(0,7), new Coordenadas(1,7), jugador1);

        for (int i = 0; i < 5; i++) {
            Coordenadas extremo1 = new Coordenadas(1, i);
            Coordenadas extremo2 = new Coordenadas(1, i + 1);
            caso.construirCarretera(extremo1, extremo2, jugador2);
        }
        
        assertEquals(3, jugador1.calculoPuntosVictoria());
        assertEquals(7, jugador1.obtenerCaminoMasLargoDelJugador());
        caso.construirCarretera(new Coordenadas(1,3), new Coordenadas(0, 3), jugador2);
        caso.construirEn(new Coordenadas(0,3),new Poblado(), jugador2);

        assertEquals(3, jugador1.calculoPuntosVictoria());
        assertEquals(2, jugador2.calculoPuntosVictoria());
        assertEquals(5, jugador1.obtenerCaminoMasLargoDelJugador());
        assertEquals(5, jugador2.obtenerCaminoMasLargoDelJugador());
    }
    @Test
    public void test19_3JugadoresSeDisputanPorLaGranRutaComercial(){
        Jugador jugador1 = new Jugador("Jugador 1");
        Jugador jugador2 = new Jugador("Jugador 2");
        Jugador jugador3 = new Jugador("Jugador 3");
        
        var jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        jugadores.add(jugador3);
        Tablero tablero = new Tablero();
        jugador1.agregarRecurso(new Madera(10));
        jugador1.agregarRecurso(new Ladrillo(10));
        jugador1.agregarRecurso(new Lana(1));
        jugador1.agregarRecurso(new Grano(1));

        jugador2.agregarRecurso(new Madera(10));
        jugador2.agregarRecurso(new Ladrillo(10));
        jugador2.agregarRecurso(new Lana(1));
        jugador2.agregarRecurso(new Grano(1));

        jugador3.agregarRecurso(new Madera(10));
        jugador3.agregarRecurso(new Ladrillo(10));
        jugador3.agregarRecurso(new Lana(2));
        jugador3.agregarRecurso(new Grano(2));

        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugadores);
        caso.construirEn(new Coordenadas(0, 1), new Poblado(), jugador1);
        caso.construirEn(new Coordenadas(1, 0), new Poblado(), jugador2);
        caso.construirEn(new Coordenadas(2,3), new Poblado(), jugador3);

        for (int i = 1; i < 7; i++) {
            Coordenadas extremo1 = new Coordenadas(0, i); 
            Coordenadas extremo2 = new Coordenadas(0, i + 1);
            caso.construirCarretera(extremo1, extremo2, jugador1);
        }

        for (int i = 0; i < 8; i++) {
            Coordenadas extremo1 = new Coordenadas(1, i);
            Coordenadas extremo2 = new Coordenadas(1, i + 1);
            caso.construirCarretera(extremo1, extremo2, jugador2);
        }

        caso.construirCarretera(new Coordenadas(2,3), new Coordenadas(2, 4), jugador3);
        caso.construirCarretera(new Coordenadas(2,4), new Coordenadas(1, 4), jugador3);
        

        assertEquals(1, jugador1.calculoPuntosVictoria());
        assertEquals(3, jugador2.calculoPuntosVictoria());
        assertEquals(1, jugador3.calculoPuntosVictoria());
        assertEquals(6, jugador1.obtenerCaminoMasLargoDelJugador());
        assertEquals(8, jugador2.obtenerCaminoMasLargoDelJugador());

        caso.construirEn(new Coordenadas(1,4), new Poblado(), jugador3);

        assertEquals(1, jugador2.calculoPuntosVictoria());
        assertEquals(2, jugador3.calculoPuntosVictoria());
        assertEquals(3, jugador1.calculoPuntosVictoria());
      
        assertEquals(6, jugador1.obtenerCaminoMasLargoDelJugador());
        assertEquals(4, jugador2.obtenerCaminoMasLargoDelJugador());
    }
}
