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

    }
}
