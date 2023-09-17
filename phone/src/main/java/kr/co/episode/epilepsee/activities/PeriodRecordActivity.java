package kr.co.episode.epilepsee.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import kr.co.episode.epilepsee.databinding.ActivityPeriodRecordBinding;

public class PeriodRecordActivity extends AppCompatActivity {

    ActivityPeriodRecordBinding activityPeriodRecordBinding;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPeriodRecordBinding = ActivityPeriodRecordBinding.inflate(getLayoutInflater());
        setContentView(activityPeriodRecordBinding.getRoot());

        // Firebase Database 초기화
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // 액션바에 백 버튼 추가
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle("생리 기록"); // 화면 제목 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼

        activityPeriodRecordBinding.startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(activityPeriodRecordBinding.startDateEditText);
            }
        });

        activityPeriodRecordBinding.endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(activityPeriodRecordBinding.endDateEditText);
            }
        });

        activityPeriodRecordBinding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePeriodData();
            }
        });
    }

    // DatePicker를 표시하는 메서드
    private void showDatePickerDialog(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // 선택한 날짜를 EditText에 설정
                        calendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        String selectedDate = sdf.format(calendar.getTime());
                        editText.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    // 데이터를 Firebase에 저장하는 메서드
    private void savePeriodData() {
        String startDateStr = activityPeriodRecordBinding.startDateEditText.getText().toString();
        String endDateStr = activityPeriodRecordBinding.endDateEditText.getText().toString();

        if (!startDateStr.isEmpty() && !endDateStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                Date startDate = sdf.parse(startDateStr);
                Date endDate = sdf.parse(endDateStr);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);

                while (!calendar.getTime().after(endDate)) {
                    String currentDate = sdf.format(calendar.getTime());

                    // Firebase에 데이터 저장
                    DatabaseReference currentDateRef = databaseReference.child(currentDate);
                    currentDateRef.child("menstrualData").child("menstrualBool").setValue(true);

                    // 다음 날짜로 이동
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }

                Toast.makeText(this, "데이터가 저장되었습니다.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "날짜 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "시작 날짜와 종료 날짜를 입력하세요.", Toast.LENGTH_SHORT).show();
        }
    }


    // 액션바의 백 버튼 클릭 이벤트 처리
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
