package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tiposBanca.Banca2a1;
import edu.fiuba.algo3.modelo.tiposBanca.Banca3a1;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import javafx.scene.Group;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PuertoUI extends Group {
    private final Circle disco;
    private final Text textoRatio;

    public PuertoUI(double x, double y, Object banca) {
        setTranslateX(x);
        setTranslateY(y);

        disco = new Circle(16);
        disco.setStroke(Color.BLACK);
        disco.setStrokeWidth(1.0);


        textoRatio = new Text();
        textoRatio.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        textoRatio.setTextOrigin(VPos.CENTER);

        textoRatio.layoutBoundsProperty().addListener((obs, oldB, newB) -> {
            textoRatio.setTranslateX(-newB.getWidth() / 2.0);
        });
        textoRatio.setTranslateY(0);

        getChildren().addAll(disco, textoRatio);
        
        configurarSegunBanca(banca, disco);
        
        setMouseTransparent(true);
    }

    private void configurarSegunBanca(Object banca, Circle base) {
        if (banca instanceof Banca3a1) {
            textoRatio.setText("3:1");
            base.setFill(Color.web("#1a2075ff"));
            base.setStrokeWidth(2.0);
            textoRatio.setFill(Color.WHITE);
            return;
        }
        
        if (banca instanceof Banca2a1 b21) {
            Recurso tipo = b21.obtenerRecursoTipo();
            textoRatio.setText("2:1");

            Color colorRecurso = colorDeRecurso(tipo);
            base.setFill(colorRecurso);
            
            textoRatio.setFill(esColorClaro(colorRecurso) ? Color.BLACK : Color.WHITE);
        }
    }

    private Color colorDeRecurso(Recurso r) {

        if (r instanceof Madera) return Color.web("#2ecc71");   
        if (r instanceof Ladrillo) return Color.web("#e74c3c"); 
        if (r instanceof Lana) return Color.web("#ecf0f1");    
        if (r instanceof Grano) return Color.web("#f1c40f");   
        if (r instanceof Piedra) return Color.web("#95a5a6"); 
        return Color.WHITE;
    }

    private boolean esColorClaro(Color c) {
        double r = c.getRed();
        double g = c.getGreen();
        double b = c.getBlue();
        double luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b;
        return luminance > 0.6;
    }
}