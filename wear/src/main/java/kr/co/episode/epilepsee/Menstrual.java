package kr.co.episode.epilepsee;

public class Menstrual {
    private boolean menstrualBool;

    public Menstrual() {
        // Default constructor required for Firebase
    }

    public Menstrual(boolean menstrualBool) {
        this.menstrualBool = menstrualBool;
    }

    public boolean isMenstrualBool() {
        return menstrualBool;
    }
}

