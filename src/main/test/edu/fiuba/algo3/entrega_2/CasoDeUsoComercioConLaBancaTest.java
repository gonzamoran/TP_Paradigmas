package edu.fiuba.algo3.entrega_2;


import edu.fiuba.algo3.modelo.cartas.*;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tiposRecurso.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Banca;
import edu.fiuba.algo3.modelo.tiposBanca.*;
import edu.fiuba.algo3.entrega_2.casosDeUso.CasoDeUsoComercioConLaBanca;



public class CasoDeUsoComercioConLaBancaTest {
    
    @Test
    public void test01Banca4a1Intercambia4RecursosDelMismoTipoPor1Elegido(){
        Jugador jugador = new Jugador("Jugador 1");
        Banca banca = new Banca4a1();

        jugador.agregarRecurso(new Ladrillo(4));

        ArrayList<Recurso> oferta = new ArrayList<>(List.of(new Ladrillo(4)));
        Recurso demanda = new Madera();
        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, banca);
        caso.comerciar(oferta, demanda);

        int cantidadMaderaEsperada = 1;
        int cantidadLadrilloEsperada = 0;

        assertEquals(cantidadLadrilloEsperada, jugador.obtenerCantidadRecurso(new Ladrillo()));
        assertEquals(cantidadMaderaEsperada, jugador.obtenerCantidadRecurso(new Madera()));
    }

    @Test
    public void test02Banca3a1Intercambia3RecursosDeCualquierRecursoPor1Elegido(){
        Jugador jugador = new Jugador("Jugador 1");
        Banca banca = new Banca3a1();

        jugador.agregarRecurso(new Ladrillo(1));
        jugador.agregarRecurso(new Madera(1));
        jugador.agregarRecurso(new Grano(1));

        ArrayList<Recurso> oferta = new ArrayList<>(List.of(new Ladrillo(1), new Madera(1), new Grano(1)));
        Recurso demanda = new Lana();
        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, banca);
        caso.comerciar(oferta, demanda);

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

        Jugador jugador = new Jugador("Jugador 1");
        Banca banca = new Banca2a1(new Lana());
        jugador.agregarRecurso(new Lana(2));
        ArrayList<Recurso> oferta = new ArrayList<>(List.of(new Lana(2)));
        Recurso demanda = new Piedra();
        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, banca);
        caso.comerciar(oferta, demanda);

        int cantidadLanaEsperada = 0;
        int cantidadPiedraEsperada = 1;
        assertEquals(cantidadLanaEsperada, jugador.obtenerCantidadRecurso(new Lana()));
        assertEquals(cantidadPiedraEsperada, jugador.obtenerCantidadRecurso(new Piedra()));
    }

    @Test
    public void test04Banca4a1NoPermiteComerciarSiNoHayRecursosSuficientes(){
        Jugador jugador = new Jugador("Jugador 1");
        Banca banca = new Banca4a1();
        jugador.agregarRecurso(new Ladrillo(3));
        ArrayList<Recurso> oferta = new ArrayList<>(List.of(new Ladrillo(4)));
        Recurso demanda = new Madera();
        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, banca);
        assertThrows(IllegalArgumentException.class, () -> {
            caso.comerciar(oferta, demanda);
        });
    }

    @Test
    public void test05Banca4a1NoPermiteComerciarSiLosRecursosNoSonDelMismoTipo(){
        Jugador jugador = new Jugador("Jugador 1");
        Banca banca = new Banca4a1();
        jugador.agregarRecurso(new Ladrillo(2));
        jugador.agregarRecurso(new Madera(2));
        ArrayList<Recurso> oferta = new ArrayList<>(List.of(new Ladrillo(2), new Madera(2)));
        Recurso demanda = new Grano();
        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, banca);
        assertThrows(IllegalArgumentException.class, () -> {
            caso.comerciar(oferta, demanda);
        });
    }

    @Test
    public void test06Banca3a1NoPermiteComerciarSiNoHayRecursosSuficientes(){
        Jugador jugador = new Jugador("Jugador 1");
        Banca banca = new Banca3a1();
        jugador.agregarRecurso(new Ladrillo(1));
        jugador.agregarRecurso(new Madera(1));
        ArrayList<Recurso> oferta = new ArrayList<>(List.of(new Ladrillo(2), new Madera(1)));
        Recurso demanda = new Grano();
        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, banca);
        assertThrows(IllegalArgumentException.class, () -> {
            caso.comerciar(oferta, demanda);
        });
    }

    @Test
    public void test07Banca2a1NoPermiteComerciarSiNoHayRecursosSuficientes(){
        Jugador jugador = new Jugador("Jugador 1");
        Banca banca = new Banca2a1(new Lana());
        jugador.agregarRecurso(new Lana(1));
        ArrayList<Recurso> oferta = new ArrayList<>(List.of(new Lana(2)));
        Recurso demanda = new Madera();
        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, banca);
        assertThrows(IllegalArgumentException.class, () -> {
            caso.comerciar(oferta, demanda);
        });
    }

    @Test
    public void test08Banca2a1NoPermiteComerciarSiLosRecursosNoSonDelTipoHabilitado(){
        Jugador jugador = new Jugador("Jugador 1");
        Banca banca = new Banca2a1(new Lana());
        jugador.agregarRecurso(new Ladrillo(2));
        ArrayList<Recurso> oferta = new ArrayList<>(List.of(new Ladrillo(2)));
        Recurso demanda = new Madera();
        CasoDeUsoComercioConLaBanca caso = new CasoDeUsoComercioConLaBanca(jugador, banca);
        assertThrows(IllegalArgumentException.class, () -> {
            caso.comerciar(oferta, demanda);
        });
    }
}

//FALTA: 
//HAY QUE VERIFICAR QUE EL JUGADOR SOLO PUEDE INTERCAMBIAR CUANDO TIENE UN POBLADO/CIUDAD EN LAS ARISTAS QUE CONECTAN AL PUERTO
//HAY QUE IMPLEMENTAR LAS CARTAS DE DESARROLLO RESTANTES
//HAY QUE IMPLEMENTAR LAS CARTAS DE PROGRESO(LAS QUE SE OTORGAN DINAMICAMENTE)
//HAY QUE IMPLEMENTAR LAS CONDICIONES DE VICTORIA
//HAY QUE IMPLEMENTAR EL SISTEMA DE TURNOS
