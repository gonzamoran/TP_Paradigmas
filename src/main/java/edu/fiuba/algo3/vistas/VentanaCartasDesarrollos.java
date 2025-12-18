package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.controllers.ReproductorDeSonido;

import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.ContextoCartaDesarrollo;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.GestorDeTurnos;
import edu.fiuba.algo3.vistas.ControladorFases;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartaCaballero;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartaConstruccionCarretera;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartaMonopolio;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartaDescubrimiento;
import edu.fiuba.algo3.vistas.TableroUI;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VentanaCartasDesarrollos extends VBox {

    private final GestorDeTurnos gestor;
    private final ControladorFases controladorFases;
    private final TableroUI tableroUI;
    private final FlowPane panelCartas;
    private final List<VBox> cartasJugablesVisuales = new ArrayList<>();
    private CartasDesarrollo cartaSeleccionada = null;
    private VBox cartaVisualSeleccionada = null;
    private final Stage stage;
    private final String nombreJugador;

    private final Runnable onActualizarUI;

    public VentanaCartasDesarrollos(Stage stage, String nombreJugador, GestorDeTurnos gestor,
            ControladorFases controladorFases, TableroUI tableroUI, Runnable onActualizarUI) {
        this.stage = stage;
        this.stage.setAlwaysOnTop(true);
        this.nombreJugador = nombreJugador;
        this.gestor = gestor;
        this.controladorFases = controladorFases;
        this.tableroUI = tableroUI;
        this.onActualizarUI = onActualizarUI;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setPadding(new Insets(30));

        this.setStyle("-fx-background-color: #2c3e50; -fx-min-width: 600; -fx-min-height: 500;");

        String nombreActual = gestor != null && gestor.obtenerJugadorActual() != null ? gestor.obtenerJugadorActual().obtenerNombre() : nombreJugador;
        Label titulo = new Label("Cartas Desarrollo de " + nombreActual);
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        this.panelCartas = new FlowPane();
        this.panelCartas.setHgap(20);
        this.panelCartas.setVgap(20);
        this.panelCartas.setAlignment(Pos.CENTER);

        // Obtener cartas reales del jugador actual mediante el gestor
        Jugador jugador = gestor != null ? gestor.obtenerJugadorActual() : null;


        boolean fasePermiteUso = (controladorFases != null) && controladorFases.getFaseActual() == 2;
        if (jugador == null || jugador.obtenerCartasDeDesarrollo().isEmpty()) {
            Label vacio = new Label("No tienes cartas de desarrollo.");
            vacio.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 16px;");
            panelCartas.getChildren().add(vacio);
        } else {
            ArrayList<CartasDesarrollo> jugables = gestor.obtenerCartasJugablesJugadorActual();
            ArrayList<CartasDesarrollo> noJugables = gestor.obtenerCartasNoJugablesJugadorActual();

            for (CartasDesarrollo carta : jugables) {
                VBox cartaVisual = crearCartaVisual(carta, true);
                cartasJugablesVisuales.add(cartaVisual);
                panelCartas.getChildren().add(cartaVisual);
            }
            for (CartasDesarrollo carta : noJugables) {
                panelCartas.getChildren().add(crearCartaVisual(carta, false));
            }
        }

        ScrollPane scroll = new ScrollPane(panelCartas);
        scroll.setFitToWidth(true);

        scroll.setPrefHeight(400);
        scroll.setStyle("-fx-background: #2c3e50; -fx-border-color: transparent; -fx-background-color: transparent;");

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setStyle(
                "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 14px; -fx-padding: 10 20;");
        btnCerrar.setOnAction(e -> {
            ReproductorDeSonido.getInstance().playClick();
            stage.close();
        });
        // Botón grande para usar carta seleccionada (solo si hay jugables y en fase correcta)
        Button btnUsarCarta = new Button("Usar carta seleccionada");
        btnUsarCarta.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 20px; -fx-padding: 18 36; -fx-font-weight: bold; -fx-border-radius: 10; -fx-background-radius: 10;");
        btnUsarCarta.setDisable(true);

        btnUsarCarta.setVisible(fasePermiteUso && !cartasJugablesVisuales.isEmpty());

        // Selección de carta jugable solo durante la fase correcta
        if (fasePermiteUso) {
            for (VBox cartaVisual : cartasJugablesVisuales) {
                cartaVisual.setOnMouseClicked(e -> {
                    // Deseleccionar anterior
                    if (cartaVisualSeleccionada != null) {
                        cartaVisualSeleccionada.setStyle(cartaVisualSeleccionada.getStyle().replace("; -fx-border-color: #f1c40f; -fx-border-width: 4;", ""));
                    }
                    cartaVisualSeleccionada = cartaVisual;
                    cartaSeleccionada = (CartasDesarrollo) cartaVisual.getUserData();
                    cartaVisual.setStyle(cartaVisual.getStyle() + "; -fx-border-color: #f1c40f; -fx-border-width: 4;");
                    btnUsarCarta.setDisable(false);
                });
            }
        } else {
            // Si no es la fase, quitar selección y deshabilitar botón
            cartaSeleccionada = null;
            cartaVisualSeleccionada = null;
            btnUsarCarta.setDisable(true);
        }

        btnUsarCarta.setOnAction(evt -> {
            if (cartaSeleccionada != null && cartaVisualSeleccionada != null) {
                // Lógica de uso de carta (idéntica a crearBotonUsar)
                ReproductorDeSonido.getInstance().playClick();
                boolean confirmado = confirmarAccion("¿Confirmas usar la carta " + obtenerNombreCarta(cartaSeleccionada) + "?");
                if (!confirmado) return;
                if (this.stage != null) this.stage.close();
                ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(
                        gestor.obtenerJugadorActual(), gestor.obtenerJugadores(), gestor.obtenerTurnoActual(),
                        gestor.obtenerTablero(), gestor.obtenerLadron());
                try {
                    handleUsoCartaSegunTipo(cartaSeleccionada, contexto, cartaVisualSeleccionada, obtenerNombreCarta(cartaSeleccionada));
                } catch (Exception ex) {
                    mostrarError("No se pudo usar la carta", ex.getMessage());
                }
            }
        });

        if (fasePermiteUso && !cartasJugablesVisuales.isEmpty()) {
            this.getChildren().addAll(titulo, scroll, btnUsarCarta, btnCerrar);
        } else {
            this.getChildren().addAll(titulo, scroll, btnCerrar);
        }
    }

    private VBox crearCartaVisual(CartasDesarrollo cartaModelo, boolean jugable) {
        String nombreCartaDesarrollo = obtenerNombreCarta(cartaModelo);
        VBox carta = new VBox(10);
        carta.setAlignment(Pos.CENTER);

        carta.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 8, 0, 0, 0);");

        carta.setPrefSize(150, 220);

        String ruta = "/imagenes/" + nombreCartaDesarrollo + ".png";
        InputStream is = getClass().getResourceAsStream(ruta);

        if (is != null) {
            try {
                Image img = new Image(is);
                ImageView view = new ImageView(img);
                view.setFitHeight(140);
                view.setPreserveRatio(true);
                carta.getChildren().add(view);
            } catch (Exception ex) {
                Label lblFalla = new Label(nombreCartaDesarrollo.substring(0, 1));
                carta.getChildren().add(lblFalla);
            }
        } else {
            Label lblFalla = new Label(nombreCartaDesarrollo.substring(0, 1));
            carta.getChildren().add(lblFalla);
        }

        // Ya no se agrega el botón "Usar" dentro de la carta
        if (jugable) {
            carta.setUserData(cartaModelo);
        }

        if (!jugable) {
            carta.setOpacity(0.45);
            carta.setDisable(true);
        } else {
            carta.setOpacity(1.0);
            carta.setDisable(false);
        }

        return carta;
    }

    private String obtenerNombreCarta(CartasDesarrollo carta) {
        String cls = carta.getClass().getSimpleName();
        if (cls.contains("Caballero"))
            return "Caballero";
        if (cls.contains("Monopolio"))
            return "Monopolio";
        if (cls.contains("ConstruccionCarretera"))
            return "Construccion";
        if (cls.contains("PuntoVictoria"))
            return "PV";
        if (cls.contains("Descubrimiento"))
            return "Descubrimiento";
        return cls;
    }

    private Button crearBotonUsar(CartasDesarrollo cartaModelo, String nombreCartaDesarrollo, VBox carta) {
        Button btn = new Button("Usar");
        btn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-cursor: hand;");
        btn.setOnAction(evt -> {
            ReproductorDeSonido.getInstance().playClick();
            boolean confirmado = confirmarAccion("¿Confirmas usar la carta " + nombreCartaDesarrollo + "?");
            if (!confirmado)
                return;

            // Cerrar la ventana de la baraja de cartas al confirmar
            if (this.stage != null) {
                this.stage.close();
            }

            ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(
                    gestor.obtenerJugadorActual(), gestor.obtenerJugadores(), gestor.obtenerTurnoActual(),
                    gestor.obtenerTablero(), gestor.obtenerLadron());

            try {
                handleUsoCartaSegunTipo(cartaModelo, contexto, carta, nombreCartaDesarrollo);
            } catch (Exception ex) {
                mostrarError("No se pudo usar la carta", ex.getMessage());
            }
        });
        return btn;
    }

    private boolean confirmarAccion(String mensaje) {
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setHeaderText(null);
        confirm.setContentText(mensaje);
        // Forzar que la alerta esté encima de todo
        Stage alertStage = (Stage) confirm.getDialogPane().getScene().getWindow();
        alertStage.setAlwaysOnTop(true);
        if (this.stage != null) {
            alertStage.initOwner(this.stage);
        }
        Optional<ButtonType> res = confirm.showAndWait();
        return res.isPresent() && res.get() == ButtonType.OK;
    }

    private void mostrarInfo(String mensaje) {
        Alert info = new Alert(AlertType.INFORMATION);
        info.setHeaderText(null);
        info.setContentText(mensaje);
        info.show();
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert err = new Alert(AlertType.ERROR);
        err.setHeaderText(titulo);
        err.setContentText(mensaje != null ? mensaje : "");
        err.showAndWait();
    }

    private void handleUsoCartaSegunTipo(CartasDesarrollo cartaModelo, ContextoCartaDesarrollo contexto, VBox carta,
            String nombreCarta) {
        if (cartaModelo instanceof CartaCaballero) {
            handleCaballero((CartaCaballero) cartaModelo, contexto, carta, nombreCarta);
        } else if (cartaModelo instanceof CartaConstruccionCarretera) {
            handleConstruccionCarretera((CartaConstruccionCarretera) cartaModelo, contexto, carta, nombreCarta);
        } else if (cartaModelo instanceof CartaMonopolio) {
            handleMonopolio((CartaMonopolio) cartaModelo, contexto, carta, nombreCarta);
        } else if (cartaModelo instanceof CartaDescubrimiento) {
            handleDescubrimiento((CartaDescubrimiento) cartaModelo, contexto, carta, nombreCarta);
        } else {
            ejecutarUsoCartaEnGestor(cartaModelo, contexto, carta, nombreCarta, null);
        }
    }

    private void ejecutarUsoCartaEnGestor(CartasDesarrollo cartaModelo, ContextoCartaDesarrollo contexto, VBox carta,
            String nombreCarta, Runnable uiExtra) {
        try {
            gestor.usarCartaDesarrollo(cartaModelo, contexto);
            Platform.runLater(() -> {
                try {
                    panelCartas.getChildren().remove(carta);
                    try {
                        var coordenadasCarreteras = contexto.obtenerCoordenadasCarreteras();
                        if (coordenadasCarreteras != null && tableroUI != null && gestor != null) {
                            int indiceJugador = gestor.obtenerIndiceJugadorActual();
                            for (java.util.List<Coordenadas> par : coordenadasCarreteras) {
                                if (par != null && par.size() >= 2) {
                                    Coordenadas p1 = par.get(0);
                                    Coordenadas p2 = par.get(1);
                                    try {
                                        tableroUI.marcarCarretera(p1, p2, indiceJugador);
                                    } catch (Exception ignore) {
                                    }
                                }
                            }
                        }
                    } catch (Exception ignore) {
                    }

                    if (uiExtra != null) {
                        try {
                            uiExtra.run();
                        } catch (Exception e) {
                        }
                    }
                    if (tableroUI != null)
                        tableroUI.refrescarDesdeModelo();
                    if (onActualizarUI != null)
                        onActualizarUI.run();
                    mostrarInfo("Carta usada correctamente: " + nombreCarta);
                } catch (Exception ex) {
                    mostrarError("Error al actualizar UI", ex.getMessage());
                }
            });
        } catch (Exception ex) {
            Platform.runLater(() -> mostrarError("No se pudo usar la carta", ex.getMessage()));
            if (tableroUI != null)
                tableroUI.refrescarDesdeModelo();
        }
    }

    private void handleCaballero(CartaCaballero cartaModelo, ContextoCartaDesarrollo contexto, VBox carta,
            String nombreCarta) {
        if (tableroUI != null) {
            // Reutilizar ventana dedicada para mover ladrón (permitir cerrar/cancelar en
            // caballero)
            Stage child = new Stage();
            if (this.stage != null)
                child.initOwner(this.stage);
            new VentanaMoverLadron(child, nombreJugador, gestor, tableroUI, (coord, jugadorObjetivo) -> {
                contexto.establecerCoordenadasDestino(coord);
                contexto.establecerJugadorObjetivo(jugadorObjetivo);
                ejecutarUsoCartaEnGestor(cartaModelo, contexto, carta, nombreCarta, () -> {
                    try {
                        if (tableroUI != null)
                            tableroUI.actualizarLadronEn(coord);
                    } catch (Exception e) {
                    }
                });
            }, true);
        } else {
            TextInputDialog dx = new TextInputDialog();
            dx.setHeaderText(null);
            dx.setContentText("Coordenada fila (int):");
            Optional<String> sx = dx.showAndWait();
            if (sx.isEmpty())
                return;
            TextInputDialog dy = new TextInputDialog();
            dy.setHeaderText(null);
            dy.setContentText("Coordenada columna (int):");
            Optional<String> sy = dy.showAndWait();
            if (sy.isEmpty())
                return;
            try {
                int x = Integer.parseInt(sx.get());
                int y = Integer.parseInt(sy.get());
                Coordenadas coord = new Coordenadas(x, y);
                contexto.establecerCoordenadasDestino(coord);
                List<Jugador> candidatos = gestor.obtenerJugadoresAdyacentes(coord);
                Jugador jugadorObjetivo = (candidatos == null || candidatos.isEmpty()) ? null : candidatos.get(0);
                contexto.establecerJugadorObjetivo(jugadorObjetivo);
                ejecutarUsoCartaEnGestor(cartaModelo, contexto, carta, nombreCarta, null);
            } catch (Exception ex) {
                mostrarError("No se pudo usar la carta", ex.getMessage());
            }
        }
    }

    private void handleConstruccionCarretera(CartaConstruccionCarretera cartaModelo, ContextoCartaDesarrollo contexto,
            VBox carta, String nombreCarta) {
        if (tableroUI == null) {
            mostrarError("No es posible", "La selección en tablero no está disponible.");
            return;
        }
        Stage child1 = new Stage();
        if (this.stage != null)
            child1.initOwner(this.stage);
        new VentanaSeleccionCamino(child1, nombreJugador, tableroUI, camino1 -> {
            Platform.runLater(() -> {
                Stage child2 = new Stage();
                if (this.stage != null)
                    child2.initOwner(this.stage);
                final boolean[] confirmadoSegundo = { false };
                child2.setOnHidden(ev -> {
                    if (!confirmadoSegundo[0]) {
                        tableroUI.refrescarDesdeModelo();
                        tableroUI.deshabilitarSeleccionVertice();
                    }
                });
                new VentanaSeleccionCamino(child2, nombreJugador, tableroUI, camino2 -> {
                    confirmadoSegundo[0] = true;
                    final ArrayList<List<Coordenadas>> caminos = new ArrayList<>();
                    caminos.add(camino1);
                    caminos.add(camino2);
                    contexto.establecerCoordenadasCarreteras(caminos);
                    try {
                        ejecutarUsoCartaEnGestor(cartaModelo, contexto, carta, nombreCarta, () -> {
                            tableroUI.refrescarDesdeModelo();
                        });
                    } catch (Exception ex) {
                        tableroUI.refrescarDesdeModelo();
                        mostrarError("No se pudo usar la carta", ex.getMessage());
                    }
                }, true, true);
            });
        }, true, false);

    }

    private void handleMonopolio(CartaMonopolio cartaModelo, ContextoCartaDesarrollo contexto, VBox carta,
            String nombreCarta) {
        List<String> opciones = List.of("Madera", "Ladrillo", "Grano", "Lana", "Piedra");
        ChoiceDialog<String> dlg = new ChoiceDialog<>(opciones.get(0), opciones);
        dlg.setHeaderText(null);
        dlg.setContentText("Elegí recurso para monopolizar:");
        Optional<String> pick = dlg.showAndWait();
        if (pick.isEmpty())
            return;
        String elegido = pick.get();
        edu.fiuba.algo3.modelo.Recurso recurso = mapearRecurso(elegido);
        contexto.establecerRecursoElegido(recurso);
        ejecutarUsoCartaEnGestor(cartaModelo, contexto, carta, nombreCarta, null);
    }

    private void handleDescubrimiento(CartaDescubrimiento cartaModelo, ContextoCartaDesarrollo contexto, VBox carta,
            String nombreCarta) {
        List<String> opciones = List.of("Madera", "Ladrillo", "Grano", "Lana", "Piedra");
        ChoiceDialog<String> d1 = new ChoiceDialog<>(opciones.get(0), opciones);
        d1.setHeaderText(null);
        d1.setContentText("Elegí primer recurso:");
        Optional<String> p1 = d1.showAndWait();
        if (p1.isEmpty())
            return;
        ChoiceDialog<String> d2 = new ChoiceDialog<>(opciones.get(0), opciones);
        d2.setHeaderText(null);
        d2.setContentText("Elegí segundo recurso:");
        Optional<String> p2 = d2.showAndWait();
        if (p2.isEmpty())
            return;
        ArrayList<edu.fiuba.algo3.modelo.Recurso> recursosElegidos = new ArrayList<>();
        recursosElegidos.add(mapearRecurso(p1.get()));
        recursosElegidos.add(mapearRecurso(p2.get()));
        contexto.establecerRecursosElegidos(recursosElegidos);
        ejecutarUsoCartaEnGestor(cartaModelo, contexto, carta, nombreCarta, null);
    }

    private edu.fiuba.algo3.modelo.Recurso mapearRecurso(String nombre) {
        return switch (nombre) {
            case "Madera" -> new edu.fiuba.algo3.modelo.tiposRecurso.Madera(1);
            case "Ladrillo" -> new edu.fiuba.algo3.modelo.tiposRecurso.Ladrillo(1);
            case "Grano" -> new edu.fiuba.algo3.modelo.tiposRecurso.Grano(1);
            case "Lana" -> new edu.fiuba.algo3.modelo.tiposRecurso.Lana(1);
            case "Piedra" -> new edu.fiuba.algo3.modelo.tiposRecurso.Piedra(1);
            default -> new edu.fiuba.algo3.modelo.tiposRecurso.Madera(1);
        };
    }

}