package no.njoter.metrodronev2.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SectorHeader extends VBox {


    public SectorHeader(String string) {
        SeparatorLine line = new SeparatorLine();
        line = new SeparatorLine();
        Label label = new Label(string);
        this.getChildren().addAll(label, line);
        this.setAlignment(Pos.CENTER);
    }
}
