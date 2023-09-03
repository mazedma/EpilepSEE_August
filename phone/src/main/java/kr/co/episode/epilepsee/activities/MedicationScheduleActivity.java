package kr.co.episode.epilepsee.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.content.Context;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kr.co.episode.epilepsee.databinding.ActivityMedicationScheduleBinding;

public class MedicationScheduleActivity extends AppCompatActivity {

    ActivityMedicationScheduleBinding activityMedicationScheduleBinding;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMedicationScheduleBinding = ActivityMedicationScheduleBinding.inflate(getLayoutInflater());
        setContentView(activityMedicationScheduleBinding.getRoot());

        // Firebase 데이터베이스 루트 레퍼런스를 가져옵니다.
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // 액션바 설정
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle("복용 일정 등록");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 저장 버튼 클릭 이벤트
        Button buttonSave = activityMedicationScheduleBinding.buttonSave;
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMedication();
            }
        });

        // 시작 날짜 선택 이벤트
        EditText editTextStartDate = activityMedicationScheduleBinding.editTextStartDate;
        editTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(MedicationScheduleActivity.this, editTextStartDate);
            }
        });

        // 종료 날짜 선택 이벤트
        EditText editTextEndDate = activityMedicationScheduleBinding.editTextEndDate;
        editTextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(MedicationScheduleActivity.this, editTextEndDate);
            }
        });
    }

    // DatePickerDialog 표시 메서드
    private void showDatePickerDialog(Context context, final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 날짜가 선택되면 EditText에 표시
                        String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                        editText.setText(date);
                    }
                },
                year,
                month,
                dayOfMonth
        );

        datePickerDialog.show();
    }

    // 저장 버튼 클릭 시 호출되는 메서드
    private void saveMedication() {
        // 사용자 입력 정보 가져오기
        ArrayList<String> selectedDays = getSelectedDays();
        int numDosage = Integer.parseInt(activityMedicationScheduleBinding.editTextNumDosage.getText().toString());
        int numPills = Integer.parseInt(activityMedicationScheduleBinding.editTextNumPills.getText().toString());
        ArrayList<String> selectedTimings = getSelectedTimings();
        String medicationName = activityMedicationScheduleBinding.editTextMedication.getText().toString();
        String startDateStr = activityMedicationScheduleBinding.editTextStartDate.getText().toString();
        String endDateStr = activityMedicationScheduleBinding.editTextEndDate.getText().toString();

        // 시작 날짜와 종료 날짜를 Calendar 객체로 변환합니다.
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);

            startCalendar.setTime(startDate);
            endCalendar.setTime(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 선택한 날짜 범위 내에 데이터 저장
        DatabaseReference medicationRef = databaseReference;
        for (Calendar date = startCalendar; !date.after(endCalendar); date.add(Calendar.DATE, 1)) {
            int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
            String dayOfWeekStr = "";

            // dayOfWeek을 해당하는 요일 문자열로 변환합니다.
            switch (dayOfWeek) {
                case Calendar.SUNDAY:
                    dayOfWeekStr = "일요일";
                    break;
                case Calendar.MONDAY:
                    dayOfWeekStr = "월요일";
                    break;
                case Calendar.TUESDAY:
                    dayOfWeekStr = "화요일";
                    break;
                case Calendar.WEDNESDAY:
                    dayOfWeekStr = "수요일";
                    break;
                case Calendar.THURSDAY:
                    dayOfWeekStr = "목요일";
                    break;
                case Calendar.FRIDAY:
                    dayOfWeekStr = "금요일";
                    break;
                case Calendar.SATURDAY:
                    dayOfWeekStr = "토요일";
                    break;
            }

            // 선택한 요일 중에 포함되어 있다면 데이터를 저장합니다.
            if (selectedDays.contains(dayOfWeekStr)) {
                DatabaseReference dateRef = medicationRef.child(sdf.format(date.getTime())); // 날짜를 "yyyy-MM-dd" 형식으로 포맷합니다.
                DatabaseReference medicationListRef = dateRef.child("medicationList");
                Medication medication = new Medication(medicationName, numPills + " 정", selectedTimings);
                DatabaseReference newMedicationRef = medicationListRef.push();
                newMedicationRef.setValue(medication);
            }
        }
    }

    // 선택한 요일을 반환하는 메서드
    private ArrayList<String> getSelectedDays() {
        ArrayList<String> selectedDays = new ArrayList<>();
        CheckBox checkBoxMon = activityMedicationScheduleBinding.checkBoxMon;
        CheckBox checkBoxTue = activityMedicationScheduleBinding.checkBoxTue;
        CheckBox checkBoxWed = activityMedicationScheduleBinding.checkBoxWed;
        CheckBox checkBoxLThu = activityMedicationScheduleBinding.checkBoxLThu;
        CheckBox checkBoxFri = activityMedicationScheduleBinding.checkBoxFri;
        CheckBox checkBoxSat = activityMedicationScheduleBinding.checkBoxSat;
        CheckBox checkBoxSun = activityMedicationScheduleBinding.checkBoxSun;

        if (checkBoxMon.isChecked()) selectedDays.add("월요일");
        if (checkBoxTue.isChecked()) selectedDays.add("화요일");
        if (checkBoxWed.isChecked()) selectedDays.add("수요일");
        if (checkBoxLThu.isChecked()) selectedDays.add("목요일");
        if (checkBoxFri.isChecked()) selectedDays.add("금요일");
        if (checkBoxSat.isChecked()) selectedDays.add("토요일");
        if (checkBoxSun.isChecked()) selectedDays.add("일요일");

        return selectedDays;
    }

    // 선택한 복용 시간을 반환하는 메서드
    private ArrayList<String> getSelectedTimings() {
        ArrayList<String> selectedTimings = new ArrayList<>();
        CheckBox checkBoxEarlyMorning = activityMedicationScheduleBinding.checkBoxEarlyMorning;
        CheckBox checkBoxMorning = activityMedicationScheduleBinding.checkBoxMorning;
        CheckBox checkBoxLunch = activityMedicationScheduleBinding.checkBoxLunch;
        CheckBox checkBoxDinner = activityMedicationScheduleBinding.checkBoxDinner;
        CheckBox checkBoxBedtime = activityMedicationScheduleBinding.checkBoxBedtime;

        if (checkBoxEarlyMorning.isChecked()) selectedTimings.add("이른 아침");
        if (checkBoxMorning.isChecked()) selectedTimings.add("아침");
        if (checkBoxLunch.isChecked()) selectedTimings.add("점심");
        if (checkBoxDinner.isChecked()) selectedTimings.add("저녁");
        if (checkBoxBedtime.isChecked()) selectedTimings.add("취침 전");

        return selectedTimings;
    }

    // 액션바의 백 버튼 클릭 이벤트 처리
    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return true;
    }
}
