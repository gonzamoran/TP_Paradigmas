package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.*;
import edu.fiuba.algo3.modelo.tiposRecurso.Madera;
import javafx.scene.control.Tab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.fiuba.algo3.entrega_1.casosDeUso.CasoDeUsoTocaUsarLadron;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Produccion;

public class CasoDeUsoTocaUsarAlLadronTest {

    @Test
    public void testMoverLadron() {
        Tablero tablero = new Tablero();

        CasoDeUsoTocaUsarLadron caso = new CasoDeUsoTocaUsarLadron(tablero, "Azul");
        Hexagono destino = tablero.obtenerHexagono(new Coordenadas(2, 2));

        caso.moverLadronA(destino);

        assertEquals(destino, tablero.obtenerHexagonoLadron());
    }

    @Test
    public void testHexagonoConLadronNoGeneraRecursos() {
        Tablero tablero = new Tablero();

        CasoDeUsoTocaUsarLadron caso = new CasoDeUsoTocaUsarLadron(tablero, "Azul");
        Hexagono destino = tablero.obtenerHexagono(new Coordenadas(2, 2));
        Hexagono otroHexagono = tablero.obtenerHexagono(new Coordenadas(0, 6));
        caso.moverLadronA(otroHexagono);

        // desierto (tras las sombras) -> otro hexagono -> destino
        // otro hexagono NO puede generar recursos
        // muevo ladron al destino
        // ahora otro hexagono SI puede generar recursos
        // destino NO puede generar recursos

        caso.moverLadronA(destino);

        assertFalse(destino.puedeGenerarRecursos());

        assertTrue(otroHexagono.puedeGenerarRecursos());
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
