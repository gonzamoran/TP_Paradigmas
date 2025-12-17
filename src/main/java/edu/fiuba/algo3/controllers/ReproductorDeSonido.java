
package edu.fiuba.algo3.controllers;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Objects;

public class ReproductorDeSonido {

    private final String fAudioDeFondo = "/resources/sounds/medieval.mp3";
    private final String fAudioDados = "/resources/sounds/sonido_dados.mp3";
    private final String fAudioClick = "/resources/sounds/botonClick.mp3";
    private final String fAudioVictoria = "/resources/sounds/victoria.mp3";
    private final String fAudioError = "/resources/sounds/error.mp3";
    
    private final MediaPlayer reproductorFondo;
    private final AudioClip reproductorDados;
    private final AudioClip reproductorClick;
    private final AudioClip reproductorVictoria;
    private final AudioClip reproductorError;

    private static ReproductorDeSonido instance;

    private ReproductorDeSonido() {
        this.reproductorFondo = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource(fAudioDeFondo)).toString()));
        this.reproductorDados = new AudioClip(Objects.requireNonNull(getClass().getResource(fAudioDados)).toString());
        this.reproductorClick = new AudioClip(Objects.requireNonNull(getClass().getResource(fAudioClick)).toString());
        this.reproductorVictoria = new AudioClip(Objects.requireNonNull(getClass().getResource(fAudioVictoria)).toString());
        this.reproductorError = new AudioClip(Objects.requireNonNull(getClass().getResource(fAudioError)).toString());
        this.reproductorDados.setVolume(0.5);
    }

    public static ReproductorDeSonido getInstance() {
        if(instance == null)
            instance = new ReproductorDeSonido();
        return instance;
    }

    public void playFondo(){
        reproductorFondo.setVolume(0.25);
        reproductorFondo.setCycleCount(MediaPlayer.INDEFINITE);
        reproductorFondo.play();
    }

    public void reproducirSonidoDados(){reproductorDados.play(); }
    public void playClick(){reproductorClick.play(); }
    public void playVictoria() { reproductorVictoria.play(); }
    public void playError() { reproductorError.play(); }
}

