package edu.fiuba.algo3.modelo;
import edu.fiuba.algo3.modelo.*;
import java.util.List;
import java.util.ArrayList;

public class Hexagono {
    private TipoRecurso recurso;
    private int id;
    private int numero;
    private boolean tieneLadron;
    private Coordenadas coordenadas;
    private List<Vertice> vertices;

    public Hexagono(int id, TipoRecurso recurso){
        if(id < 0){
            throw new IllegalArgumentException("El ID del hexágono no puede ser negativo");
        }
        this.id = id;
        this.recurso = recurso;
        this.tieneLadron = false;
        this.vertices = new ArrayList<>();
    }
    public void asignarCoordenada(int x, int y){
        this.coordenadas= new Coordenadas(x,y);
    }
    public void asignarNumero(int numero){
        this.numero = numero;
    }

    public void asignarVertices(){
        
        int hx = this.coordenadas.obtenerCoordenadaX();
        int hy = this.coordenadas.obtenerCoordenadaY();

        Coordenadas[] coordenadasVertices = new Coordenadas[] {
            new Coordenadas(hx, hy),
            new Coordenadas(hx, hy - 1),
            new Coordenadas(hx + 1, hy - 1),
            new Coordenadas(hx + 1, hy),
            new Coordenadas(hx + 1, hy + 1),
            new Coordenadas(hx, hy + 1)
        };
    }

    public void colocarLadron(){
        this.tieneLadron = true;
    }

    public void sacarLadron(){
        this.tieneLadron = false;
    }

    public int obtenerNumeroFicha(){
        if(this.recurso == TipoRecurso.DESIERTO){
            throw new DesiertoNoTieneFichaException(); // Tal vez no deberia tirar una excepcion?
        }
        return this.numero;
    }

    public TipoRecurso obtenerRecurso(){
        return recurso;
    }

    public boolean puedeGenerarRecursos(){
        if(this.tieneLadron){
            return false;
        }
        if(this.recurso == TipoRecurso.DESIERTO){
            return false;
        }
        return true;
    }
}


