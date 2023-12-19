package no.njoter.metrodronev2.model;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class Display extends StackPane {
    public Display(Label label) {
        label.getStyleClass().add("label-light");
        this.getChildren().addAll(label);
        this.setMinWidth(50);
        this.setMaxWidth(50);
        this.setMinHeight(30);
        this.getStyleClass().add("display");
    }
}
