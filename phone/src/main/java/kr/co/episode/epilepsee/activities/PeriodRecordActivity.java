package kr.co.episode.epilepsee.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import kr.co.episode.epilepsee.databinding.ActivityPeriodRecordBinding;

public class PeriodRecordActivity extends AppCompatActivity {

    ActivityPeriodRecordBinding activityPeriodRecordBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPeriodRecordBinding = ActivityPeriodRecordBinding.inflate(getLayoutInflater());
        setContentView(activityPeriodRecordBinding.getRoot());


        //액션바에 백 버튼 추가
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle("생리 기록"); // 화면 제목 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼

    }
    // 액션바의 백 버튼 클릭 이벤트 처리
    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return true;
    }
}