package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.*;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.*;
import edu.fiuba.algo3.modelo.tablero.Produccion;
import edu.fiuba.algo3.modelo.Dados;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.entrega_1.casosDeUso.CasoDeUsoDadoCargado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CasoDeUsoTurno {
    private ArrayList<Hexagono> listaDeHexagonos;
    private ArrayList<Produccion> listaDeNumeros;
    private String jugador;

    @Test
    public void test01LanzarDadosGeneraNumeroValido(){
        //Arrange
        Dados dados = new Dados();
        //Act
        int res = dados.lanzarDados();

        //Assert
        assert(res >= 2 && res <= 12);
    }
    
    @Test
    public void test02ProduccionCorrectaPorConstruccion(){

        Tablero tablero = new Tablero();

        CasoDeUsoDadoCargado caso = new CasoDeUsoDadoCargado(tablero, "Azul");
        caso.colocarEn(new Coordenadas(2,3),new Poblado("Azul"), "Azul");

       //caso.colocarEn(new Coordenadas(2,7),new Ciudad("Azul")); 

        int resultado = caso.lanzarDados();
        
        var recursosObtenidos = caso.producirRecursos(resultado);
        var produccionEsperada = List.of(
            Recurso.generarRecurso("Lana", 1)
        );

        assertEquals(produccionEsperada, recursosObtenidos);
        
    }

    // public abstract Recurso generarRecurso(int cantidad);

}
