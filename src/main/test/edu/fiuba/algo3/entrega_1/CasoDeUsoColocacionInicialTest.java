package edu.fiuba.algo3.entrega_1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.construcciones.Carretera;
import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.acciones.CasoDeUsoColocacionInicial;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.Jugador;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.fiuba.algo3.modelo.excepciones.NoEsPosibleConstruirException;
import edu.fiuba.algo3.modelo.excepciones.PosInvalidaParaConstruirException;

public class CasoDeUsoColocacionInicialTest {
        // Regla de la distancia
        // Reparto de recursos correcto en el caso inicial.

        @Test
        public void test01CasoDeUsoColocacionInicialDePoblados() {
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

                assertThrows(NoEsPosibleConstruirException.class,
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

                assertThrows(NoEsPosibleConstruirException.class,
                                () -> caso.colocarConstruccionInicial(new Coordenadas(4, 3),new Poblado(), jugador));
        }

        @Test
        public void test06CasoDeUsoColocacionInicialColocarCarreteraInicial() {
                Tablero tablero = new Tablero();
                Jugador jugador = new Jugador("Azul");
                CasoDeUsoColocacionInicial caso = new CasoDeUsoColocacionInicial(tablero);

                caso.colocarConstruccionInicial(new Coordenadas(4, 3), new Poblado(), jugador);
                caso.colocarCarreteraInicial(new Coordenadas(4, 3), new Coordenadas(5, 3), jugador);
                assertTrue(tablero.estaConstruidoCon(new Carretera(), new Coordenadas(4, 3), jugador));
                assertTrue(tablero.estaConstruidoCon(new Carretera(), new Coordenadas(5, 3), jugador));
        }

        @Test
        public void test07CasoDeUsoColocacionInicialNoSePuedeColocarCarreteraSinPoblado() {
                Tablero tablero = new Tablero();
                Jugador jugador = new Jugador("Azul");
                CasoDeUsoColocacionInicial caso = new CasoDeUsoColocacionInicial(tablero);

                assertThrows(NoEsPosibleConstruirException.class,
                                () -> caso.colocarCarreteraInicial(new Coordenadas(4, 3), new Coordenadas(5, 3), jugador));
        }

        @Test
        public void test08CasoDeUsoColocacionInicialNoSePuedeColocarCarreteraFueraDelTablero() {
                Tablero tablero = new Tablero();
                Jugador jugador = new Jugador("Azul");
                CasoDeUsoColocacionInicial caso = new CasoDeUsoColocacionInicial(tablero);
                caso.colocarConstruccionInicial(new Coordenadas(4, 3), new Poblado(), jugador);
                assertThrows(PosInvalidaParaConstruirException.class,
                                () -> caso.colocarCarreteraInicial(new Coordenadas(20, 20), new Coordenadas(21, 21), jugador));
        }

        @Test
        public void test09CasoDeUsoColocacionInicialNoSePuedeColocarCarreteraEnPosicionNoAdyacenteAlPoblado() {
                Tablero tablero = new Tablero();
                Jugador jugador = new Jugador("Azul");
                CasoDeUsoColocacionInicial caso = new CasoDeUsoColocacionInicial(tablero);
                caso.colocarConstruccionInicial(new Coordenadas(4, 3), new Poblado(), jugador);
                assertThrows(NoEsPosibleConstruirException.class,
                                () -> caso.colocarCarreteraInicial(new Coordenadas(1, 1), new Coordenadas(1, 2), jugador));
        }

        @Test
        public void test10CasoDeUsoColocacionInicialNoSePuedeDarCoordenadasDeCarreteraNoAdyacentes() {
                Tablero tablero = new Tablero();
                Jugador jugador = new Jugador("Azul");
                CasoDeUsoColocacionInicial caso = new CasoDeUsoColocacionInicial(tablero);
                caso.colocarConstruccionInicial(new Coordenadas(4, 3), new Poblado(), jugador);
                assertThrows(PosInvalidaParaConstruirException.class,
                                () -> caso.colocarCarreteraInicial(new Coordenadas(4, 3), new Coordenadas(6, 6), jugador));
        }

}
