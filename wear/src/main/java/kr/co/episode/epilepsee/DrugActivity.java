package kr.co.episode.epilepsee;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
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
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import kr.co.episode.epilepsee.databinding.ActivityDrugBinding;

public class DrugActivity extends Activity {

    private ActivityDrugBinding binding;

    long now = System.currentTimeMillis(); // 현재 시간 가져오기
    Date mDate = new Date(now); // Date 형식으로 고치기
    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd"); // 시간을 나타낼 포맷 설정
    String getTime = simpleDate.format(mDate); // getTime 변수에 값을 저장
    DatabaseReference todayDataReference;


    // 동적으로 생성한 RadioGroup 객체와 선택된 라디오 버튼을 저장할 변수 선언
    private RadioGroup dynamicRadioGroup;
    private RadioButton selectedRadioButton;

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

        // Firebase로부터 데이터 가져오기
        getDataFromFirebase();


        //하..
        // 동적으로 생성한 RadioGroup 객체 초기화
        dynamicRadioGroup = new RadioGroup(this);
        // 라디오버튼 결과값 관련
        context_drug = this; // onCreate에서 this 할당

        // 기록하기 버튼 클릭 리스너
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 선택된 라디오 버튼의 값을 가져와서 사용
                if (selectedRadioButton != null) {
                    String selectedText = selectedRadioButton.getText().toString();
                    recordedTimeDrug = mDate; // 현재시간 할당

                    // Firebase에 데이터 추가
                    addDataToFirebase(selectedText);

                    // 확인 화면 띄우기
                    Intent intent = new Intent(getApplicationContext(), DrugCompleteActivity.class);
                    intent.putExtra("checkedRadioDrug", selectedText); // 선택된 라디오 버튼의 텍스트 값을 인텐트에 추가
                    startActivity(intent);
                    finish();
                } else {
                    // 선택된 라디오 버튼이 없을 때 처리
                    // 예: 사용자에게 선택하도록 메시지 표시
                }
            }
        });

    }

    // Firebase에 데이터 추가하는 메서드
    private void addDataToFirebase(String selectedText) {
        if (todayDataReference != null) {
            // "done" 아래에 새로운 노드를 만들고 선택된 텍스트를 추가
            DatabaseReference doneReference = todayDataReference.child("medicationList").child("done").child(selectedText);
            doneReference.setValue(true);
        }
    }

    private void getDataFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(); // 루트 경로를 가져옴
        String todayDate = getTime; // 오늘 날짜를 가져옴

        // 오늘 날짜를 사용하여 경로 설정
        todayDataReference = databaseReference.child(todayDate);

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

                            // "timing" 값을 ArrayList로 가져오기
                            ArrayList<String> timingData = new ArrayList<>();
                            for (DataSnapshot timingSnapshot : medicationSnapshot.child("timing").getChildren()) {
                                String timingValue = timingSnapshot.getValue(String.class);
                                timingData.add(timingValue);
                            }

                            // 하나의 문자열로 합치기
                            String medicationInfo = dosage + " " + dosageTimings + " " + medicationName;
                            medicationListText.append(medicationInfo).append("\n");

                            // "timing" 값을 radioButton으로 추가
                            for (String timing : timingData) {
                                RadioButton radioButton = new RadioButton(context_drug);
                                radioButton.setText(timing);
                                dynamicRadioGroup.addView(radioButton);

                                // 라디오 버튼에 선택 리스너 추가
                                radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            // 사용자가 라디오 버튼을 선택한 경우, 선택된 라디오 버튼을 저장
                                            selectedRadioButton = radioButton;
                                        }
                                    }
                                });
                            }
                        }

                        // TextView에 값을 설정
                        binding.dosageTextView.setText(medicationListText.toString());

                        // dynamicRadioGroup을 화면에 추가
                        binding.radioGroup.addView(dynamicRadioGroup);
                    }
                } else {
                    Log.e("FirebaseError", "Firebase 데이터베이스 오류: " + task.getException().getMessage());
                }
            }
        });
    }
}
