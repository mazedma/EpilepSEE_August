package kr.co.episode.epilepsee.dataModel;

import java.util.Date;

public class SeizureData {
    //데이터 변수명 작성
    private Date seizureTime; //발작 일시
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

    // 생성자,Getter 및 Setter 메서드 추가

    public SeizureData(){
        //Firebase와의 연동을 위한 기본 생성자
    }

    //Getter Setter
    public Date getSeizureTime(){
        return seizureTime;
    }
    public void setSeizureTime(Date seizureTime) {
        this.seizureTime = seizureTime;
    }
    public String getSeizureTypePrimary() {
        return seizureTypePrimary;
    }
    public void setSeizureTypePrimary(String seizureTypePrimary) {
        this.seizureTypePrimary = seizureTypePrimary;
    }
    public String getSeizureTypeSecondary() {
        return seizureTypeSecondary;
    }

    public void setSeizureTypeSecondary(String seizureTypeSecondary) {
        this.seizureTypeSecondary = seizureTypeSecondary;
    }

    public boolean isSeizurePredicted() {
        return seizurePredicted;
    }

    public void setSeizurePredicted(boolean seizurePredicted) {
        this.seizurePredicted = seizurePredicted;
    }

    public String getSeizureLocation() {
        return seizureLocation;
    }

    public void setSeizureLocation(String seizureLocation) {
        this.seizureLocation = seizureLocation;
    }

    public boolean isSeizureDuringSleep() {
        return seizureDuringSleep;
    }

    public void setSeizureDuringSleep(boolean seizureDuringSleep) {
        this.seizureDuringSleep = seizureDuringSleep;
    }

    public String getSeizureDuration() {
        return seizureDuration;
    }

    public void setSeizureDuration(String seizureDuration) {
        this.seizureDuration = seizureDuration;
    }

    public String getRecoveryTime() {
        return recoveryTime;
    }

    public void setRecoveryTime(String recoveryTime) {
        this.recoveryTime = recoveryTime;
    }

    public String getEmergencyMedication() {
        return emergencyMedication;
    }

    public void setEmergencyMedication(String emergencyMedication) {
        this.emergencyMedication = emergencyMedication;
    }

    public String getSeizureReaction() {
        return seizureReaction;
    }

    public void setSeizureReaction(String seizureReaction) {
        this.seizureReaction = seizureReaction;
    }

    public String getSeizureSymptomBody() {
        return seizureSymptomBody;
    }

    public void setSeizureSymptomBody(String seizureSymptomBody) {
        this.seizureSymptomBody = seizureSymptomBody;
    }

    public String getSeizureSymptomMovement() {
        return seizureSymptomMovement;
    }

    public void setSeizureSymptomMovement(String seizureSymptomMovement) {
        this.seizureSymptomMovement = seizureSymptomMovement;
    }

    public String getSeizureSymptomEyes() {
        return seizureSymptomEyes;
    }

    public void setSeizureSymptomEyes(String seizureSymptomEyes) {
        this.seizureSymptomEyes = seizureSymptomEyes;
    }

    public String getSeizureSymptomMouth() {
        return seizureSymptomMouth;
    }

    public void setSeizureSymptomMouth(String seizureSymptomMouth) {
        this.seizureSymptomMouth = seizureSymptomMouth;
    }

    public String getSeizureSymptomSkinColor() {
        return seizureSymptomSkinColor;
    }

    public void setSeizureSymptomSkinColor(String seizureSymptomSkinColor) {
        this.seizureSymptomSkinColor = seizureSymptomSkinColor;
    }

    public String getSeizureSymptomSuddenUrinationDefecation() {
        return seizureSymptomSuddenUrinationDefecation;
    }

    public void setSeizureSymptomSuddenUrinationDefecation(String seizureSymptomSuddenUrinationDefecation) {
        this.seizureSymptomSuddenUrinationDefecation = seizureSymptomSuddenUrinationDefecation;
    }

    public String getSeizureMemo() {
        return seizureMemo;
    }

    public void setSeizureMemo(String seizureMemo) {
        this.seizureMemo = seizureMemo;
    }
}
