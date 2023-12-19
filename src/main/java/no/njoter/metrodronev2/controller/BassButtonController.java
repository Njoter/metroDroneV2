package no.njoter.metrodronev2.controller;

import no.njoter.metrodronev2.global.GlobalState;
import no.njoter.metrodronev2.model.NoteToggleButton;

import java.util.ArrayList;

public class BassButtonController {

    public static void setHandlers(ArrayList<NoteToggleButton> buttons, SynthController synthController) {

        for (NoteToggleButton button : buttons) {
            button.setOnAction(e -> {
                if (button.isSelected()) {
                    clearAllButtons(buttons, synthController);
                    GlobalState.currentChordSave.setBassNoteNumber(button.getNoteNumber());
                    GlobalState.setCurrentBassNote(button.getNoteNumber());
                } else {
                    clearAllButtons(buttons, synthController);
                    GlobalState.currentChordSave.setBassNoteNumber(-1);
                    GlobalState.setCurrentBassNote(-1);
                }
            });
        }

        setBassnoteListener(buttons, synthController);
    }

    private static void setBassnoteListener(ArrayList<NoteToggleButton> buttons, SynthController synthController) {
        GlobalState.currentBassNote.addListener(e -> {

            clearAllButtons(buttons, synthController);

            if (GlobalState.currentBassNote.get() != -1) {

                for (NoteToggleButton button : buttons) {
                    if (button.getNoteNumber() == GlobalState.currentBassNote.get()) {
                        button.setSelected(true);
                        synthController.playNote(button.getNoteNumber(), SynthController.VELOCITY_LOUD);
                    }
                }
            }
        });
    }

    private static void clearAllButtons(ArrayList<NoteToggleButton> buttons, SynthController synthController) {
        for (NoteToggleButton button : buttons) {
            synthController.stopAll();
            button.setSelected(false);
        }
    }
}
