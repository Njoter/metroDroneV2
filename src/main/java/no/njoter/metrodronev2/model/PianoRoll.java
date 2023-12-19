package no.njoter.metrodronev2.model;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;

public class PianoRoll extends ScrollPane {

    private final ArrayList<NoteToggleButton> notes;

    public PianoRoll(int startNote, int endNote) {

        notes = new ArrayList<>();
        populateNotes(startNote, endNote);
        Collections.reverse(notes);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(notes);

        this.setFitToWidth(true);
        this.setContent(vBox);
    }

    private void populateNotes(int startNote, int endNote) {
        for (int i = startNote; i <= endNote; i++) {
            NoteToggleButton btn = new NoteToggleButton(i);
            btn.setPrefWidth(Double.MAX_VALUE);
            btn.getStyleClass().add("piano-button");
            btn.setText(btn.getNoteName());
            notes.add(btn);
        }
    }

    public ArrayList<NoteToggleButton> getNotes() {
        return notes;
    }
}