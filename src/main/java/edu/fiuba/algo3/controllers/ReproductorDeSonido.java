
package edu.fiuba.algo3.controllers;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Objects;

public class ReproductorDeSonido {

    private final String fAudioDeFondo = "/sounds/medieval.wav";
    private final String fAudioDados = "/sounds/sonido_dados.wav";
    private final String fAudioClick = "/sounds/botonClick.wav";
    private final String fAudioVictoria = "/sounds/victoria.wav";
    private final String fAudioError = "/sounds/error.wav";

    private final AudioClip reproductorFondo;
    private final AudioClip reproductorDados;
    private final AudioClip reproductorClick;
    private final AudioClip reproductorVictoria;
    private final AudioClip reproductorError;

    private static ReproductorDeSonido instance;

    private ReproductorDeSonido() {
        this.reproductorFondo = new AudioClip(Objects.requireNonNull(getClass().getResource(fAudioDeFondo)).toString());
        this.reproductorDados = new AudioClip(Objects.requireNonNull(getClass().getResource(fAudioDados)).toString());
        this.reproductorClick = new AudioClip(Objects.requireNonNull(getClass().getResource(fAudioClick)).toString());
        this.reproductorVictoria = new AudioClip(
                Objects.requireNonNull(getClass().getResource(fAudioVictoria)).toString());
        this.reproductorError = new AudioClip(Objects.requireNonNull(getClass().getResource(fAudioError)).toString());
        this.reproductorDados.setVolume(0.5);
    }

    public static ReproductorDeSonido getInstance() {
        if (instance == null)
            instance = new ReproductorDeSonido();
        return instance;
    }

    public void playFondo() {
        reproductorFondo.setVolume(1);
        reproductorFondo.setCycleCount(AudioClip.INDEFINITE);
        reproductorFondo.play();

        reproductorDados.play();
    }

    public void reproducirSonidoDados() {
        reproductorDados.play();
    }

    public void playClick() {
        reproductorClick.play();
    }

    public void playVictoria() {
        reproductorVictoria.play();
    }

    public void playError() {
        reproductorError.play();
    }
}
