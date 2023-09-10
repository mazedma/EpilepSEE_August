package kr.co.episode.epilepsee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SideEffectSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_effect_summary);

        // Intent로부터 parentKey 값을 가져옵니다.
        Intent intent = getIntent();
        String parentKey = intent.getStringExtra("parentKey");

        // parentKey를 TextView에 출력합니다.
        TextView parentKeyTextView = findViewById(R.id.parentKeyTextView);
        parentKeyTextView.setText("Parent Key: " + parentKey);
    }
}