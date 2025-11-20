package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.construcciones.Ciudad;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.Recurso;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.fiuba.algo3.modelo.excepciones.NoEsPosibleConstruirException;

import java.lang.invoke.CallSite;

import org.junit.Test;

import edu.fiuba.algo3.entrega_2.casosDeUso.CasoDeUsoConstruccion;

public class CasoDeUsoConstruccionTest {
    
    @Test
    public void test01ConstruyoUnPobladoYSeConsumenLosRecursosCorrectamente() {
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Jugador 1");
        var recursos = new Recurso[] {
            new Madera(1),
            new Ladrillo(1),
            new Lana(1),
            new Grano(1),
            new Piedra(1)
        };

        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugador, recursos);
        caso.construirEn(new Coordenadas(2,2), new Poblado());

        assertTrue(tablero.hayPobladoEn( new Coordenadas(2,2), jugador));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Madera(0)));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Ladrillo(0)));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Lana(0)));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Grano(0)));
        assertEquals(1, jugador.obtenerCantidadRecurso(new Piedra(0)));
    }

    @Test
    public void test02ConstruyoUnaCiudadYSeConsumenLosRecursosCorrectamente() {
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Jugador 1");
        var recursos = new Recurso[] {
            new Grano(3),
            new Piedra(3),
            new Madera(1),
            new Ladrillo(1),
            new Lana(1)
        };

        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugador, recursos);
        caso.construirEn(new Coordenadas(2,2), new Poblado());
        caso.construirEn(new Coordenadas(2,2), new Ciudad());

        assertTrue(tablero.hayCiudadEn( new Coordenadas(2,2), jugador));
        assertFalse(tablero.hayPobladoEn( new Coordenadas(2,2), jugador));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Grano(0)));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Piedra(0)));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Madera(0)));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Ladrillo(0)));
        assertEquals(0, jugador.obtenerCantidadRecurso(new Lana(0)));
    }

    @Test
    public void test03NoSePuedeConstruirUnaCiudadSiNoHayPobladoPrevio() {
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Jugador 1");
        var recursos = new Recurso[] {
            new Grano(2),
            new Piedra(3)
        };
        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugador, recursos);

        assertThrows(NoEsPosibleConstruirException.class, () -> caso.construirEn(new Coordenadas(2,5), new Ciudad()));
    }

    @Test
    public void test04NoSePuedeConstruirUnPobladoSinRecursosSuficientes() {
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Jugador 1");
        var recursos = new Recurso[] {
            new Madera(1),
            new Ladrillo(1),
            new Lana(1)
        };
        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugador, recursos);
        assertThrows(NoEsPosibleConstruirException.class, () -> caso.construirEn(new Coordenadas(2,2), new Poblado()));
    }

    @Test
    public void test05MejorarPobladoACiudadAumentaLosPVsDeLaConstruccion() {
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Jugador 1");
        var recursos = new Recurso[] {
            new Madera(1),
            new Ladrillo(1),
            new Lana(1),
            new Grano(3),
            new Piedra(3)
        };
        CasoDeUsoConstruccion caso = new CasoDeUsoConstruccion(tablero, jugador, recursos);
        caso.construirEn(new Coordenadas(2,2), new Poblado());

        assertEquals(1, jugador.calculoPuntosVictoria());
        caso.construirEn(new Coordenadas(2,2), new Ciudad());
        assertEquals(2, jugador.calculoPuntosVictoria());
    }

}