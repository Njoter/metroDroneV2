package no.njoter.metrodronev2.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.njoter.metrodronev2.global.GlobalControllers;
import no.njoter.metrodronev2.global.GlobalState;
import no.njoter.metrodronev2.global.GlobalSynth;
import no.njoter.metrodronev2.model.*;

public class ChordSaveView extends VBox {

    private final int NUMBER_OF_CHORDSAVES = 8;
    private final int MAX_BARS = 8;
    private ChordSaveButton[] chordSaveButtons;

    public ChordSaveView() {
        buildUI();
    }

    private void buildUI() {
        this.getChildren().addAll(
                new Header("ChordSave"),
                buildChordSaveButtons(),
                buildBarsSection(),
                buildClearButtons(),
                buildMuteButton()
        );
        this.getStyleClass().add("chord-save-view");
    }

    private VBox buildChordSaveButtons() {
        VBox vBox = new VBox();
        GridPane gridPane = new GridPane();

        chordSaveButtons = new ChordSaveButton[NUMBER_OF_CHORDSAVES];
        for (int i = 0; i < NUMBER_OF_CHORDSAVES; i++) {
            chordSaveButtons[i] = new ChordSaveButton();
            chordSaveButtons[i].setText("" + (i + 1));
            chordSaveButtons[i].setIndex(i);
            chordSaveButtons[i].setDisable(true);
            if (i < 4) {
                gridPane.add(chordSaveButtons[i], i, 0);
            } else {
                gridPane.add(chordSaveButtons[i], i-4, 1);
            }
        }
        chordSaveButtons[0].setDisable(false);
        chordSaveButtons[0].setSelected(true);
        GlobalState.setCurrentChordSave(chordSaveButtons[0]);

        GlobalControllers.setChordSaveController(chordSaveButtons);

        HBox plusMinusBtns = buildPlusMinus();
        vBox.getChildren().addAll(gridPane, plusMinusBtns);

        gridPane.getStyleClass().add("chord-save-buttons");
        vBox.setSpacing(10);
        return vBox;
    }

    private HBox buildPlusMinus() {
        HBox hBox = new HBox();
        Button minusBtn = new Button("-");
        Button plusbtn = new Button("+");

        minusBtn.setMinSize(35, 35);
        minusBtn.setMaxSize(35, 35);
        plusbtn.setMinSize(35, 35);
        plusbtn.setMaxSize(35, 35);

        minusBtn.setOnAction(e -> {
            GlobalControllers.chordSaveController.subtractChordSave();
        });

        plusbtn.setOnAction(e -> {
            GlobalControllers.chordSaveController.addChordSave();
        });

        hBox.getChildren().addAll(
                minusBtn,
                plusbtn
        );

        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    private VBox buildBarsSection() {
        VBox vBox = new VBox();

        Label barsLabel = new Label();
        barsLabel.textProperty().bind(GlobalState.numberOfBars.asString());
        PlusMinusDisplay plusMinusBars = new PlusMinusDisplay(barsLabel);

        plusMinusBars.getMinusBtn().setOnAction(e -> {
            if (GlobalState.currentChordSave.getNumberOfBars() - 1 < 1) {
                if (GlobalState.numberOfBarsLocked.get()) {
                    GlobalControllers.chordSaveController.setNumberOfBarsInAllChordSaves(1);
                } else {
                    GlobalState.currentChordSave.setNumberOfBars(1);
                }
                GlobalState.numberOfBars.set(1);
            } else {
                if (GlobalState.numberOfBarsLocked.get()) {
                    GlobalControllers.chordSaveController.setNumberOfBarsInAllChordSaves(
                            GlobalState.currentChordSave.getNumberOfBars() - 1
                    );
                } else {
                    GlobalState.currentChordSave.setNumberOfBars(
                            GlobalState.currentChordSave.getNumberOfBars() - 1
                    );
                }
                GlobalState.numberOfBars.set(GlobalState.currentChordSave.getNumberOfBars());
            }
        });

        plusMinusBars.getPlusBtn().setOnAction(e -> {
            if (GlobalState.currentChordSave.getNumberOfBars() + 1 > MAX_BARS) {
                if (GlobalState.numberOfBarsLocked.get()) {
                    GlobalControllers.chordSaveController.setNumberOfBarsInAllChordSaves(MAX_BARS);
                } else {
                    GlobalState.currentChordSave.setNumberOfBars(MAX_BARS);
                }
                GlobalState.numberOfBars.set(MAX_BARS);
            } else {
                if (GlobalState.numberOfBarsLocked.get()) {
                    GlobalControllers.chordSaveController.setNumberOfBarsInAllChordSaves(
                            GlobalState.currentChordSave.getNumberOfBars() + 1
                    );
                } else {
                    GlobalState.currentChordSave.setNumberOfBars(
                            GlobalState.currentChordSave.getNumberOfBars() + 1
                    );
                }
                GlobalState.numberOfBars.set(GlobalState.currentChordSave.getNumberOfBars());
            }
        });

        vBox.getChildren().addAll(
                new SectorHeader("NUMBER OF BARS"),
                plusMinusBars,
                buildCurrentBarDisplay(),
                buildLockButton()
        );
        vBox.getStyleClass().add("sector");
        return vBox;
    }

    private HBox buildCurrentBarDisplay() {
        HBox hBox = new HBox();

        Label currentBarLabel = new Label();
        currentBarLabel.textProperty().bind(GlobalState.currentBar.asString());

        Label dash = new Label("/");

        Label numberOfBarsLabel = new Label("" + GlobalState.numberOfBars.get());
        numberOfBarsLabel.textProperty().bind(GlobalState.numberOfBars.asString());

        currentBarLabel.getStyleClass().add("label-light");
        dash.getStyleClass().add("label-light");
        numberOfBarsLabel.getStyleClass().add("label-light");

        hBox.getChildren().addAll(currentBarLabel, dash, numberOfBarsLabel);
        hBox.setMinWidth(50);
        hBox.setMaxWidth(50);
        hBox.setMinHeight(30);
        hBox.getStyleClass().add("display");

        return hBox;
    }

    private ToggleButton buildLockButton() {
        ToggleButton button = new ToggleButton("LOCK");
        button.setOnAction(e -> {
            if (button.isSelected()) {
                GlobalState.numberOfBarsLocked.set(true);
            } else {
                GlobalState.numberOfBarsLocked.set(false);
            }
        });
        return button;
    }

    private VBox buildClearButtons() {
        SectorHeader header = new SectorHeader("CLEAR CHORDSAVE");
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        Button thisbtn = new Button("THIS");
        Button allBtn = new Button("ALL");

        thisbtn.setOnAction(e -> {
            GlobalControllers.chordSaveController.clearThisChordSave(GlobalState.currentChordSave);
        });

        allBtn.setOnAction(e -> {
            GlobalControllers.chordSaveController.clearAllChordSaves();
        });

        thisbtn.setMinSize(100, 40);
        thisbtn.setMaxSize(100, 40);
        allBtn.setMinSize(100, 40);
        allBtn.setMaxSize(100, 40);

        hBox.getChildren().addAll(thisbtn, allBtn);
        vBox.getChildren().addAll(header, hBox);
        vBox.getStyleClass().add("sector");
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private VBox buildMuteButton() {
        VBox vBox = new VBox();
        SectorHeader header = new SectorHeader("GLOBAL MUTE");
        ToggleButton button = new ToggleButton("MUTE");
        button.setMinSize(100, 40);
        button.setMaxSize(100, 40);

        button.setOnAction(e -> {
            if (button.isSelected()) {
                GlobalSynth.bassSynth.mute(true);
                GlobalSynth.metronome.mute(true);
                GlobalSynth.chordSynth.mute(true);
            } else {
                GlobalSynth.bassSynth.mute(false);
                GlobalSynth.metronome.mute(false);
                GlobalSynth.chordSynth.mute(false);
            }
        });

        vBox.getChildren().addAll(header, button);
        vBox.getStyleClass().add("sector");
        return vBox;
    }
}
