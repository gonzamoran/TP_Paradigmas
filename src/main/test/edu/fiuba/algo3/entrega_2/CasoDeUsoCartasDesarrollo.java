package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.construcciones.Ciudad;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.Recurso;

import edu.fiuba.algo3.entrega_2.casosDeUso.CasoDeUsoCartasDeDesarrollo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.lang.invoke.CallSite;

import org.junit.Test;


public class CasoDeUsoCartasDesarrollo {
    @Test
    public void testComprarCartaDesarrolloConsumeRecursos(){
        Jugador jugador = new Jugador("Azul");

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Piedra(1));
        jugador.agregarRecurso(new Grano(1));
        
        CasoDeUsoCartasDeDesarrollo caso = new CasoDeUsoCartasDeDesarrollo();
        

    }
}