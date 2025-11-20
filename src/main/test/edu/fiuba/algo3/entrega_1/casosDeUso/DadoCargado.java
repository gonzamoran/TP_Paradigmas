package edu.fiuba.algo3.entrega_1.casosDeUso;

import edu.fiuba.algo3.modelo.Dados;

public class DadoCargado extends Dados{
    int resultado;

    public DadoCargado(int resultado){
        this.resultado = resultado;
    }

    @Override
    public int lanzarDados(){
        return this.resultado;
    }
}
