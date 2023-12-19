package no.njoter.metrodronev2.global;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import no.njoter.metrodronev2.model.ChordSaveButton;

public class GlobalState {

    // currentBar updated by MetronomeController
    public static IntegerProperty currentBar = new SimpleIntegerProperty(1);
    public static IntegerProperty barsPassed = new SimpleIntegerProperty(-1);
    public static IntegerProperty numberOfBars = new SimpleIntegerProperty(1);

    public static ChordSaveButton currentChordSave = new ChordSaveButton();
    public static IntegerProperty currentBassNote = new SimpleIntegerProperty(-1);

    public static boolean advance = false;
    public static BooleanProperty timeLocked = new SimpleBooleanProperty(false);
    public static BooleanProperty numberOfBarsLocked = new SimpleBooleanProperty(false);

    public static void setCurrentChordSave(ChordSaveButton chordSave) {
        currentChordSave = chordSave;
    }

    public static void setCurrentBassNote(int noteNumber) {
        currentBassNote.set(noteNumber);
    }

    public static void setNumberOfBars(int value) {
        numberOfBars.set(value);
    }
}
