package edu.fiuba.algo3.modelo.acciones;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.*;
import edu.fiuba.algo3.modelo.Dados;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.cartas.CartasJugador;
import edu.fiuba.algo3.modelo.construcciones.Construccion;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;

import edu.fiuba.algo3.modelo.ProveedorDeDatos;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CasoDeUsoGirarElDado {
    private final Tablero tablero;

   public CasoDeUsoGirarElDado(Tablero tablero){
        this.tablero = tablero;
   }

   public int tirarDado(Dados dados){
        return dados.lanzarDados();
   }

   public void resolverResultado(int resultado, Jugador jugador, ProveedorDeDatos proveedor){
        if (resultado == 7){

            jugador.descartarse();

            // Aca la unica distincion seria que las coordenadas son de un hexagono no de un vertice.
            Coordenadas destinoLadron = proveedor.pedirCoordenadasAlUsuario();
            //Hexagono destino = tablero.obtenerHexagono(destinoLadron); //para despues -> ladron.moverLadronA(destino);

            tablero.moverLadronA(destinoLadron);
            
            tablero.ladronRobaRecurso(jugador, proveedor);
        } 
        tablero.producirRecurso(resultado);
   }


}
