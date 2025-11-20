// /*
// package edu.fiuba.algo3.vistas;

// import edu.fiuba.algo3.modelo.Jugador;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.VBox;
// import javafx.scene.control.Label;
// import javafx.scene.paint.Color;
// import javafx.scene.shape.Circle;
// import java.util.List;

// //Comienzo de panel para mostrar los puntos de victoria de cada jugador


// public class PanelPuntosVictoria extends VBox {
//     private List<Jugador> jugadores;
//     private Map<String, Label> etiquetasPV;

//     private static final String[] COLORES_JUGADORES = {
//         "#ff4444",   
//         "#4444ff", 
//         "#ffffff",   
//         "#ffaa44"   
//     };

//     public PanelPuntosVictoria(List<Jugador> jugadores) {
//         this.jugadores = jugadores;
//         this.etiquetasPV = new HashMap<>();

//         this.setPadding(new Insets(10));
//         this.setSpacing(8);
//         this.setStyle("-fx-border-color: #444444; -fx-border-width: 1; -fx-background-color: #1a1a1a;");

//         inicializarComponentes();
//     }

//     private void inicializarComponentes() {
//         Label titulo = new Label("Puntos de Victoria");
//         titulo.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: #ffffff;");
//         this.getChildren().add(titulo);

//         for (int i = 0; i < jugadores.size(); i++) {
//             HBox jugadorBox = crearFilaJugador(jugadores.get(i), COLORES_JUGADORES[i % COLORES_JUGADORES.length]);
//             this.getChildren().add(jugadorBox);
//         }
//     }

//     private HBox crearFilaJugador(Jugador jugador, String color) {
//         HBox fila = new HBox(10);
//         fila.setPadding(new Insets(8));
//         fila.setStyle("-fx-background-color: #0a0a0a;");
//         fila.setAlignment(Pos.CENTER_LEFT);

//         Label jugadorNombre = new Label(jugador.getNombre());
//         jugadorNombre.setStyle("-fx-text-fill: #ffffff; -fx-font-size:11;")
//         jugadorNombre.setPrefWidth(100);

//         Label puntosVictoria = new Label("0");
//         puntosVictoria.setStyle("-fx-text-fill: #ffff00; -fx-font-size:11; -fx-font-weight: bold;")
//     }