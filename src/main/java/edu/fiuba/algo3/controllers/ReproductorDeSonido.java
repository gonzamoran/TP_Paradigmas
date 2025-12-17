
package edu.fiuba.algo3.controllers;

import javafx.scene.media.AudioClip;


import java.util.Objects;

public class ReproductorDeSonido {

    private final String fAudioDados = "/sonidos/sonido_dados.mp3";
    private final String fAudioClick = "/sonidos/botonClick.mp3";
    private final String fAudioVictoria = "/sonidos/victoria.mp3";
    
    private final AudioClip reproductorDados;
    private final AudioClip reproductorClick;
    private final AudioClip reproductorVictoria;

    private static ReproductorDeSonido instance;

    private ReproductorDeSonido() {
        this.reproductorDados = new AudioClip(Objects.requireNonNull(getClass().getResource(fAudioDados)).toString());
        this.reproductorClick = new AudioClip(Objects.requireNonNull(getClass().getResource(fAudioClick)).toString());
        this.reproductorVictoria = new AudioClip(Objects.requireNonNull(getClass().getResource(fAudioVictoria)).toString());
        this.reproductorDados.setVolume(0.5);
    }

    public static ReproductorDeSonido getInstance() {
        if(instance == null)
            instance = new ReproductorDeSonido();
        return instance;
    }

    public void reproducirSonidoDados(){reproductorDados.play(); }
    public void playClick(){reproductorClick.play(); }
    public void playVictoria() { reproductorVictoria.play(); }
}

