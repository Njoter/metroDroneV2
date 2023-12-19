package no.njoter.metrodronev2.global;

import no.njoter.metrodronev2.controller.MetronomeController;
import no.njoter.metrodronev2.controller.SynthController;
import no.njoter.metrodronev2.model.SynthModel;

public class GlobalSynth {

    private static final SynthModel synthModel = new SynthModel();

    public static final MetronomeController metronome = new MetronomeController(
            synthModel, 0
    );
    public static final SynthController chordSynth = new SynthController(synthModel, 1);
    public static final SynthController bassSynth = new SynthController(synthModel, 2);

    public static void setSynths() {
        bassSynth.setInstrumentPatch(50);
        chordSynth.setInstrumentPatch(48);
    }

    public static void closeSynth() {
        synthModel.closeSynth();
    }
}
