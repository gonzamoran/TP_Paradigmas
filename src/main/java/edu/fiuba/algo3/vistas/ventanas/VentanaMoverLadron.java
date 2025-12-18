package edu.fiuba.algo3.vistas.ventanas;

import edu.fiuba.algo3.modelo.GestorDeTurnos;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.vistas.TableroUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.application.Platform;

import java.util.List;

public class VentanaMoverLadron extends VentanaModalBase {
    private final boolean permitirCerrar;
    private final GestorDeTurnos gestor;
    private final java.util.function.BiConsumer<Coordenadas, Jugador> onSeleccion;
    private Label lblSeleccion;

    public VentanaMoverLadron(Stage stage, String nombreJugador, GestorDeTurnos gestor, TableroUI tableroUI, java.util.function.BiConsumer<Coordenadas, Jugador> onSeleccion, boolean permitirCerrar) {
        super(stage, "Mover Ladrón", nombreJugador, tableroUI);
        stage.setAlwaysOnTop(true);
        this.gestor = gestor;
        this.onSeleccion = onSeleccion;
        this.permitirCerrar = permitirCerrar;
        // Si no se permite cerrar, se consume el evento de cierre
        stage.setOnCloseRequest(e -> {
            if (!permitirCerrar) {
                e.consume();
            } else {
                if (tableroUI != null) tableroUI.deshabilitarSeleccionHexagono();
            }
        });
        construirYMostrar();
    }

    @Override
    protected String getTitulo() {
        return "Mover Ladrón";
    }

    @Override
    protected String getInstruccion() {
        return "Haz clic en un hexágono del tablero para mover el ladrón";
    }

    @Override
    protected VBox crearContenidoPersonalizado() {
        lblSeleccion = new Label("Ningún hexágono seleccionado");
        lblSeleccion.setStyle("-fx-font-size: 11px; -fx-text-fill: #7f8c8d;");

        configurarSeleccion();

        VBox contenido = new VBox(8, lblSeleccion);
        contenido.setAlignment(Pos.CENTER);
        return contenido;
    }

    private void configurarSeleccion() {
        if (tableroUI != null) {
            tableroUI.habilitarSeleccionHexagono(coord -> {
                lblSeleccion.setText("Hexágono seleccionado: (" + coord.obtenerCoordenadaX() + ", " + coord.obtenerCoordenadaY() + ")");
                lblSeleccion.setStyle("-fx-font-size: 12px; -fx-text-fill: #27ae60; -fx-font-weight: bold;");
                // Guardamos la coordenada en userData del label para recuperar al confirmar
                lblSeleccion.setUserData(coord);
            });
        }
    }

    @Override
    protected void alConfirmar() {
        Object obj = lblSeleccion.getUserData();
        if (obj == null || !(obj instanceof Coordenadas)) {
            mostrarError("Debes seleccionar un hexágono");
            return;
        }
        Coordenadas coord = (Coordenadas) obj;

        // deshabilitar seleccion para evitar doble evento
        limpiarSelecciones();

        List<Jugador> jugables = gestor.obtenerJugadoresAdyacentes(coord);
        if (jugables == null || jugables.isEmpty()) {
            try {
                if (onSeleccion != null) onSeleccion.accept(coord, null);
            } finally {
                confirmado = true;
                stage.close();
            }
            return;
        }

        // Abrir ventana para elegir jugador a robar
        Stage stageRobo = new Stage();
        if (tableroUI != null && tableroUI.getScene() != null && tableroUI.getScene().getWindow() instanceof Stage owner) {
            stageRobo.initOwner(owner);
        }
        stageRobo.setAlwaysOnTop(true);

        VBox cont = new VBox(12);
        cont.setPadding(new Insets(18));
        cont.setAlignment(Pos.TOP_CENTER);
        cont.setStyle("-fx-background-color: white; -fx-pref-width: 220; -fx-max-width: 220;");

        Label titulo = new Label("Elige a quién robar");
        titulo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        titulo.setWrapText(true);
        titulo.setMaxWidth(190);

        ToggleGroup grupo = new ToggleGroup();
        VBox opciones = new VBox(8);
        opciones.setAlignment(Pos.CENTER);
        for (Jugador j : jugables) {
            ToggleButton tarjeta = crearTarjetaJugador(j, grupo);
            opciones.getChildren().add(tarjeta);
        }

        Button btnRobar = new Button("Robar");
        btnRobar.setStyle("-fx-font-size: 12px; -fx-padding: 6 12; -fx-background-color: #27ae60; -fx-text-fill: white; -fx-cursor: hand;");
        btnRobar.setMaxWidth(Double.MAX_VALUE);
        btnRobar.setOnAction(e -> {
            if (grupo.getSelectedToggle() == null) return;
            Jugador victima = (Jugador) grupo.getSelectedToggle().getUserData();
            try {
                if (onSeleccion != null) onSeleccion.accept(coord, victima);
            } finally {
                confirmado = true;
                stageRobo.close();
                stage.close();
            }
        });

        cont.getChildren().addAll(titulo, opciones, btnRobar);
        cont.setMinHeight(320);
        Scene scene = new Scene(cont);
        stageRobo.setTitle("Selecciona jugador a robar");
        stageRobo.setScene(scene);
        stageRobo.sizeToScene();
        stageRobo.show();
    }

    private ToggleButton crearTarjetaJugador(Jugador jugador, ToggleGroup grupo) {
        int indice = -1;
        if (gestor.obtenerJugadores() != null) indice = gestor.obtenerJugadores().indexOf(jugador);
        Color colorBase = (indice >= 0) ? ColoresJugadores.obtenerColorPoblado(indice) : Color.GRAY;
        Color colorBorde = (indice >= 0) ? ColoresJugadores.obtenerColorBorde(indice) : Color.DARKGRAY;

        Circle avatar = new Circle(24);
        avatar.setFill(colorBase);
        avatar.setStroke(colorBorde);
        avatar.setStrokeWidth(2);

        String inicial = jugador.obtenerNombre() != null && !jugador.obtenerNombre().isEmpty() ? jugador.obtenerNombre().substring(0,1).toUpperCase() : "?";
        Text letra = new Text(inicial);
        letra.setFill(Color.WHITE);
        letra.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        StackPane retrato = new StackPane(avatar, letra);
        retrato.setPrefSize(60,60);

        Label nombre = new Label(jugador.obtenerNombre());
        nombre.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

        VBox contenido = new VBox(8, retrato, nombre);
        contenido.setAlignment(Pos.CENTER);

        ToggleButton btn = new ToggleButton();
        btn.setUserData(jugador);
        btn.setToggleGroup(grupo);
        btn.setGraphic(contenido);
        btn.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 10;");
        btn.setOnAction(e -> {
            if (btn.isSelected()) btn.setStyle("-fx-background-color: #dff9fb; -fx-border-color: " + toRgbString(colorBase) + "; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 10;");
            else btn.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 10;");
        });
        return btn;
    }

    private String toRgbString(Color color) {
        int r = (int) Math.round(color.getRed() * 255);
        int g = (int) Math.round(color.getGreen() * 255);
        int b = (int) Math.round(color.getBlue() * 255);
        return String.format("rgb(%d,%d,%d)", r, g, b);
    }
}
