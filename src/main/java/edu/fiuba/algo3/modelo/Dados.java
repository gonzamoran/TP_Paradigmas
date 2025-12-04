package edu.fiuba.algo3.modelo;

import java.util.Random;

public class Dados {
    private final Random random;

    public Dados() {
        this.random = new Random();
    }

    public int lanzarDados() {
        // doble tirada para respetar las probabilidades
        return (random.nextInt(6) + 1) + (random.nextInt(6) + 1); 
    }
}
