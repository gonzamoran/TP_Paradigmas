package edu.fiuba.algo3.modelo.reglas;


import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.construcciones.Construccion;
import edu.fiuba.algo3.modelo.tablero.Vertice;

public class ReglaVerticeVacio implements ReglasConstruccion {

    @Override
    public boolean validar(Vertice vertice, Construccion construccion, Jugador jugador){
        if(vertice.tieneConstruccion()){
            return false;
        }
        return true;
    }
}