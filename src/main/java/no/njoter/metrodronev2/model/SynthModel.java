package no.njoter.metrodronev2.model;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.Arrays;

public class SynthModel {

    private Synthesizer synth;
    private MidiChannel[] channel;

    public SynthModel() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channel = synth.getChannels();
        } catch (MidiUnavailableException mue) {
            System.out.println("Midi device unavailable!");
        }
    }

    public MidiChannel getChannel(int channelNumber) {
        return channel[channelNumber];
    }

    public void closeSynth() {
        synth.close();
    }
}
