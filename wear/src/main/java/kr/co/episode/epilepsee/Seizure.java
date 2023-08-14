package kr.co.episode.epilepsee;

public class Seizure {
    private String seizureTime;
    private String seizureDuration;
    private String seizureDate; // 추가된 필드

    public Seizure() {
        // Default constructor required for Firebase
    }

    public Seizure(String seizureTime, String seizureDuration, String seizureDate) {
        this.seizureTime = seizureTime;
        this.seizureDuration = seizureDuration;
        this.seizureDate = seizureDate;
    }

    public String getSeizureTime() {
        return seizureTime;
    }

    public String getSeizureDuration() {
        return seizureDuration;
    }

    public String getSeizureDate() {
        return seizureDate;
    }
}


