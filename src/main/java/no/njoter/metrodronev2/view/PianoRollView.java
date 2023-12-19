package no.njoter.metrodronev2.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.njoter.metrodronev2.controller.PianoRollController;
import no.njoter.metrodronev2.controller.VolumeController;
import no.njoter.metrodronev2.global.GlobalControllers;
import no.njoter.metrodronev2.global.GlobalState;
import no.njoter.metrodronev2.global.GlobalSynth;
import no.njoter.metrodronev2.model.Header;
import no.njoter.metrodronev2.model.PianoRoll;
import no.njoter.metrodronev2.model.SectorHeader;
import no.njoter.metrodronev2.model.VolumeSlider;

public class PianoRollView extends VBox {

    public PianoRollView() {
        buildUI();
    }

    private void buildUI() {

        this.getChildren().addAll(
                new Header("PianoRoll"),
                buildPianoRoll(),
                buildVolumeSlider(),
                buildButtons()
                );

        this.getStyleClass().add("piano-roll-view");
    }

    private PianoRoll buildPianoRoll() {
        PianoRoll pianoRoll = new PianoRoll(48, 71);
        pianoRoll.setMinWidth(250);
        pianoRoll.setMaxWidth(250);
        pianoRoll.setVvalue(100);

        GlobalControllers.setPianoRollController(pianoRoll);

        return pianoRoll;
    }

    public VBox buildVolumeSlider() {
        VBox vBox = new VBox();

        VolumeSlider volumeSlider = new VolumeSlider();
        VolumeController.setVolumeHandler(volumeSlider, GlobalSynth.chordSynth.getChannel());

        vBox.getChildren().addAll(
                new SectorHeader("VOLUME"),
                volumeSlider
        );

        vBox.getStyleClass().add("sector");
        return vBox;
    }

    private VBox buildButtons() {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        hBox.getChildren().addAll(
                buildClearButton(),
                buildMuteButton()
        );

        VBox vBox = new VBox();

        vBox.getChildren().addAll(
                hBox
        );

        vBox.getStyleClass().add("sector");
        return vBox;
    }

    private Button buildClearButton() {
        Button btn = new Button("CLEAR");

        btn.setOnAction(e -> {
            GlobalControllers.pianoRollController.unselectAllNotes();
            GlobalState.currentChordSave.getNotes().clear();
            GlobalSynth.chordSynth.stopAll();
        });

        btn.setMinSize(100, 40);
        btn.setMaxSize(100, 40);
        return btn;
    }

    private ToggleButton buildMuteButton() {
        ToggleButton btn = new ToggleButton("MUTE");

        btn.setOnAction(e -> {
            if (btn.isSelected()) {
                GlobalSynth.chordSynth.mute(true);
            } else {
                GlobalSynth.chordSynth.mute(false);
            }
        });

        btn.setMinSize(100, 40);
        btn.setMaxSize(100, 40);
        return btn;
    }
}
