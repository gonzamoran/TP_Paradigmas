package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tiposBanca.Banca2a1;
import edu.fiuba.algo3.modelo.tiposBanca.Banca3a1;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class PuertoConexionesUI extends Group {
    private final Circle figura;
    private final Text etiqueta;
    private final Line linea1;
    private final Line linea2;

    public PuertoConexionesUI(double vx1, double vy1, double vx2, double vy2, Object banca) {
        double mx = (vx1 + vx2) / 2.0;
        double my = (vy1 + vy2) / 2.0;
        double dx = vx2 - vx1;
        double dy = vy2 - vy1;
        double len = Math.sqrt(dx * dx + dy * dy);
        double ox = 0;
        double oy = 0;
        if (len > 0) {
            ox = (-dy / len) * 14;
            oy = (dx / len) * 14;
        }
        double px = mx + ox;
        double py = my + oy;

        figura = new Circle(px, py, 9);
        figura.setFill(Color.web("#2c3e50"));
        figura.setStroke(Color.web("#ecf0f1"));
        figura.setStrokeWidth(1.5);

        etiqueta = new Text();
        etiqueta.setFill(Color.web("#ecf0f1"));
        etiqueta.setFont(Font.font("Arial", 10));
        etiqueta.setX(px - 10);
        etiqueta.setY(py + 4);
        etiqueta.setText(textoSegunBanca(banca));

        linea1 = new Line(px, py, vx1, vy1);
        linea1.setStroke(Color.web("#34495e"));
        linea1.setStrokeWidth(2.0);
        linea2 = new Line(px, py, vx2, vy2);
        linea2.setStroke(Color.web("#34495e"));
        linea2.setStrokeWidth(2.0);

        getChildren().addAll(linea1, linea2, figura, etiqueta);
        setMouseTransparent(true);
    }

    private String textoSegunBanca(Object banca) {
        if (banca instanceof Banca3a1) return "3:1";
        if (banca instanceof Banca2a1 b21) {
            Recurso tipo = b21.obtenerRecursoTipo();
            return "2:1 " + inicialDe(tipo);
        }
        return "";
    }

    private String inicialDe(Recurso r) {
        if (r instanceof Madera) return "M";
        if (r instanceof Ladrillo) return "L";
        if (r instanceof Lana) return "La";
        if (r instanceof Grano) return "G";
        if (r instanceof Piedra) return "P";
        return "?";
    }
}
