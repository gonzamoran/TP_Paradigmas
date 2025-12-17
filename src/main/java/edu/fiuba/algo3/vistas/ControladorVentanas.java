package edu.fiuba.algo3.vistas;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import edu.fiuba.algo3.modelo.GestorDeTurnos;

public class ControladorVentanas {

    private String jugador;
    private GestorDeTurnos gestor;

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

    public void abrirVentanaCartasDesarrollo(ControladorFases controladorFases, TableroUI tableroUI) {
        Stage stageDesarrollo = new Stage();

        VentanaCartasDesarrollos ventana = new VentanaCartasDesarrollos(stageDesarrollo, jugador, gestor, controladorFases, tableroUI);
        ScrollPane scroll = new ScrollPane(ventana);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);

        Scene scene = new Scene(scroll, 600, 500);
        stageDesarrollo.setTitle("Cartas de Desarrollo");
        stageDesarrollo.setScene(scene);
        stageDesarrollo.show();
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
