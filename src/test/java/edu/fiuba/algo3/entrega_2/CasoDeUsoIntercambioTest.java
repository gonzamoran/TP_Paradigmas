package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.acciones.CasoDeUsoIntercambio;
import edu.fiuba.algo3.modelo.excepciones.IntercambioInvalidoException;
import edu.fiuba.algo3.modelo.tiposRecurso.Ladrillo;
import edu.fiuba.algo3.modelo.tiposRecurso.Lana;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;



public class CasoDeUsoIntercambioTest {
    
    @Test
    public void test01AmbosJugadoresConRecursosSuficientesPuedenIntercambiar() {
        Jugador jugador1 = new Jugador("Azul");
        Jugador jugador2 = new Jugador("Rojo");

        jugador1.agregarRecurso(new Ladrillo(1));
        jugador2.agregarRecurso(new Lana(1));


        CasoDeUsoIntercambio caso = new CasoDeUsoIntercambio(jugador1, jugador2);

        ArrayList<Recurso> ofertaJugador1 = new ArrayList<Recurso>() {{
            add(new Ladrillo(1));
        }};
        ArrayList<Recurso> demandaJugador1 = new ArrayList<Recurso>() {{
            add(new Lana(1));
        }};

        assertDoesNotThrow(() -> caso.ejecutarIntercambio(ofertaJugador1, demandaJugador1));
    }

    @Test
    public void test02AmbosJugadoresAceptanElIntercambio() {
        Jugador jugador1 = new Jugador("Azul");
        Jugador jugador2 = new Jugador("Rojo");
        jugador1.agregarRecurso(new Ladrillo(1));
        jugador2.agregarRecurso(new Lana(1));

        CasoDeUsoIntercambio caso = new CasoDeUsoIntercambio(jugador1, jugador2);

        ArrayList<Recurso> ofertaJugador1 = new ArrayList<Recurso>() {{
            add(new Ladrillo(1));
        }};

        ArrayList<Recurso> demandaJugador1 = new ArrayList<Recurso>() {{
            add(new Lana(1));
        }};

        caso.ejecutarIntercambio(ofertaJugador1, demandaJugador1);

        assertEquals(1, jugador1.obtenerCantidadRecurso(new Lana()));
        assertEquals(1, jugador2.obtenerCantidadRecurso(new Ladrillo()));
    }

    @Test
    public void test02UnJugadorNoPoseeRecursosSuficientesParaIntercambiar() {
        Jugador jugador1 = new Jugador("Azul");
        Jugador jugador2 = new Jugador("Rojo");
        jugador1.agregarRecurso(new Ladrillo(1));
        jugador2.agregarRecurso(new Lana(1));

        CasoDeUsoIntercambio caso = new CasoDeUsoIntercambio(jugador1, jugador2);
        ArrayList<Recurso> ofertaJugador1 = new ArrayList<Recurso>() {{
            add(new Ladrillo(2));
        }};

        ArrayList<Recurso> demandaJugador1 = new ArrayList<Recurso>() {{
            add(new Lana(1));
        }};

        assertThrows(IntercambioInvalidoException.class, () -> caso.ejecutarIntercambio(ofertaJugador1, demandaJugador1));
    }
}
