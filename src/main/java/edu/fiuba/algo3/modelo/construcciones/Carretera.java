package edu.fiuba.algo3.modelo.construcciones;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.reglas.ReglaAdyacencia;
import edu.fiuba.algo3.modelo.reglas.ReglaRecursos;
import edu.fiuba.algo3.modelo.reglas.ReglasCompuestas;
import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.tiposRecurso.Ladrillo;
import edu.fiuba.algo3.modelo.tiposRecurso.Madera;

import java.util.ArrayList;
import java.util.List;

public class Carretera extends Construccion {
    private Vertice vertice1;
    private Vertice vertice2;

    public Carretera() {
        this.puntosVictoria = 0;
        this.ReglasConstruccionInicial = new ReglasCompuestas(
            new ReglaAdyacencia()
        );
        this.ReglasConstruccionNormal = new ReglasCompuestas(
            new ReglaRecursos(),
            new ReglaAdyacencia()
        );
    }

    public Jugador getJugador() {
        return jugador;
    }


    public void asignarVertice(Vertice vertice) {
        if (this.vertice1 == null) {
            this.vertice1 = vertice;
        } else if (this.vertice2 == null) {
            this.vertice2 = vertice;
        }
    }

    public ArrayList<Vertice> conseguirVertices() {
        return new ArrayList<>(List.of(this.vertice1, this.vertice2));
    }

    public boolean conecta(Vertice vertice1, Vertice vertice2) {
        return (this.vertice1 == vertice1 && this.vertice2 == vertice2) ||
               (this.vertice1 == vertice2 && this.vertice2 == vertice1);
    }

    public ArrayList<Recurso> obtenerRecursosNecesarios() {
        ArrayList<Recurso> recursos = new ArrayList<>();
        recursos.add(new Madera(1));
        recursos.add(new Ladrillo(1));
        return recursos;
    }
}