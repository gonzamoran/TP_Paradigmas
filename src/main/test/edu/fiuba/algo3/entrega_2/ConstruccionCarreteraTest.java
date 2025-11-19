package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConstruccionCarreteraTest {

    @Test
    public void test01ConstruirCarreteraConsumeRecursosCorrectos() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(), 1);
        jugador.agregarRecurso(new Ladrillo(), 1);

        Coordenada coordenadaOK = new Coordenada(0, 0);

        boolean construido = tablero.construirCarretera(jugador, coordenadaOK);

        assertTrue(construido);
        assertEquals(0, jugador.getCantidadRecurso(TipoRecurso.MADERA));
        assertEquals(0, jugador.getCantidadRecurso(TipoRecurso.LADRILLO));
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
        jugador.agregarRecurso(new Madera(), 2);
        jugador.agregarRecurso(new Ladrillo(), 2);

        Coordenada coordenada1 = new Coordenada(0, 0);
        Coordenada coordenada2 = new Coordenada(0, 1);

        tablero.construirCarretera(jugador, coordenada1);
        boolean segundaCarretera = tablero.construirCarretera(jugador, coordenada2);

        assertTrue(segundaCarretera);
        assertTrue(tablero.hayCarreteraEn(coordenada2, jugador));
    }

    @Test
    public void test04ConstruirCarreteraNoAdyacenteAOtraNoEsPermitido() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(), 2);
        jugador.agregarRecurso(new Ladrillo(), 2);

        Coordenada coordenada1 = new Coordenada(0, 0);
        Coordenada coordenadaNoAdyacente = new Coordenada(3, 3);

        tablero.construirCarretera(jugador, coordenada1);
        boolean segundaCarretera = tablero.construirCarretera(jugador, coordenadaNoAdyacente);

        assertFalse(segundaCarretera);
        assertFalse(tablero.hayCarreteraEn(coordenadaNoAdyacente, jugador));
    }

    @Test
    public void test05ConstuirCarreteraAdyacenteAPobladoEsPermitido() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(), 3);
        jugador.agregarRecurso(new Ladrillo(), 3);
        jugador.agregarRecurso(new Lana(), 1);
        jugador.agregarRecurso(new Trigo(), 1);

        Coordenadas coordenadaPoblado = new Coordenadas(0, 0);
        Coordenadas coordenadaCarretera = new Coordenadas(0, 1);

        tablero.construirPoblado(jugador, coordenadaPoblado);
        boolean carreteraConstruida = tablero.construirCarretera(jugador, coordenadaCarretera);

        assertTrue(carreteraConstruida);
        assertTrue(tablero.hayCarreteraEn(coordenadaCarretera, jugador));
    }
}