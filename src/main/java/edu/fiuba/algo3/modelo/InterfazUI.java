package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import edu.fiuba.algo3.modelo.construcciones.Construccion;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface InterfazUI {
    
    CompletableFuture<Coordenadas> solicitarCoordenadas();
    
    CompletableFuture<Recurso> solicitarRecurso();
    
    CompletableFuture<ArrayList<Recurso>> solicitarGrupoRecursos(int cantidad);
    
    CompletableFuture<String> solicitarNombreJugador(int numeroJugador);
    
    CompletableFuture<Integer> solicitarCantidadJugadores();
    
    CompletableFuture<Jugador> solicitarJugadorARobar(ArrayList<Jugador> jugadores);
    
    CompletableFuture<String> solicitarSiguienteAccion();
    
    CompletableFuture<Construccion> solicitarTipoConstruccion();
    
    CompletableFuture<Boolean> solicitarSeguirComerciando();
    
    CompletableFuture<Banca> solicitarTipoBanca(ArrayList<Banca> bancasDisponibles);
    
    CompletableFuture<ArrayList<Recurso>> solicitarOferta();
    
    CompletableFuture<ArrayList<Recurso>> solicitarDemanda();
    
    CompletableFuture<Boolean> solicitarAceptacionIntercambio(Jugador jugador, ArrayList<Recurso> oferta, ArrayList<Recurso> demanda);
    
    CompletableFuture<Jugador> solicitarJugadorParaComerciar(Jugador jugadorActual, ArrayList<Jugador> jugadores);
    
    void mostrarGanador(Jugador jugador);
    
    CompletableFuture<CartasDesarrollo> solicitarCartaDesarrollo(Jugador jugador, ArrayList<CartasDesarrollo> cartasDelJugador, ArrayList<CartasDesarrollo> cartasJugables);
    
    CompletableFuture<Boolean> solicitarUsarCartaDesarrollo(Jugador jugador);
}
