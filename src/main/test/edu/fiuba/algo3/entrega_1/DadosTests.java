package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.Dados;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DadosTests{
    @Test
    public void test01TirarElDadoUnaVezSacaUnNumeroDel2Al12() {
        //Arrange
        Dados dados = new Dados();
        //Act
        int res = dados.lanzarDados();
        //Assert
        assert(res >= 2 && res <= 12);
    }
    
    @Test
    public void test02TirarDadosNoPuedeSerMayorA12() {

        Dados dados = new Dados();

        int res = dados.lanzarDados();

        assertTrue(res <= 12);
    }
    @Test
    public void test03TirarDadosNoPuedeSerMenorA2() {
        Dados dados = new Dados();
  
        int res = dados.lanzarDados();

        assertTrue(res >= 2);
    }
    

}

