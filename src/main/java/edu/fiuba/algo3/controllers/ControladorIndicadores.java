package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.vistas.ventanas.ColoresJugadores;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ControladorIndicadores {
    
    private VBox boxTurno;
    private VBox boxFase;
    private Label lblPuntosVictoria;
    private Label lblTurno;
    private Label lblFase;
    private Label lblCaballeros;
    
    public VBox crearBoxTurno(String nombreJugador) {
        this.boxTurno = crearIndicadorHUD("TURNO", nombreJugador, "#f1c40f");
        this.lblTurno = (Label) boxTurno.getChildren().get(1);
        return boxTurno;
    }
    
    public VBox crearBoxFase() {
        this.boxFase = crearIndicadorHUD("FASE", "Lanzamiento de Dados", "#e67e22");
        this.lblFase = (Label) boxFase.getChildren().get(1);
        return boxFase;
    }
    
    public VBox crearBoxPuntos() {
        VBox boxPuntos = crearIndicadorHUD("PUNTOS VICTORIA", "0", "#3498db");
        this.lblPuntosVictoria = (Label) boxPuntos.getChildren().get(1);
        return boxPuntos;
    }
    
    public VBox crearBoxCaballeros() {
        VBox boxCaballeros = crearIndicadorHUD("CABALLEROS", "0", "#9b59b6");
        this.lblCaballeros = (Label) boxCaballeros.getChildren().get(1);
        return boxCaballeros;
    }
    
    public Label obtenerLblPuntosVictoria() {
        return lblPuntosVictoria;
    }

    public Label obtenerLblCaballeros() {
        return lblCaballeros;
    }

    public Label obtenerLblFase() {
        return lblFase;
    }

    public Label obtenerLblTurno() {
        return lblTurno;
    }
    
    public void actualizarTurno(String nombreJugador, int indiceJugador) {
        if (lblTurno != null) {
            lblTurno.setText(nombreJugador);
            lblTurno.setTextFill(Color.WHITE);
        }
        if (boxTurno != null) {
            Color colorJugador = ColoresJugadores.obtenerColorPoblado(indiceJugador);
            String colorHex = String.format("#%02X%02X%02X",
                (int)(colorJugador.getRed() * 255),
                (int)(colorJugador.getGreen() * 255),
                (int)(colorJugador.getBlue() * 255));
            boxTurno.setStyle("-fx-background-color: rgba(44, 62, 80, 0.9); " +
                "-fx-border-color: " + colorHex + "; " +
                "-fx-border-width: 0 0 3 0; " +
                "-fx-background-radius: 5;");
        }
    }

    private VBox crearIndicadorHUD(String titulo, String valorInicial, String colorBorde) {
        VBox box = new VBox(2);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(5, 15, 5, 15));
        box.setStyle("-fx-background-color: rgba(44, 62, 80, 0.9); " +
                "-fx-border-color: " + colorBorde + "; " +
                "-fx-border-width: 0 0 3 0; " +
                "-fx-background-radius: 5;");
        
        Label lblTitulo = new Label(titulo);
        lblTitulo.setTextFill(Color.web("#bdc3c7"));
        lblTitulo.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        
        Label lblValor = new Label(valorInicial);
        lblValor.setTextFill(Color.WHITE);
        lblValor.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        box.getChildren().addAll(lblTitulo, lblValor);
        return box;
    }
}
