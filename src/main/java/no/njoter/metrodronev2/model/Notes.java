package no.njoter.metrodronev2.model;

public class Notes {

    private static final String[] noteNames = {
            "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "Bb", "B"
    };

    public static String getNoteName(int noteNumber) {
        while (noteNumber - 12 >= 0) {
            noteNumber -= 12;
        }
        return noteNames[noteNumber];
    }
}
