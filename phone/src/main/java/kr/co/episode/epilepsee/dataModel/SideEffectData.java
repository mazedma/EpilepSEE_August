package kr.co.episode.epilepsee.dataModel;

import java.util.List;

public class SideEffectData {
    private String date;
    private String time;
    private List<String> effects;

    public SideEffectData() {
        // Default constructor required for calls to DataSnapshot.getValue(SideEffectData.class)
    }

    public SideEffectData(String date, String time, List<String> effects) {
        this.date = date;
        this.time = time;
        this.effects = effects;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getEffects() {
        return effects;
    }

    public void setEffects(List<String> effects) {
        this.effects = effects;
    }
}

