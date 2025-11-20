package edu.fiuba.algo3.modelo.construcciones;

import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tiposRecurso.Grano;
import edu.fiuba.algo3.modelo.tiposRecurso.Piedra;

import java.util.List;

public class Ciudad extends Construccion {

    public Ciudad(){
        this.puntosVictoria = 2;
    }

    public boolean esPoblado() {
        return false;
    }

    public boolean esCiudad() {
        return true;
    }

    public boolean puedeConstruirse(Construccion construccionPrevia){
        if (construccionPrevia == null){
            return false;
        }
        return construccionPrevia.esPoblado();
    }

    public List<Recurso> obtenerRecursosNecesarios() {
        return List.of(new Grano(2), new Piedra(3));
    }
}