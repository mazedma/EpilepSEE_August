package kr.co.episode.epilepsee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import kr.co.episode.epilepsee.databinding.ActivityStatusMenuBinding;

public class StatusMenuActivity extends AppCompatActivity {

    ActivityStatusMenuBinding activityStatusMenuBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStatusMenuBinding = ActivityStatusMenuBinding.inflate(getLayoutInflater());
        setContentView(activityStatusMenuBinding.getRoot());

    }
}