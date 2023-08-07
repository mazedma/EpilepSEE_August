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

        //뒷배경이 반투명한 activity 만들기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
    }
}