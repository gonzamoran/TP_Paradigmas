package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.acciones.*;
import edu.fiuba.algo3.modelo.cartas.MazoCartasDesarrollo;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.*;
import edu.fiuba.algo3.modelo.construcciones.Construccion;
import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.tablero.*;
import edu.fiuba.algo3.modelo.tiposRecurso.*;

import java.util.*;


public class GestorDeTurnos {
    private int turnoActual;
    private int indiceJugadorActual;
    private ArrayList<Jugador> jugadores;
    private Tablero tablero;
    private ProveedorDeDatos proveedor;
    private Dados dados;
    private MazoCartasDesarrollo mazo;
    private Ladron ladron;
    private java.util.concurrent.CompletableFuture<Void> finFaseConstruccion;

    public GestorDeTurnos(ProveedorDeDatos proveedor, ArrayList<Hexagono> hexagonos, ArrayList<Produccion> producciones) {
        this.turnoActual = 0;
        this.indiceJugadorActual = 0;
        this.proveedor = proveedor;
        CasoDeUsoArmarTablero casoTablero = new CasoDeUsoArmarTablero(hexagonos, producciones);
        Tablero tablero = casoTablero.armarTablero();
        this.tablero = tablero;
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
        CasoDeUsoColocacionInicial casoColocacion = new CasoDeUsoColocacionInicial(tablero);
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugadorActual = jugadores.get(i);
            Coordenadas coordPoblado = proveedor.pedirCoordenadasAlUsuario();
            casoColocacion.colocarConstruccionInicial(coordPoblado, new Poblado(), jugadorActual);
            Coordenadas extremoCarretera = proveedor.pedirCoordenadasAlUsuario();
            casoColocacion.colocarCarreteraInicial(coordPoblado, extremoCarretera, jugadorActual);
            proveedor.notificarCambioInventario(jugadorActual, obtenerInventarioJugador(jugadorActual));
            proveedor.notificarCambiosPuntosVictoria(jugadorActual, jugadorActual.calculoPuntosVictoria());
        }
        for (int i = jugadores.size() -1 ; i >= 0 ; i--) {
            Jugador jugadorActual = jugadores.get(i);
            Coordenadas coordPoblado = proveedor.pedirCoordenadasAlUsuario();
            ArrayList<Recurso> recursos = casoColocacion.colocarConstruccionInicial(coordPoblado, new Poblado(), jugadorActual);
            for (Recurso recurso : recursos){
                jugadorActual.agregarRecurso(recurso);
            }
            Coordenadas extremoCarretera = proveedor.pedirCoordenadasAlUsuario();
            casoColocacion.colocarCarreteraInicial(coordPoblado, extremoCarretera, jugadorActual);
            proveedor.notificarCambioInventario(jugadorActual, obtenerInventarioJugador(jugadorActual));
            proveedor.notificarCambiosPuntosVictoria(jugadorActual, jugadorActual.calculoPuntosVictoria());
        }
    }

    private void iniciarFaseLanzamientoDeDados(Jugador jugadorActual){
        CasoDeUsoGirarElDado casoDado = new CasoDeUsoGirarElDado(tablero, ladron);
        var resultado = casoDado.tirarDado(dados);
        casoDado.resolverResultado(resultado, jugadorActual, proveedor);
    }

    private void iniciarFaseConstruccionYComercio(Jugador jugadorActual){
        finFaseConstruccion = new java.util.concurrent.CompletableFuture<>();
        finFaseConstruccion.join();
    }

    public void finalizarFaseConstruccion() {
        if (finFaseConstruccion != null && !finFaseConstruccion.isDone()) {
            finFaseConstruccion.complete(null);
        }
    }

    public void construir(Coordenadas coordenadas, Construccion construccion) {
        Jugador jugadorActual = obtenerJugadorActual();
        if (jugadorActual == null) return;
        CasoDeUsoConstruccion casoConstruir = new CasoDeUsoConstruccion(tablero, jugadores);
        casoConstruir.construirEn(coordenadas, construccion, jugadorActual);
        proveedor.notificarCambioInventario(jugadorActual, obtenerInventarioJugador(jugadorActual));
        proveedor.notificarCambiosPuntosVictoria(jugadorActual, jugadorActual.calculoPuntosVictoria());
    }

    public void comprarCartaDesarrollo() {
        Jugador jugadorActual = obtenerJugadorActual();
        if (jugadorActual == null) return;
        CasoDeUsoSacarCartasDelMazoDeDesarrollo casoDeUsoCartas = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        casoDeUsoCartas.comprarCartaDesarrollo(mazo, jugadorActual, turnoActual);
        proveedor.notificarCambioInventario(jugadorActual, obtenerInventarioJugador(jugadorActual));
    }

    public void comerciarConLaBanca() {
        Jugador jugadorActual = obtenerJugadorActual();
        if (jugadorActual == null) return;
        CasoDeUsoComercioConLaBanca casoComercioBanca = new CasoDeUsoComercioConLaBanca(jugadorActual, tablero);
        ArrayList<Banca> bancasDisponibles = casoComercioBanca.obtenerBancasDisponibles();
        Banca bancaSeleccionada = proveedor.pedirTipoDeBancaAlUsuario(bancasDisponibles);
        ArrayList<Recurso> oferta = proveedor.pedirOfertaAlUsuario();
        ArrayList<Recurso> demanda = proveedor.pedirDemandaAlUsuario();
        casoComercioBanca.comerciar(oferta, demanda, bancaSeleccionada);
        proveedor.notificarCambioInventario(jugadorActual, obtenerInventarioJugador(jugadorActual));
    }

    public void comerciarConJugador() {
        Jugador jugadorActual = obtenerJugadorActual();
        if (jugadorActual == null) return;
        Jugador jugadorDestino = proveedor.pedirJugadorParaComerciar(jugadorActual, jugadores);
        CasoDeUsoIntercambio caso = new CasoDeUsoIntercambio(jugadorActual, jugadorDestino);

        ArrayList<Recurso> oferta = proveedor.pedirOfertaAlUsuario();
        ArrayList<Recurso> demanda = proveedor.pedirDemandaAlUsuario();

        if (proveedor.aceptaIntercambio(jugadorDestino, oferta, demanda)) {
            caso.ejecutarIntercambio(oferta, demanda);
            proveedor.notificarCambioInventario(jugadorActual, obtenerInventarioJugador(jugadorActual));
            proveedor.notificarCambioInventario(jugadorDestino, obtenerInventarioJugador(jugadorDestino));
        }
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
                return;
            }
        }
    }
    
    public void jugar(){
        this.inicializarJuego(); 
        this.comenzarFaseInicial(); 
        while(!this.terminoElJuego()){ 
            var jugadorActual = jugadores.get(indiceJugadorActual);
            proveedor.notificarCambioTurno(jugadorActual, indiceJugadorActual, turnoActual);
            
            this.iniciarFaseLanzamientoDeDados(jugadorActual); 
            this.iniciarFaseConstruccionYComercio(jugadorActual); 
            if (this.terminoElJuego()){ 
                break; 
            }
            this.iniciarFaseUsoDeCartasDesarrollo(jugadorActual); 
            

            this.avanzarTurno();
        }
        this.finalizarJuego(); 
    }
    
    
    public Jugador obtenerJugadorActual() {
        if (jugadores != null && !jugadores.isEmpty() && indiceJugadorActual >= 0 && indiceJugadorActual < jugadores.size()) {
            return jugadores.get(indiceJugadorActual);
        }
        return null;
    }
    

    public java.util.Map<Recurso, Integer> obtenerInventarioJugador(Jugador jugador) {
        ArrayList<Recurso> recursos = new ArrayList<Recurso>(List.of(
            new Madera(), new Ladrillo(), new Grano(), new Lana(), new Piedra()
        ));
        Map<Recurso, Integer> inventario = new HashMap<>();
        for (Recurso recurso : recursos) {
            int cantidad = jugador.obtenerCantidadRecurso(recurso);
            if (cantidad > 0) {
                inventario.put(recurso, cantidad);
            }
        }
        return inventario;
    }
    
    public int obtenerPuntosVictoriaJugadorActual() {
        Jugador jugador = obtenerJugadorActual();
        if (jugador == null) {
            return 0;
        }
        return jugador.calculoPuntosVictoria();
    }
    

    public int obtenerTurnoActual() {
        return turnoActual;
    }
    

    public ArrayList<Jugador> obtenerJugadores() {
        return jugadores;
    }    

    public int obtenerIndiceJugadorActual() {
        return indiceJugadorActual;
    }
}
