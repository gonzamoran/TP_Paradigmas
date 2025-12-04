package edu.fiuba.algo3.modelo.acciones;

import edu.fiuba.algo3.modelo.Dados;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.ProveedorDeDatos;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Ladron;
import edu.fiuba.algo3.modelo.tablero.Tablero;

import java.util.ArrayList;

public class CasoDeUsoGirarElDado {
     private final Tablero tablero;
     private final Ladron ladron;

     public CasoDeUsoGirarElDado(Tablero tablero, Ladron ladron){
          this.tablero = tablero;
          this.ladron = ladron;
     }

     public int tirarDado(Dados dados){
          return dados.lanzarDados();
     }

     public void resolverResultado(int resultado, Jugador jugador, ProveedorDeDatos proveedor){
          if (resultado == 7){

               jugador.descartarse();

               Coordenadas destinoLadron = proveedor.pedirCoordenadasAlUsuario();

               Hexagono coordenadasHexagono = tablero.obtenerHexagono(destinoLadron);
               
               ladron.moverLadronA(coordenadasHexagono);

               ArrayList<Jugador> jugadoresAfectados = tablero.obtenerJugadoresAdyacentes(destinoLadron);
               
               ladron.moverLadronA(coordenadasHexagono);
               
               ladron.robarRecurso(jugador, jugadoresAfectados, proveedor);
          } 
          tablero.producirRecurso(resultado);
     }


}
