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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        SeekBar moodSeekBar = findViewById(R.id.moodSeekBar);
        SeekBar sleepQualitySeekBar = findViewById(R.id.sleepQualitySeekBar);

        // 날짜를 선택하기 위한 EditText 클릭 이벤트 처리
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });



// SeekBar의 최대 값을 10으로 설정
        moodSeekBar.setMax(10);
        sleepQualitySeekBar.setMax(10);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Firebase Realtime Database에 데이터를 저장하기 위한 DatabaseReference 생성
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                if (selectedDate != null) {
                    // 선택한 날짜를 "yyyy-MM-dd" 형식으로 포맷팅
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    String formattedDate = sdf.format(selectedDate.getTime());

                    // 저장할 데이터를 생성 (mood와 sleepQuality 값을 가져옴)
                    int moodValue = moodSeekBar.getProgress();
                    int sleepQualityValue = sleepQualitySeekBar.getProgress();

                    // moodValue와 sleepQualityValue를 그대로 저장
                    // Firebase에 데이터를 저장할 때는 0부터 10까지의 범위로 나누지 않고 그대로 저장
                    DatabaseReference statusDataReference = databaseReference.child(formattedDate).child("statusData");
                    statusDataReference.child("mood").setValue(moodValue);
                    statusDataReference.child("sleepQuality").setValue(sleepQualityValue);

                    Toast.makeText(StatusMenuActivity.this, "데이터가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StatusMenuActivity.this, "날짜를 선택하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });



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


}
