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
    private Dados dados;
    private int ultimoResultadoDados;
    private MazoCartasDesarrollo mazo;
    private Ladron ladron;
    private int colocacionesInicialesRealizadas = 0;
    
    public GestorDeTurnos(ArrayList<Hexagono> hexagonos, ArrayList<Produccion> producciones) {
        this.turnoActual = 0;
        this.indiceJugadorActual = 0;
        CasoDeUsoArmarTablero casoTablero = new CasoDeUsoArmarTablero(hexagonos, producciones);
        this.tablero = casoTablero.armarTablero();
        this.dados = new Dados();
        this.ultimoResultadoDados = 0;
    }
    
    public void inicializarJuego(List<String> nombresJugadores) {
        this.jugadores = new ArrayList<>();
        for (String nombre : nombresJugadores) {
            this.jugadores.add(new Jugador(nombre));
        }
        this.colocacionesInicialesRealizadas = 0;
        
        ArrayList<CartasDesarrollo> cartas = new ArrayList<>(List.of(
            new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(),
            new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(),
            new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new CartaCaballero(),
            new CartaConstruccionCarretera(), new CartaConstruccionCarretera(), new CartaConstruccionCarretera(),
            new CartaMonopolio(), new CartaMonopolio(), new CartaMonopolio(),
            new CartaDescubrimiento(), new CartaDescubrimiento(), new CartaDescubrimiento(),
            new CartaPuntoVictoria(), new CartaPuntoVictoria(), new CartaPuntoVictoria(),
            new CartaPuntoVictoria(), new CartaPuntoVictoria()
        ));
        
        this.mazo = new MazoCartasDesarrollo(cartas);
        Hexagono desierto = tablero.obtenerDesierto();
        this.ladron = new Ladron(desierto);
    }

    public void colocacionInicial(Coordenadas coordPoblado, Coordenadas coordCarretera) {
        Jugador jugadorActual = obtenerJugadorActual();
        if (jugadorActual == null) return;
        
        CasoDeUsoColocacionInicial casoColocacion = new CasoDeUsoColocacionInicial(tablero);
        ArrayList<Recurso> recursos = casoColocacion.colocarConstruccionInicial(coordPoblado, new Poblado(), jugadorActual);
        
        if (jugadores != null && colocacionesInicialesRealizadas >= jugadores.size()) {
            for (Recurso recurso : recursos) {
                jugadorActual.agregarRecurso(recurso);
            }
        }
        casoColocacion.colocarCarreteraInicial(coordPoblado, coordCarretera, jugadorActual);
        colocacionesInicialesRealizadas++;
        notificarCambios();
    }

    public int tirarDados() {
        Jugador jugadorActual = obtenerJugadorActual();
        if (jugadorActual == null) return 0;
        
        CasoDeUsoGirarElDado casoGirarDado = new CasoDeUsoGirarElDado(tablero, ladron);
        int resultado = casoGirarDado.tirarDado(dados);
        this.ultimoResultadoDados = resultado;
        
        notificarCambios();
        return resultado;
    }
    
    public void resolverResultadoDado(int resultado) {
        Jugador jugadorActual = obtenerJugadorActual();
        if (jugadorActual == null) return;
        
        CasoDeUsoGirarElDado casoGirarDado = new CasoDeUsoGirarElDado(tablero, ladron);
        casoGirarDado.resolverResultado(resultado, jugadorActual);
        notificarCambios();
    }
    
    public void resolverResultadoDadoSiete(int resultado, Coordenadas destino, Jugador jugadorARobar) {
        Jugador jugadorActual = obtenerJugadorActual();
        if (jugadorActual == null) return;
        
        CasoDeUsoGirarElDado casoGirarDado = new CasoDeUsoGirarElDado(tablero, ladron);
        casoGirarDado.resolverResultado(resultado, jugadorActual, destino, jugadorARobar);
        notificarCambios();
    }

    public int obtenerUltimoResultadoDados() {
        return ultimoResultadoDados;
    }
    
    public Tablero obtenerTablero() {
        return tablero;
    }
    
    public Ladron obtenerLadron() {
        return ladron;
    }

    public void construir(Coordenadas coordenadas, Construccion construccion) {
        Jugador jugadorActual = obtenerJugadorActual();
        if (jugadorActual == null) return;
        
        CasoDeUsoConstruccion casoConstruir = new CasoDeUsoConstruccion(tablero, jugadores);
        casoConstruir.construirEn(coordenadas, construccion, jugadorActual);
        notificarCambios();
    }

    public void construirCarretera(Coordenadas origen, Coordenadas destino) {
        Jugador jugadorActual = obtenerJugadorActual();
        if (jugadorActual == null) return;

        CasoDeUsoConstruccion casoConstruir = new CasoDeUsoConstruccion(tablero, jugadores);
        casoConstruir.construirCarretera(origen, destino, jugadorActual);
        notificarCambios();
    }

    public void comprarCartaDesarrollo() {
        Jugador jugadorActual = obtenerJugadorActual();
        if (jugadorActual == null) return;
        
        CasoDeUsoSacarCartasDelMazoDeDesarrollo casoDeUsoCartas = new CasoDeUsoSacarCartasDelMazoDeDesarrollo();
        casoDeUsoCartas.comprarCartaDesarrollo(mazo, jugadorActual, turnoActual);
        notificarCambios();
    }

    public void comerciarConLaBanca(Banca banca, ArrayList<Recurso> oferta, ArrayList<Recurso> demanda) {
        Jugador jugadorActual = obtenerJugadorActual();
        if (jugadorActual == null) return;
        
        CasoDeUsoComercioConLaBanca casoComercioBanca = new CasoDeUsoComercioConLaBanca(jugadorActual, tablero);
        casoComercioBanca.comerciar(oferta, demanda, banca);
        notificarCambios();
    }
    
    public ArrayList<Banca> obtenerBancasDisponibles() {
        Jugador jugadorActual = obtenerJugadorActual();
        if (jugadorActual == null) return new ArrayList<>();
        
        CasoDeUsoComercioConLaBanca casoComercioBanca = new CasoDeUsoComercioConLaBanca(jugadorActual, tablero);
        return casoComercioBanca.obtenerBancasDisponibles();
    }

    public void comerciarConJugador(Jugador jugadorDestino, ArrayList<Recurso> oferta, ArrayList<Recurso> demanda) {
        Jugador jugadorActual = obtenerJugadorActual();
        if (jugadorActual == null) return;
        
        CasoDeUsoIntercambio caso = new CasoDeUsoIntercambio(jugadorActual, jugadorDestino);
        caso.ejecutarIntercambio(oferta, demanda);
        notificarCambios();
    }

    public void avanzarTurno() {
        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();
        if (indiceJugadorActual == 0) {
            turnoActual++;
        }
        notificarCambios();
    }

    public boolean terminoElJuego() {
        for (Jugador jugador : jugadores) {
            if (jugador.calculoPuntosVictoria() >= 10) {
                return true;
            }
        }
        return false;
    }
    
    public String obtenerGanador() {
        for (Jugador jugador : jugadores) {
            if (jugador.calculoPuntosVictoria() >= 10) {
                return jugador.obtenerNombre();
            }
        }
        return null;
    }

    private void notificarCambios() {
    }
    
    public Jugador obtenerJugadorActual() {
        if (jugadores != null && !jugadores.isEmpty() && indiceJugadorActual >= 0 && indiceJugadorActual < jugadores.size()) {
            return jugadores.get(indiceJugadorActual);
        }
        return null;
    }
    
    public Map<Recurso, Integer> obtenerInventarioJugador(Jugador jugador) {
        ArrayList<Recurso> recursos = new ArrayList<>(List.of(
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

    public void jugar() {
        System.out.println("Iniciando juego...");
    }
}
