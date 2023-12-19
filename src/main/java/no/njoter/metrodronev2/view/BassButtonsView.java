package no.njoter.metrodronev2.view;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.njoter.metrodronev2.controller.BassButtonController;
import no.njoter.metrodronev2.controller.VolumeController;
import no.njoter.metrodronev2.global.GlobalSynth;
import no.njoter.metrodronev2.model.NoteToggleButton;
import no.njoter.metrodronev2.model.VolumeSlider;

import java.util.ArrayList;

public class BassButtonsView extends VBox {

    public BassButtonsView() {
        buildUI();
    }

    private void buildUI() {
        HBox bassBtnsLayout = buildBassBtns();

        Label header = new Label("Array of Bass");
        header.getStyleClass().add("label-light");

        VolumeSlider volumeSlider = new VolumeSlider();
        VolumeController.setVolumeHandler(volumeSlider, GlobalSynth.bassSynth.getChannel());

        this.getChildren().addAll(
                header,
                bassBtnsLayout,
                volumeSlider
        );

        this.getStyleClass().add("bass-buttons-view");
    }

    private HBox buildBassBtns() {
        HBox hBox = new HBox();

        ArrayList<NoteToggleButton> buttons = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            NoteToggleButton button = new NoteToggleButton(i + 24);
            button.setText(button.getNoteName());
            buttons.add(button);
            hBox.getChildren().add(button);
        }

        BassButtonController.setHandlers(buttons, GlobalSynth.bassSynth);
        hBox.getStyleClass().add("bass-buttons");
        return hBox;
    }
}
