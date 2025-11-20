package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.*;
import edu.fiuba.algo3.modelo.Dados;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.entrega_1.casosDeUso.CasoDeUsoDadoCargado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        CasoDeUsoDadoCargado caso = new CasoDeUsoDadoCargado(tablero, "Azul");
        caso.colocarEn(new Coordenadas(2, 4), new Poblado("Azul"), "Azul");
        caso.colocarEn(new Coordenadas(3, 3), new Ciudad("Azul"), "Azul");

        int resultado = caso.lanzarDados();

        var recursosObtenidos = caso.producirRecursos(6);
        var produccionEsperada = List.of(
        
                Recurso.generarRecurso("Madera", 2),
                Recurso.generarRecurso("Lana", 1),
                Recurso.generarRecurso("Lana", 2));


        //sirven para que no importe el orden de los recursos en la lista
        assertTrue(recursosObtenidos.containsAll(produccionEsperada));
        assertTrue(produccionEsperada.containsAll(recursosObtenidos));

    }

}
