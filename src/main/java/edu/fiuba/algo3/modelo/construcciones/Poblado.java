package edu.fiuba.algo3.modelo.construcciones;

import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.reglas.*;
import edu.fiuba.algo3.modelo.tiposRecurso.Grano;
import edu.fiuba.algo3.modelo.tiposRecurso.Ladrillo;
import edu.fiuba.algo3.modelo.tiposRecurso.Lana;
import edu.fiuba.algo3.modelo.tiposRecurso.Madera;

import java.util.ArrayList;
import java.util.List;
public class Poblado extends Construccion {
   
    public Poblado(){
        this.puntosVictoria = 1;

        this.ReglasConstruccionNormal = new ReglasCompuestas(
            new ReglaVerticeVacio(),
            new ReglaDistancia(),
            new ReglaRecursos(),
            new ReglaAdyacencia()
        );

        this.ReglasConstruccionInicial = new ReglasCompuestas(
            new ReglaVerticeVacio(),
            new ReglaDistancia()
        );

    }

    public ArrayList<Recurso> obtenerRecursosNecesarios() {
        return new ArrayList<Recurso>(List.of(new Madera(1), new Ladrillo(1), new Grano(1), new Lana(1)));
    }
}