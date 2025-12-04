package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.*;
import edu.fiuba.algo3.modelo.Banca;
import edu.fiuba.algo3.modelo.tiposBanca.*;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.cartas.*;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.*;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;


import java.util.ArrayList;

public class ProveedorDeDatos {
    
    public Coordenadas pedirCoordenadasAlUsuario(){
        //var coords = ui.pedirCoordenadasAlUsuario(); 
        //Coordenadas coordenadasParseadas = parsearCoordenadas(coords);
        //return coordenadasParseadas;
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

    public String pedirSiguienteAccionARealizarAlUsuario(){
        return ("Hola mundo!");
    }
    public Construccion pedirTipoDeConstruccionAlUsuario(){
        return new Poblado();        
    }
    public boolean quiereSeguirComerciando(/*Jugador jugadorActual*/){
        return true;
    }

    public Banca pedirTipoDeBancaAlUsuario(ArrayList<Banca> bancasDisponibles){
        return new Banca4a1();
    }

    public ArrayList<Recurso> pedirOfertaAlUsuario(){
        return new ArrayList<Recurso>();
    }

    public ArrayList<Recurso> pedirDemandaAlUsuario(){
        return new ArrayList<Recurso>();
    }

    public boolean aceptaIntercambio(Jugador jugador, ArrayList<Recurso> oferta, ArrayList<Recurso> demanda){
        return true;
    }
    public Jugador pedirJugadorParaComerciar(Jugador jugador, ArrayList<Jugador> jugadores){
        return jugador;
    }
     
    //public void ejecutarComercio(Banca banca, ArrayList<Recurso> oferta, ArrayList<Recurso> demanda){
    //    System.out.println("Hola mundo!");
    //}
    public void anunciarGanador(Jugador jugador){
        System.out.println("Hola mundo!");
    }

    public CartasDesarrollo elegirCartaDesarrolloParaUsar(Jugador jugador, ArrayList<CartasDesarrollo> cartasDelJugador, ArrayList<CartasDesarrollo> cartasJugables){
        return new CartaCaballero();
    }

    public boolean quiereUsarCartaDesarrollo(Jugador jugador){
        return true;
    }
}
