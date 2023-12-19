package no.njoter.metrodronev2.model;

import javafx.scene.control.ToggleButton;

public class NoteToggleButton extends ToggleButton {

    private final int noteNumber;
    private final String noteName;

    public NoteToggleButton(int noteNumber) {
        this.noteNumber = noteNumber;
        this.noteName = Notes.getNoteName(noteNumber);
    }

    public int getNoteNumber() {
        return noteNumber;
    }

    public String getNoteName() {
        return noteName;
    }
}
