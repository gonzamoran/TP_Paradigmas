package edu.fiuba.algo3.modelo;
import edu.fiuba.algo3.modelo.*;
import java.util.List;

public class Hexagono {
    private TipoRecurso recurso;
    private int id;
    private int numero;
    private boolean tieneLadron;
    
    public Hexagono(int id, TipoRecurso recurso){
        if(id < 0){
            throw new IllegalArgumentException("El ID del hexágono no puede ser negativo");
        }
        this.id = numero;
        this.recurso = recurso;
        this.tieneLadron = false;
    }

    public void asignarNumero(int numero){
        this.numero = numero;
    }
    
    public void colocarLadron(){
        this.tieneLadron = true;
    }

    public void sacarLadron(){
        this.tieneLadron = false;
    }

    public TipoRecurso obtenerRecurso(){
        return recurso;
    }
}


