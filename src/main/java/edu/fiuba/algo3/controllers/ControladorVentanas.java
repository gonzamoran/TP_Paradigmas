package edu.fiuba.algo3.controllers;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import edu.fiuba.algo3.modelo.GestorDeTurnos;
import edu.fiuba.algo3.vistas.TableroUI;
import edu.fiuba.algo3.vistas.ventanas.VentanaBaraja;
import edu.fiuba.algo3.vistas.ventanas.VentanaCartasBonificacion;
import edu.fiuba.algo3.vistas.ventanas.VentanaCartasDesarrollos;
import edu.fiuba.algo3.vistas.ventanas.VentanaComerciar;
import edu.fiuba.algo3.vistas.ventanas.VentanaFinJuego;

public class ControladorVentanas {

    private String jugador;
    private GestorDeTurnos gestor;
    private Stage ventanaCartasDesarrollo;

    public ControladorVentanas(String jugador, GestorDeTurnos gestor) {
        this.jugador = jugador;
        this.gestor = gestor;
    }

    public void abrirVentanaBaraja() {
        Stage stageBaraja = new Stage();

        VentanaBaraja ventana = new VentanaBaraja(stageBaraja, jugador);
        ScrollPane scroll = new ScrollPane(ventana);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);

        Scene scene = new Scene(scroll, 600, 500);
        stageBaraja.setTitle("Baraja");
        stageBaraja.setScene(scene);
        stageBaraja.show();
    }

    public void abrirVentanaCartasDesarrollo(ControladorFases controladorFases, TableroUI tableroUI, Runnable onActualizarUI) {
        if (ventanaCartasDesarrollo != null && ventanaCartasDesarrollo.isShowing()) {
            ventanaCartasDesarrollo.requestFocus();
            return;
        }

        ventanaCartasDesarrollo = new Stage();
        ventanaCartasDesarrollo.setOnCloseRequest(e -> {
            ventanaCartasDesarrollo = null;
            if (onActualizarUI != null) onActualizarUI.run();
        });

        VentanaCartasDesarrollos ventana = new VentanaCartasDesarrollos(ventanaCartasDesarrollo, jugador, gestor, controladorFases, tableroUI, onActualizarUI);
        ScrollPane scroll = new ScrollPane(ventana);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);

        Scene scene = new Scene(scroll, 600, 500);
        ventanaCartasDesarrollo.setTitle("Cartas de Desarrollo");
        ventanaCartasDesarrollo.setScene(scene);
        ventanaCartasDesarrollo.show();
    }

    public void abrirVentanaComerciar() {
        Stage stageComercio = new Stage();

        VentanaComerciar ventana = new VentanaComerciar(stageComercio, jugador);

        Scene scene = new Scene(ventana, 500, 300);
        stageComercio.setTitle("Comerciar");
        stageComercio.setScene(scene);
        stageComercio.show();
    }

    public void abrirVentanaGanador(String nombreGanador) {
        Stage stageGanador = new Stage();

        VentanaFinJuego ventana = new VentanaFinJuego(nombreGanador);

        Scene scene = new Scene(ventana, 400, 200);
        stageGanador.setTitle("¡Tenemos un ganador!");
        stageGanador.setScene(scene);
        stageGanador.show();
    }

    public void abrirVentanaCartasBonificacion() {
        Stage stageBonificacion = new Stage();

        VentanaCartasBonificacion ventana = new VentanaCartasBonificacion(stageBonificacion, jugador, gestor);
        ScrollPane scroll = new ScrollPane(ventana);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);

        Scene scene = new Scene(scroll, 600, 500);
        stageBonificacion.setTitle("Cartas de Bonificación");
        stageBonificacion.setScene(scene);
        stageBonificacion.show();
    }
}
