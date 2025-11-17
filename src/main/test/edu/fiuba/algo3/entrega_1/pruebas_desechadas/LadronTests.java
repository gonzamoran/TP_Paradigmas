// package edu.fiuba.algo3.entrega_1.pruebas_desechadas;

// import org.junit.jupiter.api.Test;

// import edu.fiuba.algo3.modelo.tablero.Hexagono;
// import edu.fiuba.algo3.modelo.tablero.Ladron;
// import edu.fiuba.algo3.modelo.tablero.Tablero;
// import edu.fiuba.algo3.modelo.tablero.TipoRecurso;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.util.List;

// import org.junit.jupiter.api.BeforeEach;    

// public class LadronTests {

//     private List<Hexagono> hexagonos;
//     private Tablero tablero;
//     private Ladron ladron;

//     @BeforeEach
//     public void setUp() {
//         Tablero tablero = new Tablero();
//         Ladron ladron = new Ladron();
//         List<Hexagono> hexagonos = tablero.obtenerHexagonos();
//     }
    
//     //@Test
//     //public void testTerrenoBajoLadronNoGeneraRecursos() {
//         //Arrange
//       //  Mapa mapa = new Mapa();
//       //  Hexagono terreno = new; TerrenoConRecurso(new RecursoMadera());
//       //  mapa.agregarHexagono()
//       //  Ladron ladron = new Ladron();
//         //Act
//       //  ladron.moverALadronA(terreno);
//       //  Recurso recursoObtenido = terreno.obtenerRecurso();
//         //Assert
//       //  assertEquals(new RecursoNulo(), recursoObtenido);
//     //}


//     @Test
//     public void test01LadronEmpiezaEnTerrenoDesierto() {
//         //Act y Assert
//         Hexagono hexagonoDesierto = null;
//         for (Hexagono hexagono : hexagonos) {
//             if (hexagono.esDesierto()) {
//                 hexagonoDesierto = hexagono;

//                 hexagonoDesierto.colocarLadron();
//             }
//         }
//     }
//     @Test void test02TerrenoBajoElLadronNoGeneraRecursos(){
//         //Act y Assert
//         for (Hexagono hexagono : hexagonos) {
//             if (!hexagono.esDesierto()) {
//                 ladron.moverLadronA(hexagono);
//                 assertEquals(false, hexagono.puedeGenerarRecursos());
//             }
//         }
//     }
//     @Test void test03TerrenoSinLadronGeneraRecursos(){
//         //Act y Assert
//         for (Hexagono hexagono : hexagonos) {
//             if (!hexagono.esDesierto()) {
//                 assertEquals(true, hexagono.puedeGenerarRecursos());
//             }
//         }
//     }
//     @Test void test04JugadorActivoPuedeMoverLadron(){
//         //Arrange
//         Hexagono hexagonoDestino = null;    //Hexagono al que se movera el ladron esta bien inicializarlo en null?
//         //Act
//         for (Hexagono hexagono : hexagonos) {
//             if (!hexagono.esDesierto()) {
//                 hexagonoDestino = hexagono;
//             }
//         }
//         ladron.moverLadronA(hexagonoDestino);
//         //Assert
//         assertEquals(false, hexagonoDestino.puedeGenerarRecursos());
//     }
// }