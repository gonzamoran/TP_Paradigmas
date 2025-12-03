/*
package edu.fiuba.algo3.controllers;

import javafx.scene.media.AudioClip;


import java.util.Objects;

public class ReproductorDeSonido {

    private final String fAudioDados = "/hellofx/sonido_dados.mp3";

    private final AudioClip reproductorDados;

    private static ReproductorDeSonido instance;

    private ReproductorDeSonido() {
        this.reproductorDados = new AudioClip(Objects.requireNonNull(getClass().getResource(fAudioDados)).toString());
    }

    public static ReproductorDeSonido getInstance() {
        if(instance == null)
            instance = new ReproductorDeSonido();
        return instance;
    }

    public void playPrincipal(){
        reproductorDados.setVolume(0.35);

    }

    public void playClick(){
        reproductorDados.play();
    }
}
*/