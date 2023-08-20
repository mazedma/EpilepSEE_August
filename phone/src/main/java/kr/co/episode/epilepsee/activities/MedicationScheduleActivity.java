package kr.co.episode.epilepsee.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kr.co.episode.epilepsee.databinding.ActivityMedicationScheduleBinding;

public class MedicationScheduleActivity extends AppCompatActivity {

    ActivityMedicationScheduleBinding activityMedicationScheduleBinding;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMedicationScheduleBinding = ActivityMedicationScheduleBinding.inflate(getLayoutInflater());
        setContentView(activityMedicationScheduleBinding.getRoot());

        //액션바에 백 버튼 추가
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle("복용 일정 등록"); // 화면 제목 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼

        databaseReference = FirebaseDatabase.getInstance().getReference().child("medications");

        EditText editTextMedication = activityMedicationScheduleBinding.editTextMedication;
        EditText editTextStartDate = activityMedicationScheduleBinding.editTextStartDate;
        EditText editTextEndDate = activityMedicationScheduleBinding.editTextEndDate;
        Button buttonSave = activityMedicationScheduleBinding.buttonSave;

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMedication();
            }
        });
    }

    private void saveMedication() {
        String medicationName = activityMedicationScheduleBinding.editTextMedication.getText().toString();
        String startDate = activityMedicationScheduleBinding.editTextStartDate.getText().toString();
        String endDate = activityMedicationScheduleBinding.editTextEndDate.getText().toString();

        // Medication 객체 생성
        Medication medication = new Medication(medicationName, startDate, endDate);

        // Firebase에 데이터 저장
        databaseReference.push().setValue(medication);
    }



    // 액션바의 백 버튼 클릭 이벤트 처리
    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return true;
    }
}