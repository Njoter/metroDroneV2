package no.njoter.metrodronev2.global;

import no.njoter.metrodronev2.controller.ChordSaveController;
import no.njoter.metrodronev2.controller.PianoRollController;
import no.njoter.metrodronev2.model.ChordSaveButton;
import no.njoter.metrodronev2.model.NoteToggleButton;
import no.njoter.metrodronev2.model.PianoRoll;

import java.util.ArrayList;

public class GlobalControllers {

    public static ChordSaveController chordSaveController;
    public static PianoRollController pianoRollController;

    public static void setChordSaveController(ChordSaveButton[] chordSaveButtons) {
        chordSaveController = new ChordSaveController(chordSaveButtons);
    }

    public static void setPianoRollController(PianoRoll pianoRoll) {
        pianoRollController = new PianoRollController(pianoRoll);
    }
}
