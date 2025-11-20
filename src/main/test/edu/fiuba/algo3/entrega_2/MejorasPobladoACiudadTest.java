package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.excepciones.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MejorasPobladoACiudadTest {

    @Test
    public void test01ConstruirCiudadEnPobladoExistenteDisminuyeRecursosDelJugador() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        Poblado poblado = new Poblado();

        Coordenadas coordenadaPoblado = new Coordenadas(0, 0);

        jugador.agregarRecurso(new Madera(2));
        jugador.agregarRecurso(new Ladrillo(2));
        jugador.agregarRecurso(new Lana(2));
        jugador.agregarRecurso(new Grano(3));

        tablero.colocarEn(coordenadaPoblado, poblado, jugador); // colocarEn o colocarConstruccionInicial

        assertDoesNotThrow(() -> {
            tablero.mejorarPobladoACiudad(coordenadaPoblado, jugador);
        }, "No se puede mejorar la construccion.");
        assertEquals(0, jugador.obtenerCantidadRecurso(new Madera(0)));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Ladrillo(0)));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Lana(0)));
        assertEquals(1, jugador.obtenerCantidadRecurso(new Grano(0)));
    }

    @Test
    public void test02MejorarPobladoACiudadSinRecursosLanzaExcepcion() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        Poblado poblado = new Poblado();

        Coordenadas coordenadasPoblado = new Coordenadas(1, 1);

        tablero.colocarEn(coordenadasPoblado, poblado, jugador);

        assertThrows(ErrorAlMejorarConstruccionException.class, () -> {
            tablero.mejorarPobladoACiudad(coordenadasPoblado, jugador);
        });
        assertTrue(tablero.hayPobladoEn(coordenadasPoblado, jugador));
        assertFalse(tablero.hayCiudadEn(coordenadasPoblado, jugador));
    }

    @Test
    public void test03MejorarPobladoACiudadSinPobladoExistenteFalla() {
        Jugador jugador = new Jugador("Jugador1");
        Tablero tablero = new Tablero();

        jugador.agregarRecurso(new Madera(2));
        jugador.agregarRecurso(new Ladrillo(2));
        jugador.agregarRecurso(new Lana(2));
        jugador.agregarRecurso(new Grano(2));

        Coordenadas coordenadas = new Coordenadas(2, 3);

        assertThrows(ErrorAlMejorarConstruccionException.class, () -> {
            tablero.mejorarPobladoACiudad(coordenadas, jugador);
        });
        assertFalse(tablero.hayPobladoEn(coordenadas, jugador));
        assertFalse(tablero.hayCiudadEn(coordenadas, jugador));
    }

    @Test
    public void test04MejorarPobladoACiudad_DeOtroJugador_Falla() {
        Jugador jugador1 = new Jugador("Jugador1");
        Jugador jugador2 = new Jugador("Jugador2");
        Tablero tablero = new Tablero();
        Poblado poblado = new Poblado();

        // dar recursos a j1
        jugador1.agregarRecurso(new Madera(2));
        jugador1.agregarRecurso(new Ladrillo(2));
        jugador1.agregarRecurso(new Lana(2));
        jugador1.agregarRecurso(new Grano(3));
        // dar recursos a j2
        jugador2.agregarRecurso(new Madera(2));
        jugador2.agregarRecurso(new Ladrillo(2));
        jugador2.agregarRecurso(new Lana(2));
        jugador2.agregarRecurso(new Grano(3));

        Coordenadas coordenadasPoblado = new Coordenadas(2, 3);

        tablero.colocarEn(coordenadasPoblado, poblado, jugador1);

        assertThrows(ErrorAlMejorarConstruccionException.class, () -> {
            tablero.mejorarPobladoACiudad(coordenadasPoblado, jugador2);
        }); // j2 intenta mejorar poblado de j1
        assertTrue(tablero.hayPobladoEn(coordenadasPoblado, jugador1)); // Sigue siendo de jugador1
        assertFalse(tablero.hayCiudadEn(coordenadasPoblado, jugador2)); // No es ciudad de jugador2
    }

    @Test
    public void test05MejorarPobladoACiudad_PuntosVictoriaCorrectos() {
        Jugador jugador = new Jugador("Jugador1");
        Tablero tablero = new Tablero();
        Poblado poblado = new Poblado();

        jugador.agregarRecurso(new Madera(2));
        jugador.agregarRecurso(new Ladrillo(2));
        jugador.agregarRecurso(new Lana(2));
        jugador.agregarRecurso(new Grano(3));

        Coordenadas coordenadasPoblado = new Coordenadas(2, 3);

        int puntosIniciales = jugador.calculoPuntosVictoria();

        // poblado +1 PV
        tablero.colocarEn(coordenadasPoblado, poblado, jugador);
        int puntosConPoblado = jugador.calculoPuntosVictoria();

        // mejorar a ciudad +1 PV extra
        tablero.mejorarPobladoACiudad(coordenadasPoblado, jugador);
        int puntosConCiudad = jugador.calculoPuntosVictoria();

        assertEquals(puntosIniciales + 1, puntosConPoblado); // Poblado = +1
        assertEquals(puntosIniciales + 2, puntosConCiudad); // Ciudad = +2
    }

}