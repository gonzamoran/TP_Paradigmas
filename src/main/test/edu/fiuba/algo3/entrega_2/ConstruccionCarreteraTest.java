package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.construcciones.Poblado;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConstruccionCarreteraTest {

    @Test
    public void test01ConstruirCarreteraConsumeRecursosCorrectos() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(1));
        jugador.agregarRecurso(new Ladrillo(1));

        Coordenadas coordenadaOK = new Coordenadas(0, 0);

        boolean construido = tablero.construirCarretera(jugador, coordenadaOK);

        assertTrue(construido);
        assertEquals(0, jugador.obtenerCantidadRecurso(new Madera(0)));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Ladrillo(0)));
    }

    @Test
    public void test02ConstruirCarreteraSinRecursosNoPermiteConstruir() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();

        Coordenadas coordenadaOK = new Coordenadas(0, 0);

        boolean construido = tablero.construirCarretera(jugador, coordenadaOK);

        assertFalse(construido);
        assertFalse(tablero.hayCarreteraEn(coordenadaOK, jugador));
    }

    @Test
    public void test03ConstruirCarreteraAdyacenteAOtraEsPermitido() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(2));
        jugador.agregarRecurso(new Ladrillo(2));

        Coordenadas coordenada1 = new Coordenadas(0, 0);
        Coordenadas coordenada2 = new Coordenadas(0, 1);

        tablero.construirCarretera(jugador, coordenada1);
        boolean segundaCarretera = tablero.construirCarretera(jugador, coordenada2);

        assertTrue(segundaCarretera);
        assertTrue(tablero.hayCarreteraEn(coordenada2, jugador));
    }

    @Test
    public void test04ConstruirCarreteraNoAdyacenteAOtraNoEsPermitido() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(2));
        jugador.agregarRecurso(new Ladrillo(2));

        Coordenadas coordenada1 = new Coordenadas(0, 0);
        Coordenadas coordenadaNoAdyacente = new Coordenadas(3, 3);

        tablero.construirCarretera(jugador, coordenada1);
        boolean segundaCarretera = tablero.construirCarretera(jugador, coordenadaNoAdyacente);

        assertFalse(segundaCarretera);
        assertFalse(tablero.hayCarreteraEn(coordenadaNoAdyacente, jugador));
    }

    @Test
    public void test05ConstuirCarreteraAdyacenteAPobladoEsPermitido() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        Poblado poblado = new Poblado();

        jugador.agregarRecurso(new Madera(3));
        jugador.agregarRecurso(new Ladrillo(3));
        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Grano(1));

        Coordenadas coordenadasPoblado = new Coordenadas(0, 0);
        Coordenadas coordenadasCarretera = new Coordenadas(0, 1);

        tablero.colocarEn(coordenadasPoblado, poblado,jugador);
        boolean carreteraConstruida = tablero.construirCarretera(jugador, coordenadasCarretera);

        assertTrue(carreteraConstruida);
        assertTrue(tablero.hayCarreteraEn(coordenadasCarretera, jugador));
    }
}