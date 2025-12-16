
package edu.fiuba.algo3.controllers;

import javafx.scene.media.AudioClip;


import java.util.Objects;

public class ReproductorDeSonido {

    private final String fAudioDados = "/sonidos/sonido_dados.mp3";
    private final AudioClip reproductorDados;
    private static ReproductorDeSonido instance;

    private ReproductorDeSonido() {
        this.reproductorDados = new AudioClip(Objects.requireNonNull(getClass().getResource(fAudioDados)).toString());
        this.reproductorDados.setVolume(0.5);
    }

    public static ReproductorDeSonido getInstance() {
        if(instance == null)
            instance = new ReproductorDeSonido();
        return instance;
    }

    public void reproducirSonidoDados(){
        reproductorDados.play();
    }
}
