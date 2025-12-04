package edu.fiuba.algo3.modelo.construcciones;


import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.reglas.ReglasConstruccion;
import edu.fiuba.algo3.modelo.tablero.Vertice;

import java.util.ArrayList;


public abstract class Construccion {
    protected int puntosVictoria;
    protected Jugador jugador;
    protected ReglasConstruccion ReglasConstruccionNormal;
    protected ReglasConstruccion ReglasConstruccionInicial;


    public int obtenerPuntosDeVictoria() {
        return this.puntosVictoria;
    }

    public void asignarJugador(Jugador jugador){
        this.jugador = jugador;
        jugador.agregarConstruccion(this);
    }

    public boolean esDueno(Jugador jugador){
        return this.jugador.equals(jugador);
    }

    public abstract ArrayList<Recurso> obtenerRecursosNecesarios();
    
    public boolean validarConstruccion(Vertice vertice, Jugador jugador){
        return ReglasConstruccionNormal.validar(vertice, this, jugador);
    }

    public boolean validarConstruccionInicial(Vertice vertice, Jugador jugador){
        return ReglasConstruccionInicial.validar(vertice, this, jugador);
    }


    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        return this.puntosVictoria == ((Construccion) obj).puntosVictoria;
    }
}
