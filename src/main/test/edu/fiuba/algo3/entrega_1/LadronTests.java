package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.Ladron;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;    

public class LadronTests {

    @BeforeEach
    public void setUp() {
        //List<TipoTerreno> tipos = new ArrayList<>(
        //    TipoTerreno.Colina
        //    TipoTerreno .Bosque
        //    TipoTerreno.Campo 
        //    TipoTerreno.Pastizal
        //);
        Mapa mapa = new Mapa();
        Hexagono colina= newTerrenoConRecurso(new RecursoGrano());
        Hexagono bosquel = new TerrenoConRecurso(new RecursoMadera());
        Hexagono montana = new TrrenoConReco(new RecursoMadera());
        Hexagono campo = new TerrenoConRecurso(new RecursoMadera());
        Hexagono terreno = new TerrenoConRecurso(new RecursoMadera());
        Hexagono desierto = new TerronoConRecurso(new Desierto());
        mapa.agregarHexagono(terreno);
        Ladron ladron = new Ladron();
    }
    
    @Test
    public void testTerrenoBajoLadronNoGeneraRecursos() {
        //Arrange
        Mapa mapa = new Mapa();
        Hexagono terreno = new; TerrenoConRecurso(new RecursoMadera());
        mapa.agregarHexagono()
        Ladron ladron = new Ladron();
        //Act
        ladron.moverALadronA(terreno);
        Recurso recursoObtenido = terreno.obtenerRecurso();
        //Assert
        assertEquals(new RecursoNulo(), recursoObtenido);
    }


    @Test
    public void testLadronEmpiezaEnDesierto(){
        //Arrange
        
    }

}