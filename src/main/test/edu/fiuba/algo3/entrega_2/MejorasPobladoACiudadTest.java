package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tiposRecurso.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MejorasPobladoACiudadTest {

    @Test
    public void test01ConstruirCiudadEnPobladoExistenteDisminuyeRecursosDelJugador() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();

        Coordenadas coordenadaPoblado = new Coordenadas(0, 0);

        jugador.agregarRecurso(new Madera(), 2);
        jugador.agregarRecurso(new Ladrillo(), 2);
        jugador.agregarRecurso(new Lana(), 2);
        jugador.agregarRecurso(new Grano(), 3);

        tablero.construirPoblado(jugador, coordenadaPoblado);

        boolean ciudadConstruida = tablero.mejorarPobladoACiudad(jugador, coordenadaPoblado);

        assertTrue(ciudadConstruida);
        assertEquals(0, jugador.cantidadRecurso(new Madera()));
        assertEquals(0, jugador.cantidadRecurso(new Ladrillo()));
        assertEquals(0, jugador.cantidadRecurso(new Lana()));
        assertEquals(1, jugador.cantidadRecurso(new Trigo()));
    }

    @Test
    public void test02MejorarPobladoACiudadSinRecursosLanzaExcepcion() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();

        Coordenadas coordenadaPoblado = new Coordenadas(1, 1);

        tablero.construirPoblado(jugador, coordenadaPoblado);

        boolean mejorado = tablero.mejorarPobladoACiudad(jugador, coordenadaPoblado);

        assertFalse(mejorado);
        assertTrue(tablero.hayPobladoEn(coordenadaPoblado));
        assertFalse(tablero.hayCiudadEn(coordenadaPoblado));
    }

    @Test
    public void test03MejorarPobladoACiudadSinPobladoExistenteFalla() {
        Jugador jugador = new Jugador("Jugador1");
        Tablero tablero = new Tablero();
        
        jugador.agregarRecurso(new Madera(), 2);
        jugador.agregarRecurso(new Ladrillo(), 2);
        jugador.agregarRecurso(new Lana(), 2);
        jugador.agregarRecurso(new Grano(), 3);

        Coordenada coordenada = new Coordenada(2, 3);

        boolean mejorado = tablero.mejorarPobladoACiudad(jugador, coordenada);

        assertFalse(mejorado);
        assertFalse(tablero.hayPobladoEn(coordenada, jugador));
        assertFalse(tablero.hayCiudadEn(coordenada, jugador));
    }

    @Test
    public void testMejorarPobladoACiudad_DeOtroJugador_Falla() {
        Jugador jugador1 = new Jugador("Jugador1");
        Jugador jugador2 = new Jugador("Jugador2");
        Tablero tablero = new Tablero();
        
        // dar recursos a j1
        jugador1.agregarRecurso(new Madera(), 2);
        jugador1.agregarRecurso(new Ladrillo(), 2);
        jugador1.agregarRecurso(new Lana(), 2);
        jugador1.agregarRecurso(new Grano(), 3);
        // dar recursos a j2
        jugador2.agregarRecurso(new Madera(), 2);
        jugador2.agregarRecurso(new Ladrillo(), 2);
        jugador2.agregarRecurso(new Lana(), 2);
        jugador2.agregarRecurso(new Grano(), 3);
        
        Coordenada coordenada = new Coordenada(2, 3);
        
        tablero.construirPoblado(jugador1, coordenada);

        boolean mejorado = tablero.mejorarPobladoACiudad(jugador2, coordenada);

        assertFalse(mejorado);
        assertTrue(tablero.hayPobladoEn(coordenada, jugador1)); // Sigue siendo de jugador1
        assertFalse(tablero.hayCiudadEn(coordenada, jugador2)); // No es ciudad de jugador2
    }

    @Test
    public void testMejorarPobladoACiudad_PuntosVictoriaCorrectos() {
        Jugador jugador = new Jugador("Jugador1");
        Tablero tablero = new Tablero();
        
        jugador.agregarRecurso(new Madera(), 2);
        jugador.agregarRecurso(new Ladrillo(), 2);
        jugador.agregarRecurso(new Lana(), 2);
        jugador.agregarRecurso(new Grano(), 3);
        
        Coordenada coordenada = new Coordenada(2, 3);
        
        int puntosIniciales = jugador.getPuntosVictoria();

        // poblado +1 PV
        tablero.construirPoblado(jugador, coordenada);
        int puntosConPoblado = jugador.getPuntosVictoria();

        // mejorar a ciudad +1 PV extra
        tablero.mejorarPobladoACiudad(jugador, coordenada);
        int puntosConCiudad = jugador.getPuntosVictoria();

        assertEquals(puntosIniciales + 1, puntosConPoblado); // Poblado = +1
        assertEquals(puntosIniciales + 2, puntosConCiudad);  // Ciudad = +2
    }
}