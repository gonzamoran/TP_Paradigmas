package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tablero.Tablero;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConstruccionPobladoTest {

    @Test
    public void test01ConstruirPobladoConsumeRecursosCorrectos() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();

        jugador.agregarRecurso(new Madera(), 1);
        jugador.agregarRecurso(new Ladrillo(), 1);
        jugador.agregarRecurso(new Lana(), 1);
        jugador.agregarRecurso(new Grano(), 1);

        Coordenadas coordenadaOK = new Coordenadas(0, 0);

        boolean construido = tablero.construirPoblado(jugador, coordenadaOK);

        assertTrue(construido);
        assertEquals(0, jugador.cantidadRecurso(new Madera()));
        assertEquals(0, jugador.cantidadRecurso(new Ladrillo()));
        assertEquals(0, jugador.cantidadRecurso(new Lana()));
        assertEquals(0, jugador.cantidadRecurso(new Grano()));
    }

    @Test
    public void test02ConstruirPobladoSinRecursosEsInvalido() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();

        Coordenadas coordenadaOK = new Coordenadas(0, 0);

        boolean construido = tablero.construirPoblado(jugador, coordenadaOK);

        assertFalse(construido);
        assertFalse(tablero.hayPobladoEn(coordenadaOK, jugador));
    }

    @Test
    public void test03ConstruirPobladoAdyacenteEsInvalido() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();

        jugador.agregarRecurso(new Madera(), 2);
        jugador.agregarRecurso(new Ladrillo(), 2);
        jugador.agregarRecurso(new Lana(), 2);
        jugador.agregarRecurso(new Grano(), 2);

        Coordenadas coordenadaPoblado1 = new Coordenadas(0, 0);
        Coordenadas coordenadaPoblado2 = new Coordenadas(0, 1);

        boolean primerPobladoConstruido = tablero.construirPoblado(jugador, coordenadaPoblado1);
        boolean segundoPobladoConstruido = tablero.construirPoblado(jugador, coordenadaPoblado2);

        assertTrue(primerPobladoConstruido);
        assertFalse(segundoPobladoConstruido);
    }

    @Test
    public void test04ConstruirPobladoNoAdyacenteEsValido() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();

        jugador.agregarRecurso(new Madera(), 2);
        jugador.agregarRecurso(new Ladrillo(), 2);
        jugador.agregarRecurso(new Lana(), 2);
        jugador.agregarRecurso(new Grano(), 2);

        Coordenadas coordenadaPoblado1 = new Coordenadas(0, 0);
        Coordenadas coordenadaPoblado2 = new Coordenadas(0, 4);

        tablero.construirPoblado(jugador, coordenadaPoblado1);
        boolean segundoPobladoConstruido = tablero.construirPoblado(jugador, coordenadaPoblado2);

        assertTrue(segundoPobladoConstruido);
        assertTrue(tablero.hayPobladoEn(coordenadaPoblado2, jugador));
    }

}
