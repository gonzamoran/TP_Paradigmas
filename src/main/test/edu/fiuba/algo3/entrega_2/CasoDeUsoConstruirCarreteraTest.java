package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.construcciones.*;

import edu.fiuba.algo3.modelo.excepciones.NoEsPosibleConstruirException;
import edu.fiuba.algo3.modelo.excepciones.PosInvalidaParaConstruirException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.fiuba.algo3.modelo.acciones.CasoDeUsoConstruirCarretera;

public class CasoDeUsoConstruirCarreteraTest {

    @Test
    public void test01ConstruirCarreteraEfectivamenteLoHace() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(10));
        jugador.agregarRecurso(new Ladrillo(10));
        jugador.agregarRecurso(new Piedra(10));
        jugador.agregarRecurso(new Lana(10));
        jugador.agregarRecurso(new Grano(10));

        Coordenadas coordenadaExtremo1 = new Coordenadas(2, 1);
        Coordenadas coordenadaExtremo2 = new Coordenadas(2, 2);

        CasoDeUsoConstruirCarretera caso = new CasoDeUsoConstruirCarretera(tablero, jugador);
        caso.construirEn(coordenadaExtremo1, new Poblado());
        caso.construirCarretera(coordenadaExtremo1, coordenadaExtremo2);

        assertTrue(tablero.estaConstruidoCon(new Carretera(), coordenadaExtremo1, jugador));
        assertTrue(tablero.estaConstruidoCon(new Carretera(), coordenadaExtremo2, jugador));
    }


    @Test 
    public void test02ConstruirCarreteraSinPobladoNoPermiteConstruir() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(2));
        jugador.agregarRecurso(new Ladrillo(2));

        Coordenadas coordenadaExtremo1 = new Coordenadas(1, 1);
        Coordenadas coordenadaExtremo2 = new Coordenadas(1, 2);

        CasoDeUsoConstruirCarretera caso = new CasoDeUsoConstruirCarretera(tablero, jugador);
        assertThrows(NoEsPosibleConstruirException.class, () -> caso.construirCarretera(coordenadaExtremo1, coordenadaExtremo2));

    }

    @Test
    public void test03ColocarCarreteraAdyacenteACiudadEstaPermitido() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(10));
        jugador.agregarRecurso(new Ladrillo(10));
        jugador.agregarRecurso(new Piedra(10));
        jugador.agregarRecurso(new Lana(10));
        jugador.agregarRecurso(new Grano(10));

        Coordenadas coordenadaExtremo1 = new Coordenadas(4, 4);
        Coordenadas coordenadaExtremo2 = new Coordenadas(4, 5);

        CasoDeUsoConstruirCarretera caso = new CasoDeUsoConstruirCarretera(tablero, jugador);
        caso.construirEn(coordenadaExtremo1, new Poblado());
        caso.construirEn(coordenadaExtremo1, new Ciudad());
        caso.construirCarretera(coordenadaExtremo1, coordenadaExtremo2);

        assertTrue(tablero.estaConstruidoCon(new Carretera(), coordenadaExtremo1, jugador));
        assertTrue(tablero.estaConstruidoCon(new Carretera(), coordenadaExtremo2, jugador));
    }

    @Test
    public void test04ConstruirUnaCarreteraConsumeLosRecursosCorrectamente() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(10));
        jugador.agregarRecurso(new Ladrillo(10));
        jugador.agregarRecurso(new Piedra(10));
        jugador.agregarRecurso(new Lana(10));
        jugador.agregarRecurso(new Grano(10));

        Coordenadas coordenadaExtremo1 = new Coordenadas(3, 3);
        Coordenadas coordenadaExtremo2 = new Coordenadas(3, 4);

        CasoDeUsoConstruirCarretera caso = new CasoDeUsoConstruirCarretera(tablero, jugador);
        caso.construirEn(coordenadaExtremo1, new Poblado());
        caso.construirCarretera(coordenadaExtremo1, coordenadaExtremo2);

        assertEquals(9, jugador.obtenerCantidadRecurso(new Madera(0)));
        assertEquals(9, jugador.obtenerCantidadRecurso(new Ladrillo(0)));
    }

    @Test
    public void test05NoSePuedeConstruirUnaCarreteraSiNoHayRecursosSuficientes() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(1));
        jugador.agregarRecurso(new Ladrillo(1));
        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Grano(1));
        Coordenadas coordenadaExtremo1 = new Coordenadas(5, 5);
        Coordenadas coordenadaExtremo2 = new Coordenadas(5, 6);
        CasoDeUsoConstruirCarretera caso = new CasoDeUsoConstruirCarretera(tablero, jugador);
        caso.construirEn(coordenadaExtremo1, new Poblado());
        assertThrows(NoEsPosibleConstruirException.class, () -> caso.construirCarretera(coordenadaExtremo1, coordenadaExtremo2));
    }

    @Test
    public void test06NoSePuedeConstruirUnaCarreteraEntreCoordenadasNoAdyacentes() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(10));
        jugador.agregarRecurso(new Ladrillo(10));
        jugador.agregarRecurso(new Piedra(10));
        jugador.agregarRecurso(new Lana(10));
        jugador.agregarRecurso(new Grano(10));

        Coordenadas coordenadaExtremo1 = new Coordenadas(2, 5);
        Coordenadas coordenadaExtremo2 = new Coordenadas(1, 1);

        CasoDeUsoConstruirCarretera caso = new CasoDeUsoConstruirCarretera(tablero, jugador);
        caso.construirEn(coordenadaExtremo1, new Poblado());
        assertThrows(PosInvalidaParaConstruirException.class, () -> caso.construirCarretera(coordenadaExtremo1, coordenadaExtremo2));
    }

    @Test
    public void test07NoSePuedeConstruirUnaCarreteraSiNingunoDeLosExtremosEsAdyacenteAUnaConstruccionPropia() {
        Jugador jugador1 = new Jugador("Jugador 1");
        Jugador jugador2 = new Jugador("Jugador 2");
        Tablero tablero = new Tablero();

        jugador2.agregarRecurso(new Madera(10));
        jugador2.agregarRecurso(new Ladrillo(10));
        jugador2.agregarRecurso(new Lana(10));
        jugador2.agregarRecurso(new Grano(10));

        jugador1.agregarRecurso(new Madera(10));
        jugador1.agregarRecurso(new Ladrillo(10));
        
        Coordenadas coordenadaExtremo1 = new Coordenadas(2, 2);
        Coordenadas coordenadaExtremo2 = new Coordenadas(2, 3);
        CasoDeUsoConstruirCarretera caso = new CasoDeUsoConstruirCarretera(tablero, jugador1);

        tablero.colocarEn(coordenadaExtremo1, new Poblado(), jugador2);

        assertThrows(NoEsPosibleConstruirException.class, () -> caso.construirCarretera(coordenadaExtremo1, coordenadaExtremo2));
    }

    @Test
    public void test08SePuedeConstruirUnaCarreteraAdyacenteAOtraCarreteraPropia() {
        Jugador jugador = new Jugador("Jugador 1");
        Tablero tablero = new Tablero();
        jugador.agregarRecurso(new Madera(10));
        jugador.agregarRecurso(new Ladrillo(10));
        jugador.agregarRecurso(new Piedra(10));
        jugador.agregarRecurso(new Lana(10));
        jugador.agregarRecurso(new Grano(10));

        Coordenadas coordenadaExtremo1 = new Coordenadas(3, 1);
        Coordenadas coordenadaExtremo2 = new Coordenadas(3, 2);
        Coordenadas coordenadaExtremo3 = new Coordenadas(3, 3);

        CasoDeUsoConstruirCarretera caso = new CasoDeUsoConstruirCarretera(tablero, jugador);
        caso.construirEn(coordenadaExtremo1, new Poblado());
        caso.construirCarretera(coordenadaExtremo1, coordenadaExtremo2);
        caso.construirCarretera(coordenadaExtremo2, coordenadaExtremo3);

        assertTrue(tablero.estaConstruidoCon(new Carretera(), coordenadaExtremo2, jugador));
        assertTrue(tablero.estaConstruidoCon(new Carretera(), coordenadaExtremo3, jugador));
    }
}
