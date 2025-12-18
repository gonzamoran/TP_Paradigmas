package edu.fiuba.algo3.vistas.ventanas;

import edu.fiuba.algo3.modelo.Recurso;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class PanelRecursos extends VBox {

    private final Label titulo;
    private final Map<String, Label> etiquetasRecursos = new HashMap<>();

    private static final String[][] RECURSOS = new String[][] {
        {"madera", "#2ecc71"},
        {"ladrillo", "#e74c3c"},
        {"lana", "#ecf0f1"},
        {"grano", "#f1c40f"},
        {"piedra", "#95a5a6"}
    };

    public PanelRecursos(String nombreJugador) {
        this.setPadding(new Insets(15));
        this.setSpacing(10);
        this.setStyle("-fx-background-color: rgba(20, 20, 20, 0.85); -fx-background-radius: 10; -fx-border-color: #7f8c8d; -fx-border-radius: 10;");
        this.setMinWidth(160);

        titulo = new Label("Inventario");
        titulo.setTextFill(Color.WHITE);
        titulo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        titulo.setWrapText(true);
        titulo.setMaxWidth(140);

        Label separador = new Label("________________");
        separador.setTextFill(Color.GRAY);

        this.getChildren().addAll(titulo, separador);

        for (String[] recurso : RECURSOS) {
            String nombre = recurso[0];
            String color = recurso[1];
            HBox fila = crearFilaRecurso(nombre, color);
            this.getChildren().add(fila);
        }
    }

    public void actualizarInventario(Map<Recurso, Integer> inventario) {
        Platform.runLater(() -> {
            etiquetasRecursos.values().forEach(lbl -> lbl.setText("0"));
            inventario.forEach((recurso, cantidad) -> {
                String key = recurso.getClass().getSimpleName().toLowerCase();
                Label lbl = etiquetasRecursos.get(key);
                if (lbl != null) {
                    lbl.setText(String.valueOf(cantidad));
                }
            });
        });
    }

    public void setJugador(String nombreJugador) {
        Platform.runLater(() -> titulo.setText("Inventario"));
    }

    private HBox crearFilaRecurso(String nombre, String colorWeb) {
        HBox fila = new HBox();
        fila.setAlignment(Pos.CENTER_LEFT);
        fila.setSpacing(10);

        Label colorBox = new Label("■");
        colorBox.setTextFill(Color.web(colorWeb));
        colorBox.setStyle("-fx-font-size: 18px;");

        Label lblNombre = new Label(capitalizar(nombre));
        lblNombre.setTextFill(Color.web(colorWeb));
        lblNombre.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        lblNombre.setMinWidth(60);

        Label lblCantidad = new Label("0");
        lblCantidad.setTextFill(Color.WHITE);
        lblCantidad.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        etiquetasRecursos.put(nombre.toLowerCase(), lblCantidad);

        HBox info = new HBox(5, colorBox, lblNombre);
        info.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        fila.getChildren().addAll(info, spacer, lblCantidad);
        return fila;
    }

    private String capitalizar(String texto) {
        if (texto == null || texto.isEmpty()) return "";
        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }
}
