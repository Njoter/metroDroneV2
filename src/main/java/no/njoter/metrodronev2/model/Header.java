package no.njoter.metrodronev2.model;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.awt.*;

public class Header extends StackPane {

    private final Label label;

    public Header(String string) {

        this.label = new Label(string);
        this.label.setPadding(new Insets(10));
        this.label.getStyleClass().add("label-light");

        this.getChildren().addAll(label);
        this.getStyleClass().add("header");

        this.setMinWidth(150);
        this.setMaxWidth(150);
    }
}
