package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MenuLateralDerecho extends VBox{
    private final Tablero tablero;
    private final CampoDeJuego campoDeJuego;

    public MenuLateralDerecho(CampoDeJuego campoDeJuego, Tablero tablero) {
        this.campoDeJuego = campoDeJuego;
        this.tablero = tablero;
    }
}