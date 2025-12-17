package edu.fiuba.algo3.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.application.Platform;

import edu.fiuba.algo3.modelo.GestorDeTurnos;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Banca;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tiposBanca.Banca2a1;
import edu.fiuba.algo3.modelo.tiposBanca.Banca3a1;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import java.util.*;

public class VentanaComerciar extends VBox {
    private final GestorDeTurnos gestor;
    private final String nombreJugador;
    private List<Banca> bancasDisponibles = List.of();
    private Runnable onTradeCompleted;

    public VentanaComerciar(Stage stage, String nombreJugador, GestorDeTurnos gestor) {
        this(stage, nombreJugador, gestor, null);
    }

    public VentanaComerciar(Stage stage, String nombreJugador, GestorDeTurnos gestor, Runnable onTradeCompleted) {
        stage.setTitle("Comerciar");
        this.nombreJugador = nombreJugador;
        this.gestor = gestor;
        this.onTradeCompleted = onTradeCompleted;

        if (gestor != null) {
            this.bancasDisponibles = gestor.obtenerBancasDisponibles();
        }

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #2c3e50; -fx-min-width: 700; -fx-min-height: 550;");
        stage.setMinWidth(700);
        stage.setMinHeight(550);

        mostrarMenuPrincipal(root, stage, nombreJugador);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public VentanaComerciar(Stage stage, String nombreJugador) {
        this(stage, nombreJugador, null);
    }

    public void setOnTradeCompleted(Runnable callback) {
        this.onTradeCompleted = callback;
    }

    private void mostrarMenuPrincipal(VBox root, Stage stage, String nombreJugador) {
        root.getChildren().clear();

        Label lblTitulo = new Label("Menú de Comercio");
        lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblSubtitulo = new Label("Jugador: " + nombreJugador);
        lblSubtitulo.setStyle("-fx-font-size: 14px; -fx-text-fill: #bdc3c7;");

        Button btnInterior = new Button("Comercio Interior");
        estilarBoton(btnInterior, "#27ae60");
        btnInterior.setOnAction(e -> {
            mostrarComercioInterior(root, stage, nombreJugador);
        });

        Button btnMaritimo = new Button("Comercio Marítimo");
        estilarBoton(btnMaritimo, "#3498db");
        btnMaritimo.setOnAction(e -> {
            mostrarOpcionesMaritimas(root, stage, nombreJugador);
        });

        Button btnCancelar = new Button("Cancelar");
        estilarBoton(btnCancelar, "#e74c3c");
        btnCancelar.setOnAction(e -> stage.close());

        root.getChildren().addAll(lblTitulo, lblSubtitulo, btnInterior, btnMaritimo, btnCancelar);
    }

    private void mostrarOpcionesMaritimas(VBox root, Stage stage, String nombreJugador) {
        root.getChildren().clear();

        Label lblTitulo = new Label("Comercio Marítimo");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblInstruccion = new Label("Seleccione la tasa de cambio:");
        lblInstruccion.setStyle("-fx-font-size: 14px; -fx-text-fill: #bdc3c7;");

        Button btnEstandar = new Button("Tasa Estándar (4:1)");
        estilarBoton(btnEstandar, "#f39c12");
        btnEstandar.setOnAction(e -> mostrarEstandar41(root, stage, nombreJugador));

        Button btnGenerico = new Button("Puerto Genérico (3:1)");
        estilarBoton(btnGenerico, "#f39c12");
        btnGenerico.setOnAction(e -> {
            if (!poseeBanca31()) {
                lblInstruccion.setText("No tienes acceso a puertos 3:1.");
                lblInstruccion.setStyle("-fx-font-size: 14px; -fx-text-fill: #e74c3c;");
            } else {
                mostrarPuertoGenerico31(root, stage, nombreJugador);
            }
        });

        Button btnEspecifico = new Button("Puerto Específico (2:1)");
        estilarBoton(btnEspecifico, "#f39c12");
        btnEspecifico.setOnAction(e -> {
            if (!poseeBanca21()) {
                lblInstruccion.setText("No tienes acceso a puertos 2:1 específicos.");
                lblInstruccion.setStyle("-fx-font-size: 14px; -fx-text-fill: #e74c3c;");
            } else {
                mostrarPuertoEspecifico21(root, stage, nombreJugador);
            }
        });

        Button btnVolver = new Button("Volver al Menú Anterior");
        estilarBoton(btnVolver, "#95a5a6");
        btnVolver.setOnAction(e -> mostrarMenuPrincipal(root, stage, nombreJugador));

        root.getChildren().addAll(lblTitulo, lblInstruccion, btnEstandar, btnGenerico, btnEspecifico, btnVolver);
    }

    private static void estilarBoton(Button btn, String colorHex) {
        btn.setStyle("-fx-background-color: " + colorHex
                + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-min-width: 250px; -fx-font-size: 14px;");
        VBox.setMargin(btn, new Insets(5, 0, 5, 0));
    }

    private static final String[] RECURSOS = new String[] { "Madera", "Ladrillo", "Lana", "Grano", "Piedra" };

    private void mostrarPuertoGenerico31(VBox root, Stage stage, String nombreJugador) {
        root.getChildren().clear();

        Label lblTitulo = new Label("Puerto Genérico 3:1");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblDesc = new Label(
                "Entrega recursos que sumen 3 por cada unidad que quieras recibir. Puedes mezclar tipos.");
        lblDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: #bdc3c7;");

        Map<String, Spinner<Integer>> spinnersOferta = new LinkedHashMap<>();
        VBox boxOferta = new VBox(8);
        boxOferta.setAlignment(Pos.CENTER);
        Label lblOferta = new Label("Cantidad a ENTREGAR por recurso:");
        lblOferta.setStyle("-fx-text-fill: #bdc3c7;");
        boxOferta.getChildren().add(lblOferta);
        for (String r : RECURSOS) {
            Label l = new Label(r + ":");
            l.setStyle("-fx-text-fill: white;");
            Spinner<Integer> sp = new Spinner<>();
            sp.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 0));
            sp.setEditable(true);
            HBox fila = new HBox(10, l, sp);
            fila.setAlignment(Pos.CENTER);
            boxOferta.getChildren().add(fila);
            spinnersOferta.put(r, sp);
        }

        ComboBox<String> cmbRecibir = new ComboBox<>();
        cmbRecibir.getItems().addAll(RECURSOS);
        cmbRecibir.setPromptText("Recurso a recibir");
        Spinner<Integer> spDeseo = new Spinner<>();
        spDeseo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1));
        spDeseo.setEditable(true);
        HBox boxDemanda = new HBox(10, new Label("Cantidad a RECIBIR:"), spDeseo);
        ((Label) boxDemanda.getChildren().get(0)).setStyle("-fx-text-fill: #bdc3c7;");
        boxDemanda.setAlignment(Pos.CENTER);

        Button btnConfirmar = new Button("Confirmar 3:1");
        estilarBoton(btnConfirmar, "#27ae60");
        btnConfirmar.setOnAction(e -> {
            String recibir = cmbRecibir.getValue();
            int cantidadDeseada = spDeseo.getValue();
            if (recibir == null) {
                lblDesc.setText("Selecciona el recurso a recibir y cantidades.");
                lblDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: #e74c3c;");
                return;
            }
            try {
                Banca banca = obtenerAlgunaBanca31();
                if (banca == null) {
                    lblDesc.setText("No tienes acceso a puertos 3:1.");
                    lblDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: #e74c3c;");
                    return;
                }
                ArrayList<Recurso> oferta = new ArrayList<>();
                int total = 0;
                for (Map.Entry<String, Spinner<Integer>> ent : spinnersOferta.entrySet()) {
                    int cant = ent.getValue().getValue();
                    if (cant > 0) {
                        oferta.add(crearRecurso(ent.getKey(), cant));
                        total += cant;
                    }
                }
                if (total != 3 * cantidadDeseada) {
                    lblDesc.setText("La suma ofrecida debe ser igual a 3 × lo que deseas (actual: " + total + ")");
                    lblDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: #e74c3c;");
                    return;
                }
                ArrayList<Recurso> demanda = new ArrayList<>(List.of(crearRecurso(recibir, cantidadDeseada)));
                gestor.comerciarConLaBanca(banca, oferta, demanda);
                stage.close();
                Platform.runLater(() -> {
                    if (onTradeCompleted != null)
                        onTradeCompleted.run();
                });
            } catch (RuntimeException ex) {
                lblDesc.setText("No fue posible realizar el comercio: "
                        + (ex.getMessage() != null ? ex.getMessage() : "verifica tus recursos"));
                lblDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: #e74c3c;");
            }
        });

        Button btnVolver = new Button("Volver");
        estilarBoton(btnVolver, "#95a5a6");
        btnVolver.setOnAction(ev -> mostrarOpcionesMaritimas(root, stage, nombreJugador));

        VBox cont = new VBox(12, lblTitulo, lblDesc, boxOferta, cmbRecibir, boxDemanda, btnConfirmar, btnVolver);
        cont.setAlignment(Pos.CENTER);
        cont.setPadding(new Insets(20));

        root.getChildren().add(cont);
    }

    private Banca obtenerAlgunaBanca31() {
        for (Banca b : bancasDisponibles) {
            if (b instanceof Banca3a1) {
                return b;
            }
        }
        return null;
    }

    private Banca obtenerAlgunaBanca41() {
        for (Banca b : bancasDisponibles) {
            if (b instanceof edu.fiuba.algo3.modelo.tiposBanca.Banca4a1) {
                return b;
            }
        }
        return null;
    }

    private void mostrarPuertoEspecifico21(VBox root, Stage stage, String nombreJugador) {
        root.getChildren().clear();

        Label lblTitulo = new Label("Puerto Específico 2:1");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblDesc = new Label("Entrega múltiplos de 2 del recurso del puerto y recibe la cantidad deseada.");
        lblDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: #bdc3c7;");

        ComboBox<String> cmbPuerto = new ComboBox<>();

        Set<String> recursos21 = recursosConPuerto21();
        cmbPuerto.getItems().addAll(recursos21);
        cmbPuerto.setPromptText("Tipo de puerto (recurso 2:1)");

        ComboBox<String> cmbRecibir = new ComboBox<>();
        cmbRecibir.getItems().addAll(RECURSOS);
        cmbRecibir.setPromptText("Recurso a recibir (x1)");

        Spinner<Integer> spOferta = new Spinner<>();
        spOferta.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 200, 2, 2));
        spOferta.setEditable(true);
        Spinner<Integer> spDeseo = new Spinner<>();
        spDeseo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        spDeseo.setEditable(true);
        HBox boxCantidades = new HBox(10,
                new Label("Cantidad a ENTREGAR:"), spOferta,
                new Label("Cantidad a RECIBIR:"), spDeseo);
        for (int i = 0; i < boxCantidades.getChildren().size(); i += 2) {
            ((Label) boxCantidades.getChildren().get(i)).setStyle("-fx-text-fill: #bdc3c7;");
        }
        boxCantidades.setAlignment(Pos.CENTER);

        Button btnConfirmar = new Button("Confirmar 2:1");
        estilarBoton(btnConfirmar, "#27ae60");
        btnConfirmar.setOnAction(e -> {
            String puerto = cmbPuerto.getValue();
            String recibir = cmbRecibir.getValue();
            int cantOferta = spOferta.getValue();
            int cantDeseo = spDeseo.getValue();
            if (puerto == null || recibir == null) {
                lblDesc.setText("Selecciona el puerto (recurso 2:1) y el recurso a recibir.");
                lblDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: #e74c3c;");
                return;
            }
            if (cantOferta != 2 * cantDeseo) {
                lblDesc.setText("La cantidad ofrecida debe ser exactamente 2 × lo que deseas.");
                lblDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: #e74c3c;");
                return;
            }
            try {
                Banca2a1 banca = obtenerBanca21ParaRecurso(puerto);
                if (banca == null) {
                    lblDesc.setText("No posees puerto 2:1 para ese recurso.");
                    lblDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: #e74c3c;");
                    return;
                }
                ArrayList<Recurso> oferta = new ArrayList<>(List.of(crearRecurso(puerto, cantOferta)));
                ArrayList<Recurso> demanda = new ArrayList<>(List.of(crearRecurso(recibir, cantDeseo)));
                gestor.comerciarConLaBanca(banca, oferta, demanda);
                stage.close();
                Platform.runLater(() -> {
                    if (onTradeCompleted != null)
                        onTradeCompleted.run();
                });
            } catch (RuntimeException ex) {
                lblDesc.setText("No fue posible realizar el comercio: "
                        + (ex.getMessage() != null ? ex.getMessage() : "verifica tus recursos"));
                lblDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: #e74c3c;");
            }
        });

        Button btnVolver = new Button("Volver");
        estilarBoton(btnVolver, "#95a5a6");
        btnVolver.setOnAction(ev -> mostrarOpcionesMaritimas(root, stage, nombreJugador));

        VBox cont = new VBox(12, lblTitulo, lblDesc, cmbPuerto, cmbRecibir, boxCantidades, btnConfirmar, btnVolver);
        cont.setAlignment(Pos.CENTER);
        cont.setPadding(new Insets(20));

        root.getChildren().add(cont);
    }

    private void mostrarEstandar41(VBox root, Stage stage, String nombreJugador) {
        root.getChildren().clear();

        Label lblTitulo = new Label("Comercio Estándar 4:1");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblDesc = new Label("Entrega N×4 de un mismo recurso y recibe N del recurso elegido.");
        lblDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: #bdc3c7;");

        ComboBox<String> cmbDar = new ComboBox<>();
        cmbDar.getItems().addAll(RECURSOS);
        cmbDar.setPromptText("Recurso a entregar");

        Spinner<Integer> spOferta = new Spinner<>();
        spOferta.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 400, 4, 4));
        spOferta.setEditable(true);

        ComboBox<String> cmbRecibir = new ComboBox<>();
        cmbRecibir.getItems().addAll(RECURSOS);
        cmbRecibir.setPromptText("Recurso a recibir");

        Spinner<Integer> spDeseo = new Spinner<>();
        spDeseo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        spDeseo.setEditable(true);

        HBox boxCantidades = new HBox(10,
                new Label("Cantidad a ENTREGAR:"), spOferta,
                new Label("Cantidad a RECIBIR:"), spDeseo);
        for (int i = 0; i < boxCantidades.getChildren().size(); i += 2) {
            ((Label) boxCantidades.getChildren().get(i)).setStyle("-fx-text-fill: #bdc3c7;");
        }
        boxCantidades.setAlignment(Pos.CENTER);

        Button btnConfirmar = new Button("Confirmar 4:1");
        estilarBoton(btnConfirmar, "#27ae60");
        btnConfirmar.setOnAction(e -> {
            String dar = cmbDar.getValue();
            String recibir = cmbRecibir.getValue();
            int cantOferta = spOferta.getValue();
            int cantDeseo = spDeseo.getValue();
            if (dar == null || recibir == null) {
                lblDesc.setText("Selecciona el recurso a entregar y a recibir.");
                lblDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: #e74c3c;");
                return;
            }
            if (cantOferta != 4 * cantDeseo) {
                lblDesc.setText("La cantidad ofrecida debe ser exactamente 4 × lo que deseas.");
                lblDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: #e74c3c;");
                return;
            }
            try {
                Banca banca = obtenerAlgunaBanca41();
                ArrayList<Recurso> oferta = new ArrayList<>(List.of(crearRecurso(dar, cantOferta)));
                ArrayList<Recurso> demanda = new ArrayList<>(List.of(crearRecurso(recibir, cantDeseo)));
                gestor.comerciarConLaBanca(banca, oferta, demanda);
                stage.close();
                Platform.runLater(() -> {
                    if (onTradeCompleted != null)
                        onTradeCompleted.run();
                });
            } catch (RuntimeException ex) {
                lblDesc.setText("No fue posible realizar el comercio: "
                        + (ex.getMessage() != null ? ex.getMessage() : "verifica tus recursos"));
                lblDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: #e74c3c;");
            }
        });

        Button btnVolver = new Button("Volver");
        estilarBoton(btnVolver, "#95a5a6");
        btnVolver.setOnAction(ev -> mostrarOpcionesMaritimas(root, stage, nombreJugador));

        VBox cont = new VBox(12, lblTitulo, lblDesc, cmbDar, cmbRecibir, boxCantidades, btnConfirmar, btnVolver);
        cont.setAlignment(Pos.CENTER);
        cont.setPadding(new Insets(20));

        root.getChildren().add(cont);
    }

    private boolean poseeBanca31() {
        for (Banca b : bancasDisponibles) {
            if (b instanceof Banca3a1)
                return true;
        }
        return false;
    }

    private boolean poseeBanca21() {
        for (Banca b : bancasDisponibles) {
            if (b instanceof Banca2a1)
                return true;
        }
        return false;
    }

    private Set<String> recursosConPuerto21() {
        LinkedHashSet<String> res = new LinkedHashSet<>();
        for (Banca b : bancasDisponibles) {
            if (b instanceof Banca2a1 banca) {
                // Determinar el tipo de recurso del puerto por su clase
                String nombre = nombreRecursoDesdeBanca2a1(banca);
                if (nombre != null)
                    res.add(nombre);
            }
        }
        return res;
    }

    private String nombreRecursoDesdeBanca2a1(Banca2a1 banca) {
        Recurso recurso = banca.obtenerRecursoTipo();
        return nombreDeRecurso(recurso);
    }

    private Banca2a1 obtenerBanca21ParaRecurso(String nombre) {
        for (Banca b : bancasDisponibles) {
            if (b instanceof Banca2a1 banca) {
                String n = nombreRecursoDesdeBanca2a1(banca);
                if (n != null && n.equals(nombre)) {
                    return banca;
                }
            }
        }
        return null;
    }

    private void mostrarComercioInterior(VBox root, Stage stage, String jugadorAcString) {
        root.getChildren().clear();

        ArrayList<Recurso> listaOferta = new ArrayList<>();
        ArrayList<Recurso> listaDemanda = new ArrayList<>();

        Label lblTitulo = new Label("Comercio entre Jugadores");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblDesc = new Label("Ponerse de acuerdo con el intercambio y ejecutarlo.");
        lblDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: #bdc3c7;");

        Label lblJugadorDestino = new Label("Elegir jugador con el que se quiere intercambiar:");
        lblJugadorDestino.setStyle("-fx-text-fill: white; -fx-font-weight: bold");
        ComboBox<String> cmbJugadoresBox = new ComboBox<>();

        List<Jugador> otrosJugadores = gestor.obtenerOtrosJugadores();

        for (Jugador jugador : otrosJugadores) {
            cmbJugadoresBox.getItems().add(jugador.obtenerNombre());
        }

        Label lblOfrecer = new Label("Yo ofrezco");
        lblOfrecer.setStyle("-fx-text-fill: #2ecc71;");
        ComboBox<String> cmbRecursosOfrecidos = new ComboBox<>();
        cmbRecursosOfrecidos.getItems().addAll(RECURSOS);
        Spinner<Integer> spCantOfrecer = new Spinner<>(1, 20, 1);
        Button btnAgregarOferta = new Button("Agregar");
        estilarBoton(btnAgregarOferta, "#27ae60");

        btnAgregarOferta.setOnAction(e -> {
            String recurso = cmbRecursosOfrecidos.getValue();
            int cantidad = spCantOfrecer.getValue();
            if (recurso != null) {
                listaOferta.add(crearRecurso(recurso, cantidad));
            }
        });
        Button btnLimpiarOferta = new Button("Borrar oferta");
        btnLimpiarOferta.setOnAction(e -> {
            listaOferta.clear();
        });

        Label lblPedir = new Label("Yo pido");
        lblPedir.setStyle("-fx-text-fill: #2ecc71;");
        ComboBox<String> cmbRecursosPedir = new ComboBox<>();
        cmbRecursosPedir.getItems().addAll(RECURSOS);
        Spinner<Integer> spCantPedir = new Spinner<>(1, 20, 1);

        Button btnAgregarDemanda = new Button("Agregar");
        estilarBoton(btnAgregarDemanda, "#27ae60");

        btnAgregarDemanda.setOnAction(e -> {
            String recurso = cmbRecursosPedir.getValue();
            int cantidad = spCantPedir.getValue();
            if (recurso != null) {
                listaDemanda.add(crearRecurso(recurso, cantidad));
            }
        });
        Button btnLimpiarDemanda = new Button("Borrar demanda");
        btnLimpiarDemanda.setOnAction(e -> {
            listaDemanda.clear();
        });

        HBox boxIntercambio = new HBox(15,
                new VBox(5, lblOfrecer, cmbRecursosOfrecidos, spCantOfrecer),
                new VBox(5, lblPedir, cmbRecursosPedir, spCantPedir));
        boxIntercambio.setAlignment(Pos.CENTER);

        Button btnConfirmar = new Button("Confirmar Intercambio");
        estilarBoton(btnConfirmar, "#8e44ad");

        btnConfirmar.setOnAction(e -> {
            String nombreDestino = cmbJugadoresBox.getValue();
            String recursoOfrecer = cmbRecursosOfrecidos.getValue();
            String recursoPedir = cmbRecursosPedir.getValue();
            int cantOfrecer = spCantOfrecer.getValue();
            int cantPedir = spCantPedir.getValue();

            if (nombreDestino == null) {
                lblDesc.setText(("Faltan datos para poder realizar el intercambio"));
                lblDesc.setStyle("-fx-text-fill: #e74c3c");
                return;
            }

            if (listaOferta.isEmpty()) {
                if (recursoOfrecer != null) {
                    listaOferta.add(crearRecurso(recursoOfrecer, cantOfrecer));
                }
            }
            if (listaDemanda.isEmpty()) {
                if (recursoPedir != null) {
                    listaDemanda.add(crearRecurso(recursoPedir, cantPedir));
                }
            }

            if (listaOferta.isEmpty() || listaDemanda.isEmpty()) {
                lblDesc.setText(("Faltan datos para poder realizar el intercambio"));
                lblDesc.setStyle("-fx-text-fill: #e74c3c");
                return;
            }

            try {
                Jugador jugadorDestino = gestor.obtenerJugadorPorNombre(nombreDestino);

                gestor.comerciarConJugador(jugadorDestino, listaOferta, listaDemanda);

                stage.close();
                Platform.runLater(() -> {
                    if (onTradeCompleted != null)
                        onTradeCompleted.run();
                });
            } catch (RuntimeException exception) {
                lblDesc.setText("Error: " + exception.getMessage());
                lblDesc.setStyle("-fx-text-fill: #e74c3c");
            }
        });
        Button btnVolver = new Button("Volver");
        estilarBoton(btnVolver, "#95a5a6");
        btnVolver.setOnAction(ev -> mostrarMenuPrincipal(root, stage, jugadorAcString));

        VBox cont = new VBox(12, lblTitulo, lblDesc, lblJugadorDestino, cmbJugadoresBox, boxIntercambio, btnConfirmar,
                btnVolver);
        cont.setAlignment(Pos.CENTER);
        cont.setPadding(new Insets(20));

        root.getChildren().add(cont);
    }

    private String nombreDeRecurso(Recurso recurso) {
        if (recurso instanceof Madera)
            return "Madera";
        if (recurso instanceof Ladrillo)
            return "Ladrillo";
        if (recurso instanceof Lana)
            return "Lana";
        if (recurso instanceof Grano)
            return "Grano";
        if (recurso instanceof Piedra)
            return "Piedra";
        return null;
    }

    private Recurso crearRecurso(String nombre, int cantidad) {
        switch (nombre) {
            case "Madera":
                return new Madera().obtenerCopia(cantidad);
            case "Ladrillo":
                return new Ladrillo().obtenerCopia(cantidad);
            case "Lana":
                return new Lana().obtenerCopia(cantidad);
            case "Grano":
                return new Grano().obtenerCopia(cantidad);
            case "Piedra":
                return new Piedra().obtenerCopia(cantidad);
            default:
                return new Nulo().obtenerCopia(0);
        }
    }
}