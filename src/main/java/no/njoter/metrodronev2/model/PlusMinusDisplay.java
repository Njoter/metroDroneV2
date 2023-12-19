package no.njoter.metrodronev2.model;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PlusMinusDisplay extends HBox {

    Button minusBtn = new Button("-");
    Button plusBtn = new Button("+");

    public PlusMinusDisplay(Label label) {

        minusBtn.setMinSize(35, 35);
        minusBtn.setMaxSize(35, 35);
        plusBtn.setMinSize(35, 35);
        plusBtn.setMaxSize(35, 35);

        Display display = new Display(label);
        this.getChildren().addAll(minusBtn, display, plusBtn);
        this.getStyleClass().add("plus-minus-display");
    }

    public Button getMinusBtn() {
        return minusBtn;
    }

    public Button getPlusBtn() {
        return plusBtn;
    }
}
