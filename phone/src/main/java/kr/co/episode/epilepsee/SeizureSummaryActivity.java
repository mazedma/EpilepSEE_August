package kr.co.episode.epilepsee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;

import kr.co.episode.epilepsee.dataModel.SeizureViewModel;

public class SeizureSummaryActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seizure_summary);

        //Firebase Database 참조 가져오기
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Intent로부터 parentKey 값을 가져옵니다.
        Intent intent = getIntent();
        String selectedKey = intent.getStringExtra("selectedKey");
        String selectedDate = intent.getStringExtra("selectedDate");


        TextView seizureDurationSummaryTextView = findViewById(R.id.seizureDurationSummaryTextView);
        TextView seizureTimeSummaryTextView = findViewById(R.id.seizureTimeSummaryTextView);
        TextView seizureTypePrimaryView = findViewById(R.id.seizureTypePrimaryView);
        TextView seizureTypeSecondaryView = findViewById(R.id.seizureTypeSecondaryView);
        TextView seizurePredictedView  = findViewById(R.id.seizurePredictedView);
        TextView seizureLocationView =findViewById(R.id.seizureLocationView);
        TextView seizureDuringSleepView = findViewById(R.id.seizureDuringSleepView);
        TextView recoveryTimeSummaryView = findViewById(R.id.recoveryTimeSummaryView);
        TextView emergencyMedicationView = findViewById(R.id.emergencyMedicationView);
        TextView seizureReactionView = findViewById(R.id.seizureReactionView);
        TextView symptomBodyView = findViewById(R.id.symptomBodyView);
        TextView symptomMovementView = findViewById(R.id.symptomMovementView);
        TextView symptomEyesView = findViewById(R.id.symptomEyesView);
        TextView symptomEyes2View = findViewById(R.id.symptomEyes2View);
        TextView symptomMouthView = findViewById(R.id.symptomMouthView);
        TextView symptomSkinColorView =findViewById(R.id.symptomSkinColorView);
        TextView symptomSuddenUriView = findViewById(R.id.symptomSuddenUriView);

        // Firebase에서 데이터 가져오기
        databaseReference.child(selectedDate).child("seizureData").child(selectedKey)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // 선택된 키에 해당하는 발작 데이터를 가져옵니다.
                            SeizureViewModel seizureData = dataSnapshot.getValue(SeizureViewModel.class);
                            if (seizureData != null) {
                                // seizureDuration과 seizureTime을 가져와 TextView에 설정합니다.
                                seizureDurationSummaryTextView.setText("발작 지속 시간: " + seizureData.getSeizureDuration());
                                seizureTimeSummaryTextView.setText("발작 시각: " + seizureData.getSeizureTime());
                                seizureTypePrimaryView.setText("발작 주요 유형: " + seizureData.getSeizureTypePrimary());
                                seizureTypeSecondaryView.setText("발작 부차 유형: " + seizureData.getSeizureTypeSecondary());
                                seizurePredictedView.setText("발작 예측 여부: " + (seizureData.isSeizurePredicted() ? "예측됨" : "예측되지 않음"));
                                seizureLocationView.setText("발생 장소: " + seizureData.getSeizureLocation());
                                seizureDuringSleepView.setText("수면 중 발작 여부: " + (seizureData.isSeizureDuringSleep() ? "수면 중" : "일반 상태"));
                                recoveryTimeSummaryView.setText("회복에 걸린 시간: " + seizureData.getRecoveryTime());
                                List<String> emergencyMedications = seizureData.getEmergencyMedication();
                                if (emergencyMedications != null && !emergencyMedications.isEmpty()) {
                                    StringBuilder emergencyMedicationText = new StringBuilder("사용된 긴급 약물:\n");
                                    for (String medication : emergencyMedications) {
                                        emergencyMedicationText.append("- ").append(medication).append("\n");
                                    }
                                    emergencyMedicationView.setText(emergencyMedicationText.toString());
                                }
                                seizureReactionView.setText("발작 후 반응: " + seizureData.getSeizureReaction());
                                symptomBodyView.setText("경련 증상(몸): " + seizureData.getSeizureSymptomBody());
                                symptomMovementView.setText("경련 증상(움직임): " + seizureData.getSeizureSymptomMovement());
                                symptomEyesView.setText("경련 증상(눈): " + seizureData.getSeizureSymptomEyes());
                                symptomEyes2View.setText("경련 증상(눈2): " + seizureData.getSeizureSymptomEyes2());
                                symptomMouthView.setText("경련 증상(입): " + seizureData.getSeizureSymptomMouth());
                                symptomSkinColorView.setText("경련 증상(피부색): " + seizureData.getSeizureSymptomSkinColor());
                                symptomSuddenUriView.setText("경련 증상(갑작스러운 배뇨): " + seizureData.getSeizureSymptomSuddenUrinationDefecation());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // 데이터베이스에서 데이터를 가져오는 도중 오류가 발생한 경우 처리
                        Log.e("SeizureSummaryActivity", "Firebase 데이터 가져오기 오류: " + databaseError.getMessage());
                    }
                });
    }

}