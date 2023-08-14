import java.time.LocalDateTime;
import java.time.Duration;

public class SeizureData {
    private LocalDateTime seizureTime;
    private String seizureTypePrimary;
    private String seizureTypeSecondary;
    private boolean seizurePredicted;
    private String seizureLocation;
    private boolean seizureDuringSleep;
    private Duration seizureDuration;
    private Duration recoveryTime;
    private String emergencyMedication;
    private String seizureReaction;
    private String seizureSymptomBody;
    private String seizureSymptomMovement;
    private String seizureSymptomEyes;
    private String seizureSymptomMouth;
    private String seizureSymptomSkinColor;
    private String seizureSymptomSuddenUrinationDefecation;
    private String seizureMemo;

    // 생성자, Getter, Setter 등이 있어야 합니다.

    // Getter 및 Setter 메서드 추가
    public LocalDateTime getSeizureTime() {
        return seizureTime;
    }

    public void setSeizureTime(LocalDateTime seizureTime) {
        this.seizureTime = seizureTime;
    }

    // ... (다른 필드에 대한 Getter 및 Setter 추가)
}
