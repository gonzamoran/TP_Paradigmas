package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Inventario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/** Comienzo de panel para mostrar recursoss del jugador
 */

public class PanelRecursos extends VBox {
    private Inventario inventario;
    private Map<String, Label> etiquetasRecursos;

    private static final String[] TIPOS_RECURSOS = {"Madera", "Ladrillo", "Lana", "Grano", "Mineral"};
    private static final String[] COLORES_RECURSOS = {
        "#8B4513",   
        "rgba(221, 48, 17, 1)",   
        "#F5F5DC",   
        "#FFD700",   
        "#DC143C"    
    };

    public PanelRecursos(Inventario inventario){
        this.inventario = inventario;
        this.etiquetasRecursos = new HashMap<>();

        this.setPadding(new Insets(10));
        this.setSpacing(5);

        this.setStyle("-fx-border-color: #444444; -fx-border-width: 1; -fx-background-color: #1a1a1a;");
        inicializarComponentes();
    }
    }