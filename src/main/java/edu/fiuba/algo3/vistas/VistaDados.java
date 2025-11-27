package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Dados;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class VistaDados extends HBox {

    public VistaDados(Dados dados) {
        for (Dado dado : dados) this.getChildren().add(vistaDados(dado));
      }
      this.setSpacing(10);
    }

    private ImageView vistaDados(Dado dado) {
        Image imagenDado1 = new Image("dado" + dado.obtenerValor() + ".png");
        Image imagenDado2 = new Image("dado" + dado.obtenerValor() + ".png");
        ImageView imageView = new ImageView(imagenDado1);
        ImageView imageView = new ImageView(imagenDado2);
        imageView.setPreserveRatio(true);
        return imageView;
    }
}
