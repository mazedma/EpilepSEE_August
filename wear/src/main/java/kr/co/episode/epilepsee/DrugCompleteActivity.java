package kr.co.episode.epilepsee;

import android.app.Activity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;

import kr.co.episode.epilepsee.databinding.ActivityDrugCompleteBinding;

public class DrugCompleteActivity extends Activity {

    private ActivityDrugCompleteBinding binding;

    // 시간 출력
    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
    Date mDate = ((DrugActivity)DrugActivity.context_drug).recordedTimeDrug;
    String getTime = simpleDate.format(mDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDrugCompleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // SideEffectActivity에서 선택된 라디오버튼의 text값 가져오기
        String checkedRadio =((DrugActivity)DrugActivity.context_drug).checkedRadioDrug;
        binding.textView6.setText(checkedRadio); //text값 출력

        // 기록된 시간 출력
        binding.textView8.setText(getTime);
    }
}