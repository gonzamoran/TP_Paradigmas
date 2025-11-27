package edu.fiuba.algo3.entrega_2;

import org.junit.Test;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tiposRecurso.*;

import edu.fiuba.algo3.entrega_2.casosDeUso.CasoDeUsoIntercambio;

import edu.fiuba.algo3.modelo.excepciones.IntercambioInvalidoException;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Array;
import java.util.ArrayList;



public class CasoDeUsoIntercambioTest {
    
    @Test
    public void test01AmbosJugadoresConRecursosSuficientesPuedenIntercambiar() {
        Jugador jugador1 = new Jugador("Azul");
        Jugador jugador2 = new Jugador("Rojo");

        Recurso[] recursosJugador1 = {
            new Ladrillo(1)
            };
        Recurso[] recursosJugador2 = {
            new Lana(1)
        };

        CasoDeUsoIntercambio caso = new CasoDeUsoIntercambio(jugador1, jugador2, recursosJugador1, recursosJugador2);

        ArrayList<Recurso> recursosDispuestoACederJugador1 = new ArrayList<Recurso>() {{
            add(new Ladrillo(1));
        }};
        ArrayList<Recurso> recursosEsperadosJugador1 = new ArrayList<Recurso>() {{
            add(new Lana(1));
        }};

        assertTrue(caso.puedeHacerseElIntercambio(recursosDispuestoACederJugador1, recursosEsperadosJugador1));
    }

    @Test
    public void test02AmbosJugadoresAceptanElIntercambio() {
        Jugador jugador1 = new Jugador("Azul");
        Jugador jugador2 = new Jugador("Rojo");
        Recurso[] recursosJugador1 = {
            new Ladrillo(1)
            };
        Recurso[] recursosJugador2 = {
            new Lana(1)
        };
        CasoDeUsoIntercambio caso = new CasoDeUsoIntercambio(jugador1, jugador2, recursosJugador1, recursosJugador2);

        ArrayList<Recurso> recursosDispuestoACederJugador1 = new ArrayList<Recurso>() {{
            add(new Ladrillo(1));
        }};

        ArrayList<Recurso> recursosEsperadosJugador1 = new ArrayList<Recurso>() {{
            add(new Lana(1));
        }};

        caso.ejecutarIntercambio(recursosDispuestoACederJugador1, recursosEsperadosJugador1);

        Recurso[] recursosFinalesJugador1 = new Recurso[] {
            new Lana(1)
        };
        
        Recurso[] recursosFinalesJugador2 = new Recurso[] {
            new Ladrillo(1)
        };
        // DIEGO: agregar un "ManoDelJugador"
        assertEquals(recursosFinalesJugador1[0].getCantidad(), jugador1.obtenerCantidadRecurso(new Lana(0)));
        assertEquals(recursosFinalesJugador2[0].getCantidad(), jugador2.obtenerCantidadRecurso(new Ladrillo(0)));
    }

    @Test
    public void test02UnJugadorNoPoseeRecursosSuficientesParaIntercambiar() {
        Jugador jugador1 = new Jugador("Azul");
        Jugador jugador2 = new Jugador("Rojo");
        Recurso[] recursosJugador1 = {
            new Ladrillo(1)
            };
        Recurso[] recursosJugador2 = {
            new Lana(1)
        };
        CasoDeUsoIntercambio caso = new CasoDeUsoIntercambio(jugador1, jugador2, recursosJugador1, recursosJugador2);

        ArrayList<Recurso> recursosDispuestoACederJugador1 = new ArrayList<Recurso>() {{
            add(new Ladrillo(2));
        }};

        ArrayList<Recurso> recursosEsperadosJugador1 = new ArrayList<Recurso>() {{
            add(new Lana(1));
        }};

        assertThrows(IntercambioInvalidoException.class, () -> caso.ejecutarIntercambio(recursosDispuestoACederJugador1, recursosEsperadosJugador1));
    }
}
