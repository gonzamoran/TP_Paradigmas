package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.construcciones.Ciudad;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.cartas.*;

import edu.fiuba.algo3.entrega_2.casosDeUso.CasoDeUsoSacarCartasDelMazoDeDesarrollo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartaPuntoVictoria;


import org.junit.Test;


public class CasoDeUsoSacarCartasDelMazoDeDesarrolloTest {
    @Test
    public void test01ComprarCartaDesarrolloConsumeRecursos(){
        Jugador jugador = new Jugador("Azul");

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));
        
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(jugador);
        caso.comprarCartaDesarrollo(new MazoTrucado(new CartaPuntoVictoria())); //Mazo trucado va en la carpeta de los tests

        assertEquals(1, jugador.contarCartasDeDesarrollo());
    }

    @Test
    public void test02ComprarCartaDesarrolloSinRecursosLanzaExcepcion(){
        Jugador jugador = new Jugador("Azul");
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(jugador);

        assertThrows(RecursosInsuficientesException.class, () -> caso.comprarCartaDesarrollo(new MazoTrucado(new CartaPuntoVictoria())));
    }

    @Test
    public void test03ComprarCartaDesarrolloPuntosVictoriaSeAgregaCorrectamente(){
        Jugador jugador = new Jugador("Azul");

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));
        
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(jugador);
        caso.comprarCartaDesarrollo(new MazoTrucado(new CartaPuntoVictoria()));

        int puntosDeVictoriaEsperados = 1;

        assertEquals(puntosDeVictoriaEsperados, jugador.calculoPuntosVictoria());
    }


    @Test
    public void test04InicializacionDelMazoDeCartasDeDesarrolloTieneTodasLasCartas(){
        CartasDesarrollo[] cartas = {
            new CartaPuntoVictoria(), new CartaPuntoVictoria(), new CartaPuntoVictoria(), new CartaPuntoVictoria(), new CartaPuntoVictoria(),
            new CartaMonopolio(), new CartaMonopolio(),
            new CartaCarretera(), new CartaCarretera(),
            new CartaDescubrimiento(), new CartaDescubrimiento(),
            new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(),
            new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(),
            new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero()
        };
        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(cartas);
        MazoCartasDesarrollo mazo = caso.inicializarMazoDeCartasDeDesarrollo();


        MazoCartasDesarrollo mazoEsperado = new MazoCartasDesarrollo();

        assertEquals(mazoEsperado, mazo);
    }

    @Test
    public void test05ElMazoDeCartasDeDesarrolloTieneOrdenAleatorio(){
        var mazo = new MazoCartasDesarrollo();

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        MazoCartasDesarrollo mazoAleatorio = caso.mezclarMazoDeCartasDeDesarrollo(mazo);

        MazoCartasDesarrollo mazoNoEsperado = new MazoCartasDesarrollo();

        assertNotEquals(mazoNoEsperado, mazoAleatorio);
    }

    @Test
    public void test06ElMazoDeCartasDeDesarrolloSeAgotaDespuesDeSacarTodasLasCartas(){
        var mazo = new MazoCartasDesarrollo();
        var jugador = new Jugador("Azul");
        jugador.agregarRecurso(new Lana(26));
        jugador.agregarRecurso(new Piedra(26));
        jugador.agregarRecurso(new Grano(26));

        var caso = new CasoDeUsoSacarCartasDelMazoDeDesarrollo(jugador);

        int totalCartas = 25;
        for(int i = 0; i < totalCartas; i++){
            caso.comprarCartaDesarrollo(mazo);
        }

        assertTrue(mazo.estaVacio());
    }
}