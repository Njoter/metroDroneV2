package no.njoter.metrodronev2.controller;

import no.njoter.metrodronev2.model.VolumeSlider;

import javax.sound.midi.MidiChannel;

public class VolumeController {

    public static void setVolumeHandler(VolumeSlider slider, MidiChannel channel) {
        slider.valueProperty().addListener(e -> {
            channel.controlChange(7, (int)slider.getValue());
        });
    }
}
