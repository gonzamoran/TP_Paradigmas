package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.construcciones.Ciudad;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.cartas.*;

import edu.fiuba.algo3.entrega_2.casosDeUso.CasoDeUsoCartasDeDesarrollo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartaPuntoVictoria;


import org.junit.Test;


public class CasoDeUsoCartasDesarrolloTest {
    private int turnoActual = 1;
    @Test
    public void test01ComprarCartaDesarrolloConsumeRecursos(){
        Jugador jugador = new Jugador("Azul");

        CartasDesarrollo carta = new CartaPuntoVictoria(jugador, 1);
        
        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));
        
        var caso = new CasoDeUsoCartasDeDesarrollo();

        assertTrue(caso.puedeComprar(jugador,this.turnoActual));

    }
}