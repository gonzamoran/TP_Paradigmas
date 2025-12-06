package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.ProveedorDeDatos;
import edu.fiuba.algo3.modelo.GestorDeTurnos;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Produccion;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CampoDeJuego extends BorderPane {

    private final Stage escenario;
    private final ArrayList<Hexagono> hexagones;
    private final ArrayList<Produccion> producciones;
    private ProveedorDeDatos proveedor;
    private AdaptadorVistaJavaFX adaptador;
    private List<String> nombresJugadores;
    private GestorDeTurnos gestor;
    private VBox boxTurno;
    private VBox boxFase;
    private Label lblPuntosVictoria;
    private java.util.Map<String, Label> labelsInventario = new java.util.HashMap<>();

    public CampoDeJuego(Stage escenario, GestorDeTurnos gestor, ArrayList<Hexagono> hexagones, ArrayList<Produccion> producciones, ProveedorDeDatos proveedor, List<String> nombresJugadores) {
        this.escenario = escenario;
        this.gestor = gestor;
        this.hexagones = hexagones;
        this.producciones = producciones;
        this.proveedor = proveedor;
        this.nombresJugadores = nombresJugadores != null ? nombresJugadores : new ArrayList<>();

        this.adaptador = new AdaptadorVistaJavaFX(escenario);
        this.adaptador.setCampoDeJuego(this);
        this.proveedor.setVista(adaptador);
        
        construirInterfaz();

        escenario.setScene(new Scene(this, 1500, 900));
        escenario.centerOnScreen();

        Thread hiloJuego = new Thread(() -> {
            gestor.jugar();
        });
        hiloJuego.setDaemon(true);
        hiloJuego.start();
    }

    private void construirInterfaz() {
        BorderPane barraSuperior = new BorderPane();
        barraSuperior.setPadding(new Insets(10));
        barraSuperior.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");

        HBox listaJugadores = new HBox(10);
        listaJugadores.setAlignment(Pos.CENTER_LEFT);
        for (String nombre : nombresJugadores) {
            Button btnNombre = new Button(nombre);
            btnNombre.setStyle("-fx-font-size: 12px; -fx-background-color: #ecf0f1; -fx-text-fill: #2c3e50;");
            listaJugadores.getChildren().add(btnNombre);
        }

        HBox indicadoresPanel = new HBox(15);
        indicadoresPanel.setAlignment(Pos.CENTER_RIGHT);
        
        String primerJugador = !nombresJugadores.isEmpty() ? nombresJugadores.get(0) : "Esperando...";
        this.boxTurno = crearIndicadorHUD("TURNO", primerJugador, "#f1c40f");
        this.boxFase = crearIndicadorHUD("FASE", "Construcción", "#e67e22");
        
        indicadoresPanel.getChildren().addAll(boxTurno, boxFase);

        barraSuperior.setLeft(listaJugadores);
        barraSuperior.setRight(indicadoresPanel);
        this.setTop(barraSuperior);

        VBox panelInventario = crearPanelInventario(primerJugador);

        VBox panelDerecho = new VBox(10);
        panelDerecho.setPadding(new Insets(10));
        panelDerecho.setAlignment(Pos.TOP_CENTER);
        panelDerecho.setStyle("-fx-background-color: rgba(0,0,0,0.2); -fx-padding: 10;");
        panelDerecho.setMinWidth(200);

        VBox boxPuntos = crearIndicadorHUD("PUNTOS VICTORIA", "0", "#3498db");
        this.lblPuntosVictoria = (Label) boxPuntos.getChildren().get(1);
        
        Button btnPoblado = crearBotonAccion("Construir Poblado", "#27ae60");
        Button btnCarretera = crearBotonAccion("Construir Carretera", "#27ae60");
        Button btnComerciar = crearBotonAccion("Comerciar", "#3498db");
        Button btnTerminarFase = crearBotonAccion("Terminar Fase", "#e67e22");

        btnPoblado.setOnAction(e -> ejecutarConstruccion());
        btnCarretera.setOnAction(e -> ejecutarConstruccion());
        btnComerciar.setOnAction(e -> ejecutarComercioBanca());
        btnTerminarFase.setOnAction(e -> gestor.finalizarFaseConstruccion());
        
        VBox botonesBox = new VBox(8, btnPoblado, btnCarretera, btnComerciar, btnTerminarFase);
        botonesBox.setAlignment(Pos.TOP_CENTER);

        panelDerecho.getChildren().addAll(boxPuntos, botonesBox);

        VBox contenedorDerecho = new VBox(12, panelInventario, panelDerecho);
        contenedorDerecho.setAlignment(Pos.TOP_CENTER);
        contenedorDerecho.setPadding(new Insets(10));
        contenedorDerecho.setMinWidth(220);
        this.setRight(contenedorDerecho);

        TableroUI tableroUI = new TableroUI(hexagones, producciones);
        tableroUI.setStyle("-fx-background-color: #87CEEB;");
        tableroUI.setPrefSize(700, 700);

        StackPane contenedorCentral = new StackPane(tableroUI);
        contenedorCentral.setPadding(new Insets(10));
        contenedorCentral.setAlignment(Pos.CENTER);
        contenedorCentral.setStyle("-fx-background-color: #4FA3D1;");
        this.setCenter(contenedorCentral);

        HBox barraInferior = new HBox();
        barraInferior.setPadding(new Insets(20));
        barraInferior.setAlignment(Pos.CENTER);
        barraInferior.setSpacing(20);
        barraInferior.setStyle("-fx-background-color: linear-gradient(to top, rgba(0,0,0,0.6), transparent);");
        
        Button botonDados = new Button("Tirar Dados");
        botonDados.setStyle("-fx-font-size: 16px; -fx-padding: 10 20; -fx-base: #ffcc00; -fx-cursor: hand; -fx-font-weight: bold;");
        botonDados.setOnAction(e -> abrirVentanaDados());
        
        Button botonBaraja = new Button("Ver Baraja");
        botonBaraja.setStyle("-fx-font-size: 16px; -fx-padding: 10 20; -fx-base: #3498db; -fx-cursor: hand; -fx-font-weight: bold;");
        botonBaraja.setOnAction(e -> abrirVentanaBaraja());
        
        Button botonDesarrollo = new Button("Ver Cartas Desarrollo");
        botonDesarrollo.setStyle("-fx-font-size: 16px; -fx-padding: 10 20; -fx-base: #3498db; -fx-cursor: hand; -fx-font-weight: bold;");
        botonDesarrollo.setOnAction(e -> abrirVentanaCartasDesarrollo());
        
        Button botonSalir = new Button("Salir");
        botonSalir.setStyle("-fx-font-size: 16px; -fx-padding: 10 20; -fx-base: #e74c3c; -fx-cursor: hand; -fx-font-weight: bold;");
        botonSalir.setOnAction(e -> {
            MenuInicial menuInicial = new MenuInicial();
            Stage newStage = new Stage();
            try {
                menuInicial.start(newStage);
                escenario.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        barraInferior.getChildren().addAll(botonBaraja, botonDados, botonDesarrollo, botonSalir);
        this.setBottom(barraInferior);
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

    private Button crearBotonAccion(String texto, String colorHex) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: " + colorHex + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 12;");
        VBox.setMargin(btn, new Insets(4, 0, 0, 0));
        return btn;
    }

    private VBox crearPanelInventario(String nombreJugador) {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(15));
        panel.setStyle("-fx-background-color: rgba(20, 20, 20, 0.85); -fx-background-radius: 10; -fx-border-color: #7f8c8d; -fx-border-radius: 10;");
        panel.setMinWidth(160);
        
        Label lblInventario = new Label("Inventario (" + nombreJugador + ")");
        lblInventario.setTextFill(Color.WHITE);
        lblInventario.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblInventario.setWrapText(true);
        lblInventario.setMaxWidth(140);
        
        Label separador = new Label("________________");
        separador.setTextFill(Color.GRAY);
        
        HBox filaMadera = crearFilaRecurso("Madera", "#2ecc71");
        HBox filaLadrillo = crearFilaRecurso("Ladrillo", "#e74c3c");
        HBox filaLana = crearFilaRecurso("Lana", "#ecf0f1");
        HBox filaGrano = crearFilaRecurso("Grano", "#f1c40f");
        HBox filaMineral = crearFilaRecurso("Piedra", "#95a5a6");
        
        panel.getChildren().addAll(lblInventario, separador, filaMadera, filaLadrillo, filaLana, filaGrano, filaMineral);
        return panel;
    }

    private HBox crearFilaRecurso(String nombre, String colorWeb) {
        HBox fila = new HBox();
        fila.setAlignment(Pos.CENTER_LEFT);
        fila.setSpacing(10);
        
        Label colorBox = new Label("■");
        colorBox.setTextFill(Color.web(colorWeb));
        colorBox.setFont(Font.font("Arial", 18));
        
        Label lblNombre = new Label(nombre);
        lblNombre.setTextFill(Color.web(colorWeb));
        lblNombre.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblNombre.setMinWidth(60);
        
        Label lblCantidad = new Label("0");
        lblCantidad.setTextFill(Color.WHITE);
        lblCantidad.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        labelsInventario.put(nombre.toLowerCase(), lblCantidad);
        
        HBox info = new HBox(5, colorBox, lblNombre);
        info.setAlignment(Pos.CENTER_LEFT);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        fila.getChildren().addAll(info, spacer, lblCantidad);
        return fila;
    }

    private void abrirVentanaDados() {
        Stage stageDados = new Stage();
        Random rand = new Random();
        int dado1 = rand.nextInt(6) + 1;
        int dado2 = rand.nextInt(6) + 1;
        
        VistaDados vista = new VistaDados(dado1, dado2);
        
        Scene scene = new Scene(vista, 300, 250);
        stageDados.setTitle("Dados");
        stageDados.setScene(scene);
        stageDados.show();
    }

    private void abrirVentanaBaraja() {
        Stage stageBaraja = new Stage();
        String primerJugador = !nombresJugadores.isEmpty() ? nombresJugadores.get(0) : "Jugador";
        
        VentanaBaraja ventana = new VentanaBaraja(stageBaraja, primerJugador);
        ScrollPane scroll = new ScrollPane(ventana);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        
        Scene scene = new Scene(scroll, 600, 500);
        stageBaraja.setTitle("Baraja");
        stageBaraja.setScene(scene);
        stageBaraja.show();
    }

    private void abrirVentanaCartasDesarrollo() {
        Stage stageDesarrollo = new Stage();
        String primerJugador = !nombresJugadores.isEmpty() ? nombresJugadores.get(0) : "Jugador";
        
        VentanaCartasDesarrollos ventana = new VentanaCartasDesarrollos(stageDesarrollo, primerJugador);
        ScrollPane scroll = new ScrollPane(ventana);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        
        Scene scene = new Scene(scroll, 600, 500);
        stageDesarrollo.setTitle("Cartas de Desarrollo");
        stageDesarrollo.setScene(scene);
        stageDesarrollo.show();
    }

    public static void show(Stage stage, List<String> nombresJugadores) {
        CargadorTableroJSON.TableroData tableroData = CargadorTableroJSON.cargarTableroDesdeJSON();
        ArrayList<Hexagono> hexagonos = tableroData.obtenerHexagonos();
        ArrayList<Produccion> producciones = tableroData.obtenerProducciones();
        
        ProveedorDeDatos proveedor = new ProveedorDeDatos();
        
        GestorDeTurnos gestor = new GestorDeTurnos(proveedor, hexagonos, producciones);
        
        CampoDeJuego campoDeJuego = new CampoDeJuego(stage, gestor, hexagonos, producciones, proveedor, nombresJugadores);
        stage.show();
    }

    
    public void actualizarTurno(String nombreJugador, int numeroTurno) {
        actualizarEtiquetaEnIndicador(boxTurno, "Turno " + numeroTurno + ": " + nombreJugador);
    }

    public void actualizarInventario(java.util.Map<edu.fiuba.algo3.modelo.Recurso, Integer> inventario) {
        javafx.application.Platform.runLater(() -> {
            labelsInventario.forEach((nombre, label) -> label.setText("0"));
            inventario.forEach((recurso, cantidad) -> {
                String key = recurso.getClass().getSimpleName().toLowerCase();
                Label lbl = labelsInventario.get(key);
                if (lbl != null) {
                    lbl.setText(String.valueOf(cantidad));
                }
            });
        });
    }

    public void actualizarPuntosVictoria(int puntos) {
        if (lblPuntosVictoria == null) return;
        javafx.application.Platform.runLater(() -> lblPuntosVictoria.setText(String.valueOf(puntos)));
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println("[UI] " + mensaje);
    }

    private void actualizarEtiquetaEnIndicador(VBox indicador, String nuevoValor) {
        if (indicador == null) return;
        javafx.application.Platform.runLater(() -> {
            if (indicador.getChildren().size() > 1) {
                javafx.scene.Node node = indicador.getChildren().get(1);
                if (node instanceof javafx.scene.control.Label lbl) {
                    lbl.setText(nuevoValor);
                }
            }
        });
    }

    private void ejecutarConstruccion() {
        new Thread(() -> {
            var coords = proveedor.pedirCoordenadasAlUsuario();
            var construccion = proveedor.pedirTipoDeConstruccionAlUsuario();
            if (coords != null && construccion != null) {
                gestor.construir(coords, construccion);
            }
        }).start();
    }

    private void ejecutarComercioBanca() {
        new Thread(() -> gestor.comerciarConLaBanca()).start();
    }
}
