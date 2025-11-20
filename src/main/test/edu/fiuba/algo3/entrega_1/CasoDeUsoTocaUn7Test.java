package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.*;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.cartas.CartasJugador;
import edu.fiuba.algo3.modelo.construcciones.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.fiuba.algo3.entrega_1.casosDeUso.CasoDeUsoTocaUsarLadron;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Produccion;
import edu.fiuba.algo3.entrega_1.casosDeUso.DadoCargado;
import edu.fiuba.algo3.modelo.Jugador;

public class CasoDeUsoTocaUn7Test {

    @Test
    public void test01MoverLadronLoDejaEnElHexagonoDeseado() {
        //arrange
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Azul");
        Coordenadas origen = new Coordenadas(2, 2);
        CasoDeUsoTocaUsarLadron caso = new CasoDeUsoTocaUsarLadron(tablero, jugador, origen);
        Coordenadas destino = new Coordenadas(3, 5);
        caso.configurarDestino(destino);

        //act
        caso.lanzarDado(new DadoCargado(7));

        //assert
        assertEquals(tablero.obtenerHexagono(destino), tablero.obtenerHexagonoLadron());
    }

    @Test
    public void test02HexagonoConLadronNoGeneraRecursos() {
        //arrange
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Azul");

        Coordenadas origen = new Coordenadas(2, 2);
        CasoDeUsoTocaUsarLadron caso = new CasoDeUsoTocaUsarLadron(tablero, jugador, origen);
        Coordenadas destino = new Coordenadas(0, 6);
        caso.configurarDestino(destino);

        //act
        caso.lanzarDado(new DadoCargado(7));

        //assert
        assertTrue(tablero.obtenerHexagono(origen).puedeGenerarRecursos());
        assertFalse(tablero.obtenerHexagono(destino).puedeGenerarRecursos());
    }

    @Test
    public void test03SacarUn7ReduceLosRecursosAlJugador(){
        //arrange
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Azul");
        jugador.agregarRecurso(new Madera(3));
        jugador.agregarRecurso(new Ladrillo(3));
        jugador.agregarRecurso(new Piedra(3));
        jugador.agregarRecurso(new Lana(6));

        Coordenadas origen = new Coordenadas(2, 2);
        CasoDeUsoTocaUsarLadron caso = new CasoDeUsoTocaUsarLadron(tablero, jugador, origen);
        caso.configurarDestino(new Coordenadas(0,6));

        //act
        caso.lanzarDado(new DadoCargado(7));

        int totalRecursosDespuesDeRobar = jugador.obtenerCantidadCartasRecurso();
        
        //assert
        assertEquals(8, totalRecursosDespuesDeRobar);
    }



    @Test
    public void test04SacarUn7NoReduceRecursosSiJugadorTieneMenosDe7Cartas(){
        //arrange
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Azul");
        jugador.agregarRecurso(new Madera(2));
        jugador.agregarRecurso(new Ladrillo(2));
        jugador.agregarRecurso(new Piedra(2));

        Coordenadas origen = new Coordenadas(2, 2);
        CasoDeUsoTocaUsarLadron caso = new CasoDeUsoTocaUsarLadron(tablero, jugador, origen);
        caso.configurarDestino(new Coordenadas(0,6));
        //act
        caso.lanzarDado(new DadoCargado(7));
        int totalRecursosDespuesDeRobar = jugador.obtenerCantidadCartasRecurso();

        //assert
        assertEquals(6, totalRecursosDespuesDeRobar);
    }


    //Hay que crear la clase jugador para hacer este test?
    //Seguro que si, hay que robar una carta aleatoria de otro jugador
    @Test
    public void test05JugadorMueveLadronYRobaUnRecursoAleatorioDeOtroJugador(){
        Tablero tablero = new Tablero();
        Jugador jugador1 = new Jugador("Azul");
        Jugador jugador2 = new Jugador("Rojo");
        
        jugador1.agregarRecurso(new Madera(1));
        jugador1.agregarRecurso(new Ladrillo(1));
        jugador1.agregarRecurso(new Piedra(1));
        jugador1.agregarRecurso(new Lana(1));
        jugador1.agregarRecurso(new Grano(1));

        Coordenadas origen = new Coordenadas(2, 2);
        CasoDeUsoTocaUsarLadron caso = new CasoDeUsoTocaUsarLadron(tablero, jugador2, origen);
        caso.colocarEn(new Coordenadas(0, 6), new Poblado(), jugador1);

        Coordenadas destino = new Coordenadas(0,6);
        caso.configurarDestino(destino);

        caso.lanzarDado(new DadoCargado(7));
        //Jugador2 sin cartas roba 1 carta a jugador1 y queda con 1 carta.
        assertEquals(1, jugador2.obtenerCantidadCartasRecurso());
        //jugador2 mueve al ladron al hexagono donde esta el poblado de jugador1
        assertEquals(0, jugador1.obtenerCantidadCartasRecurso());
    }
    
    
}
