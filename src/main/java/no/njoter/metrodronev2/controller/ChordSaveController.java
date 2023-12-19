package no.njoter.metrodronev2.controller;

import no.njoter.metrodronev2.global.GlobalControllers;
import no.njoter.metrodronev2.global.GlobalState;
import no.njoter.metrodronev2.global.GlobalSynth;
import no.njoter.metrodronev2.model.ChordSaveButton;
import no.njoter.metrodronev2.model.NoteToggleButton;

public class ChordSaveController {

    public final ChordSaveButton[] chordSaveButtons;

    public ChordSaveController(ChordSaveButton[] chordSaveButtons) {
       this.chordSaveButtons = chordSaveButtons;

       for (ChordSaveButton button : chordSaveButtons) {
           setOnAction(button);
       }

       setCurrentBarListener();
       setTimeLockedListener();
       setNumberOfBarsLockedListener();
    }

    private void setCurrentBarListener() {

        GlobalState.barsPassed.addListener(e -> {

            if (GlobalState.barsPassed.get() >= GlobalState.numberOfBars.get()) {

                GlobalState.barsPassed.set(0);

                if (GlobalState.advance) {
                    int index = GlobalState.currentChordSave.getIndex();
                    unselectAll();

                    if (index + 1 > chordSaveButtons.length - 1) {
                        selectChordSave(chordSaveButtons[0]);
                    } else if (chordSaveButtons[index + 1].isDisabled()) {
                        selectChordSave(chordSaveButtons[0]);
                    } else {
                        selectChordSave(chordSaveButtons[index + 1]);
                    }
                }
            }

            GlobalState.currentBar.set(GlobalState.barsPassed.get() +1);
        });
    }

    private void setTimeLockedListener() {
        GlobalState.timeLocked.addListener(e -> {
            if (GlobalState.timeLocked.get() == true) {
                setBeatsPerMeasureInAllChordSaves(GlobalState.currentChordSave.getBeatsPerMeasure());
                setSubdivisionInAllChordSaves(GlobalState.currentChordSave.getSubdivision());
            }
        });
    }

    private void setNumberOfBarsLockedListener() {
        GlobalState.numberOfBarsLocked.addListener(e -> {
            if (GlobalState.numberOfBarsLocked.get()) {
                setNumberOfBarsInAllChordSaves(GlobalState.currentChordSave.getNumberOfBars());
            }
        });
    }

    public void setBeatsPerMeasureInAllChordSaves(int beatsPerMeasure) {
        for (ChordSaveButton button : chordSaveButtons) {
            button.setBeatsPerMeasure(beatsPerMeasure);
        }
    }

    public void setNumberOfBarsInAllChordSaves(int numberOfBars) {
        for (ChordSaveButton button : chordSaveButtons) {
            button.setNumberOfBars(numberOfBars);
        }
    }

    public void setSubdivisionInAllChordSaves(int subdivision) {
        for (ChordSaveButton button : chordSaveButtons) {
            button.setSubdivision(subdivision);
        }
    }

    private void setOnAction(ChordSaveButton button) {
        button.setOnAction(e -> {
            if (button.isSelected()) {
                unselectAll();
                selectChordSave(button);
            } else {
                button.setSelected(true);
            }
        });
    }

    public void selectChordSave(ChordSaveButton button) {

        button.setSelected(true);
        GlobalState.setCurrentChordSave(button);
        GlobalSynth.chordSynth.stopAll();
        GlobalControllers.pianoRollController.unselectAllNotes();

        restoreBass(button);
        restoreTimesignature(button);
        restoreNumberOfBars(button);
        restorePianoRoll(button);

        if (GlobalSynth.metronome.isRunning()) {
            GlobalSynth.metronome.stopAll();
            GlobalSynth.metronome.start();
        }
    }

    private void restorePianoRoll(ChordSaveButton button) {
        GlobalControllers.pianoRollController.selectNotes(button.getNotes());
        for (NoteToggleButton note : GlobalControllers.pianoRollController.getPianoRoll().getNotes()) {
            if (note.isSelected()) {
                GlobalSynth.chordSynth.playNote(note.getNoteNumber(), SynthController.VELOCITY_MEDIUM);
            }
        }
    }

    private void restoreBass(ChordSaveButton button) {
        GlobalState.setCurrentBassNote(button.getBassNoteNumber());
    }

    private void restoreTimesignature(ChordSaveButton button) {
        GlobalSynth.metronome.setBeatsPerMeasure(button.getBeatsPerMeasure());
        GlobalSynth.metronome.setSubdivision(button.getSubdivision());
    }

    private void restoreNumberOfBars(ChordSaveButton button) {
        GlobalState.setNumberOfBars(button.getNumberOfBars());
    }

    private void unselectAll() {
        for (ChordSaveButton button : chordSaveButtons) {
            if (button.isSelected()) {
                button.setSelected(false);
            }
        }
    }

    public void addChordSave() {
        int index = 0;
        for (int i = 0; i < chordSaveButtons.length; i++) {
            if (chordSaveButtons[i].isDisabled()) {
                index = i;
                break;
            }
        }
        if (index != 0) {
            chordSaveButtons[index].setDisable(false);
            if (GlobalState.numberOfBarsLocked.get()) {
                chordSaveButtons[index].setNumberOfBars(GlobalState.numberOfBars.get());
            }
        }
    }

    public void subtractChordSave() {
        int index = 0;
        for (int i = 0; i < chordSaveButtons.length; i++) {
            if (chordSaveButtons[i].isDisabled()) {
                index = i;
                break;
            }
        }
        if (index != 1) {
            if (index == 0) {
                if (chordSaveButtons[chordSaveButtons.length - 1].isSelected()) {
                    selectChordSave(chordSaveButtons[chordSaveButtons.length - 2]);
                }
                disableChordSave(chordSaveButtons.length - 1);
                clearThisChordSave(chordSaveButtons[chordSaveButtons.length - 1]);
            } else {
                if (chordSaveButtons[index - 1].isSelected()) {
                    selectChordSave(chordSaveButtons[index - 2]);
                }
                disableChordSave(index - 1);
                clearThisChordSave(chordSaveButtons[index - 1]);
            }
        }
    }

    private void disableChordSave(int index) {
        if (index != 0) {
            chordSaveButtons[index].setDisable(true);
            chordSaveButtons[index].setSelected(false);
        }
    }

    public void clearThisChordSave(ChordSaveButton button) {
        button.setBassNoteNumber(-1);
        button.setNumberOfBars(1);
        button.getNotes().clear();
        GlobalControllers.pianoRollController.unselectAllNotes();
        if (button.isSelected()) {
            GlobalState.setCurrentBassNote(-1);
            GlobalSynth.chordSynth.stopAll();
        }
    }

    public void clearAllChordSaves() {
        for (ChordSaveButton button : chordSaveButtons) {
            clearThisChordSave(button);
        }
        GlobalState.numberOfBars.set(1);
        GlobalSynth.chordSynth.stopAll();
    }
}
