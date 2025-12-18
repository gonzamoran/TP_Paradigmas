package edu.fiuba.algo3.vistas.ventanas;

import javafx.scene.paint.Color;


public class ColoresJugadores {
    
    private static final Color[] COLORES_JUGADORES = {
        Color.web("#e74c3c"),  
        Color.web("#27ae60"),  
        Color.web("#3498db"),  
        Color.web("#f39c12"),  
        Color.web("#8b4513"),  
        Color.web("#2c3e50"),  
    };
    
    public static Color obtenerColorPoblado(int indiceJugador) {
        if (indiceJugador < 0 || indiceJugador >= COLORES_JUGADORES.length) {
            return Color.GRAY;
        }
        return COLORES_JUGADORES[indiceJugador];
    }

    public static Color obtenerColorBorde(int indiceJugador) {
        Color color = obtenerColorPoblado(indiceJugador);
        return color.darker().darker();
    }
}
