package edu.fiuba.algo3.entrega_1.pruebas_desechadas;

import org.junit.jupiter.api.*;

import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.TipoRecurso;
import edu.fiuba.algo3.modelo.excepciones.*;


import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

public class TableroTests {

    private List<Hexagono> hexagonos;
    private Tablero tablero;

    @BeforeEach
    public void setUp() {
        this.tablero = new Tablero();
        this.hexagonos = tablero.obtenerHexagonos();
    };
    
    @Test
    public void test01AlCrearElTableroEsteContiene19Hexagonos() {
        //"Assert"
        assertEquals(19, hexagonos.size());
    }
    
    @Test
    public void test02CorrectaAsignacionDeBosques() {
        //Arrange
        int contadorMadera = 0;
        //Act
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.obtenerRecurso() == TipoRecurso.MADERA) {
                contadorMadera++;
            }
        }
        //Assert
        assertEquals(4, contadorMadera);
    }
    
    @Test
    public void test03CorrectaAsignacionDeColinas() {
        //Arrange
        int contadorLadrillo = 0;
        //Act
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.obtenerRecurso() == TipoRecurso.LADRILLO) {
                contadorLadrillo++;
            }
        }
        //Assert
        assertEquals(3, contadorLadrillo);
    }

    @Test
    public void test04CorrectaAsignacionDePastizal() {
        //Arrange
        int contadorMadera = 0;
        //Act
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.obtenerRecurso() == TipoRecurso.LANA) {
                contadorMadera++;
            }
        }
        //Assert
        assertEquals(4, contadorMadera);
    }

    @Test
    public void test05CorrectaAsignacionDeTerrenosCampo(){
        //Arrange
        int contadorCampo = 0;
        //Act
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.obtenerRecurso() == TipoRecurso.GRANO) {
                contadorCampo++;
            }
        }
        //Assert
        assertEquals(4, contadorCampo);
    }


    @Test
    public void test06CorrectaAsignacionDeTerrenosMontana() {
        //Arrange
        int contadorMineral = 0;
        //Act
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.obtenerRecurso() == TipoRecurso.MINERAL) {
                contadorMineral++;
            }
        }
        //Assert
        assertEquals(3, contadorMineral);
    }

    @Test
    public void test07CorrectaAsignacionDeTerrenosDesierto() {
        //Arrange
        int contadorDesierto = 0;
        //Act
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.esDesierto()) {
                contadorDesierto++;
            }
        }
        //Assert
        assertEquals(1, contadorDesierto);
    }

    @Test
    public void test08ElHexagonoDeDesiertoNoTieneFichaAsignada() {
        //Act & Assert
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.esDesierto()) {
                assertThrows(DesiertoNoTieneFichaException.class,() -> hexagono.obtenerNumeroFicha()); 
            }
        }
    }

    @Test
    public void test09CorrectaAsignacionDeFichasPosibles() {
        //Arrange
        List<Integer> fichasPosibles = new ArrayList<>(List.of(2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12));
        //Act
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.esDesierto()) {
                continue;
            }
            Integer ficha = hexagono.obtenerNumeroFicha();
            fichasPosibles.remove(ficha);
        }
        //Assert
        assertTrue(fichasPosibles.isEmpty());
    }
   
    @Test
    public void test10ElHexagonoDeDesiertoNoTieneFichaAsignada() {
        //Act & Assert
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.esDesierto()) {
                assertThrows(DesiertoNoTieneFichaException.class, () -> hexagono.obtenerNumeroFicha()); 
            }
        }
    }
    
    //puede fallar alguna vez cada mucho tiempo, pero la probabilidad es baja, (como hacemos?)
    @Test
    public void test11CorrectaAsignacionAleatoriaDeFichas(){
        //Arrange
        List<Integer> fichasPosibles = List.of(2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12);
        List<Integer> fichasAsignadas = new ArrayList<Integer>();
        //Act
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.esDesierto()) {
                continue;
            }
            Integer ficha = hexagono.obtenerNumeroFicha();
            fichasAsignadas.add(ficha);
        }
        //Assert
        assertNotEquals(fichasPosibles, fichasAsignadas);
    }

    // puede fallar alguna vez cada mucho tiempo, pero la probabilidad es baja, (como hacemos?)
    @Test
    public void test12DosTablerosNoDeberianTenerMismaDistribucionDeFichas(){
        //Arrange
        Tablero tableroAux = new Tablero();
        List<Hexagono> hexagonosAux = tableroAux.obtenerHexagonos();
        List<Integer> fichasAsignadasTablero1 = new ArrayList<Integer>();
        List<Integer> fichasAsignadasTablero2 = new ArrayList<Integer>();
        //Act
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.esDesierto()) {
                continue;
            }
            Integer ficha = hexagono.obtenerNumeroFicha();
            fichasAsignadasTablero1.add(ficha);
        }
        for (Hexagono hexagono : hexagonosAux) {
            if (hexagono.esDesierto()) {
                continue;
            }
            Integer ficha = hexagono.obtenerNumeroFicha();
            fichasAsignadasTablero2.add(ficha);
        }
        //Assert
        assertNotEquals(fichasAsignadasTablero1, fichasAsignadasTablero2);
    }

    // puede fallar alguna vez cada mucho tiempo, pero la probabilidad es baja, (como hacemos?)
    @Test
    public void test13DosTablerosNoDeberianTenerMismaDistribucionDeTerrenos(){
        //Arrange
        Tablero tableroAux = new Tablero();
        List<Hexagono> hexagonosAux = tableroAux.obtenerHexagonos();
        List<TipoRecurso> RecursosAsignadosTablero1 = new ArrayList<TipoRecurso>();
        List<TipoRecurso> RecursosAsignadosTablero2 = new ArrayList<TipoRecurso>();
        //Act
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.esDesierto()) {
                continue;
            }
            TipoRecurso recurso = hexagono.obtenerRecurso();
            RecursosAsignadosTablero1.add(recurso);
        }
        for (Hexagono hexagono : hexagonosAux) {
            if (hexagono.esDesierto()) {
                continue;
            }
            TipoRecurso recurso = hexagono.obtenerRecurso();
            RecursosAsignadosTablero2.add(recurso);
        }
        //Assert
        assertNotEquals(RecursosAsignadosTablero1, RecursosAsignadosTablero2);
    }

    @Test
    public void test14NingunHexagonoTieneLaFicha7Asignada() {
        //Act & Assert
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.esDesierto()) {
                continue;
            }
            Integer ficha = hexagono.obtenerNumeroFicha();
            assertNotEquals(7, ficha);
        }
    }

}
    

