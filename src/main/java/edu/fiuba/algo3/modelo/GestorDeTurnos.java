package edu.fiuba.algo3.modelo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Ladron;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.*;
import edu.fiuba.algo3.modelo.tablero.Produccion;
import edu.fiuba.algo3.modelo.acciones.*;
import edu.fiuba.algo3.modelo.construcciones.*;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.ContextoCartaDesarrollo;

import edu.fiuba.algo3.modelo.cartas.MazoCartasDesarrollo;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.*;


public class GestorDeTurnos {
    private int turnoActual;
    private int indiceJugadorActual;
    private ArrayList<Jugador> jugadores;
    private Tablero tablero;
    private boolean terminoElJuego;
    private ProveedorDeDatos proveedor;
    private Dados dados;
    private MazoCartasDesarrollo mazo;
    private Ladron ladron;

    public GestorDeTurnos(ProveedorDeDatos proveedor) {
        this.turnoActual = 0;
        this.indiceJugadorActual = 0;
        this.proveedor = proveedor;
    }

    public void avanzarTurno() {
        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();
        if (indiceJugadorActual == 0) {
            turnoActual++;
        }
        turnoActual++;
    }

    public boolean terminoElJuego() {
        for (Jugador jugador : jugadores) {
            if (jugador.calculoPuntosVictoria() >= 10) {
                return true;
            }
        }
        return false;
    }

    public void inicializarJuego(){
        ArrayList<Hexagono> hexagonos = new ArrayList<Hexagono>(List.of(
            new Desierto(),
            new Campo(), new Campo(), new Campo(), new Campo(),
            new Bosque(), new Bosque(), new Bosque(), new Bosque(),
            new Pastizal(), new Pastizal(), new Pastizal(), new Pastizal(),
            new Colina(), new Colina(), new Colina(),
            new Montana(), new Montana(), new Montana()
        ));
        ArrayList<Produccion> numeros = new ArrayList<Produccion>(List.of(
                new Produccion(2),
                new Produccion(3),
                new Produccion(3),
                new Produccion(4),
                new Produccion(4),
                new Produccion(5),
                new Produccion(5),
                new Produccion(6),
                new Produccion(6),
                new Produccion(8),
                new Produccion(8),
                new Produccion(9),
                new Produccion(9),
                new Produccion(10),
                new Produccion(10),
                new Produccion(11),
                new Produccion(11),
                new Produccion(12)
        ));
        Collections.shuffle(hexagonos);
        Collections.shuffle(numeros);

        //inicializar el ladron y pasarselo a tablero?

        CasoDeUsoArmarTablero casoTablero = new CasoDeUsoArmarTablero(hexagonos, numeros);
        Tablero tablero = casoTablero.armarTablero();
        this.tablero = tablero;
        int cantidadDeJugadores = proveedor.pedirCantidadDeJugadoresAlUsuario();
        this.jugadores = new ArrayList<Jugador>();
        for (int i = 0; i < cantidadDeJugadores; i++) {
            Jugador jugador = proveedor.pedirNombreJugadorAlUsuario(cantidadDeJugadores);
            this.jugadores.add(jugador);
        }
        this.dados = new Dados();
        ArrayList<CartasDesarrollo> cartas = new ArrayList<CartasDesarrollo>(List.of(

            new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(),
            new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(),
            new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(),

            new CartaConstruccionCarretera(), new CartaConstruccionCarretera(), new CartaConstruccionCarretera(),
            new CartaMonopolio(), new CartaMonopolio(), new CartaMonopolio(),
            new CartaDescubrimiento(), new CartaDescubrimiento(), new CartaDescubrimiento(),

            new CartaPuntoVictoria(), new CartaPuntoVictoria(), new CartaPuntoVictoria(),
            new CartaPuntoVictoria(), new CartaPuntoVictoria()
        ));

        MazoCartasDesarrollo mazoCartasDesarrollo = new MazoCartasDesarrollo(cartas);
        this.mazo = mazoCartasDesarrollo;

        Hexagono desierto = tablero.obtenerDesierto();
        this.ladron = new Ladron(desierto);
    }

    private void comenzarFaseInicial(){
        // primer turno
        CasoDeUsoColocacionInicial casoColocacion = new CasoDeUsoColocacionInicial(tablero);
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugadorActual = jugadores.get(i);
            Coordenadas coordPoblado = proveedor.pedirCoordenadasAlUsuario();
            casoColocacion.colocarConstruccionInicial(coordPoblado, new Poblado(), jugadorActual);
            Coordenadas extremoCarretera = proveedor.pedirCoordenadasAlUsuario();
            casoColocacion.colocarCarreteraInicial(coordPoblado, extremoCarretera, jugadorActual);
        }
        // segundo turno
        for (int i = jugadores.size() -1 ; i >= 0 ; i--) {
            Jugador jugadorActual = jugadores.get(i);
            Coordenadas coordPoblado = proveedor.pedirCoordenadasAlUsuario();
            ArrayList<Recurso> recursos = casoColocacion.colocarConstruccionInicial(coordPoblado, new Poblado(), jugadorActual);
            for (Recurso recurso : recursos){
                jugadorActual.agregarRecurso(recurso);
            }
            Coordenadas extremoCarretera = proveedor.pedirCoordenadasAlUsuario();
            casoColocacion.colocarCarreteraInicial(coordPoblado, extremoCarretera, jugadorActual);
        }
    }

    private void iniciarFaseLanzamientoDeDados(Jugador jugadorActual){
        CasoDeUsoGirarElDado casoDado = new CasoDeUsoGirarElDado(tablero, ladron);
        var resultado = casoDado.tirarDado(dados);
        casoDado.resolverResultado(resultado, jugadorActual, proveedor);
    }

    private void iniciarFaseConstruccionYComercio(Jugador jugadorActual){
        boolean terminoFase = false;
        while (!terminoFase){
            String accion = proveedor.pedirSiguienteAccionARealizarAlUsuario();
            switch (accion) {
                case "TERMINAR_TURNO":
                    terminoFase = true;
                    break;
                case "CONSTRUIR":
                    CasoDeUsoConstruccion casoConstruir = new CasoDeUsoConstruccion(tablero, jugadores);
                    Coordenadas coordenadas = proveedor.pedirCoordenadasAlUsuario();
                    Construccion construccion = proveedor.pedirTipoDeConstruccionAlUsuario();
                    casoConstruir.construirEn(coordenadas, construccion, jugadorActual);
                    break;
                case "COMPRAR_CARTA_DESARROLLO":
                    CasoDeUsoSacarCartasDelMazoDeDesarrollo casoDeUsoCartas = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
                    casoDeUsoCartas.comprarCartaDesarrollo(mazo, jugadorActual, turnoActual);
                    break;
                case "COMERCIAR_BANCA":
                    while(true){
                        this.comercioConLaBanca(jugadorActual);
                        if (!proveedor.quiereSeguirComerciando()) {
                            break;
                        }
                    }
                    break;
                case "COMERCIAR_JUGADOR":
                    while (true) {
                        this.comercioConJugador(jugadorActual);
                        if (!proveedor.quiereSeguirComerciando()) {
                            break;
                        }
                    }
                    break;
                default:
                    terminoFase = true;
                    break;
            }
        }
    }

    private void comercioConLaBanca(Jugador jugadorActual){
        CasoDeUsoComercioConLaBanca casoComercioBanca = new CasoDeUsoComercioConLaBanca(jugadorActual, tablero);
        ArrayList<Banca> bancasDisponibles = casoComercioBanca.obtenerBancasDisponibles();
        Banca bancaSeleccionada = proveedor.pedirTipoDeBancaAlUsuario(bancasDisponibles);
        ArrayList<Recurso> oferta = proveedor.pedirOfertaAlUsuario();
        ArrayList<Recurso> demanda = proveedor.pedirDemandaAlUsuario();
        casoComercioBanca.comerciar( oferta, demanda, bancaSeleccionada);
    }

    private void comercioConJugador(Jugador jugadorActual){
        Jugador jugador2 = proveedor.pedirJugadorParaComerciar(jugadorActual, jugadores);
        CasoDeUsoIntercambio caso = new CasoDeUsoIntercambio(jugadorActual, jugador2);

        ArrayList<Recurso> oferta = proveedor.pedirOfertaAlUsuario();
        ArrayList<Recurso> demanda = proveedor.pedirDemandaAlUsuario();

        if (proveedor.aceptaIntercambio(jugador2, oferta, demanda)) {
            caso.ejecutarIntercambio(oferta, demanda);
        }
    }

    private boolean quiereConstruir(String accion){
        return new String("CONSTRUIR").equals(accion);
    }

    private void iniciarFaseUsoDeCartasDesarrollo(Jugador jugador){
        while(proveedor.quiereUsarCartaDesarrollo(jugador)){
            ArrayList<CartasDesarrollo> cartasDelJugador = jugador.obtenerCartasDeDesarrollo();
            ArrayList<CartasDesarrollo> cartasJugables = new ArrayList<CartasDesarrollo>();
            ContextoCartaDesarrollo contexto = new ContextoCartaDesarrollo(jugador, jugadores, turnoActual, tablero, ladron);
            for (CartasDesarrollo carta : cartasDelJugador){
                if(carta.esJugable(new ContextoCartaDesarrollo(jugador, jugadores, turnoActual, tablero, ladron))){
                    cartasJugables.add(carta);
                }
            }
            CartasDesarrollo cartaElegida = proveedor.elegirCartaDesarrolloParaUsar(jugador, cartasDelJugador, cartasJugables);
            if (cartaElegida == null){
                return;
            }
            
            CasoDeUsoSacarCartasDelMazoDeDesarrollo casoDeUsoCartas = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
            casoDeUsoCartas.usarCartaDesarrollo(cartaElegida, jugador, contexto, proveedor);
        }
    }
    
    private void finalizarJuego(){
        for(Jugador jugador : jugadores){
            if(jugador.calculoPuntosVictoria() >= 10){
                proveedor.anunciarGanador(jugador);
                this.terminoElJuego = true;

                return;
            }
        }
    }
    public void jugar(){
        this.inicializarJuego(); 
        this.comenzarFaseInicial(); 
        while(!this.terminoElJuego()){ 
            var jugadorActual = jugadores.get(indiceJugadorActual); 
            this.iniciarFaseLanzamientoDeDados(jugadorActual); 
            this.iniciarFaseConstruccionYComercio(jugadorActual); 
            if (this.terminoElJuego()){ 
                terminoElJuego = true; 
                break; 
            }
            this.iniciarFaseUsoDeCartasDesarrollo(jugadorActual); 
            

            this.avanzarTurno();
        }
        this.finalizarJuego(); 
    }
}
