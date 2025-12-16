package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.GestorDeTurnos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControladorConstruccion {
    
    private final GestorDeTurnos gestor;
    private final String nombreJugador;
    private TableroUI tableroUI;
    private Button btnPoblado;
    private Button btnCarretera;
    private Button btnComerciar;
    
    public ControladorConstruccion(GestorDeTurnos gestor, String nombreJugador) {
        this.gestor = gestor;
        this.nombreJugador = nombreJugador;
    }
    
    public void setTableroUI(TableroUI tableroUI) {
        this.tableroUI = tableroUI;
    }
    
    public VBox crearPanelBotonesConstruccion() {
        btnPoblado = crearBotonAccion("Construir Poblado", "#27ae60");
        btnCarretera = crearBotonAccion("Construir Carretera", "#27ae60");
        btnComerciar = crearBotonAccion("Comerciar", "#3498db");
        
        btnPoblado.setOnAction(e -> abrirVentanaConstruirPoblado());
        btnCarretera.setOnAction(e -> abrirVentanaConstruirCarretera());
        btnComerciar.setOnAction(e -> abrirVentanaComerciar());
        
        btnPoblado.setVisible(false);
        btnCarretera.setVisible(false);
        btnComerciar.setVisible(false);

        VBox botonesBox = new VBox(8, btnPoblado, btnCarretera, btnComerciar);
        botonesBox.setAlignment(Pos.TOP_CENTER);
        
        return botonesBox;
    }
    
    private Button crearBotonAccion(String texto, String colorHex) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: " + colorHex + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 12;");
        VBox.setMargin(btn, new Insets(4, 0, 0, 0));
        return btn;
    }
    
    private void abrirVentanaConstruirPoblado() {
        Stage stage = new Stage();
        new VentanaConstruirPoblado(stage, nombreJugador, gestor, tableroUI);
    }
    
    private void abrirVentanaConstruirCarretera() {
        Stage stage = new Stage();
        new VentanaConstruirCarretera(stage, nombreJugador, gestor, tableroUI);
    }
    
    private void abrirVentanaComerciar() {
        Stage stage = new Stage();
        new VentanaComerciar(stage, nombreJugador);
    }
    
    public void mostrarVentanaVictoria() {
        VentanaVictoria ventana = new VentanaVictoria();
        ventana.mostrar();
    }
    
    public Button getBtnTerminarFase() {
        Button btn = crearBotonAccion("Terminar Fase", "#e67e22");
        return btn;
    }

    public void actualizarVisibilidadSegunFase(int indiceFase) {
        boolean enConstruccion = (indiceFase == 1);
        if (btnPoblado != null) btnPoblado.setVisible(enConstruccion);
        if (btnCarretera != null) btnCarretera.setVisible(enConstruccion);
        if (btnComerciar != null) btnComerciar.setVisible(enConstruccion);
    }
}
