package edu.fiuba.algo3.entrega_1;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.fiuba.algo3.modelo.tablero.TipoRecurso;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.Jugador;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;  

public class JuegoTests{

    private List<Hexagono> hexagonos;
    private Tablero tablero;
    private Jugador dueño;


    @BeforeEach
    public void setUp(){
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador();
    }

    @Test void test01VerificarAgregarRecursosCorrectamente(){
       for(Hexagono hexagono : hexagonos){
              if(hexagono.obtenerRecurso() != TipoRecurso.DESIERTO){
                dueño.agregarRecurso(hexagono.obtenerRecurso(), 5);
                assertEquals(5, dueño.obtenerCantidadRecurso(hexagono.obtenerRecurso()));
              }
       }
    }

    @Test void test02VerificarAgregarRecursosSegunSegundoPobladoColocado(){
        //Arrange

    }

    @Test void test03VerificarQueElJugadorRecibaRecursosIniciales(){
        //Arrange
        for(Hexagono hexagono : hexagonos){
            if(hexagono.obtenerRecurso() != TipoRecurso.DESIERTO){
                dueño.agregarRecurso(hexagono.obtenerRecurso(), 1);
            }
        }
    }

    @Test void test04VerificarQueNoSePermitaRecibirRecursosNegativo(){
        //Arrange
        assertEquals(0, dueño.obtenerCantidadRecurso(TipoRecurso.MADERA));
        //Act
        dueño.agregarRecurso(TipoRecurso.MADERA, -3);
        //Assert
        assertThrows(IllegalArgumentException.class,() -> {
            dueño.agregarRecurso(TipoRecurso.MADERA, -3);
        });
    }
}