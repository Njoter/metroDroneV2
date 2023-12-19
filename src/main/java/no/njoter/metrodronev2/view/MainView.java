package no.njoter.metrodronev2.view;

import javafx.scene.layout.*;

public class MainView extends VBox {

    public final MetronomeView metronomeView;
    public final ChordSaveView chordSaveView;
    public final PianoRollView pianoRollView;
    public final BassButtonsView bassButtonsView;

    public MainView() {
        metronomeView = new MetronomeView();
        chordSaveView = new ChordSaveView();
        pianoRollView = new PianoRollView();
        bassButtonsView = new BassButtonsView();
        buildUI();
    }

    private void buildUI() {
        GridPane gridPane = buildGridPane();

        setConstraints(gridPane);

        VBox.setVgrow(gridPane, Priority.ALWAYS);
        this.getChildren().addAll(gridPane, bassButtonsView);
    }

    private GridPane buildGridPane() {
        GridPane gridPane = new GridPane();

        gridPane.add(metronomeView, 0, 0);
        gridPane.add(chordSaveView, 1, 0);
        gridPane.add(pianoRollView, 2, 0);

        return gridPane;
    }

    private void setConstraints(GridPane gridPane) {
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();

        col1.setPercentWidth(33);
        col2.setPercentWidth(34);
        col3.setPercentWidth(33);

        RowConstraints row1 = new RowConstraints();
        row1.setVgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(col1, col2, col3);
        gridPane.getRowConstraints().addAll(row1);
    }
}
