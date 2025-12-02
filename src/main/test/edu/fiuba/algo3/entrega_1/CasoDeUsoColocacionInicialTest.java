package edu.fiuba.algo3.entrega_1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.acciones.CasoDeUsoColocacionInicial;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.Jugador;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.fiuba.algo3.modelo.excepciones.PosInvalidaParaConstruirException;

public class CasoDeUsoColocacionInicialTest {
        // Regla de la distancia
        // Reparto de recursos correcto en el caso inicial.

        @Test
        public void test01CasoDeUsoColocacionInicialDePoblados() {
                // prueba colocar en dos coordenadas distintas y que respetan regla de distancia
                // que se coloquen bien los poblados y que el segundo devuelva recursos
                // correctamente
                Tablero tablero = new Tablero();
                Jugador jugador = new Jugador("Azul");
                CasoDeUsoColocacionInicial caso = new CasoDeUsoColocacionInicial(tablero);

                caso.colocarConstruccionInicial(new Coordenadas(4, 3),new Poblado(), jugador);
                ArrayList<Recurso> produccion = caso.colocarConstruccionInicial(new Coordenadas(2, 1),new Poblado(), jugador);

                var produccionEsperada = new ArrayList<Recurso>(List.of(
                                new Grano(1),
                                new Madera(1),
                                new Madera(1)));

                assertTrue(produccion.containsAll(produccionEsperada));
                assertTrue(produccionEsperada.containsAll(produccion));
        }

        @Test
        public void test02CasoDeUsoColocacionInicialCumpleReglaDeDistancia() {
                Tablero tablero = new Tablero();
                Jugador jugador = new Jugador("Azul");
                CasoDeUsoColocacionInicial caso = new CasoDeUsoColocacionInicial(tablero);

                caso.colocarConstruccionInicial(new Coordenadas(4, 3), new Poblado(), jugador);

                assertThrows(PosInvalidaParaConstruirException.class,
                                () -> caso.colocarConstruccionInicial(new Coordenadas(4, 4), new Poblado(), jugador));
        }

        @Test
        public void test03CasoDeUsoColocacionInicialNoSePuedeColocarFueraDelTablero() {
                Tablero tablero = new Tablero();
                Jugador jugador = new Jugador("Azul");
                CasoDeUsoColocacionInicial caso = new CasoDeUsoColocacionInicial(tablero);

                assertThrows(PosInvalidaParaConstruirException.class,
                                () -> caso.colocarConstruccionInicial(new Coordenadas(20, 20), new Poblado(), jugador));
        }

        @Test
        public void test05CasoDeUsoColocacionInicialNoSePuedeColocarEnLaMismaPosicion() {
                Tablero tablero = new Tablero();
                Jugador jugador = new Jugador("Azul");
                CasoDeUsoColocacionInicial caso = new CasoDeUsoColocacionInicial(tablero);

                caso.colocarConstruccionInicial(new Coordenadas(4, 3), new Poblado(), jugador);

                assertThrows(PosInvalidaParaConstruirException.class,
                                () -> caso.colocarConstruccionInicial(new Coordenadas(4, 3),new Poblado(), jugador));
        }
}
