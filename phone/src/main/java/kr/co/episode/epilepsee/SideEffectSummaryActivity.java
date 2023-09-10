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
        String selectedKey = intent.getStringExtra("selectedKey");

        // parentKey를 TextView에 출력합니다.
        TextView selectedKeyTextView = findViewById(R.id.selectedKeyTextView);
        selectedKeyTextView.setText("selectedKey: " + selectedKey);
    }
}