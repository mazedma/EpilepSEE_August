package kr.co.episode.epilepsee;

import android.app.Activity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;

import kr.co.episode.epilepsee.databinding.ActivityPeriodCompleteBinding;

public class PeriodCompleteActivity extends Activity {

    private ActivityPeriodCompleteBinding activityPeriodCompleteBinding;

    //시간 출력
    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
    Date mDate = ((PeriodActivity)PeriodActivity.context_period).recordedTimePeriod;
    String getTime = simpleDate.format(mDate); // getTime 변수에 생리 일자 할당

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityPeriodCompleteBinding = ActivityPeriodCompleteBinding.inflate(getLayoutInflater());
        setContentView(activityPeriodCompleteBinding.getRoot());

        //기록된 시간 출력
        activityPeriodCompleteBinding.textView8.setText(getTime);
    }
}