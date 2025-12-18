package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.GestorDeTurnos;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Produccion;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.controllers.CargadorTableroJSON;
import edu.fiuba.algo3.controllers.ControladorConstruccion;
import edu.fiuba.algo3.controllers.ControladorDados;
import edu.fiuba.algo3.controllers.ControladorFases;
import edu.fiuba.algo3.controllers.ControladorIndicadores;
import edu.fiuba.algo3.controllers.ControladorVentanas;
import edu.fiuba.algo3.controllers.ReproductorDeSonido;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.Cursor;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import edu.fiuba.algo3.vistas.TableroUI;
import edu.fiuba.algo3.vistas.ventanas.PanelRecursos;
import edu.fiuba.algo3.vistas.ventanas.VentanaColocacionInicial;

public class CampoDeJuego extends BorderPane {

    private final Stage escenario;
    private final ArrayList<Hexagono> hexagones;
    private final ArrayList<Produccion> producciones;
    private List<String> nombresJugadores;
    private GestorDeTurnos gestor;
    private PanelRecursos panelRecursos;
    private TableroUI tableroUI;
    private ControladorFases controladorFases;
    private ControladorDados controladorDados;
    private ControladorConstruccion controladorConstruccion;
    private ControladorVentanas controladorVentanas;
    private ControladorIndicadores controladorIndicadores;
    private Label lblPuntosVictoria;
    private Button btnTerminarFase;

    public CampoDeJuego(Stage escenario, GestorDeTurnos gestor, ArrayList<Hexagono> hexagones,
            ArrayList<Produccion> producciones, List<String> nombresJugadores) {
        this.escenario = escenario;
        this.gestor = gestor;
        this.hexagones = hexagones;
        this.producciones = producciones;
        this.nombresJugadores = nombresJugadores != null ? nombresJugadores : new ArrayList<>();

        construirInterfaz();

        escenario.setScene(new Scene(this, 1500, 900));
        escenario.centerOnScreen();

        Thread hiloJuego = new Thread(() -> {
            gestor.jugar();
        });
        hiloJuego.setDaemon(true);
        hiloJuego.start();
    }

    private void reproducirSonido() {
        ReproductorDeSonido.getInstance().reproducirSonidoDados();
    }

    private void construirInterfaz() {
        String primerJugador = !nombresJugadores.isEmpty() ? nombresJugadores.get(0) : "Esperando...";

        this.controladorIndicadores = new ControladorIndicadores();

        HBox listaJugadores = new HBox(10);
        listaJugadores.setAlignment(Pos.CENTER_LEFT);
        for (String nombre : nombresJugadores) {
            Button btnNombre = new Button(nombre);
            btnNombre.setStyle("-fx-font-size: 12px; -fx-background-color: #ecf0f1; -fx-text-fill: #2c3e50;");
            listaJugadores.getChildren().add(btnNombre);
        }

        HBox indicadoresPanel = new HBox(15);
        indicadoresPanel.setAlignment(Pos.CENTER_RIGHT);
        indicadoresPanel.getChildren().addAll(
            controladorIndicadores.crearBoxTurno(primerJugador),
            controladorIndicadores.crearBoxCaballeros(),
            controladorIndicadores.crearBoxFase());

        controladorIndicadores.actualizarTurno(primerJugador, 0);

        this.controladorFases = new ControladorFases(controladorIndicadores.obtenerLblFase(), gestor);
        this.controladorDados = new ControladorDados(gestor, controladorFases);
        this.controladorConstruccion = new ControladorConstruccion(gestor, primerJugador);
        this.controladorVentanas = new ControladorVentanas(primerJugador, gestor);

        controladorConstruccion.setOnVentanaComerciarCerrada(() -> {
            actualizarInterfaz();
        });

        controladorConstruccion.setOnModalCerrada(() -> {
            actualizarInterfaz();
            if (tableroUI != null)
                tableroUI.refrescarDesdeModelo();
            lanzarVentanaGanador();
        });

        BorderPane barraSuperior = new BorderPane();
        barraSuperior.setPadding(new Insets(10));
        barraSuperior.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");

        barraSuperior.setLeft(listaJugadores);
        barraSuperior.setRight(indicadoresPanel);
        this.setTop(barraSuperior);

        panelRecursos = new PanelRecursos(primerJugador);

        VBox panelDerecho = new VBox(10);
        panelDerecho.setPadding(new Insets(10));
        panelDerecho.setAlignment(Pos.TOP_CENTER);
        panelDerecho.setStyle("-fx-background-color: rgba(0,0,0,0.2); -fx-padding: 10;");
        panelDerecho.setMinWidth(200);

        VBox boxPuntos = controladorIndicadores.crearBoxPuntos();
        this.lblPuntosVictoria = controladorIndicadores.obtenerLblPuntosVictoria();

        VBox panelConstruccion = controladorConstruccion.crearPanelBotonesConstruccion();
        controladorConstruccion.actualizarVisibilidadSegunFase(controladorFases.getFaseActual());

        this.btnTerminarFase = controladorConstruccion.getBtnTerminarFase();
        btnTerminarFase.setDisable(true);
        btnTerminarFase.setCursor(Cursor.HAND);
        btnTerminarFase.setOnAction(e -> terminarFaseYAvanzarTurno());

        panelDerecho.getChildren().addAll(boxPuntos, panelConstruccion, btnTerminarFase);

        VBox contenedorDerecho = new VBox(12, panelRecursos, panelDerecho);
        contenedorDerecho.setAlignment(Pos.TOP_CENTER);
        contenedorDerecho.setPadding(new Insets(10));
        contenedorDerecho.setMinWidth(220);
        this.setRight(contenedorDerecho);

        this.tableroUI = new TableroUI(hexagones, producciones, gestor.obtenerTablero());
        tableroUI.setStyle("-fx-background-color: #87CEEB;");
        tableroUI.setPrefSize(700, 700);

        controladorDados.setTableroUI(tableroUI);
        controladorConstruccion.setTableroUI(tableroUI);

        if (gestor.obtenerLadron() != null && gestor.obtenerLadron().obtenerHexagonoActual() != null) {
            Hexagono hexLadron = gestor.obtenerLadron().obtenerHexagonoActual();
            Coordenadas coordLadron = tableroUI.buscarCoordenadasPorHexagono(hexLadron);
            if (coordLadron != null) {
                tableroUI.actualizarLadronEn(coordLadron);
            }
        }

        VBox panelAyuda = new VBox(4);
        panelAyuda.setPadding(new Insets(6, 8, 6, 8));
        panelAyuda.setStyle("-fx-background-color: rgba(255,255,255,0.92); -fx-border-color: #888; -fx-border-width: 1.2; -fx-background-radius: 8; -fx-border-radius: 8;");
        panelAyuda.setMaxHeight(200);
        panelAyuda.setMaxWidth(300);
        panelAyuda.setAlignment(Pos.TOP_LEFT);

        Label tituloAyuda = new Label("Ayuda de juego");
        tituloAyuda.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #222;");
        panelAyuda.getChildren().add(tituloAyuda);

        VBox leyendaColores = new VBox(2);
        leyendaColores.setAlignment(Pos.TOP_LEFT);
        leyendaColores.getChildren().add(new Label("Colores de jugadores:"));
        for (int i = 0; i < nombresJugadores.size(); i++) {
            String nombre = nombresJugadores.get(i);
            HBox fila = new HBox(6);
            Region colorBox = new Region();
            colorBox.setPrefSize(18, 18);
            String[] colores = {"#e74c3c", "#3498db", "#27ae60", "#f1c40f"};
            String color = colores[i % colores.length];
            colorBox.setStyle("-fx-background-color: " + color + "; -fx-border-color: #222; -fx-border-width: 1;");
            Label lblNombre = new Label(nombre);
            fila.getChildren().addAll(colorBox, lblNombre);
            leyendaColores.getChildren().add(fila);
        }
        panelAyuda.getChildren().add(leyendaColores);

        VBox leyendaConstrucciones = new VBox(2);
        leyendaConstrucciones.setAlignment(Pos.TOP_LEFT);
        leyendaConstrucciones.getChildren().add(new Label("Construcciones y costes:"));
        leyendaConstrucciones.getChildren().add(new Label("Poblado: 1 madera, 1 ladrillo, 1 lana, 1 trigo"));
        leyendaConstrucciones.getChildren().add(new Label("Ciudad: 3 arcilla, 2 trigo"));
        leyendaConstrucciones.getChildren().add(new Label("Carretera: 1 madera, 1 ladrillo"));
        leyendaConstrucciones.getChildren().add(new Label("Carta desarrollo: 1 trigo, 1 lana, 1 mineral"));
        panelAyuda.getChildren().add(leyendaConstrucciones);

        StackPane contenedorCentral = new StackPane(tableroUI, panelAyuda);
        StackPane.setAlignment(panelAyuda, Pos.BOTTOM_LEFT);
        StackPane.setMargin(panelAyuda, new Insets(20, 0, 20, 20));
        this.setCenter(contenedorCentral);
        contenedorCentral.setPadding(new Insets(10));
        contenedorCentral.setAlignment(Pos.CENTER);
        contenedorCentral.setStyle("-fx-background-color: #4FA3D1;");
        this.setCenter(contenedorCentral);

        HBox barraInferior = new HBox();
        barraInferior.setPadding(new Insets(20));
        barraInferior.setAlignment(Pos.CENTER);
        barraInferior.setSpacing(20);
        barraInferior.setStyle("-fx-background-color: linear-gradient(to top, rgba(0,0,0,0.6), transparent);");

        Button btnTirarDados = controladorDados.crearBotonTirarDados(() -> {
            controladorDados.actualizarVisibilidadSegunFase(controladorFases.getFaseActual());
            btnTerminarFase.setDisable(false);
            actualizarInterfaz();
        });

        btnTirarDados.setOnMousePressed(e -> {
            ReproductorDeSonido.getInstance().reproducirSonidoDados();
        });
        controladorDados.actualizarVisibilidadSegunFase(controladorFases.getFaseActual());

        Button botonBonificacion = new Button("Ver Cartas Bonificacion");
        botonBonificacion.setStyle(
                "-fx-font-size: 16px; -fx-padding: 10 20; -fx-base: #4856db; -fx-cursor: hand; -fx-font-weight: bold;");
        botonBonificacion.setOnAction(e -> {
            ReproductorDeSonido.getInstance().playClick();
            controladorVentanas.abrirVentanaCartasBonificacion();
        });

        Button botonDesarrollo = new Button("Ver Cartas Desarrollo");
        botonDesarrollo.setStyle(
                "-fx-font-size: 16px; -fx-padding: 10 20; -fx-base: #3498db; -fx-cursor: hand; -fx-font-weight: bold;");
        botonDesarrollo.setOnAction(e -> {
            ReproductorDeSonido.getInstance().playClick();
            controladorVentanas.abrirVentanaCartasDesarrollo(controladorFases, tableroUI, () -> {
                actualizarInterfaz();
                if (tableroUI != null)
                    tableroUI.refrescarDesdeModelo();
                lanzarVentanaGanador();
            });
        });

        Button botonSalir = new Button("Salir");
        botonSalir.setStyle(
                "-fx-font-size: 16px; -fx-padding: 10 20; -fx-base: #e74c3c; -fx-cursor: hand; -fx-font-weight: bold;");
        botonSalir.setOnAction(e -> {
            MenuInicial menuInicial = new MenuInicial();
            Stage newStage = new Stage();
            try {
                menuInicial.start(newStage);
                ReproductorDeSonido.getInstance().playClick();
                escenario.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Region separador = new Region();
        HBox.setHgrow(separador, Priority.ALWAYS);
        Region separador2 = new Region();
        HBox.setHgrow(separador2, Priority.ALWAYS);

        barraInferior.getChildren().addAll(botonSalir, separador, btnTirarDados, botonDesarrollo, botonBonificacion,
                separador2, btnTerminarFase);
        this.setBottom(barraInferior);
    }

    private void actualizarInterfaz() {
        if (panelRecursos != null && gestor.obtenerJugadorActual() != null) {
            panelRecursos.actualizarInventario(gestor.obtenerInventarioJugador(gestor.obtenerJugadorActual()));
        }
        if (lblPuntosVictoria != null) {
            lblPuntosVictoria.setText(String.valueOf(gestor.obtenerPuntosVictoriaJugadorActual()));
        }
        if (controladorIndicadores != null && controladorIndicadores.obtenerLblCaballeros() != null) {
            int caballos = 0;
            if (gestor.obtenerJugadorActual() != null) {
                caballos = gestor.obtenerJugadorActual().obtenerCantidadCaballerosUsados();
            }
            controladorIndicadores.obtenerLblCaballeros().setText(String.valueOf(caballos));
        }
    }

    public static void show(Stage stage, List<String> nombresJugadores) {
        CargadorTableroJSON.TableroData tableroData = CargadorTableroJSON.cargarTableroDesdeJSON();
        ArrayList<Hexagono> hexagonos = tableroData.obtenerHexagonos();
        ArrayList<Produccion> producciones = tableroData.obtenerProducciones();

        GestorDeTurnos gestor = new GestorDeTurnos(hexagonos, producciones);
        gestor.inicializarJuego(nombresJugadores);

        CampoDeJuego campo = new CampoDeJuego(stage, gestor, hexagonos, producciones, nombresJugadores);
        stage.show();
        campo.ejecutarColocacionInicial();
    }

    private void terminarFaseYAvanzarTurno() {
        String jugadorActual = gestor.obtenerJugadorActual() != null ? gestor.obtenerJugadorActual().obtenerNombre()
                : "---";
        controladorFases.avanzarFase(jugadorActual);

        if (controladorFases.estamosEnFaseLanzamiento()) {
            String nuevoJugador = gestor.obtenerJugadorActual() != null ? gestor.obtenerJugadorActual().obtenerNombre()
                    : "---";
            int indiceJugador = gestor.obtenerIndiceJugadorActual();
            controladorIndicadores.actualizarTurno(nuevoJugador, indiceJugador);
            btnTerminarFase.setDisable(true);
        } else {
            btnTerminarFase.setDisable(false);
        }

        controladorConstruccion.actualizarVisibilidadSegunFase(controladorFases.getFaseActual());

        controladorDados.actualizarVisibilidadSegunFase(controladorFases.getFaseActual());

        actualizarInterfaz();
    }

    public void ejecutarColocacionInicial() {
        if (gestor.obtenerTurnoActual() < 2) {
            mostrarVentanaColocacionInicial();
        }
    }

    private void mostrarVentanaColocacionInicial() {
        if (gestor.obtenerTurnoActual() >= 2) {
            return;
        }

        String nombreJugador = gestor.obtenerJugadorActual() != null ? gestor.obtenerJugadorActual().obtenerNombre()
                : "Jugador";

        Stage modal = new Stage();
        new VentanaColocacionInicial(modal, nombreJugador, tableroUI, (p, c) -> {
            gestor.colocacionInicial(p, c);

            int indiceJugador = gestor.obtenerIndiceJugadorActual();
            tableroUI.marcarPoblado(p, indiceJugador);

            tableroUI.marcarCarretera(p, c, indiceJugador);

            gestor.avanzarTurno();

            String nuevo = gestor.obtenerJugadorActual() != null ? gestor.obtenerJugadorActual().obtenerNombre()
                    : "---";
            int indiceNuevo = gestor.obtenerIndiceJugadorActual();
            controladorIndicadores.actualizarTurno(nuevo, indiceNuevo);

            actualizarInterfaz();

            mostrarVentanaColocacionInicial();
        });
    }

    private void lanzarVentanaGanador() {
        String jugador = gestor.obtenerGanador();
        if (jugador != null) {
            Platform.runLater(() -> {
                controladorVentanas.abrirVentanaGanador(jugador);
            });
        }
    }
}