package no.njoter.metrodronev2.controller;

import no.njoter.metrodronev2.model.SynthModel;

import javax.sound.midi.MidiChannel;

public class SynthController {

    private final MidiChannel channel;

    public static final int VELOCITY_LOUD = 90;
    public static final int VELOCITY_MEDIUM = 50;
    public static final int VELOCITY_SOFT = 30;

    public SynthController(SynthModel synth, int channelNumber) {
        this.channel = synth.getChannel(channelNumber);
    }

    public void setInstrumentPatch(int patch) {
        this.channel.programChange(patch);
    }

    public void playNote(int noteNumber, int velocity) {
        this.channel.noteOn(noteNumber, velocity);
    }

    public void mute(boolean bool) {
        this.channel.setMute(bool);
    }

    public void stopAll() {
        this.channel.allNotesOff();
    }

    public void stopNote(int noteNumber) {
        this.channel.noteOff(noteNumber);
    }

    public void setVolume(int volume) {
        this.channel.controlChange(7, volume);
    }

    public int getCurrentPatchNumber() {
        return this.channel.getProgram();
    }

    public MidiChannel getChannel() {
        return channel;
    }
}
