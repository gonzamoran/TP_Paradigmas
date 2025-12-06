package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.InterfazUI;
import edu.fiuba.algo3.modelo.ProveedorDeDatos;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Banca;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.Construccion;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AdaptadorVistaJavaFX implements InterfazUI {
    
    private Stage escenarioPrincipal;
    private VentanaConstruirCarretera ventanaCarretera;
    private VentanaConstruirPoblado ventanaPoblado;
    private VentanaComerciar ventanaComerciar;
    private VentanaCartasDesarrollos ventanaCartas;
    private VentanaVictoria ventanaVictoria;
    private CampoDeJuego campoDeJuego;
    
    public AdaptadorVistaJavaFX(Stage escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void setCampoDeJuego(CampoDeJuego campoDeJuego) {
        this.campoDeJuego = campoDeJuego;
    }
    
    @Override
    public CompletableFuture<Coordenadas> solicitarCoordenadas() {
        CompletableFuture<Coordenadas> futuro = new CompletableFuture<>();
        
        Platform.runLater(() -> {
            Stage ventana = new Stage();
            ventanaCarretera = new VentanaConstruirCarretera(ventana, "Jugador Actual");
            
            ventanaCarretera.solicitarCoordenadas().thenAccept(coords -> {
                if (coords != null && coords.length > 0) {
                    futuro.complete(coords[0]);
                } else {
                    futuro.complete(null);
                }
            });
        });
        
        return futuro;
    }
    
    @Override
    public CompletableFuture<Recurso> solicitarRecurso() {

        CompletableFuture<Recurso> futuro = new CompletableFuture<>();
        futuro.complete(null);
        return futuro;
    }
    
    @Override
    public CompletableFuture<ArrayList<Recurso>> solicitarGrupoRecursos(int cantidad) {

        CompletableFuture<ArrayList<Recurso>> futuro = new CompletableFuture<>();
        futuro.complete(new ArrayList<>());
        return futuro;
    }
    
    @Override
    public CompletableFuture<String> solicitarNombreJugador(int numeroJugador) {

        CompletableFuture<String> futuro = new CompletableFuture<>();
        futuro.complete("Jugador " + numeroJugador);
        return futuro;
    }
    
    @Override
    public CompletableFuture<Integer> solicitarCantidadJugadores() {

        CompletableFuture<Integer> futuro = new CompletableFuture<>();
        futuro.complete(2);
        return futuro;
    }
    
    @Override
    public CompletableFuture<Jugador> solicitarJugadorARobar(ArrayList<Jugador> jugadores) {
        CompletableFuture<Jugador> futuro = new CompletableFuture<>();
        if (!jugadores.isEmpty()) {
            futuro.complete(jugadores.get(0));
        } else {
            futuro.complete(null);
        }
        return futuro;
    }
    
    @Override
    public CompletableFuture<String> solicitarSiguienteAccion() {
        
        CompletableFuture<String> futuro = new CompletableFuture<>();
        futuro.complete("Terminar turno");
        return futuro;
    }

    @Override
    public CompletableFuture<String> solicitarAccionConstruccionComercio() {
        CompletableFuture<String> futuro = new CompletableFuture<>();
        futuro.complete("TERMINAR_TURNO");
        return futuro;
    }
    
    @Override
    public CompletableFuture<Construccion> solicitarTipoConstruccion() {
        CompletableFuture<Construccion> futuro = new CompletableFuture<>();
        futuro.complete(null);
        return futuro;
    }
    
    @Override
    public CompletableFuture<Boolean> solicitarSeguirComerciando() {
       
        CompletableFuture<Boolean> futuro = new CompletableFuture<>();
        futuro.complete(false);
        return futuro;
    }
    
    @Override
    public CompletableFuture<Banca> solicitarTipoBanca(ArrayList<Banca> bancasDisponibles) {
        
        CompletableFuture<Banca> futuro = new CompletableFuture<>();
        if (!bancasDisponibles.isEmpty()) {
            futuro.complete(bancasDisponibles.get(0));
        } else {
            futuro.complete(null);
        }
        return futuro;
    }
    
    @Override
    public CompletableFuture<ArrayList<Recurso>> solicitarOferta() {
        
        CompletableFuture<ArrayList<Recurso>> futuro = new CompletableFuture<>();
        futuro.complete(new ArrayList<>());
        return futuro;
    }
    
    @Override
    public CompletableFuture<ArrayList<Recurso>> solicitarDemanda() {
    
        CompletableFuture<ArrayList<Recurso>> futuro = new CompletableFuture<>();
        futuro.complete(new ArrayList<>());
        return futuro;
    }
    
    @Override
    public CompletableFuture<Boolean> solicitarAceptacionIntercambio(Jugador jugador, ArrayList<Recurso> oferta, ArrayList<Recurso> demanda) {
        
        CompletableFuture<Boolean> futuro = new CompletableFuture<>();
        futuro.complete(true);
        return futuro;
    }
    
    @Override
    public CompletableFuture<Jugador> solicitarJugadorParaComerciar(Jugador jugadorActual, ArrayList<Jugador> jugadores) {
    
        CompletableFuture<Jugador> futuro = new CompletableFuture<>();
        futuro.complete(jugadorActual);
        return futuro;
    }
    
    @Override
    public void mostrarGanador(Jugador jugador) {
    }
    
    @Override
    public CompletableFuture<CartasDesarrollo> solicitarCartaDesarrollo(Jugador jugador, ArrayList<CartasDesarrollo> cartasDelJugador, ArrayList<CartasDesarrollo> cartasJugables) {
        CompletableFuture<CartasDesarrollo> futuro = new CompletableFuture<>();
        if (!cartasJugables.isEmpty()) {
            futuro.complete(cartasJugables.get(0));
        } else {
            futuro.complete(null);
        }
        return futuro;
    }
    
    @Override
    public CompletableFuture<Boolean> solicitarUsarCartaDesarrollo(Jugador jugador) {
        CompletableFuture<Boolean> futuro = new CompletableFuture<>();
        futuro.complete(false);
        return futuro;
    }

    @Override
    public void notificarCambioTurno(Jugador jugadorActual, int indiceJugador, int numeroTurno) {
        if (campoDeJuego != null) {
            campoDeJuego.actualizarTurno(jugadorActual.obtenerNombre(), numeroTurno);
        }
    }

    @Override
    public void notificarCambioInventario(Jugador jugador, Map<Recurso, Integer> inventario) {
        if (campoDeJuego != null) {
            campoDeJuego.actualizarInventario(inventario);
        }
    }

    @Override
    public void notificarCambiosPuntosVictoria(Jugador jugador, int puntos) {
        if (campoDeJuego != null) {
            campoDeJuego.actualizarPuntosVictoria(puntos);
        }
    }
}

