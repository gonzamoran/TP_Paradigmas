package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.Tablero;
import edu.fiuba.algo3.modelo.Hexagono;
import edu.fiuba.algo3.modelo.TipoRecurso;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

public class TableroTests {

    private List<Hexagono> hexagonos;
    private Tablero tablero;

    @BeforeEach
    public void setUp() {
        Tablero tablero = new Tablero();
        List<Hexagono> hexagonos = tablero.obtenerHexagonos();
    };
    
    @Test
    public void test01AlCrearElTableroEsteContiene19Hexagonos() {
        assertEquals(19, hexagonos.size());
    }
    
    @Test
    public void test02CorrectaAsignacionDeBosques() {
        int contadorMadera = 0;
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.obtenerRecurso() == TipoRecurso.MADERA) {
                contadorMadera++;
            }
        }
        assertEquals(4, contadorMadera);
    }
    
    @Test
    public void test03CorrectaAsignacionDeColinas() {
        int contadorLadrillo = 0;
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.obtenerRecurso() == TipoRecurso.LADRILLO) {
                contadorLadrillo++;
            }
        }
        assertEquals(3, contadorLadrillo);
    }

    @Test
    public void test04CorrectaAsignacionDePastizal() {
        int contadorMadera = 0;
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.obtenerRecurso() == TipoRecurso.LANA) {
                contadorMadera++;
            }
        }
        assertEquals(4, contadorMadera);
    }

    @Test
    public void test05CorrectaAsignacionDeTerrenosCampo(){
        int contadorCampo = 0;
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.obtenerRecurso() == TipoRecurso.GRANO) {
                contadorCampo++;
            }
        }
        assertEquals(4, contadorCampo);
    }


    @Test
    public void test06CorrectaAsignacionDeTerrenosMontana() {
        int contadorMineral = 0;
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.obtenerRecurso() == TipoRecurso.MINERAL) {
                contadorMineral++;
            }
        }
        assertEquals(3, contadorMineral);
    }

    @Test
    public void test07CorrectaAsignacionDeTerrenosDesierto() {
        int contadorDesierto = 0;
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.obtenerRecurso() == TipoRecurso.DESIERTO) {
                contadorDesierto++;
            }
        }
        assertEquals(1, contadorDesierto);
    }

    @Test
    public void test08ElHexagonoDeDesiertoNoTieneFichaAsignada() {
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.obtenerRecurso() == TipoRecurso.DESIERTO) {
                assertThrows(DesiertoNoTieneFichaException.class, hexagono.obtenerNumeroFicha()); 
            }
        }
    }

    @Test
    public void test09CorrectaAsignacionDeFichasPosibles() {
        List<Integer> fichasPosibles = List.of(2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12);
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.obtenerRecurso() == TipoRecurso.DESIERTO) {
                continue;
            }
            Integer ficha = hexagono.obtenerNumeroFicha();
            fichasPosibles.remove(ficha);
        }
        assertTrue(fichasPosibles.isEmpty());
    }
   
    @Test
    public void test10ElHexagonoDeDesiertoNoTieneFichaAsignada() {
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.obtenerRecurso() == TipoRecurso.DESIERTO) {
                assertThrows(DesiertoNoTieneFichaException.class, hexagono.obtenerNumeroFicha()); 
            }
        }
    }
    
    //puede fallar alguna vez cada mucho tiempo, pero la probabilidad es baja, (como hacemos?)
    @Test
    public void test11CorrectaAsignacionAleatoriaDeFichas(){
        List<Integer> fichasPosibles = List.of(2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12);
        List<Integer> fichasAsignadas = new ArrayList<Integer>();
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.obtenerRecurso() == TipoRecurso.DESIERTO) {
                continue;
            }
            Integer ficha = hexagono.obtenerNumeroFicha();
            fichasAsignadas.add(ficha);
        }
        assertNotEquals(fichasPosibles, fichasAsignadas);
    }

    // puede fallar alguna vez cada mucho tiempo, pero la probabilidad es baja, (como hacemos?)
    @Test
    public void test12DosTablerosNoDeberianTenerMismaDistribucionDeFichas(){
        Tablero tableroAux = new Tablero();
        List<Hexagono> hexagonosAux = tableroAux.obtenerHexagonos();
        List<Integer> fichasAsignadasTablero1 = new ArrayList<Integer>();
        List<Integer> fichasAsignadasTablero2 = new ArrayList<Integer>();
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.obtenerRecurso() == TipoRecurso.DESIERTO) {
                continue;
            }
            Integer ficha = hexagono.obtenerNumeroFicha();
            fichasAsignadasTablero1.add(ficha);
        }
        for (Hexagono hexagono : hexagonosAux) {
            if (hexagono.obtenerRecurso() == TipoRecurso.DESIERTO) {
                continue;
            }
            Integer ficha = hexagono.obtenerNumeroFicha();
            fichasAsignadasTablero2.add(ficha);
        }
        assertNotEquals(fichasAsignadasTablero1, fichasAsignadasTablero2);
    }

    // puede fallar alguna vez cada mucho tiempo, pero la probabilidad es baja, (como hacemos?)
    @Test
    public void test13DosTablerosNoDeberianTenerMismaDistribucionDeTerrenos(){
        Tablero tableroAux = new Tablero();
        List<Hexagono> hexagonosAux = tableroAux.obtenerHexagonos();
        List<TipoRecurso> RecursosAsignadosTablero1 = new ArrayList<TipoRecurso>();
        List<TipoRecurso> RecursosAsignadosTablero2 = new ArrayList<TipoRecurso>();
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.obtenerRecurso() == TipoRecurso.DESIERTO) {
                continue;
            }
            TipoRecurso recurso = hexagono.obtenerRecurso();
            RecursosAsignadosTablero1.add(recurso);
        }
        for (Hexagono hexagono : hexagonosAux) {
            if (hexagono.obtenerRecurso() == TipoRecurso.DESIERTO) {
                continue;
            }
            TipoRecurso recurso = hexagono.obtenerRecurso();
            RecursosAsignadosTablero2.add(recurso);
        }
        assertNotEquals(RecursosAsignadosTablero1, RecursosAsignadosTablero2);
    }

    @Test
    public void test14NingunHexagonoTieneLaFicha7Asignada() {
        for (Hexagono hexagono : hexagonos) {
            Integer ficha = hexagono.obtenerNumeroFicha();
            assertNotEquals(7, ficha);
        }
    }




}
    

