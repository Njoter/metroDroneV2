package no.njoter.metrodronev2.controller;

import no.njoter.metrodronev2.global.GlobalControllers;
import no.njoter.metrodronev2.global.GlobalState;
import no.njoter.metrodronev2.global.GlobalSynth;
import no.njoter.metrodronev2.model.NoteToggleButton;
import no.njoter.metrodronev2.model.PianoRoll;

import java.util.ArrayList;

public class PianoRollController {

    private PianoRoll pianoRoll;
    private ArrayList<NoteToggleButton> notes;

    public PianoRollController(PianoRoll pianoRoll) {

        this.pianoRoll = pianoRoll;
        this.notes = pianoRoll.getNotes();

        for (NoteToggleButton note : notes) {
            setOnAction(note);
        }
    }

    private void setOnAction(NoteToggleButton note) {

        note.setOnAction(e -> {
            if (note.isSelected()) {
                GlobalSynth.chordSynth.playNote(note.getNoteNumber(), SynthController.VELOCITY_MEDIUM);
                addToCurrentChordSave(note);
            } else {
                GlobalSynth.chordSynth.stopNote(note.getNoteNumber());
                subtractFromCurrentChordSave(note);
            }
        });
    }

    private void addToCurrentChordSave(NoteToggleButton note) {
        GlobalState.currentChordSave.getNotes().add(note);
    }

    private void subtractFromCurrentChordSave(NoteToggleButton note) {
        for (int i = 0; i < GlobalState.currentChordSave.getNotes().size(); i++) {
            if (GlobalState.currentChordSave.getNotes().get(i).getNoteNumber() == note.getNoteNumber()) {
                GlobalState.currentChordSave.getNotes().remove(i);
            }
        }
    }

    public void selectNotes(ArrayList<NoteToggleButton> notes) {
        int i = 0;
        int j = 0;
        for (NoteToggleButton noteInChordSave : notes) {
            for (NoteToggleButton noteInPianoRoll : GlobalControllers.pianoRollController.notes) {
                if (noteInChordSave.getNoteNumber() == noteInPianoRoll.getNoteNumber()) {
                    GlobalControllers.pianoRollController.notes.get(j).setSelected(true);
                }
                j++;
            }
            i++;
            j = 0;
        }
    }

    public void unselectAllNotes() {
        for (NoteToggleButton note : pianoRoll.getNotes()) {
            note.setSelected(false);
        }
    }

    public PianoRoll getPianoRoll() {
        return pianoRoll;
    }
}
