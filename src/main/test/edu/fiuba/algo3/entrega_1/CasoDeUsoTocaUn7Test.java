package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.*;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tiposRecurso.Madera;
import edu.fiuba.algo3.modelo.tiposRecurso.Ladrillo;
import edu.fiuba.algo3.modelo.tiposRecurso.Piedra;
import edu.fiuba.algo3.modelo.tiposRecurso.Lana;
import edu.fiuba.algo3.modelo.cartas.CartasJugador;

import javafx.scene.control.Tab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.fiuba.algo3.entrega_1.casosDeUso.CasoDeUsoTocaUsarLadron;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Produccion;

public class CasoDeUsoTocaUn7Test {

    @Test
    public void testMoverLadron() {
        Tablero tablero = new Tablero();
        CartasJugador cartasJugador = new CartasJugador();

        Hexagono origen = tablero.obtenerHexagono(new Coordenadas(2, 2));
        CasoDeUsoTocaUsarLadron caso = new CasoDeUsoTocaUsarLadron(tablero, "Azul", origen, cartasJugador);
        Hexagono destino = tablero.obtenerHexagono(new Coordenadas(2, 2));

        caso.moverLadronA(destino);

        assertEquals(destino, tablero.obtenerHexagonoLadron());
    }

    @Test
    public void testHexagonoConLadronNoGeneraRecursos() {
        Tablero tablero = new Tablero();
        CartasJugador cartasJugador = new CartasJugador();

        Hexagono origen = tablero.obtenerHexagono(new Coordenadas(2, 2));
        CasoDeUsoTocaUsarLadron caso = new CasoDeUsoTocaUsarLadron(tablero, "Azul", origen, cartasJugador);
        Hexagono destino = tablero.obtenerHexagono(new Coordenadas(0, 6));

        assertFalse(origen.puedeGenerarRecursos());
        assertTrue(destino.puedeGenerarRecursos());
        caso.moverLadronA(destino); //desierto -> otroHexagono
        assertTrue(origen.puedeGenerarRecursos());
        assertFalse(destino.puedeGenerarRecursos());
    }

    @Test
    public void testSacarUn7ReduceLosRecursosAlJugador(){
        Tablero tablero = new Tablero();
        CartasJugador cartasJugador = new CartasJugador();
        Recurso[] recursos = {
            new Madera("Madera", 3),
            new Ladrillo("Ladrillo", 3),
            new Piedra("Piedra", 3),
            new Lana("Lana", 6)
        };

        Hexagono origen = tablero.obtenerHexagono(new Coordenadas(2, 2));
        CasoDeUsoTocaUsarLadron caso = new CasoDeUsoTocaUsarLadron(tablero, "Azul", origen, cartasJugador);
        caso.agregarRecursos(recursos);

        caso.ladronRobaRecursos();

        int totalRecursosDespuesDeRobar = cartasJugador.cantidadTotalCartasRecurso();
        assertEquals(8, totalRecursosDespuesDeRobar);
    }

    @Test
    public void testSacarUn7NoReduceRecursosSiJugadorTieneMenosDe7Cartas(){
        Tablero tablero = new Tablero();
        CartasJugador cartasJugador = new CartasJugador();
        Recurso[] recursos = {
            new Madera("Madera", 2),
            new Ladrillo("Ladrillo", 2),
            new Piedra("Piedra", 2),
            new Lana("Lana", 0)
        };
        Hexagono origen = tablero.obtenerHexagono(new Coordenadas(2, 2));
        CasoDeUsoTocaUsarLadron caso = new CasoDeUsoTocaUsarLadron(tablero, "Azul", origen, cartasJugador);
        caso.agregarRecursos(recursos);
        caso.ladronRobaRecursos();
        int totalRecursosDespuesDeRobar = cartasJugador.cantidadTotalCartasRecurso();
        assertEquals(6, totalRecursosDespuesDeRobar);
    }
    
        /*
     * @Test
     * public void testHexagonoSinLadronPuedeGenerarRecursos(){
     * Tablero tablero = new Tablero();
     * 
     * CasoDeUsoTocaUsarLadron caso = new CasoDeUsoTocaUsarLadron(tablero,"Azul");
     * Hexagono original = tablero.obtenerHexagonoLadron();
     * Hexagono destino = tablero.obtenerHexagono(new Coordenadas(2, 2));
     * tablero.moverLadronA(destino);
     * 
     * assertTrue(false);
     * }
     */
}
