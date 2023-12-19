package no.njoter.metrodronev2.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.ToggleButton;
import no.njoter.metrodronev2.global.GlobalState;
import no.njoter.metrodronev2.global.GlobalSynth;

import java.util.ArrayList;

public class ChordSaveButton extends ToggleButton {

    ArrayList<NoteToggleButton> notes;

    private IntegerProperty bassNoteNumber;
    private IntegerProperty numberOfBars;

    private int beatsPerMeasure;
    private int subdivision;
    private int index;

    public ChordSaveButton() {

        notes = new ArrayList<>();

        beatsPerMeasure = 4;
        subdivision = 4;

        this.bassNoteNumber = new SimpleIntegerProperty(-1);
        this.numberOfBars = new SimpleIntegerProperty(1);

        this.setMinSize(60, 50);
        this.setMaxSize(60, 50);
    }

    public ArrayList<NoteToggleButton> getNotes() {
        return notes;
    }

    public void setBassNoteNumber(int noteNumber) {
        this.bassNoteNumber.set(noteNumber);
    }

    public int getBassNoteNumber() {
        return bassNoteNumber.get();
    }

    public int getNumberOfBars() {
        return numberOfBars.get();
    }

    public void setNumberOfBars(int numberOfBars) {
        this.numberOfBars.set(numberOfBars);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setBeatsPerMeasure(int beatsPerMeasure) {
        if (beatsPerMeasure > GlobalSynth.metronome.MAX_BEATS) {
            this.beatsPerMeasure = GlobalSynth.metronome.MAX_BEATS;
        } else if (beatsPerMeasure < 1) {
            this.beatsPerMeasure = 1;
        } else {
            this.beatsPerMeasure = beatsPerMeasure;
        }
        GlobalSynth.metronome.setBeatsPerMeasure(this.beatsPerMeasure);
    }

    public int getBeatsPerMeasure() {
        return beatsPerMeasure;
    }

    public void setSubdivision(int subdivision) {
        if (subdivision > GlobalSynth.metronome.MAX_SUBDIVISIONS) {
            this.subdivision = GlobalSynth.metronome.MAX_SUBDIVISIONS;
        } else if (subdivision < 1) {
            this.subdivision = 1;
        } else {
            this.subdivision = subdivision;
        }
        GlobalSynth.metronome.setSubdivision(this.subdivision);
    }

    public int getSubdivision() {
        return subdivision;
    }
}
