package kr.co.episode.epilepsee;

import android.app.Activity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import kr.co.episode.epilepsee.databinding.ActivitySideEffectCompleteBinding;

public class SideEffectCompleteActivity extends Activity {
    private ActivitySideEffectCompleteBinding binding;

    // 시간 출력
    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date mDate = ((SideEffectActivity)SideEffectActivity.context_sideEffect).recordedTimeSE;
    String getTime = simpleDate.format(mDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySideEffectCompleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // SideEffectActivity에서 선택된 라디오버튼의 text값 가져오기
        String checkedRadio =((SideEffectActivity)SideEffectActivity.context_sideEffect).checkedRadioSE;
        binding.textView6.setText(checkedRadio); //text값 출력

        // 기록된 시간 출력
        binding.textView8.setText(getTime);

        // Firebase Database 레퍼런스
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        // 날짜를 가져오고, 날짜를 노드로 사용하여 데이터 저장
        String date = getTime.substring(0, 10); // "2023-08-14"
        String time = getTime.substring(11, 16); // "20:59"
        String type = checkedRadio;
        databaseReference.child(date).child("sideEffectData").push().setValue(new SideEffect(time, date, type));
    }

}