package edu.fiuba.algo3.entrega_1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import edu.fiuba.algo3.modelo.Juego;

public class ConstruccionTests {

    private Juego juego;

    @BeforeEach
    public void setUp() {
        Juego juego = new Juego();
    };

    @Test
    public void test01JugadorConstruyeEnUbicacionValidaEsCorrecto() {
        /*
         * usuario -> juego construi en tal coord
         * juego -> tablero construi en tal coord
         * tablero -> agarra el vertice en tal coord es construible?
         * vertice -> chequea ady, podes construir o no
         * 
         * if coord es par:
         * if ady vacios
         * construye
         * else: exception
         * else:
         * if ady vacios
         * construye
         * else: exception
         */
        assertDoesNotThrow(juego.jugadorConstruyeEnCoordenada(unaCoordenada));
    }

    @Test
    public void test02JugadorConstruyeEnUbicacionInvalidaLanzaException() {
        juego.jugadorConstruyeEnCoordenada(unaCoordenada);
        assertThrows(ConstruccionPosInvalidaException.class, juego.jugadorConstruyeEnCoordenada(unaCoordenada));

    }

    @Test
    public void test02JugadorConstruyeEnUbicacionInvalidaLanzaException() {
        juego.jugadorConstruyeEnCoordenada(unaCoordenada);
        assertThrows(ConstruccionPosInvalidaException.class,
                juego.jugadorConstruyeEnCoordenada(unaCoordenadaAdyacente));

    }

}
