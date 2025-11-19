package edu.fiuba.algo3.entrega_1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.construcciones.Construccion;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.entrega_1.casosDeUso.CasoDeUsoColocacionInicial;
import edu.fiuba.algo3.modelo.Recurso;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
                String jugador = "Azul";
                CasoDeUsoColocacionInicial caso = new CasoDeUsoColocacionInicial(tablero);

                caso.colocarConstruccionInicial(new Coordenadas(4, 3),
                                Construccion.crearConstruccion("Poblado", jugador),
                                jugador);
                var produccion = caso.colocarConstruccionInicial(new Coordenadas(2, 1),
                                Construccion.crearConstruccion("Poblado", jugador), jugador);

                var produccionEsperada = new ArrayList<Recurso>(List.of(
                                Recurso.generarRecurso("Grano", 1),
                                Recurso.generarRecurso("Madera", 2)));

                assertEquals(produccionEsperada, produccion);
        }

        @Test
        public void test02CasoDeUsoColocacionInicialCumpleReglaDeDistancia() {
                Tablero tablero = new Tablero();
                String jugador = "Azul";
                CasoDeUsoColocacionInicial caso = new CasoDeUsoColocacionInicial(tablero);

                caso.colocarConstruccionInicial(new Coordenadas(4, 3),
                                Construccion.crearConstruccion("Poblado", jugador),
                                jugador);

                assertThrows(PosInvalidaParaConstruirException.class,
                                () -> caso.colocarConstruccionInicial(new Coordenadas(4, 4),
                                                Construccion.crearConstruccion("Poblado", jugador), jugador));
        }

        @Test
        public void test03CasoDeUsoColocacionInicialNoSePuedeColocarFueraDelTablero() {
                Tablero tablero = new Tablero();
                String jugador = "Azul";
                CasoDeUsoColocacionInicial caso = new CasoDeUsoColocacionInicial(tablero);

                assertThrows(PosInvalidaParaConstruirException.class,
                                () -> caso.colocarConstruccionInicial(new Coordenadas(20, 20),
                                                Construccion.crearConstruccion("Poblado", jugador), jugador));
        }

        @Test
        public void test05CasoDeUsoColocacionInicialNoSePuedeColocarEnLaMismaPosicion() {
                Tablero tablero = new Tablero();
                String jugador = "Azul";
                CasoDeUsoColocacionInicial caso = new CasoDeUsoColocacionInicial(tablero);

                caso.colocarConstruccionInicial(new Coordenadas(4, 3),
                                Construccion.crearConstruccion("Poblado", jugador),
                                jugador);

                assertThrows(PosInvalidaParaConstruirException.class,
                                () -> caso.colocarConstruccionInicial(new Coordenadas(4, 3),
                                                Construccion.crearConstruccion("Poblado", jugador), jugador));
        }
}
