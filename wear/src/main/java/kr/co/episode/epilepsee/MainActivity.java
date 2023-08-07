package kr.co.episode.epilepsee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.co.episode.epilepsee.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        // 버튼 클릭 화면 전환

        // 발작 버튼
        activityMainBinding.seizureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SeizureActivity.class);
                startActivity(intent);
            }
        });

        // 부작용 버튼
        activityMainBinding.sideEffectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SideEffectActivity.class);
                startActivity(intent);
            }
        });

        // 생리 버튼
        activityMainBinding.periodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PeriodActivity.class);
                startActivity(intent);
            }
        });

        // 약물 버튼
        activityMainBinding.drugButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DrugActivity.class);
                startActivity(intent);
            }
        });
    }
}