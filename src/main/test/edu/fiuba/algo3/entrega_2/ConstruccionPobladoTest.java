package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.construcciones.Poblado;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConstruccionPobladoTest {

    @Test
    public void test01ConstruirPobladoConsumeRecursosCorrectos() {
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Jugador 1");
        Poblado poblado = new Poblado();

        jugador.agregarRecurso(new Madera(1));
        jugador.agregarRecurso(new Ladrillo(1));
        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Grano(1));

        Coordenadas coordenadasOK = new Coordenadas(2, 4);

        tablero.colocarEn(coordenadasOK, poblado, jugador);

        assertTrue(tablero.hayPobladoEn(coordenadasOK, jugador));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Madera(0)));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Ladrillo(0)));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Lana(0)));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Grano(0)));
    }

    @Test
    public void test02ConstruirPobladoSinRecursosEsInvalido() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        Poblado poblado = new Poblado();

        Coordenadas coordenadasOK = new Coordenadas(2, 4);

        tablero.colocarEn(coordenadasOK, poblado, jugador);

        assertFalse(tablero.hayPobladoEn(coordenadasOK, jugador));
    }

    @Test
    public void test03ConstruirPobladoAdyacenteEsInvalido() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        Poblado poblado1 = new Poblado();
        Poblado poblado2 = new Poblado();

        jugador.agregarRecurso(new Madera(2));
        jugador.agregarRecurso(new Ladrillo(2));
        jugador.agregarRecurso(new Lana(2));
        jugador.agregarRecurso(new Grano(2));

        Coordenadas coordenadaPoblado1 = new Coordenadas(2, 4);
        Coordenadas coordenadaPoblado2 = new Coordenadas(2, 5);

        tablero.colocarEn(coordenadaPoblado1, poblado1, jugador);
        tablero.colocarEn(coordenadaPoblado2, poblado2, jugador);
        
        assertTrue(tablero.hayPobladoEn(coordenadaPoblado1, jugador));
        assertFalse(tablero.hayPobladoEn(coordenadaPoblado2, jugador));
    }

    @Test
    public void test04ConstruirPobladoNoAdyacenteEsValido() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        Poblado poblado1 = new Poblado();
        Poblado poblado2 = new Poblado();

        jugador.agregarRecurso(new Madera(2));
        jugador.agregarRecurso(new Ladrillo(2));
        jugador.agregarRecurso(new Lana(2));
        jugador.agregarRecurso(new Grano(2));

        Coordenadas coordenadaPoblado1 = new Coordenadas(2, 4);
        Coordenadas coordenadaPoblado2 = new Coordenadas(3, 4);

        tablero.colocarEn(coordenadaPoblado1, poblado1, jugador);
        tablero.colocarEn(coordenadaPoblado2, poblado2, jugador);

        assertTrue(tablero.hayPobladoEn(coordenadaPoblado1, jugador));
        assertTrue(tablero.hayPobladoEn(coordenadaPoblado2, jugador));
    }

}
