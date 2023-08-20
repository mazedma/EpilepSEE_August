package kr.co.episode.epilepsee.activities;

public class Medication {
    private String medicationName;
    private String startDate;
    private String endDate;

    public Medication() {
        // 기본 생성자는 Firebase 데이터 스냅샷에서 필요합니다.
    }

    public Medication(String medicationName, String startDate, String endDate) {
        this.medicationName = medicationName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
