package kr.co.episode.epilepsee.dataModel;
//23/08.15 브랜치확인용ㅇㅇㅈddddddd

import androidx.lifecycle.ViewModel;

//프레그먼트 데이터 저장할 ViewModel 생성 23.08.14
public class SeizureViewModel extends ViewModel {

    private String seizureTime; //발작 발생 시각
    private String seizureDate; //발작 발생 날짜
    private String seizureTypePrimary;  //발작 유형(구분1)
    private String seizureTypeSecondary; //발작 유형(구분2)
    private boolean seizurePredicted; //발작 예측
    private String seizureLocation; //발생 장소
    private boolean seizureDuringSleep; //수면중 여부
    private String seizureDuration; //발작 지속 시간
    private String recoveryTime; //회복에 걸린 시간
    private String emergencyMedication; //사용된 긴급 약물
    private String seizureReaction; //발작 후 반응
    private String seizureSymptomBody; //경련 증상-몸
    private String seizureSymptomMovement; //경련 증상-움직임
    private String seizureSymptomEyes; //경련 증상-눈
    private String seizureSymptomMouth; // 경련 증상-입
    private String seizureSymptomSkinColor; //경련 증상- 피부색
    private String seizureSymptomSuddenUrinationDefecation; //경련증상-갑작스러운배뇨,배변
    private String seizureMemo; //메모

    // Getter 메서드
    public String getSeizureTime() {
        return seizureTime;
    }
    public String getSeizureDate() {
        return seizureDate;
    }
    public String getSeizureTypePrimary() {
        return seizureTypePrimary;
    }
    public String getSeizureTypeSecondary() {
        return seizureTypeSecondary;
    }
    public boolean isSeizurePredicted() {
        return seizurePredicted;
    }
    public String getSeizureLocation() {
        return seizureLocation;
    }
    public boolean isSeizureDuringSleep() {
        return seizureDuringSleep;
    }
    public String getSeizureDuration() {
        return seizureDuration;
    }
    public String getRecoveryTime() {
        return recoveryTime;
    }
    public String getEmergencyMedication() {
        return emergencyMedication;
    }
    public String getSeizureReaction() {
        return seizureReaction;
    }
    public String getSeizureSymptomBody() {
        return seizureSymptomBody;
    }
    public String getSeizureSymptomMovement() {
        return seizureSymptomMovement;
    }
    public String getSeizureSymptomEyes() {
        return seizureSymptomEyes;
    }
    public String getSeizureSymptomMouth() {
        return seizureSymptomMouth;
    }
    public String getSeizureSymptomSkinColor() {
        return seizureSymptomSkinColor;
    }
    public String getSeizureSymptomSuddenUrinationDefecation() {
        return seizureSymptomSuddenUrinationDefecation;
    }
    public String getSeizureMemo() {
        return seizureMemo;
    }

    // Setter 메서드
    public void setSeizureTime(String seizureTime) {
        this.seizureTime = seizureTime;
    }
    public void setSeizureDate(String seizureDate) {
        this.seizureDate = seizureDate;
    }
    public void setSeizureTypePrimary(String seizureTypePrimary) {
        this.seizureTypePrimary = seizureTypePrimary;
    }
    public void setSeizureTypeSecondary(String seizureTypeSecondary) {
        this.seizureTypeSecondary = seizureTypeSecondary;
    }
    public void setSeizurePredicted(boolean seizurePredicted) {
        this.seizurePredicted = seizurePredicted;
    }
    public void setSeizureLocation(String seizureLocation) {
        this.seizureLocation = seizureLocation;
    }
    public void setSeizureDuringSleep(boolean seizureDuringSleep) {
        this.seizureDuringSleep = seizureDuringSleep;
    }
    public void setSeizureDuration(String seizureDuration) {
        this.seizureDuration = seizureDuration;
    }
    public void setRecoveryTime(String recoveryTime) {
        this.recoveryTime = recoveryTime;
    }
    public void setEmergencyMedication(String emergencyMedication) {
        this.emergencyMedication = emergencyMedication;
    }
    public void setSeizureReaction(String seizureReaction) {
        this.seizureReaction = seizureReaction;
    }
    public void setSeizureSymptomBody(String seizureSymptomBody) {
        this.seizureSymptomBody = seizureSymptomBody;
    }
    public void setSeizureSymptomMovement(String seizureSymptomMovement) {
        this.seizureSymptomMovement = seizureSymptomMovement;
    }
    public void setSeizureSymptomEyes(String seizureSymptomEyes) {
        this.seizureSymptomEyes = seizureSymptomEyes;
    }
    public void setSeizureSymptomMouth(String seizureSymptomMouth) {
        this.seizureSymptomMouth = seizureSymptomMouth;
    }
    public void setSeizureSymptomSkinColor(String seizureSymptomSkinColor) {
        this.seizureSymptomSkinColor = seizureSymptomSkinColor;
    }
    public void setSeizureSymptomSuddenUrinationDefecation(String seizureSymptomSuddenUrinationDefecation) {
        this.seizureSymptomSuddenUrinationDefecation = seizureSymptomSuddenUrinationDefecation;
    }
    public void setSeizureMemo(String seizureMemo) {
        this.seizureMemo = seizureMemo;
    }
}
