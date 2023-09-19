package kr.co.episode.epilepsee.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import kr.co.episode.epilepsee.EventDecorator;
import kr.co.episode.epilepsee.R;
import kr.co.episode.epilepsee.databinding.ActivityPeriodRecordBinding;

public class PeriodRecordActivity extends AppCompatActivity {

    ActivityPeriodRecordBinding activityPeriodRecordBinding;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPeriodRecordBinding = ActivityPeriodRecordBinding.inflate(getLayoutInflater());
        setContentView(activityPeriodRecordBinding.getRoot());

        ArrayList<String> menstrualDates = new ArrayList<>();
        // Firebase Database 초기화
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // "menstrualData" 하위의 데이터를 가져오기 위한 ValueEventListener 생성
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 모든 날짜에 대해 반복
                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    // "menstrualData" 하위의 데이터를 가져옴
                    DataSnapshot menstrualDataSnapshot = dateSnapshot.child("menstrualData");
                    if (menstrualDataSnapshot.exists()) {
                        // "menstrualData" 하위에 "menstrualBool" 키가 있는 경우
                        Boolean menstrualBool = menstrualDataSnapshot.child("menstrualBool").getValue(Boolean.class);
                        if (menstrualBool != null && menstrualBool) {
                            // "menstrualBool"이 true인 경우, 해당 날짜를 ArrayList에 추가
                            menstrualDates.add(dateSnapshot.getKey());
                        }
                    }
                }

                // menstrualDates ArrayList에 저장된 날짜를 로그로 출력
                for (String date : menstrualDates) {
                    Log.d("MenstrualDate", date);
                }

                displayMenstrualDatesInTextView(menstrualDates);

                // menstrualDates 리스트에서 CalendarDay 객체로 변환하여 빨간색으로 표시할 날짜의 컬렉션 생성
                HashSet<CalendarDay> eventDates = new HashSet<>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                for (String date : menstrualDates) {
                    try {
                        Date parsedDate = sdf.parse(date);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(parsedDate);
                        eventDates.add(CalendarDay.from(calendar));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                EventDecorator eventDecorator = new EventDecorator(Color.RED, eventDates);

                // MaterialCalendarView에 Decorator 추가
                activityPeriodRecordBinding.calendarView.addDecorator(eventDecorator);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 데이터 가져오기 실패 시 처리
                Log.e("FirebaseError", "Failed to retrieve data", databaseError.toException());
            }
        };

        // "menstrualData" 하위의 데이터를 가져오기 위한 ValueEventListener를 추가
        databaseReference.addListenerForSingleValueEvent(eventListener);



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

    // "MenstrualDate"를 화면의 TextView에 표시하는 메서드
    private void displayMenstrualDatesInTextView(List<String> menstrualDates) {
        StringBuilder builder = new StringBuilder();
        for (String date : menstrualDates) {
            builder.append(date).append("\n");
        }
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

                // "save" 버튼을 누를 때 ValueEventListener를 다시 실행
                databaseReference.addListenerForSingleValueEvent(eventListener);

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
