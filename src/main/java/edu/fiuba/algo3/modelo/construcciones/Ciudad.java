package edu.fiuba.algo3.modelo.construcciones;

import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.reglas.ReglaRecursos;
import edu.fiuba.algo3.modelo.reglas.ReglaVerticeOcupadoPorJugador;
import edu.fiuba.algo3.modelo.reglas.ReglasCompuestas;
import edu.fiuba.algo3.modelo.tiposRecurso.Grano;
import edu.fiuba.algo3.modelo.tiposRecurso.Piedra;

import java.util.ArrayList;
import java.util.List;

public class Ciudad extends Construccion {

    public Ciudad(){
        this.puntosVictoria = 2;
        
        this.ReglasConstruccionNormal = new ReglasCompuestas(
            new ReglaRecursos(),
            new ReglaVerticeOcupadoPorJugador()
        );
    }

    public ArrayList<Recurso> obtenerRecursosNecesarios() {
        return new ArrayList<Recurso>(List.of(new Grano(2), new Piedra(3)));
    }
}