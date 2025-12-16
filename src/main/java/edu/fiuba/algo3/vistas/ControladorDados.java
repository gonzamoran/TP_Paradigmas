package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.GestorDeTurnos;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class ControladorDados {
    
    private final GestorDeTurnos gestor;
    private final ControladorFases controladorFases;
    private final Random random;
    private TableroUI tableroUI;
    private Button btnTirarDados;
    
    public ControladorDados(GestorDeTurnos gestor, ControladorFases controladorFases) {
        this.gestor = gestor;
        this.controladorFases = controladorFases;
        this.random = new Random();
    }

    public void setTableroUI(TableroUI tableroUI) {
        this.tableroUI = tableroUI;
    }

    public Button crearBotonTirarDados(Runnable onDadosResueltos) {
        this.btnTirarDados = new Button("Tirar Dados");
        this.btnTirarDados.setStyle("-fx-font-size: 16px; -fx-padding: 10 20; -fx-base: #ffcc00; -fx-cursor: hand; -fx-font-weight: bold;");
        this.btnTirarDados.setOnAction(e -> abrirVentanaDados(onDadosResueltos));
        this.btnTirarDados.setVisible(false);
        return this.btnTirarDados;
    }

    public void actualizarVisibilidadSegunFase(int indiceFase) {
        if (this.btnTirarDados == null) return;
        boolean visible = (indiceFase == 0) && !controladorFases.yaTiroDadoEnTurno();
        this.btnTirarDados.setVisible(visible);
    }
    
    public void abrirVentanaDados(Runnable onDadosResueltos) {
        if (controladorFases.yaTiroDadoEnTurno()) {
            return;
        }
        
        int resultado = gestor.tirarDados();
        
        controladorFases.marcarDadoTirado();
        validarResultado(resultado);
        
        int[] dados = descomponerResultado(resultado);
        
        mostrarVentanaDados(dados[0], dados[1], resultado, onDadosResueltos);
    }
    
    private void validarResultado(int resultado) {
        if (resultado < 2 || resultado > 12) {
            System.err.println("ERROR: Resultado inválido del dado: " + resultado);
            System.err.println("Jugador actual: " + gestor.obtenerJugadorActual());
            throw new IllegalArgumentException("Resultado de dados inválido: " + resultado);
        }
    }
    private int[] descomponerResultado(int resultado) {
        int minDado1 = Math.max(1, resultado - 6);
        int maxDado1 = Math.min(6, resultado - 1);
        
        int dado1 = minDado1 + random.nextInt(maxDado1 - minDado1 + 1);
        int dado2 = resultado - dado1;
        
        return new int[]{dado1, dado2};
    }
    
    private void mostrarVentanaDados(int dado1, int dado2, int resultado, Runnable onDadosResueltos) {
        final Stage stageDados = new Stage();
        VistaDados vista = new VistaDados(dado1, dado2);
        
        Button btnContinuar = new Button("Continuar");
        btnContinuar.setStyle("-fx-font-size: 14px; -fx-padding: 8 20; -fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        btnContinuar.setOnAction(e -> {
            stageDados.close();
            if (resultado == 7) {
                solicitarDatosSiete(resultado, onDadosResueltos);
            } else {
                gestor.resolverResultadoDado(resultado);
                onDadosResueltos.run();
            }
        });
        
        vista.getChildren().add(btnContinuar);
        vista.setSpacing(15);
        
        Scene scene = new Scene(vista, 300, 280);
        stageDados.setTitle("Resultado de los Dados");
        stageDados.setScene(scene);
        stageDados.show();
    }

    private void solicitarDatosSiete(int resultado, Runnable onDadosResueltos) {
        if (tableroUI == null) {
            return;
        }

        Stage stageSeleccion = new Stage();
        if (tableroUI.getScene() != null && tableroUI.getScene().getWindow() instanceof Stage owner) {
            stageSeleccion.initOwner(owner);
        }
        stageSeleccion.setAlwaysOnTop(true);
        VBox contenedor = new VBox(15);
        contenedor.setPadding(new Insets(20));
        contenedor.setAlignment(Pos.CENTER);
        contenedor.setStyle("-fx-background-color: white;");
        
        Label titulo = new Label("¡Salió 7! Debes mover el ladrón");
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        Label instrucciones = new Label("Haz clic en un hexágono del tablero para mover el ladrón");
        instrucciones.setStyle("-fx-font-size: 12px;");
        instrucciones.setWrapText(true);
        
        Label coordSeleccionadas = new Label("Ningún hexágono seleccionado");
        coordSeleccionadas.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
        
        Label lblError = new Label("Debes seleccionar un hexágono");
        lblError.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        lblError.setVisible(false);
        
        final Coordenadas[] coordenadasSeleccionadas = {null};
        
        tableroUI.habilitarSeleccionHexagono(coord -> {
            coordenadasSeleccionadas[0] = coord;
            coordSeleccionadas.setText("Hexágono seleccionado: (" + coord.obtenerCoordenadaX() + ", " + coord.obtenerCoordenadaY() + ")");
            coordSeleccionadas.setStyle("-fx-font-size: 12px; -fx-text-fill: #27ae60; -fx-font-weight: bold;");
            lblError.setVisible(false);
        });
        
        Button btnConfirmar = new Button("Confirmar");
        btnConfirmar.setStyle("-fx-font-size: 14px; -fx-padding: 8 20; -fx-background-color: #27ae60; -fx-text-fill: white;");
        btnConfirmar.setOnAction(e -> {
            if (coordenadasSeleccionadas[0] == null) {
                lblError.setVisible(true);
                return;
            }
            tableroUI.deshabilitarSeleccionHexagono();
            gestor.resolverResultadoDadoSiete(resultado, coordenadasSeleccionadas[0], null);

            tableroUI.actualizarLadronEn(coordenadasSeleccionadas[0]);
            onDadosResueltos.run();
            stageSeleccion.close();
        });
        
        HBox botones = new HBox(10, btnConfirmar);
        botones.setAlignment(Pos.CENTER);
        
        contenedor.getChildren().addAll(titulo, instrucciones, coordSeleccionadas, lblError, botones);
        
        Scene scene = new Scene(contenedor, 400, 240);
        stageSeleccion.setTitle("Mover Ladrón");
        stageSeleccion.setScene(scene);
        
        stageSeleccion.setOnCloseRequest(e -> tableroUI.deshabilitarSeleccionHexagono());
        stageSeleccion.show();
    }
}
