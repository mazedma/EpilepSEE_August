package kr.co.episode.epilepsee;

public class SideEffect {
    private String sideEffectTime;
    private String sideEffectDate;
    private String sideEffectType;

    public SideEffect() {
        // Default constructor required for Firebase
    }

    public SideEffect(String sideEffectTime, String sideEffectDate, String sideEffectType) {
        this.sideEffectTime = sideEffectTime;
        this.sideEffectDate = sideEffectDate;
        this.sideEffectType = sideEffectType;
    }

    public String getSideEffectTime() {
        return sideEffectTime;
    }

    public String getSideEffectDate() {
        return sideEffectDate;
    }

    public String getSideEffectType() {
        return sideEffectType;
    }
}
