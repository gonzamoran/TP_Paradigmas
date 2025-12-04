package edu.fiuba.algo3.modelo.reglas;


import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.construcciones.Construccion;

import edu.fiuba.algo3.modelo.excepciones.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReglasCompuestas implements ReglasConstruccion {

    private List<ReglasConstruccion> reglas = new ArrayList<>();

    public ReglasCompuestas(ReglasConstruccion... reglas){
        this.reglas.addAll(Arrays.asList(reglas));
    }

    @Override
    public boolean validar(Vertice vertice, Construccion construccion, Jugador jugador){
        for( ReglasConstruccion regla : reglas){
            if (!regla.validar(vertice, construccion, jugador)){
                return false;
            }
        }
        return true;
    }
}