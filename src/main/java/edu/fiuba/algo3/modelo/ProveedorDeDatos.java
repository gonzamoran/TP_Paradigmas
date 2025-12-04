package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.*;
import edu.fiuba.algo3.modelo.Banca;
import edu.fiuba.algo3.modelo.tiposBanca.*;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.cartas.*;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.*;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ProveedorDeDatos {
    
    private InterfazUI vista;
    
    public void setVista(InterfazUI vista) {
        this.vista = vista;
    }
    
    public Coordenadas pedirCoordenadasAlUsuario(){
        if (vista != null) {
            return vista.solicitarCoordenadas().join();
        }
        return null;
    }

    public Recurso pedirRecursoAlUsuario(){
        if (vista != null) {
            return vista.solicitarRecurso().join();
        }
        return null;
    }

    public ArrayList<Recurso> pedirGrupoRecursosAlUsuario(int cantidad){
        if (vista != null) {
            return vista.solicitarGrupoRecursos(cantidad).join();
        }
        return new ArrayList<>();
    }

    public Jugador pedirNombreJugadorAlUsuario(int cantidadDeJugadores){
        if (vista != null) {
            String nombre = vista.solicitarNombreJugador(cantidadDeJugadores).join();
            return new Jugador(nombre);
        }
        return null;
    }

    public int pedirCantidadDeJugadoresAlUsuario(){
        if (vista != null) {
            return vista.solicitarCantidadJugadores().join();
        }
        return 0;
    }

    public Jugador pedirJugadorARobar(ArrayList<Jugador> jugadores){
        if (vista != null) {
            return vista.solicitarJugadorARobar(jugadores).join();
        }
        return jugadores.get(0);
    }

    public String pedirSiguienteAccionARealizarAlUsuario(){
        if (vista != null) {
            return vista.solicitarSiguienteAccion().join();
        }
        return "Hola mundo!";
    }
    
    public Construccion pedirTipoDeConstruccionAlUsuario(){
        if (vista != null) {
            return vista.solicitarTipoConstruccion().join();
        }
        return new Poblado();        
    }
    
    public boolean quiereSeguirComerciando(){
        if (vista != null) {
            return vista.solicitarSeguirComerciando().join();
        }
        return true;
    }

    public Banca pedirTipoDeBancaAlUsuario(ArrayList<Banca> bancasDisponibles){
        if (vista != null) {
            return vista.solicitarTipoBanca(bancasDisponibles).join();
        }
        return new Banca4a1();
    }

    public ArrayList<Recurso> pedirOfertaAlUsuario(){
        if (vista != null) {
            return vista.solicitarOferta().join();
        }
        return new ArrayList<Recurso>();
    }

    public ArrayList<Recurso> pedirDemandaAlUsuario(){
        if (vista != null) {
            return vista.solicitarDemanda().join();
        }
        return new ArrayList<Recurso>();
    }

    public boolean aceptaIntercambio(Jugador jugador, ArrayList<Recurso> oferta, ArrayList<Recurso> demanda){
        if (vista != null) {
            return vista.solicitarAceptacionIntercambio(jugador, oferta, demanda).join();
        }
        return true;
    }
    
    public Jugador pedirJugadorParaComerciar(Jugador jugador, ArrayList<Jugador> jugadores){
        if (vista != null) {
            return vista.solicitarJugadorParaComerciar(jugador, jugadores).join();
        }
        return jugador;
    }

    public void anunciarGanador(Jugador jugador){
        if (vista != null) {
            vista.mostrarGanador(jugador);
        } else {
            System.out.println("Hola mundo!");
        }
    }

    public CartasDesarrollo elegirCartaDesarrolloParaUsar(Jugador jugador, ArrayList<CartasDesarrollo> cartasDelJugador, ArrayList<CartasDesarrollo> cartasJugables){
        if (vista != null) {
            return vista.solicitarCartaDesarrollo(jugador, cartasDelJugador, cartasJugables).join();
        }
        return new CartaCaballero();
    }

    public boolean quiereUsarCartaDesarrollo(Jugador jugador){
        if (vista != null) {
            return vista.solicitarUsarCartaDesarrollo(jugador).join();
        }
        return true;
    }
}
