package no.njoter.metrodronev2.model;

import javafx.scene.shape.Rectangle;

public class SeparatorLine extends Rectangle {

    public SeparatorLine() {
        this.setWidth(200);
        this.setHeight(2);
        this.getStyleClass().add("line");
    }
}
