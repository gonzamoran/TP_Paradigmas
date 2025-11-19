package edu.fiuba.algo3.entrega_1.casosDeUso;

import java.util.ArrayList;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.construcciones.Construccion;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.Recurso;

public class CasoDeUsoColocacionInicial {
    private Tablero tablero;

    public CasoDeUsoColocacionInicial(Tablero tablero) {
        this.tablero = tablero;
    }

    public ArrayList<Recurso> colocarConstruccionInicial(Coordenadas coordenadas, Construccion construccion,
            String jugador) {
        var recursos = tablero.colocarConstruccionInicial(construccion, coordenadas, jugador); // devuelve recursos
                                                                                               // siempre, el primer
                                                                                               // poblado solo los
                                                                                               // desecha
        return recursos;
    }
}
