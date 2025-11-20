package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.*;
import edu.fiuba.algo3.modelo.Dados;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.entrega_1.casosDeUso.CasoDeUsoDadoCargado;
import edu.fiuba.algo3.entrega_1.casosDeUso.DadoCargado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CasoDeUsoTurno {
    @Test
    public void test01LanzarDadosGeneraNumeroValido() {
        // Arrange
        Dados dados = new Dados();
        // Act
        int res = dados.lanzarDados();
        // Assert
        assert (res >= 2 && res <= 12);
    }

    @Test
    public void test02ProduccionCorrectaPorConstruccion() {

        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Azul");

        CasoDeUsoDadoCargado caso = new CasoDeUsoDadoCargado(tablero, jugador);
        caso.colocarEn(new Coordenadas(2, 7), new Poblado());
        caso.colocarEn(new Coordenadas(2, 9), new Ciudad());
        
        ArrayList<Recurso> recursosObtenidos = caso.lanzarDados(new DadoCargado(8));

        var produccionEsperada = List.of(
                new Lana(1),
                new Lana(1),
                new Lana(2));

        //sirven para que no importe el orden de los recursos en la lista
        assertTrue(recursosObtenidos.containsAll(produccionEsperada));
        assertTrue(produccionEsperada.containsAll(recursosObtenidos));
    }

    @Test
    public void test03ProduccionCorrectaDevuelveCantidadCorrectaDeRecursos() {

        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Azul");
        CasoDeUsoDadoCargado caso = new CasoDeUsoDadoCargado(tablero,jugador);
        caso.colocarEn(new Coordenadas(2, 4), new Poblado());
        caso.colocarEn(new Coordenadas(3, 3), new Ciudad());

        ArrayList<Recurso> recursosObtenidos = caso.lanzarDados(new DadoCargado(6));
        var produccionIncorrecta = List.of(

                new Madera(4),
                new Lana(5),
                new Lana(6));


        assertFalse(recursosObtenidos.containsAll(produccionIncorrecta));
        assertFalse(produccionIncorrecta.containsAll(recursosObtenidos));
    }

}
