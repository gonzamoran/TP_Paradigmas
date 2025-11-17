package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.*;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Produccion;

import edu.fiuba.algo3.entrega_1.casosDeUso.CasoDeUsoArmarTablero;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CasoDeUsoArmarTableroTest {

    private ArrayList<Hexagono> listaDeHexagonos;
    private ArrayList<Produccion> listaDeNumeros;

    @BeforeEach
    public void setUp() {
        this.listaDeHexagonos = new ArrayList<Hexagono>(Arrays.asList(
            new Desierto(),
            new Campo(), new Campo(), new Campo(), new Campo(),
            new Bosque(), new Bosque(), new Bosque(), new Bosque(),
            new Pastizal(), new Pastizal(), new Pastizal(), new Pastizal(),
            new Colina(), new Colina(), new Colina(),
            new Montana(), new Montana(), new Montana()
        ));
        this.listaDeNumeros = new ArrayList<Produccion>(Arrays.asList(
                new Produccion(2),
                new Produccion(3),
                new Produccion(3),
                new Produccion(4),
                new Produccion(4),
                new Produccion(5),
                new Produccion(5),
                new Produccion(6),
                new Produccion(6),
                new Produccion(8),
                new Produccion(8),
                new Produccion(9),
                new Produccion(9),
                new Produccion(10),
                new Produccion(10),
                new Produccion(11),
                new Produccion(11),
                new Produccion(12)
        ));
    }

    @Test
    public void test01CasoDeUsoArmarTablero() {
        CasoDeUsoArmarTablero caso = new CasoDeUsoArmarTablero(listaDeHexagonos, listaDeNumeros);
        var tablero = caso.armarTablero();
        Tablero tableroEsperado = new Tablero(listaDeHexagonos, listaDeNumeros);

        assertEquals(tableroEsperado, tablero);
    }

    @Test
    public void test02CasoDeUsoArmarTableroYMezclarNumerosNoTieneMismaDisposicion() {
        CasoDeUsoArmarTablero caso = new CasoDeUsoArmarTablero(listaDeHexagonos, listaDeNumeros);
        var tableroObtenido = caso.armarTablero();
        caso.mezclarNumeros();
        Tablero tableroEsperado = new Tablero(listaDeHexagonos, listaDeNumeros);

        assertNotEquals(tableroObtenido, tableroEsperado);
    }
    @Test
    public void test03CasoDeUsoArmarTableroYMezclarHexagonosNoTieneMismaDisposicion() {
        CasoDeUsoArmarTablero caso = new CasoDeUsoArmarTablero(listaDeHexagonos, listaDeNumeros);
        var tableroObtenido = caso.armarTablero();
        caso.mezclarHexagonos();
        Tablero tableroEsperado = new Tablero(listaDeHexagonos, listaDeNumeros);

        assertNotEquals(tableroObtenido, tableroEsperado);
    }

    @Test
    public void test04CasoDeUsoArmarTableroYMezclarNumerosYHexagonosNoTieneMismaDisposicion() {
        CasoDeUsoArmarTablero caso = new CasoDeUsoArmarTablero(listaDeHexagonos, listaDeNumeros);
        var tableroObtenido = caso.armarTablero();
        caso.mezclarTablero();
        Tablero tableroEsperado = new Tablero(listaDeHexagonos, listaDeNumeros);
        assertNotEquals(tableroObtenido, tableroEsperado);
    }
}