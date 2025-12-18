package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.acciones.CasoDeUsoArmarTablero;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Produccion;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.Bosque;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.Campo;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.Colina;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.Desierto;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.Montana;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.Pastizal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        Tablero tableroNoEsperado = new Tablero(listaDeHexagonos, listaDeNumeros);

        ArrayList<Produccion> listaDeNumerosMezclados = new ArrayList<>(listaDeNumeros);
        Collections.shuffle(listaDeNumerosMezclados);
        CasoDeUsoArmarTablero caso = new CasoDeUsoArmarTablero(listaDeHexagonos, listaDeNumerosMezclados);
        var tableroObtenido = caso.armarTablero();

        assertNotEquals(tableroObtenido, tableroNoEsperado);
    }

    @Test
    public void test03CasoDeUsoArmarTableroYMezclarHexagonosNoTieneMismaDisposicion() {
        Tablero tableroNoEsperado = new Tablero(listaDeHexagonos, listaDeNumeros);
        ArrayList<Hexagono> listaHexagonosMezclados = new ArrayList<>(listaDeHexagonos);
        Collections.shuffle(listaHexagonosMezclados);
        CasoDeUsoArmarTablero caso = new CasoDeUsoArmarTablero(listaHexagonosMezclados, listaDeNumeros);
        var tableroObtenido = caso.armarTablero();

        assertNotEquals(tableroObtenido, tableroNoEsperado);
    }

    @Test
    public void test04CasoDeUsoArmarTableroYMezclarNumerosYHexagonosNoTieneMismaDisposicion() {
        Tablero tableroNoEsperado = new Tablero(listaDeHexagonos, listaDeNumeros);
        ArrayList<Hexagono> listaHexagonosMezclados = new ArrayList<>(listaDeHexagonos);
        ArrayList<Produccion> listaNumerosMezclados = new ArrayList<>(listaDeNumeros);
        Collections.shuffle(listaHexagonosMezclados);
        Collections.shuffle(listaNumerosMezclados);
        CasoDeUsoArmarTablero caso = new CasoDeUsoArmarTablero(listaHexagonosMezclados, listaNumerosMezclados);
        var tableroObtenido = caso.armarTablero();
        assertNotEquals(tableroObtenido, tableroNoEsperado);
    }
    
}