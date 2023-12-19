package no.njoter.metrodronev2.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;
import no.njoter.metrodronev2.global.GlobalState;
import no.njoter.metrodronev2.model.SynthModel;

import javax.sound.midi.MidiChannel;

public class MetronomeController {

    private final MidiChannel channel;

    public static final int DEFAULT_VELOCITY = 60;
    public static final int MIN_BPM = 40;
    public static final int MAX_BPM = 220;
    public final int MAX_BEATS = 16;
    public final int MAX_SUBDIVISIONS = 32;

    private IntegerProperty currentBpm;
    private IntegerProperty beatsPerMeasure;
    private IntegerProperty subdivision;
    private int currentBeat;

    private Timeline timeline;

    public MetronomeController(SynthModel synth, int channelNumber) {
        this.channel = synth.getChannel(channelNumber);

        currentBpm = new SimpleIntegerProperty(90);
        beatsPerMeasure = new SimpleIntegerProperty(4);
        subdivision = new SimpleIntegerProperty(4);
        currentBeat = 1;

        this.timeline = new Timeline();
        this.channel.programChange(115);
    }

    public void start() {

        int ms = (int)((60000.0 / (currentBpm.get() * (0.25 * subdivision.get()))) + 0.5);
        timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(ms),
                        event -> {
                            this.channel.allNotesOff();
                            if (currentBeat == 1) {
                                this.channel.noteOn(86, DEFAULT_VELOCITY);
                                GlobalState.barsPassed.set(GlobalState.barsPassed.get() + 1);
                            } else {
                                this.channel.noteOn(81, DEFAULT_VELOCITY);
                            }
                            updateCurrentBeat();
                        }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateCurrentBeat() {
        if (currentBeat < beatsPerMeasure.get()) {
            currentBeat++;
        } else {
            currentBeat = 1;
        }
    }

    public boolean isRunning() {
        return this.timeline.getStatus() == Animation.Status.RUNNING;
    }

    public void stopAll() {
        timeline.stop();
        this.channel.allNotesOff();
        currentBeat = 1;
    }

    public MidiChannel getChannel() {
        return channel;
    }

    public void setVolume(int volume) {
        this.channel.controlChange(7, volume);
    }

    public void mute(boolean mute) {
        this.channel.setMute(mute);
    }

    public IntegerProperty currentBpmProperty() {
        return currentBpm;
    }

    public IntegerProperty beatsPerMeasureProperty() {
        return beatsPerMeasure;
    }

    public void setBeatsPerMeasure(int beatsPerMeasure) {
        this.beatsPerMeasure.set(beatsPerMeasure);
    }

    public IntegerProperty subdivisionProperty() {
        return subdivision;
    }

    public void setSubdivision(int subdivision) {
        this.subdivision.set(subdivision);
    }
}
