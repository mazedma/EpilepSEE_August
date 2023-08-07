package kr.co.episode.epilepsee;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

import kr.co.episode.epilepsee.databinding.ActivityPeriodBinding;

public class PeriodActivity extends Activity {

    private ActivityPeriodBinding binding;

    //시간 출력
    long now = System.currentTimeMillis(); // 현재 시간 가져오기
    Date mDate = new Date(now); // Date 형식으로 고치기
    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd"); // 시간을 나타낼 포맷 설정
    String getTime = simpleDate.format(mDate); // getTime변수에 값을 저장근

    // 다른 Activity 접근
    @SuppressLint("StaticFieldLeak")
    public static Context context_period; // context 변수 선언
    public Date recordedTimePeriod; // 현재시간

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPeriodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 오늘 날짜 화면에 출력
        binding.date.setText(getTime);

        // 컨텍스트 변수 관련
        context_period = this; // onCreate에서 this 할당

        // 기록하기 버튼 클릭 리스너
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재시간 할당
                recordedTimePeriod = mDate;

                // 확인 화면 띄우기
                Intent intent = new Intent(getApplicationContext(), PeriodCompleteActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}