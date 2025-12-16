package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.GestorDeTurnos;
import javafx.scene.control.Label;

public class ControladorFases {
    
    private final String[] fases = {"Lanzamiento de Dados", "Construcción/Comercio", "Cartas de Desarrollo"};
    private int indiceFaseActual = 0;
    private final Label lblFaseJugador;
    private final GestorDeTurnos gestor;
    private boolean dadoTiradoEnTurno = false;
    
    public ControladorFases(Label lblFaseJugador, GestorDeTurnos gestor) {
        this.lblFaseJugador = lblFaseJugador;
        this.gestor = gestor;
        actualizarEtiqueta();
    }
    
    public void avanzarFase(String jugadorActual) {
        if (estamosEnFaseLanzamiento() && !yaTiroDadoEnTurno()) {
            return;
        }
        
        indiceFaseActual = (indiceFaseActual + 1) % fases.length;
        
        if (indiceFaseActual == 0) {
            dadoTiradoEnTurno = false;
            gestor.avanzarTurno();
        }
        actualizarEtiqueta();
    }
    
    private void actualizarEtiqueta() {
        lblFaseJugador.setText(fases[indiceFaseActual]);
    }
    
    public int getFaseActual() {
        return indiceFaseActual;
    }
    
    public String getNombreFaseActual() {
        return fases[indiceFaseActual];
    }
    
    public boolean estamosEnFaseLanzamiento() {
        return indiceFaseActual == 0;
    }
    
    public boolean yaTiroDadoEnTurno() {
        return dadoTiradoEnTurno;
    }
    
    public void marcarDadoTirado() {
        dadoTiradoEnTurno = true;
    }
}
