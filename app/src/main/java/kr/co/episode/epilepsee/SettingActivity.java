package kr.co.episode.epilepsee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import kr.co.episode.epilepsee.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding activitySettingBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayAdapter<CharSequence> adapter = null;

        super.onCreate(savedInstanceState);
        activitySettingBinding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(activitySettingBinding.getRoot());

        adapter = ArrayAdapter.createFromResource(this, R.array.ment_spinner_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        activitySettingBinding.mentSpinner.setAdapter(adapter);


    }
}