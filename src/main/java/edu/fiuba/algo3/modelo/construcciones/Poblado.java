package edu.fiuba.algo3.modelo.construcciones;

import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tiposRecurso.Ladrillo;
import edu.fiuba.algo3.modelo.tiposRecurso.Madera;
import edu.fiuba.algo3.modelo.tiposRecurso.Grano;
import edu.fiuba.algo3.modelo.tiposRecurso.Lana;

import java.util.List;
public class Poblado extends Construccion {
   
    public Poblado(){
        this.puntosVictoria = 1;
    }

    public boolean esPoblado() {
        return true;
    }

    public boolean esCiudad() {
        return false;
    }
    
    public boolean puedeConstruirse(Construccion construccionPrevia){
        if (construccionPrevia != null){
            return false;
        }
        return true;
    }

    public List<Recurso> obtenerRecursosNecesarios() {
        return List.of(new Madera(1), new Ladrillo(1), new Grano(1), new Lana(1));
    }
}