package edu.fiuba.algo3.vistas.ventanas;

import javafx.scene.control.TextField;


public class VistaUtilidades {
    
    private VistaUtilidades() {
    }

    public static Integer validarEntero(TextField textField, String nombreCampo) {
        try {
            if (textField.getText().isEmpty()) {
                return null;
            }
            return Integer.parseInt(textField.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
