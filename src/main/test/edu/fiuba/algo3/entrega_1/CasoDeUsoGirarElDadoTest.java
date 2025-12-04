package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.*;
import edu.fiuba.algo3.modelo.Dados;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.ProveedorDeDatos;

import edu.fiuba.algo3.modelo.acciones.CasoDeUsoGirarElDado;
import edu.fiuba.algo3.entrega_1.DadoCargado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class CasoDeUsoGirarElDadoTest {
    @Test
    public void test01LanzarDadosGeneraNumeroValido() {
        // Arrange
        Dados dados = new Dados();
        // Act
        int res = dados.lanzarDados();
        // Assert
        assert (res >= 2 && res <= 12);
    }

    @Test
    public void test02ProduccionCorrectaPorConstruccion() {

        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Azul");
        Coordenadas origen = new Coordenadas(2, 4);
        jugador.agregarRecurso(new Lana(10));
        jugador.agregarRecurso(new Madera(10));
        jugador.agregarRecurso(new Grano(10));
        jugador.agregarRecurso(new Ladrillo(10));
        jugador.agregarRecurso(new Piedra(10));
        
        CasoDeUsoGirarElDado caso = new CasoDeUsoGirarElDado(tablero);

        tablero.colocarEn(new Coordenadas(2, 7), new Poblado(), jugador);
        tablero.colocarEn(new Coordenadas(2, 9), new Poblado(), jugador);
        tablero.colocarEn(new Coordenadas(2, 9), new Ciudad(), jugador);
        
        var dado = new DadoCargado(8);
        int valorDado = caso.tirarDado(dado);
        caso.resolverResultado(valorDado, jugador, null);

        int cantidadEsperada = 12; // Gasto 2 lanas en poblados, queda en 8 y levanta 4.
        int cantidadLana = jugador.obtenerCantidadRecurso(new Lana());

        assertEquals(cantidadEsperada, cantidadLana);
    }

    @Test
    public void test03ProduccionCorrectaDevuelveCantidadCorrectaDeRecursos() {

        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Azul");
        Jugador jugador2 = new Jugador("Rojo");
        
        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Madera(1));
        jugador.agregarRecurso(new Grano(1));
        jugador.agregarRecurso(new Ladrillo(1));


        jugador2.agregarRecurso(new Lana(1));
        jugador2.agregarRecurso(new Madera(1));
        jugador2.agregarRecurso(new Grano(1));
        jugador2.agregarRecurso(new Ladrillo(1));
        
        CasoDeUsoGirarElDado caso = new CasoDeUsoGirarElDado(tablero);
        tablero.colocarEn(new Coordenadas(2, 4), new Poblado(), jugador);
        tablero.colocarEn(new Coordenadas(3, 3), new Poblado(), jugador2);

        var dado = new DadoCargado(6);
        int resultadoDado = dado.lanzarDados();
        caso.resolverResultado(resultadoDado, jugador,null);

        int cantidadLanaEsperada = 1;
        int cantLanaJ1 = jugador.obtenerCantidadRecurso(new Lana());
        int cantLanaJ2 = jugador2.obtenerCantidadRecurso(new Lana());

        assertEquals(cantidadLanaEsperada, cantLanaJ1);
        assertEquals(cantidadLanaEsperada, cantLanaJ2);

    }

    @Test
    public void test04MoverLadronLoDejaEnElHexagonoDeseado() {
        // arrange
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Azul");
        Coordenadas origen = new Coordenadas(2, 2);
        CasoDeUsoGirarElDado caso = new CasoDeUsoGirarElDado(tablero);
       
        Coordenadas destino = new Coordenadas(3, 5);
        ProveedorDeDatos provedorMockeado = mock(ProveedorDeDatos.class);
        when(provedorMockeado.pedirCoordenadasAlUsuario()).thenReturn(destino);

        // act
        var dado = new DadoCargado(7);
        var resultadoDado = dado.lanzarDados();

        caso.resolverResultado(resultadoDado, jugador, provedorMockeado);

 
        // assert
        assertEquals(tablero.obtenerHexagono(destino), tablero.obtenerHexagonoLadron());
    }

    @Test
    public void test05HexagonoConLadronNoGeneraRecursos() {
        // arrange
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Azul");

        Coordenadas origen = new Coordenadas(2, 2);
        CasoDeUsoGirarElDado caso = new CasoDeUsoGirarElDado(tablero);
        
        Coordenadas destino = new Coordenadas(0, 6);

        ProveedorDeDatos provedorMockeado = mock(ProveedorDeDatos.class);
        when(provedorMockeado.pedirCoordenadasAlUsuario()).thenReturn(destino);

        // act
        var dado = new DadoCargado(7);
        var resultadoDado = dado.lanzarDados();

        caso.resolverResultado(resultadoDado, jugador, provedorMockeado);

        // assert
        assertTrue(tablero.obtenerHexagono(origen).puedeGenerarRecursos());
        assertFalse(tablero.obtenerHexagono(destino).puedeGenerarRecursos());
    }

    @Test
    public void test06SacarUn7ReduceLosRecursosAlJugador() {
        // arrange
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Azul");
        jugador.agregarRecurso(new Madera(3));
        jugador.agregarRecurso(new Ladrillo(3));
        jugador.agregarRecurso(new Piedra(3));
        jugador.agregarRecurso(new Lana(6));

        CasoDeUsoGirarElDado caso = new CasoDeUsoGirarElDado(tablero);
         

        var destino = new Coordenadas(0,6);

        ProveedorDeDatos provedorMockeado = mock(ProveedorDeDatos.class);
        when(provedorMockeado.pedirCoordenadasAlUsuario()).thenReturn(destino);

        // act
        var dado = new DadoCargado(7);
        var resultadoDado = dado.lanzarDados();

        caso.resolverResultado(resultadoDado, jugador, provedorMockeado);
        int totalRecursosDespuesDeRobar = jugador.obtenerCantidadCartasRecurso();

        // assert
        assertEquals(8, totalRecursosDespuesDeRobar);
    }

    @Test
    public void test07SacarUn7NoReduceRecursosSiJugadorTieneMenosDe7Cartas() {
        // arrange
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Azul");
        jugador.agregarRecurso(new Madera(2));
        jugador.agregarRecurso(new Ladrillo(2));
        jugador.agregarRecurso(new Piedra(2));

        CasoDeUsoGirarElDado caso = new CasoDeUsoGirarElDado(tablero);
        
        var destino = new Coordenadas(0, 6);
        ProveedorDeDatos provedorMockeado = mock(ProveedorDeDatos.class);
        when(provedorMockeado.pedirCoordenadasAlUsuario()).thenReturn(destino);
        
        // act
        var dado = new DadoCargado(7);
        var resultadoDado = dado.lanzarDados();

        caso.resolverResultado(resultadoDado, jugador, provedorMockeado);
        int totalRecursosDespuesDeRobar = jugador.obtenerCantidadCartasRecurso();

        // assert
        assertEquals(6, totalRecursosDespuesDeRobar);
    }

    @Test
    public void test08JugadorMueveLadronYRobaUnRecursoAleatorioDeOtroJugador() {
        Tablero tablero = new Tablero();
        Jugador jugador1 = new Jugador("Azul");
        Jugador jugador2 = new Jugador("Rojo");

        jugador1.agregarRecurso(new Madera(1));
        jugador1.agregarRecurso(new Ladrillo(1));
        jugador1.agregarRecurso(new Piedra(1));
        jugador1.agregarRecurso(new Lana(1));
        jugador1.agregarRecurso(new Grano(1));

        var candidatos = new ArrayList<Jugador>();

        candidatos.add(jugador1);
        candidatos.add(jugador2);
        Coordenadas origen = new Coordenadas(2, 2);
        CasoDeUsoGirarElDado caso = new CasoDeUsoGirarElDado(tablero);
        tablero.colocarEn(new Coordenadas(0, 6), new Poblado(), jugador1);

        Coordenadas destino = new Coordenadas(0, 6);

        ProveedorDeDatos proveedorMockeado = mock(ProveedorDeDatos.class);
        when(proveedorMockeado.pedirCoordenadasAlUsuario()).thenReturn(destino);
        when(proveedorMockeado.pedirJugadorARobar(any(ArrayList.class))).thenReturn(jugador1);

        var dado = new DadoCargado(7);
        var resultadoDado = dado.lanzarDados();
        
        caso.resolverResultado(resultadoDado, jugador2, proveedorMockeado);
        
        // Jugador2 sin cartas roba 1 carta a jugador1 y queda con 1 carta.
        assertEquals(1, jugador2.obtenerCantidadCartasRecurso());
        // jugador2 mueve al ladron al hexagono donde esta el poblado de jugador1
        assertEquals(0, jugador1.obtenerCantidadCartasRecurso());
    }

}
