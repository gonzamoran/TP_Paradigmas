package edu.fiuba.algo3.modelo.construcciones;

import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.reglas.*;
import edu.fiuba.algo3.modelo.tiposRecurso.Grano;
import edu.fiuba.algo3.modelo.tiposRecurso.Piedra;

import java.util.List;
import java.util.ArrayList;

public class Ciudad extends Construccion {

    public Ciudad(){
        this.puntosVictoria = 2;
        
        this.ReglasConstruccionNormal = new ReglasCompuestas(
            new ReglaRecursos(),
            new ReglaVerticeOcupadoPorJugador()
        );
    }

    public boolean puedeConstruirse(Construccion construccionPrevia){
        if (construccionPrevia == null){
            return false;
        }
        return construccionPrevia.equals(new Poblado());
    }

    public ArrayList<Recurso> obtenerRecursosNecesarios() {
        return new ArrayList<Recurso>(List.of(new Grano(2), new Piedra(3)));
    }
}