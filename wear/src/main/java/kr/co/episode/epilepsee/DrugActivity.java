package kr.co.episode.epilepsee;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import kr.co.episode.epilepsee.databinding.ActivityDrugBinding;

public class DrugActivity extends Activity {

    private ActivityDrugBinding binding;

    long now = System.currentTimeMillis(); // 현재 시간 가져오기
    Date mDate = new Date(now); // Date 형식으로 고치기
    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd"); // 시간을 나타낼 포맷 설정
    String getTime = simpleDate.format(mDate); // getTime 변수에 값을 저장

    // 다른 Activity 접근
    public static Context context_drug; // context 변수 선언
    public String checkedRadioDrug; // 다른 Activity에서 접근할 변수 : 라디오 선택값
    public Date recordedTimeDrug;  // 현재시간

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDrugBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.date.setText(getTime); // 오늘날짜 화면에 출력

        // 라디오버튼 결과값 관련
        context_drug = this; // onCreate에서 this 할당

        // Firebase로부터 데이터 가져오기
        getDataFromFirebase();

        // 기록하기 버튼 클릭 리스너
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 라디오버튼 결과값 할당
                int checkedRadioButtonId = binding.radioGroup.getCheckedRadioButtonId(); // 체크된 라디오버튼 아이디 가져오기
                RadioButton radioButton = findViewById(checkedRadioButtonId); // 받은 id 값으로 해당 뷰 불러오기
                checkedRadioDrug = radioButton.getText().toString(); // text값 가져와서 변수에 저장

                recordedTimeDrug = mDate; // 현재시간 할당

                // 확인 화면 띄우기
                Intent intent = new Intent(getApplicationContext(), DrugCompleteActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getDataFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(); // 루트 경로를 가져옴
        String todayDate = getTime; // 오늘 날짜를 가져옴

        // 오늘 날짜를 사용하여 경로 설정
        DatabaseReference todayDataReference = databaseReference.child(todayDate);

        // 데이터 가져오기
        todayDataReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        // "medicationList" 아래의 데이터 가져오기
                        DataSnapshot medicationListSnapshot = dataSnapshot.child("medicationList");
                        StringBuilder medicationListText = new StringBuilder();

                        for (DataSnapshot medicationSnapshot : medicationListSnapshot.getChildren()) {
                            String dosage = medicationSnapshot.child("dosage").getValue(String.class);
                            String dosageTimings = medicationSnapshot.child("dosageTimings").getValue(String.class);
                            String medicationName = medicationSnapshot.child("medicationName").getValue(String.class);

                            // 하나의 문자열로 합치기
                            String medicationInfo = dosage + " " + dosageTimings + " " + medicationName;
                            medicationListText.append(medicationInfo).append("\n");
                        }

                        // TextView에 값을 설정
                        binding.dosageTextView.setText(medicationListText.toString());

                        // "timing" 아래의 데이터 가져오기
                        DataSnapshot timingSnapshot = dataSnapshot.child("timing");
                        RadioGroup radioGroup = findViewById(R.id.radioGroup);

                        for (DataSnapshot timingDataSnapshot : timingSnapshot.getChildren()) {
                            String timing = timingDataSnapshot.getValue(String.class);
                            RadioButton radioButton = new RadioButton(DrugActivity.this);
                            radioButton.setText(timing);
                            radioGroup.addView(radioButton);
                        }
                    } else {
                        binding.dosageTextView.setText("값이 존재하지 않음");
                    }
                } else {
                    Log.e("FirebaseError", "Firebase 데이터베이스 오류: " + task.getException().getMessage());
                }
            }
        });
    }

}
