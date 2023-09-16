package kr.co.episode.epilepsee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kr.co.episode.epilepsee.databinding.ActivitySeizureCompleteBinding;

public class SeizureCompleteActivity extends Activity {

    private ActivitySeizureCompleteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeizureCompleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        // Get seizure start and duration from intent
        Intent intent = getIntent();
        String seizureTime = intent.getStringExtra("seizureTime");
        String seizureDuration = intent.getStringExtra("seizureDuration");



        // Display seizure start and duration
        binding.seizureStartTextview.setText(seizureTime);
        binding.seizureDurationTextview.setText(seizureDuration);

        // Firebase Database 레퍼런스
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        // 날짜를 가져오고, 날짜를 노드로 사용하여 데이터 저장
        String date = seizureTime.substring(0, 10); // "2023-08-14"
        String time = seizureTime.substring(11, 16); // "20:59"
        String duration = seizureDuration.substring(0,5); // mm:ss
        databaseReference.child(date).child("seizureData").push().setValue(new Seizure(time, duration, date));
    }
}


