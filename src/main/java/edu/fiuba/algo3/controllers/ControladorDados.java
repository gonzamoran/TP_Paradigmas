package edu.fiuba.algo3.controllers;


import edu.fiuba.algo3.modelo.GestorDeTurnos;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.vistas.TableroUI;
import edu.fiuba.algo3.vistas.ventanas.ColoresJugadores;
import edu.fiuba.algo3.vistas.ventanas.VistaDados;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

public class ControladorDados {
    
    private final GestorDeTurnos gestor;
    private final ControladorFases controladorFases;
    private final Random random;
    private TableroUI tableroUI;
    private Button btnTirarDados;
    private Label lblError;
    
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
        this.lblError = new Label();
        lblError.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 10px;");
        lblError.setWrapText(true);
        lblError.setMaxWidth(350);
        lblError.setVisible(false);
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
        if (tableroUI != null && tableroUI.getScene() != null && tableroUI.getScene().getWindow() instanceof Stage owner) {
            stageDados.initOwner(owner);
            stageDados.setAlwaysOnTop(true);
            stageDados.setOnShown(ev -> {
                stageDados.setX(owner.getX() + 30);
                stageDados.setY(owner.getY() + 30);
            });
        } else {
            stageDados.setAlwaysOnTop(true);
        }
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
        VBox contenedor = new VBox(14);
        contenedor.setPadding(new Insets(18));
        contenedor.setAlignment(Pos.TOP_CENTER);
        contenedor.setStyle("-fx-background-color: white; -fx-pref-width: 240; -fx-max-width: 240;");

        Label titulo = new Label("¡Salió 7! Debes mover el ladrón");
        titulo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        titulo.setWrapText(true);
        titulo.setMaxWidth(210);

        Label instrucciones = new Label("Haz clic en un hexágono del tablero para mover el ladrón");
        instrucciones.setStyle("-fx-font-size: 11px;");
        instrucciones.setWrapText(true);
        instrucciones.setMaxWidth(210);
        instrucciones.setWrapText(true);
        
        Label coordSeleccionadas = new Label("Ningún hexágono seleccionado");
        coordSeleccionadas.setStyle("-fx-font-size: 11px; -fx-text-fill: #7f8c8d;");
        coordSeleccionadas.setWrapText(true);
        coordSeleccionadas.setMaxWidth(210);
        
        Label lblError = new Label("Debes seleccionar un hexágono");
        lblError.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 10px;");
        lblError.setWrapText(true);
        lblError.setMaxWidth(210);
        lblError.setVisible(false);
        
        final Coordenadas[] coordenadasSeleccionadas = {null};
        
        tableroUI.habilitarSeleccionHexagono(coord -> {
            coordenadasSeleccionadas[0] = coord;
            coordSeleccionadas.setText("Hexágono seleccionado: (" + coord.obtenerCoordenadaX() + ", " + coord.obtenerCoordenadaY() + ")");
            coordSeleccionadas.setStyle("-fx-font-size: 12px; -fx-text-fill: #27ae60; -fx-font-weight: bold;");
            lblError.setVisible(false);
        });
        
        Button btnConfirmar = new Button("Confirmar");
        btnConfirmar.setStyle("-fx-font-size: 12px; -fx-padding: 6 12; -fx-background-color: #27ae60; -fx-text-fill: white; -fx-cursor: hand;");
        btnConfirmar.setMaxWidth(Double.MAX_VALUE);
        btnConfirmar.setOnAction(e -> {
            if (coordenadasSeleccionadas[0] == null) {
                lblError.setVisible(true);
                return;
            }
            tableroUI.deshabilitarSeleccionHexagono();
            List<Jugador> jugadoresRobables = gestor.obtenerJugadoresAdyacentes(coordenadasSeleccionadas[0]);
            if (jugadoresRobables.isEmpty()) {
                gestor.resolverResultadoDadoSiete(resultado, coordenadasSeleccionadas[0], null);
                tableroUI.actualizarLadronEn(coordenadasSeleccionadas[0]);
                onDadosResueltos.run();
                stageSeleccion.close();
                return;
            }

            stageSeleccion.close();
            abrirSeleccionRobo(jugadoresRobables, resultado, coordenadasSeleccionadas[0], onDadosResueltos);
        });
        
        VBox botones = new VBox(8, btnConfirmar);
        botones.setAlignment(Pos.CENTER);
        
        contenedor.getChildren().addAll(titulo, instrucciones, coordSeleccionadas, lblError, botones);
        
        contenedor.setMinHeight(280);
        Scene scene = new Scene(contenedor);
        stageSeleccion.setTitle("Mover Ladrón");
        stageSeleccion.setScene(scene);
        stageSeleccion.sizeToScene();
        stageSeleccion.setOnShown(ev -> {
            stageSeleccion.sizeToScene();
            posicionarFueraDelTablero(stageSeleccion);
        });
        stageSeleccion.setOnCloseRequest(e -> {
            e.consume();
            lblError.setText("Debes seleccionar un hexágono para continuar");
            lblError.setVisible(true);
        });
        stageSeleccion.show();
    }

    private void abrirSeleccionRobo(List<Jugador> jugadoresRobables, int resultado, Coordenadas coordenadas, Runnable onDadosResueltos) {
        Stage stageRobo = new Stage();
        if (tableroUI != null && tableroUI.getScene() != null && tableroUI.getScene().getWindow() instanceof Stage owner) {
            stageRobo.initOwner(owner);
        }
        stageRobo.setAlwaysOnTop(true);

        VBox contenedor = new VBox(12);
        contenedor.setPadding(new Insets(18));
        contenedor.setAlignment(Pos.TOP_CENTER);
        contenedor.setStyle("-fx-background-color: white; -fx-pref-width: 220; -fx-max-width: 220;");

        Label titulo = new Label("Elige a quién robar");
        titulo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        titulo.setWrapText(true);
        titulo.setMaxWidth(190);

        ToggleGroup grupo = new ToggleGroup();
        VBox opciones = new VBox(8);
        opciones.setAlignment(Pos.CENTER);
        for (Jugador j : jugadoresRobables) {
            ToggleButton tarjeta = crearTarjetaJugador(j, grupo);
            opciones.getChildren().add(tarjeta);
        }

        Label lblError = new Label("Debes elegir un jugador");
        lblError.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 10px;");
        lblError.setWrapText(true);
        lblError.setMaxWidth(190);
        lblError.setVisible(false);

        Button btnConfirmar = new Button("Robar");
        btnConfirmar.setStyle("-fx-font-size: 12px; -fx-padding: 6 12; -fx-background-color: #27ae60; -fx-text-fill: white; -fx-cursor: hand;");
        btnConfirmar.setMaxWidth(Double.MAX_VALUE);
        btnConfirmar.setOnAction(e -> {
            if (grupo.getSelectedToggle() == null) {
                lblError.setVisible(true);
                return;
            }
            Jugador victima = (Jugador) grupo.getSelectedToggle().getUserData();
            gestor.resolverResultadoDadoSiete(resultado, coordenadas, victima);
            tableroUI.actualizarLadronEn(coordenadas);
            onDadosResueltos.run();
            stageRobo.close();
        });

        contenedor.getChildren().addAll(titulo, opciones, lblError, btnConfirmar);

        contenedor.setMinHeight(320);
        Scene scene = new Scene(contenedor);
        stageRobo.setTitle("Selecciona jugador a robar");
        stageRobo.setScene(scene);
        stageRobo.sizeToScene();
        stageRobo.setOnShown(ev -> {
            stageRobo.sizeToScene();
            posicionarFueraDelTablero(stageRobo);
        });
        stageRobo.show();
    }

    private void posicionarFueraDelTablero(Stage stage) {
        if (tableroUI == null || tableroUI.getScene() == null || !(tableroUI.getScene().getWindow() instanceof Stage owner)) {
            return;
        }
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        double stageW = stage.getWidth();
        double stageH = stage.getHeight();
        double margin = 16;

        Bounds tableroBounds = tableroUI.localToScreen(tableroUI.getBoundsInLocal());
        if (tableroBounds != null) {
            double targetX = tableroBounds.getMinX() - stageW - margin;
            if (targetX < -100) {
                targetX = tableroBounds.getMaxX() + margin;
                if (targetX + stageW > screen.getMaxX()) {
                    targetX = screen.getMinX() + 80;
                }
            } else if (targetX < screen.getMinX()) {
                targetX = screen.getMinX() + 80;
            }

            double targetY = tableroBounds.getMinY();
            if (targetY + stageH > screen.getMaxY() - margin) {
                targetY = screen.getMaxY() - stageH - margin;
            }
            if (targetY < screen.getMinY() + margin) {
                targetY = screen.getMinY() + margin;
            }

            stage.setX(targetX);
            stage.setY(targetY);
            return;
        }

        double ownerX = owner.getX();
        double ownerY = owner.getY();
        double ownerW = owner.getWidth();
        double ownerH = owner.getHeight();

        double targetX = ownerX - stageW - margin;
        if (targetX < screen.getMinX()) {
            targetX = ownerX + ownerW + margin;
        }
        if (targetX + stageW > screen.getMaxX()) {
            targetX = screen.getMaxX() - stageW - margin;
        }

        double targetY = ownerY + (ownerH - stageH) / 2.0;
        targetY = Math.max(screen.getMinY() + margin, Math.min(targetY, screen.getMaxY() - stageH - margin));

        stage.setX(targetX);
        stage.setY(targetY);
    }

    private ToggleButton crearTarjetaJugador(Jugador jugador, ToggleGroup grupo) {
        int indice = -1;
        if (gestor.obtenerJugadores() != null) {
            indice = gestor.obtenerJugadores().indexOf(jugador);
        }
        Color colorBase = (indice >= 0) ? ColoresJugadores.obtenerColorPoblado(indice) : Color.GRAY;
        Color colorBorde = (indice >= 0) ? ColoresJugadores.obtenerColorBorde(indice) : Color.DARKGRAY;

        Circle avatar = new Circle(24);
        avatar.setFill(colorBase);
        avatar.setStroke(colorBorde);
        avatar.setStrokeWidth(2);

        String inicial = jugador.obtenerNombre() != null && !jugador.obtenerNombre().isEmpty()
                ? jugador.obtenerNombre().substring(0, 1).toUpperCase()
                : "?";
        Text letra = new Text(inicial);
        letra.setFill(Color.WHITE);
        letra.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        StackPane retrato = new StackPane(avatar, letra);
        retrato.setPrefSize(60, 60);

        Label nombre = new Label(jugador.obtenerNombre());
        nombre.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        nombre.setTextFill(Color.web("#2c3e50"));

        VBox contenido = new VBox(8, retrato, nombre);
        contenido.setAlignment(Pos.CENTER);

        ToggleButton btn = new ToggleButton();
        btn.setUserData(jugador);
        btn.setToggleGroup(grupo);
        btn.setGraphic(contenido);
        btn.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 10;");
        btn.setOnAction(e -> {
            if (btn.isSelected()) {
                btn.setStyle("-fx-background-color: #dff9fb; -fx-border-color: " + toRgbString(colorBase) + "; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 10;");
            } else {
                btn.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 10;");
            }
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
