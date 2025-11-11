package edu.fiuba.algo3.modelo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import edu.fiuba.algo3.modelo.Hexagono;
import edu.fiuba.algo3.modelo.Ladron;
import edu.fiuba.algo3.modelo.Vertice;
import edu.fiuba.algo3.modelo.TipoRecurso;


public class Tablero {
    private List<Hexagono> hexagonos;
    private Ladron ladron;
    private Vertice vertices;

    public Tablero() {
        this.ladron = new Ladron();
        this.hexagonos = new ArrayList<Hexagono>();
        this.inicializarHexagonos();
        this.inicializarLadron();
        this.asignarNumeroALosHexagonos();

    }

    public void inicializarHexagonos(){
        for (int i = 0; i < 1; i++){
            hexagonos.add(new Hexagono(i, TipoRecurso.DESIERTO));
        }
        for (int i = 1; i < 5; i++) {
            hexagonos.add(new Hexagono(i, TipoRecurso.GRANO));
        };
        for (int i = 5; i < 9; i++) {
            hexagonos.add(new Hexagono(i, TipoRecurso.MADERA));
        };
        for (int i = 9; i < 13; i++) {
            hexagonos.add(new Hexagono(i, TipoRecurso.LANA));
        };
        for (int i = 13; i < 16; i++) {
            hexagonos.add(new Hexagono(i, TipoRecurso.LADRILLO));
        };
        for (int i = 16; i < 19; i++) {
            hexagonos.add(new Hexagono(i, TipoRecurso.MINERAL));
        };
        Collections.shuffle(hexagonos);
    }

    public void asignarNumeroALosHexagonos(){
        List<Integer> numeros = List.of(2,3,3,4,4,5,5,6,6,8,8,9,9,10,10,11,11,12);
        Collections.shuffle(numeros);
        for (int i = 0; i < hexagonos.size(); i++){
            Hexagono hexagono = hexagonos.get(i);
            if (hexagono.obtenerRecurso() != TipoRecurso.DESIERTO){
                hexagono.asignarNumero(i);
            }
        }
    }
    

    public void inicializarLadron(){
        for (Hexagono hexagono : hexagonos){
            if (hexagono.obtenerRecurso() == TipoRecurso.DESIERTO){
                ladron.moverA(hexagono);
            }
        }
    }
}
/*
 1 desierto. 4 Trigo/grano, 4 madera, 4 oveja/lana, 3 arcilla/ladrillo, 3 piedra/minera. 
 
Numeros:
        2 
        3,3
        4,4
        5,5
        6,6
        8,8
        9,9
        10,10
        11,11
        12
*/
