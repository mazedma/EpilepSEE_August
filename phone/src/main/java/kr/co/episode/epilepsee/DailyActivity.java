package kr.co.episode.epilepsee;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class DailyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        // ActionBar에 백 버튼을 활성화합니다.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        // Intent에서 클릭한 날짜를 받아옵니다.
        Intent intent = getIntent();
        String selectedDate = intent.getStringExtra("selectedDate");

        // 날짜를 표시할 TextView를 찾아옵니다.
        TextView dateTextView = findViewById(R.id.dateTextView);

        // TextView에 클릭한 날짜를 표시합니다.
        dateTextView.setText("선택한 날짜: " + selectedDate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // ActionBar의 백 버튼을 누를 때
        if (id == android.R.id.home) {
            finish(); // 현재 Activity를 종료합니다.
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


