package kr.co.episode.epilepsee;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import java.text.SimpleDateFormat;
import java.util.Date;

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

        binding.date.setText(getTime); //오늘날짜 화면에 출력

        //라디오버튼 결과값 관련
        context_drug = this; // onCreate에서 this 할당

        // 기록하기 버튼 클릭 리스너
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 라디오버튼 결과값 할당
                int checkedRadioButtonId = binding.radioGroup.getCheckedRadioButtonId(); //체크된 라디오버튼 아이디 가져오기
                RadioButton radioButton = (RadioButton)findViewById(checkedRadioButtonId); // 받은 id 값으로 해당 뷰 불러오기
                checkedRadioDrug = radioButton.getText().toString(); // text값 가져와서 변수에 저장

                recordedTimeDrug = mDate; // 현재시간 할당

                // 확인 화면 띄우기
                Intent intent = new Intent(getApplicationContext(), DrugCompleteActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}