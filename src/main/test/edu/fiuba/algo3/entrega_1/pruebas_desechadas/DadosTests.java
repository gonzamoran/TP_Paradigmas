package edu.fiuba.algo3.entrega_1.pruebas_desechadas;

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
        //Arrange
        Dados dados = new Dados();
        //Act
        int res = dados.lanzarDados();
        //Assert
        assertTrue(res <= 12);
    }
    @Test
    public void test03TirarDadosNoPuedeSerMenorA2() {
        //Arrange
        Dados dados = new Dados();
        //Act
        int res = dados.lanzarDados();
        //Assert
        assertTrue(res >= 2);
    }
    

}

