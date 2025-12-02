package edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo;

import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.excepciones.NoEsPosibleConstruirException;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.ProveedorDeDatos;

import edu.fiuba.algo3.modelo.excepciones.NoSePuedeJugarEstaCartaException;
/*
 * Permite construir 2 Carreteras gratuitamente (debe
 * cumplir reglas de colocación)
 * 
 */
public class CartaConstruccionCarretera extends CartasDesarrollo {

    public CartaConstruccionCarretera(){
        super();
    }

    public CartaConstruccionCarretera(int turnoActual){
        super(turnoActual);

    }
    public boolean esJugable(ContextoCartaDesarrollo contexto) {
        return contexto.sePuedeJugarCarta(this.turnoDeCompra);
    }

    public CartasDesarrollo comprarCarta(int turnoActual) {
        return new CartaMonopolio(turnoActual);
    }

    @Override
    public void usar(ContextoCartaDesarrollo contexto, ProveedorDeDatos proveedor) {
        this.fueUsada = true;

        if(!contexto.sePuedeJugarCarta(this.turnoDeCompra)){
            throw new NoSePuedeJugarEstaCartaException();
        }

        Jugador jugador = contexto.conseguirJugadorQueUsaLaCarta();
        Tablero tablero = contexto.obtenerTablero();

        // 2 caminos, no se si ponerlo asi o como un while coloco 2 o algo asi.
        for (int i = 0; i < 2; i++){
            Coordenadas origen = proveedor.pedirCoordenadasAlUsuario();
            Coordenadas desino = proveedor.pedirCoordenadasAlUsuario();

            /* haria un metodo que sea: construirCarretera gratuita, para que no se le cobre al usuario y para que solo vea las adyacencias a los demas caminos/Poblados del mismo jugador.            
            */
        }
    }
}

/*
     public void construirCarreteraGratis(Coordenadas coordenadaExtremo1, Coordenadas coordenadaExtremo2, Jugador jugador) {
        if (!this.sonCoordenadasValidas(coordenadaExtremo1) || !this.sonCoordenadasValidas(coordenadaExtremo2)) {
            throw new PosInvalidaParaConstruirException();
        }

        Vertice vertice1 = mapaVertices.get(coordenadaExtremo1);
        Vertice vertice2 = mapaVertices.get(coordenadaExtremo2);

        if (!vertice1.esAdyacente(vertice2)) {
            throw new PosInvalidaParaConstruirException();
        }
        

        if (!vertice1.poseeCarreterasDe(jugador) && !vertice2.poseeCarreterasDe(jugador) && !(vertice1.esDueno(jugador) || vertice2.esDueno(jugador))) {
            throw new NoEsPosibleConstruirException();
        }

        var carretera = new Carretera();
        if (!vertice1.puedeConstruirse(carretera) && !vertice2.puedeConstruirse(carretera)) {
            throw new NoEsPosibleConstruirException();
        }

        // seria igual a este metodo sin este if.
        if (!jugador.poseeRecursosParaConstruir(carretera)){
            throw new NoEsPosibleConstruirException();
        }

        vertice1.construirCarretera(carretera, jugador);
        vertice2.construirCarretera(carretera, jugador);
    }
*/