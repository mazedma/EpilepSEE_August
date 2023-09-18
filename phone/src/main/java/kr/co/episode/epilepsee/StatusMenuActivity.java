package kr.co.episode.epilepsee;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StatusMenuActivity extends AppCompatActivity {

    EditText dateEditText;
    SeekBar moodSeekBar, sleepQualitySeekBar;
    Button saveButton;
    Calendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_menu);

        dateEditText = findViewById(R.id.dateEditText);
        moodSeekBar = findViewById(R.id.moodSeekBar);
        sleepQualitySeekBar = findViewById(R.id.sleepQualitySeekBar);
        saveButton = findViewById(R.id.saveButton);

        // 날짜를 선택하기 위한 EditText 클릭 이벤트 처리
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // 나머지 코드 및 기능 구현을 진행합니다.
        // ...
    }

    // 달력 다이얼로그 표시 메서드
    private void showDatePickerDialog() {
        final Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        selectedDate = Calendar.getInstance();
                        selectedDate.set(Calendar.YEAR, year);
                        selectedDate.set(Calendar.MONTH, monthOfYear);
                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // 선택한 날짜를 EditText에 표시
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        dateEditText.setText(sdf.format(selectedDate.getTime()));
                    }
                }, year, month, day);

        // 아래의 코드를 추가하여 DatePickerDialog를 표시
        datePickerDialog.show();
    }


    // 나머지 코드와 기능을 구현합니다.
    // ...
}
