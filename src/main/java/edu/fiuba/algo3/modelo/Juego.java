package edu.fiuba.algo3.modelo;
import edu.fiuba.algo3.modelo.*;
import java.util.List;


public class Juego {
    private List<Jugador> jugadores;
    private Tablero tablero;
    private final Dados dados;
    private Mazo mazo;
    private Jugador jugadorActual;

    public void Juego(){
        this.tablero = new Tablero();
        this.dados = new Dados();
        this.mazo = new Mazo();
        this.jugadores = List.<>();
    }

    public void lanzarDados(){
        return dados.();

    }
}