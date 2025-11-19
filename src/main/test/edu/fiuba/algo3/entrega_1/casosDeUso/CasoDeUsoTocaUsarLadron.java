package edu.fiuba.algo3.entrega_1.casosDeUso;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Ladron;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.*;
import edu.fiuba.algo3.modelo.tablero.Produccion;
import edu.fiuba.algo3.modelo.Dados;

public class CasoDeUsoTocaUsarLadron {
    private Tablero tablero;
    private String jugador;
    private Hexagono hexagonoActual;
    private int resultado = 7; // Para activar el ladron.

    public CasoDeUsoTocaUsarLadron(Tablero tablero, String jugador) {
        this.tablero = tablero;
        this.jugador = jugador;
        this.hexagonoActual = new Desierto();
    }

    public int lanzarDado() {
        return resultado;
    }

    public void moverLadronA(Hexagono hexagonoLadron) {
        tablero.moverLadronA(hexagonoLadron);
    }

}
