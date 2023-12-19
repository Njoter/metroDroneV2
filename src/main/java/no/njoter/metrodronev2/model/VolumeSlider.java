package no.njoter.metrodronev2.model;

import javafx.scene.control.Slider;

public class VolumeSlider extends Slider {

    private static final int MIN_VOLUME = 0;
    private static final int MAX_VOLUME = 127;
    private static final int INIT_VOLUME = 100;

    public VolumeSlider() {
        this.setMin(MIN_VOLUME);
        this.setMax(MAX_VOLUME);
        this.setValue(INIT_VOLUME);
        this.setMinWidth(200);
        this.setMaxWidth(200);
    }
}
