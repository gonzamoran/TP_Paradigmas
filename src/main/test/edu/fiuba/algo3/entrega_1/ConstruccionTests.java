package edu.fiuba.algo3.entrega_1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.excepciones.ConstruccionPosInvalidaException;
import edu.fiuba.algo3.modelo.excepciones.NoPuedeColocarConstruccionIniciales;

public class ConstruccionTests {

    private Juego juego;

    @BeforeEach
    public void setUp() {
        Juego juego = new Juego();
        Jugador jugador = new Jugador("1");
    };
 
    @Test
    public void test01JugadorConstruyeEnUbicacionInvalidaLanzaException() {
        int unaCoordenadax = -8;
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
/*
        llamadas:
        usuario -> juego -> tablero -> vertice -> construccion

        return:
        construccion -> vertice construir(construccion) -> tablero actualizar_construcciones(vertice)-> juego (no hace nada mas)-> usuario (ya no hace nada mas)

        flujo con funciones:
        usuario le pasa (coordenadas sin transf., tipo_construccion)->  juego
        juego .tranformar_coordenadas(coordenadas sin transf.)
        juego .construirEnCoordenada(coordenadas transformadas, tipo_construccion, jugador_actual) -> tablero
        tablero construir(Coordenadas, tipo_construccion,jugador_actual)
        tablero (tipo_construccion, jugador_actual) -> vertice_de_las_coordenadas
        vertice.construir(tipo_construccion, jugador_actual) -> construccion define que tipo de construccion es
        construccion devuelve su construccion -> vertice
        vertice hace verificacion

problema: construccion tiene una verificacion distinta si es camino o poblado/ciudad(tiene un metodo abstracto puedeConstruir())
entonces: si vertice solo necesita coordenadas para buscar que construccion sepa verificarse, hace falta que sepa sus coordenadas?


        Internamente:
        if coord es par:
            if ady vacios
                construye
            else: exception
        else:
            if ady vacios
                construye
            else: exception

        ej:
        creo juego
        juego.contruirEnCoordenada("0,0")
        juego.verConstrucciones() - > 
        "jugador 1: Pueblo en (0,0)"
        "jugador 2: sin construcciones"
    }
*/