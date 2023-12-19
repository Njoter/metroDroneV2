package no.njoter.metrodronev2.view;

import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.njoter.metrodronev2.controller.MetronomeController;
import no.njoter.metrodronev2.controller.VolumeController;
import no.njoter.metrodronev2.global.GlobalControllers;
import no.njoter.metrodronev2.global.GlobalState;
import no.njoter.metrodronev2.global.GlobalSynth;
import no.njoter.metrodronev2.model.*;

public class MetronomeView extends VBox {

    public MetronomeView() {
        buildUI();
    }

    private void buildUI() {

        this.getChildren().addAll(
                new Header("Metronome"),
                buildBpmVbox(),
                buildTimesignatureVbox(),
                buildVolumeSlider(),
                buildControlButtons()
        );

        this.getStyleClass().add("metronome-view");
    }

    private VBox buildBpmVbox() {
        VBox vBox = new VBox();

        SectorHeader header = new SectorHeader("BPM");
        Label bpmValue = new Label();
        bpmValue.textProperty().bind(GlobalSynth.metronome.currentBpmProperty().asString());
        Display display = new Display(bpmValue);

        Slider tempoSlider = buildTempoSlider(
                MetronomeController.MIN_BPM,
                MetronomeController.MAX_BPM,
                GlobalSynth.metronome.currentBpmProperty().get()
        );
        GlobalSynth.metronome.currentBpmProperty().bind(tempoSlider.valueProperty());

        vBox.getChildren().addAll(header, display, tempoSlider);
        vBox.getStyleClass().add("sector");
        return vBox;
    }

    private VBox buildTimesignatureVbox() {
        VBox vBox = new VBox();

        ToggleButton lock = new ToggleButton("LOCK");
        lock.setOnAction(e -> {
            if (lock.isSelected()) {
                GlobalState.timeLocked.set(true);
            } else {
                GlobalState.timeLocked.set(false);
            }
        });

        vBox.getChildren().addAll(
                new SectorHeader("TIMESIGNATURE"),
                buildPlusMinusBeats(),
                buildPlusMinusSubdivision(),
                lock
        );

        vBox.getStyleClass().add("sector");
        return vBox;
    }

    private Slider buildTempoSlider(int min, int max, int current) {
        Slider slider = new Slider(min, max, current);
        slider.setMaxWidth(200);

        slider.setOnMouseReleased(e -> {
            if (GlobalSynth.metronome.isRunning()) {
                GlobalSynth.metronome.stopAll();
                GlobalSynth.metronome.start();
            }
        });

        return slider;
    }

    private PlusMinusDisplay buildPlusMinusBeats() {
        Label label = new Label();
        label.textProperty().bind(GlobalSynth.metronome.beatsPerMeasureProperty().asString());
        PlusMinusDisplay plusMinusDisplay = new PlusMinusDisplay(label);

        plusMinusDisplay.getMinusBtn().setOnAction(e -> {
            if (GlobalState.timeLocked.get() == true) {
                // Update beatsPerMeasure in all chordSaves,
                // which in turn updates beatsPerMeasure in MetronomeController.
                GlobalControllers.chordSaveController.setBeatsPerMeasureInAllChordSaves(
                        GlobalState.currentChordSave.getBeatsPerMeasure() - 1
                );
                // Updates beatsPerMeasure in currentChordSave,
                // which in turn updates beatsPerMeasure in MetronomeController.
            } else {
                GlobalState.currentChordSave.setBeatsPerMeasure(
                        GlobalState.currentChordSave.getBeatsPerMeasure() - 1
                );
            }
        });

        plusMinusDisplay.getPlusBtn().setOnAction(e -> {
            if (GlobalState.timeLocked.get() == true) {
                if (GlobalState.timeLocked.get() == true) {
                    GlobalControllers.chordSaveController.setBeatsPerMeasureInAllChordSaves(
                            GlobalState.currentChordSave.getBeatsPerMeasure() + 1
                    );
                }
            } else {
                GlobalState.currentChordSave.setBeatsPerMeasure(
                        GlobalState.currentChordSave.getBeatsPerMeasure() + 1
                );
            }
        });

        return  plusMinusDisplay;
    }

    private PlusMinusDisplay buildPlusMinusSubdivision() {
        Label label = new Label();
        label.textProperty().bind(GlobalSynth.metronome.subdivisionProperty().asString());
        PlusMinusDisplay plusMinusDisplay = new PlusMinusDisplay(label);

        plusMinusDisplay.getMinusBtn().setOnAction(e -> {
            if (GlobalState.timeLocked.get() == true) {
                GlobalControllers.chordSaveController.setSubdivisionInAllChordSaves(
                        GlobalState.currentChordSave.getSubdivision() / 2
                );
            } else {
                GlobalState.currentChordSave.setSubdivision(
                        GlobalState.currentChordSave.getSubdivision() / 2
                );
            }
            if (GlobalSynth.metronome.isRunning()) {
                GlobalSynth.metronome.stopAll();
                GlobalSynth.metronome.start();
            }
        });

        plusMinusDisplay.getPlusBtn().setOnAction(e -> {
            if (GlobalState.timeLocked.get() == true) {
                GlobalControllers.chordSaveController.setSubdivisionInAllChordSaves(
                        GlobalState.currentChordSave.getSubdivision() * 2
                );
            } else {
                GlobalState.currentChordSave.setSubdivision(
                        GlobalState.currentChordSave.getSubdivision() * 2
                );
            }
            if (GlobalSynth.metronome.isRunning()) {
                GlobalSynth.metronome.stopAll();
                GlobalSynth.metronome.start();
            }
        });

        return plusMinusDisplay;
    }

    private VBox buildVolumeSlider() {
        VBox vBox = new VBox();

        VolumeSlider volumeSlider = new VolumeSlider();
        VolumeController.setVolumeHandler(volumeSlider, GlobalSynth.metronome.getChannel());

        vBox.getChildren().addAll(
                new SectorHeader("VOLUME"),
                volumeSlider
        );

        vBox.getStyleClass().add("sector");
        return vBox;
    }

    private VBox buildControlButtons() {
        HBox hBox = new HBox();

        hBox.getChildren().addAll(
                buildAdvanceBtn(),
                buildMuteBtn()
        );

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        VBox vBox = new VBox();

        vBox.getChildren().addAll(
                buildStartBtn(),
                hBox
        );

        vBox.getStyleClass().add("sector");
        return vBox;
    }

    private ToggleButton buildStartBtn() {
        ToggleButton btn = new ToggleButton("START");
        btn.setMinSize(100, 40);
        btn.setMaxSize(100, 40);
        // Start/stop metronome
        btn.setOnAction(e -> {
            if (GlobalSynth.metronome.isRunning()) {
                GlobalSynth.metronome.stopAll();
                GlobalState.barsPassed.set(-1);
                GlobalState.currentBar.set(1);
                btn.setText("START");
            } else {
                GlobalSynth.metronome.start();
                btn.setText("STOP");
            }
        });
        return btn;
    }

    private ToggleButton buildAdvanceBtn() {
        ToggleButton button = new ToggleButton("ADVANCE");
        button.setMinSize(100, 40);
        button.setMaxSize(100, 40);

        button.setOnAction(e -> {
            if (button.isSelected()) {
                GlobalState.advance = true;
            } else {
                GlobalState.advance = false;
            }
        });

        return button;
    }

    private ToggleButton buildMuteBtn() {
        ToggleButton toggleButton = new ToggleButton("MUTE");

        toggleButton.setOnAction(e -> {
            if (toggleButton.isSelected()) {
                GlobalSynth.metronome.mute(true);
            } else {
                GlobalSynth.metronome.mute(false);
            }
        });

        toggleButton.setMinSize(100, 40);
        toggleButton.setMaxSize(100, 40);

        return toggleButton;
    }
}