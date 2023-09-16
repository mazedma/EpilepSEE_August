package kr.co.episode.epilepsee.activities;

import java.util.ArrayList;

public class Medication {
    private String medicationName;
    private String dosage;
    private ArrayList<String> timing;
    private String dosageTimings;

    public Medication() {
        // 기본 생성자는 Firebase 데이터 스냅샷에서 필요합니다.
    }

    public Medication(String medicationName, String dosage, ArrayList<String> timing, String dosageTimings) {
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.timing = timing;
        this.dosageTimings = dosageTimings;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public ArrayList<String> getTiming() {
        return timing;
    }

    public String getDosageTimings() {
        return dosageTimings;
    }
}
