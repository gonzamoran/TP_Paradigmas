package edu.fiuba.algo3.modelo.construcciones;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tiposRecurso.Madera;
import edu.fiuba.algo3.modelo.tiposRecurso.Ladrillo;

import java.util.List;
import java.util.ArrayList;

public class Carretera extends Construccion {

    public Carretera() {
        this.puntosVictoria = 0;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public boolean puedeConstruirse(Construccion construccionVertice){
        if (construccionVertice == null) {
            return false;
        }
        if (!construccionVertice.equals(new Poblado()) && !construccionVertice.equals(new Ciudad())) {
            return false;
        }
        return true;
    }

    public List<Recurso> obtenerRecursosNecesarios() {
        List<Recurso> recursos = new ArrayList<>();
        recursos.add(new Madera(1));
        recursos.add(new Ladrillo(1));
        return recursos;
    }
}