package edu.fiuba.algo3.entrega_2;


import edu.fiuba.algo3.modelo.cartas.*;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.excepciones.ComercioInvalidoException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Banca;
import edu.fiuba.algo3.modelo.tiposBanca.*;
import edu.fiuba.algo3.modelo.acciones.CasoDeUsoComercioConLaBanca;


public class CasoDeUsoComercioConLaBancaTest {
    
    @Test
    public void test01Banca4a1Intercambia4RecursosDelMismoTipoPor1Elegido(){
        Tablero tablero = new Tablero();
        Banca banca = new Banca4a1();
        Jugador jugador = new Jugador("Jugador 1");

        jugador.agregarRecurso(new Ladrillo(4));

        ArrayList<Recurso> oferta = new ArrayList<>(List.of(new Ladrillo(4)));
        ArrayList<Recurso> demanda = new ArrayList<>(List.of(new Madera(1)));
        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, tablero);
        caso.comerciar(oferta, demanda, banca);

        int cantidadMaderaEsperada = 1;
        int cantidadLadrilloEsperada = 0;

        assertEquals(cantidadLadrilloEsperada, jugador.obtenerCantidadRecurso(new Ladrillo()));
        assertEquals(cantidadMaderaEsperada, jugador.obtenerCantidadRecurso(new Madera()));
    }

    @Test
    public void test02Banca3a1Intercambia3RecursosDeCualquierRecursoPor1Elegido(){
        Tablero tablero = new Tablero();
        Banca banca = new Banca3a1();
        Jugador jugador = new Jugador("Jugador 1");

        jugador.agregarRecurso(new Ladrillo(1));
        jugador.agregarRecurso(new Madera(1));
        jugador.agregarRecurso(new Grano(1));

        ArrayList<Recurso> oferta = new ArrayList<>(List.of(new Ladrillo(1), new Madera(1), new Grano(1)));
        ArrayList<Recurso> demanda = new ArrayList<>(List.of(new Lana(1)));
        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, tablero);
        caso.comerciar(oferta, demanda, banca);

        int cantidadMaderaEsperada = 0;
        int cantidadLadrilloEsperada = 0;
        int cantidadGranoEsperada = 0;
        int cantidadLanaEsperada = 1;

        assertEquals(cantidadMaderaEsperada, jugador.obtenerCantidadRecurso(new Madera()));
        assertEquals(cantidadLadrilloEsperada, jugador.obtenerCantidadRecurso(new Ladrillo()));
        assertEquals(cantidadGranoEsperada, jugador.obtenerCantidadRecurso(new Grano()));
        assertEquals(cantidadLanaEsperada, jugador.obtenerCantidadRecurso(new Lana()));
    }

    @Test
    public void test03Banca2a1Intercambia2RecursosDelTipoHabilitadoPor1Elegido(){
        Tablero tablero = new Tablero();
        Banca banca = new Banca2a1(new Lana());
        Jugador jugador = new Jugador("Jugador 1");

        jugador.agregarRecurso(new Lana(2));
        
        ArrayList<Recurso> oferta = new ArrayList<>(List.of(new Lana(2)));

        ArrayList<Recurso> demanda = new ArrayList<>(List.of(new Piedra(1)));

        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, tablero);
        caso.comerciar(oferta, demanda, banca);


        int cantidadLanaEsperada = 0;
        int cantidadPiedraEsperada = 1;
        assertEquals(cantidadLanaEsperada, jugador.obtenerCantidadRecurso(new Lana()));
        assertEquals(cantidadPiedraEsperada, jugador.obtenerCantidadRecurso(new Piedra()));
    }

    @Test
    public void test04Banca4a1NoPermiteComerciarSiNoHayRecursosSuficientes(){
        Tablero tablero = new Tablero();
        Banca banca = new Banca4a1();
        Jugador jugador = new Jugador("Jugador 1");

        jugador.agregarRecurso(new Ladrillo(3));

        ArrayList<Recurso> oferta = new ArrayList<>(List.of(new Ladrillo(4)));
        ArrayList<Recurso> demanda = new ArrayList<>(List.of(new Madera(1)));
        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, tablero);

        
        assertThrows(ComercioInvalidoException.class, () -> {
            caso.comerciar(oferta, demanda, banca);
        });
    }

    @Test
    public void test05Banca4a1NoPermiteComerciarSiLosRecursosNoSonDelMismoTipo(){
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Jugador 1");
        Banca banca = new Banca4a1();
        jugador.agregarRecurso(new Ladrillo(2));
        jugador.agregarRecurso(new Madera(2));

        ArrayList<Recurso> oferta = new ArrayList<>(List.of(new Ladrillo(2), new Madera(2)));
        ArrayList<Recurso> demanda = new ArrayList<>(List.of(new Grano(1)));

        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, tablero);

        assertThrows(ComercioInvalidoException.class, () -> {
            caso.comerciar(oferta, demanda, banca);
        });
    }

    @Test
    public void test06Banca3a1NoPermiteComerciarSiNoHayRecursosSuficientes(){
        Tablero tablero = new Tablero();
        Banca banca = new Banca3a1();
        Jugador jugador = new Jugador("Jugador 1");

        jugador.agregarRecurso(new Ladrillo(1));
        jugador.agregarRecurso(new Madera(1));

        ArrayList<Recurso> oferta = new ArrayList<>(List.of(new Ladrillo(2), new Madera(1)));
        ArrayList<Recurso> demanda = new ArrayList<>(List.of(new Grano(1)));

        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, tablero);


        assertThrows(ComercioInvalidoException.class, () -> {
            caso.comerciar(oferta, demanda, banca);
        });
    }

    @Test
    public void test07Banca2a1NoPermiteComerciarSiNoHayRecursosSuficientes(){
        Tablero tablero = new Tablero();
        Banca banca = new Banca2a1(new Lana());
        Jugador jugador = new Jugador("Jugador 1");

        jugador.agregarRecurso(new Lana(1));

        ArrayList<Recurso> oferta = new ArrayList<>(List.of(new Lana(2)));
        ArrayList<Recurso> demanda = new ArrayList<>(List.of(new Madera(1)));

        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, tablero);


        assertThrows(ComercioInvalidoException.class, () -> {
            caso.comerciar(oferta, demanda, banca);
        });
    }

    @Test
    public void test08Banca2a1NoPermiteComerciarSiLosRecursosNoSonDelTipoHabilitado(){
        Tablero tablero = new Tablero();
        Banca banca = new Banca2a1(new Lana());
        Jugador jugador = new Jugador("Jugador 1");

        jugador.agregarRecurso(new Ladrillo(2));
        
        
        ArrayList<Recurso> oferta = new ArrayList<>(List.of(new Ladrillo(2)));
        ArrayList<Recurso> demanda = new ArrayList<>(List.of(new Madera(1)));
        
        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, tablero);
        
        
        assertThrows(ComercioInvalidoException.class, () -> {
            caso.comerciar(oferta, demanda, banca);
        });
    }
    
    @Test
    public void test09ObtenerLasBancasDisponiblesDeUnJugadorSinConstruccionesDevuelveBanca4a1(){
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Jugador 1");

        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, tablero);
        List<Banca> bancasDisponibles = caso.obtenerBancasDisponibles();

        List<Banca> bancasEsperadas = new ArrayList<>((List.of(
            new Banca4a1()
        )));

        assertEquals(bancasEsperadas, bancasDisponibles);
    }

    @Test
    public void test10ObtenerLasBancasDisponiblesDeUnJugadorConConstruccionEnPuertoDevuelveBancaCorrespondiente(){
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Jugador 1");
        tablero.colocarConstruccionInicial(new Poblado(), new Coordenadas(0,1), jugador);
        
        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, tablero);
        
        List<Banca> bancasDisponibles = caso.obtenerBancasDisponibles();

        List<Banca> bancasEsperadas = new ArrayList<>((List.of(
            new Banca3a1(),
            new Banca4a1()
        )));
        assertEquals(bancasEsperadas, bancasDisponibles);
    }

    @Test
    public void test11ObtenerLasBancasDisponiblesDeUnJugadorConConstruccionEnPuerto2a1DevuelveBancaCorrespondiente(){
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Jugador 1");

        tablero.colocarConstruccionInicial(new Poblado(), new Coordenadas(3,0), jugador);
        
        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, tablero);
        
        List<Banca> bancasDisponibles = caso.obtenerBancasDisponibles();

        List<Banca> bancasEsperadas = new ArrayList<>((List.of(
            new Banca2a1(new Madera()),
            new Banca4a1()
        )));

        assertEquals(bancasEsperadas, bancasDisponibles);
    }


    @Test
    public void test12ObtenerBancasDisponiblesYComerciarConEl(){
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador("Jugador 1");
        jugador.agregarRecurso(new Madera(2));
        tablero.colocarConstruccionInicial(new Poblado(), new Coordenadas(3,0), jugador);
        
        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, tablero);
        
        List<Banca> bancasDisponibles = caso.obtenerBancasDisponibles();

        List<Banca> bancasEsperadas = new ArrayList<>((List.of(
            new Banca2a1(new Madera()),
            new Banca4a1()
        )));

        assertEquals(bancasEsperadas, bancasDisponibles);

        ArrayList<Recurso> oferta = new ArrayList<>(List.of(new Madera(2)));
        ArrayList<Recurso> demanda = new ArrayList<>(List.of(new Piedra(1)));

        caso.comerciar(oferta, demanda, bancasDisponibles.get(0));

        int cantidadMaderaEsperada = 0;
        int cantidadPiedraEsperada = 1;
        assertEquals(cantidadMaderaEsperada, jugador.obtenerCantidadRecurso(new Madera()));
        assertEquals(cantidadPiedraEsperada, jugador.obtenerCantidadRecurso(new Piedra()));
    }
}