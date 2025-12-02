package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.tablero.Coordenadas;

import java.util.ArrayList;

public class ProveedorDeDatos {
    public Coordenadas pedirCoordenadasAlUsuario(){
        return null;
    }

    public Recurso pedirRecursoAlUsuario(){
        return null;
    }

    public ArrayList<Recurso> pedirGrupoRecursosAlUsuario(int cantidad){
        return null;
    }

    public Jugador pedirNombreJugadorAlUsuario(int cantidadDeJugadores){
        return null;
    }

    public int pedirCantidadDeJugadoresAlUsuario(){
        return 0;
    }

    public Jugador pedirJugadorARobar(ArrayList<Jugador> jugadores){
        return jugadores.get(0);
    }

    //cada metodo se comunica con el usuario mediante la interfaz grafica o consola
    //se tiene que parsear la respuesta y devolver lo que corresponda

}