package kr.co.episode.epilepsee.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import kr.co.episode.epilepsee.MainActivity;
import kr.co.episode.epilepsee.R;
import kr.co.episode.epilepsee.dataModel.SideEffectData;
import kr.co.episode.epilepsee.databinding.ActivitySeizureBinding;
import kr.co.episode.epilepsee.databinding.ActivitySideEffectBinding;
import kr.co.episode.epilepsee.fragments.FirstFragment;

public class SideEffectActivity extends AppCompatActivity {

    Button DrugdateButton, DrugTimeButton;
    ActivitySideEffectBinding activitySideEffectBinding;

    private Button btnSideEffectSave;

    private List<String> selectedEffects = new ArrayList<>();
    private String selectedDate = "";

    private String selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySideEffectBinding = ActivitySideEffectBinding.inflate(getLayoutInflater());
        setContentView(activitySideEffectBinding.getRoot());
        // Firebase 초기화
        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true); // 오프라인 지원을 활성화하려는 경우

        DrugdateButton = activitySideEffectBinding.DrugdateButton;
        DrugTimeButton = activitySideEffectBinding.DrugTimeButton;
        //액션바에 백 버튼 추가
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle("약물 부작용"); // 화면 제목 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼

        DrugdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
                Log.d("SideEffectActivity", "DrugdateButton 클릭됨");}
            });

        DrugTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });
        //저장버튼
        btnSideEffectSave = findViewById(R.id.btnSideEffectSave);
        btnSideEffectSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedDate.isEmpty()){
                    Toast.makeText(SideEffectActivity.this,"날짜를 선택하세요.",Toast.LENGTH_SHORT).show();
                    Log.e("SideEffectActivity", "날짜를 선택하지 않고 저장 버튼 클릭됨");
                    return;
                }
                if (selectedEffects.isEmpty()){
                    Toast.makeText(SideEffectActivity.this,"부작용을 선택하세요",Toast.LENGTH_SHORT).show();
                    Log.e("SideEffectActivity", "부작용을 선택하지 않고 저장 버튼 클릭됨"); // 에러 로그 출력
                }else{
                saveSideEffectsToDatabase();}
                Log.d("SideEffectActivity", "부작용 데이터가 저장되었습니다."); // 성공 로그 출력
                // 메인 화면으로 이동
                Intent mainIntent = new Intent(SideEffectActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish(); // 현재 액티비티 종료
            }
        });

        setToggleButtonListener();
}

    private void setToggleButtonListener(){
        // 토글 버튼들의 아이디 배열
        int[] toggleButtonIds = {
                R.id.toggleBtnHeadache, R.id.toggleBtnNausea, R.id.toggleBtnHairLoss, R.id.toggleBtnHandTremors,
                R.id.toggleBtnTension, R.id.toggleBtnFatigue, R.id.toggleBtnAnxiety, R.id.toggleBtnDepression,
                R.id.toggleBtnSkinIssues, R.id.toggleBtnWeightGain, R.id.toggleBtnOralIssues, R.id.toggleBtnAggressiveEmotions,
                R.id.toggleBtnDizziness, R.id.toggleBtnBlurryVision, R.id.toggleBtnConcentrationIssues, R.id.toggleBtnConfusion,
                R.id.toggleBtnMemoryIssues, R.id.toggleBtnSleepDisturbance, R.id.toggleBtnDrowsiness
        };
        for (int id : toggleButtonIds) {
            final ToggleButton toggleButton = findViewById(id);

            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    String effect = toggleButton.getTextOn().toString();

                    if (isChecked) {
                        // 토글 버튼이 선택되었을 때
                        if (!selectedEffects.contains(effect)) {
                            selectedEffects.add(effect);
                        }
                    } else {
                        // 토글 버튼이 선택 해제되었을 때
                        if (selectedEffects.contains(effect)) {
                            selectedEffects.remove(effect);
                        }
                    }
                }
            });
        }

    }
    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }
    public void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @NonNull
        @Override
        public TimePickerDialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute, false);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            // Format the selected time
            DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String selectedTime = timeFormat.format(calendar.getTime());
            //화면에 선택한 시간 표시
            TextView seizureDrugTimeTextView = getActivity().findViewById(R.id.seizureDrugTimeTextView);
            seizureDrugTimeTextView.setText(selectedTime);
            ((SideEffectActivity) getActivity()).setSelectedTime(selectedTime);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);

            // Format the selected date
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String selectedDate = dateFormat.format(calendar.getTime());
            TextView selectedDateTextView = getActivity().findViewById(R.id.selectedDrugDateTextView);
            selectedDateTextView.setText(selectedDate);
            ((SideEffectActivity) getActivity()).setSelectedDate(selectedDate);
        }
    }

    private  void setSelectedDate(String date){
        selectedDate = date;
    }

    private void setSelectedTime(String time){
        selectedTime = time;
    }
    private void saveSideEffectsToDatabase(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference sideEffectDataRef = database.getReference(selectedDate).child("sideEffectData");

        // 랜덤 키 생성
        String key = sideEffectDataRef.push().getKey();

        // 선택된 부작용 데이터를 Firebase에 저장
        DatabaseReference entryRef = sideEffectDataRef.child(key); // 랜덤 키값 레퍼런스 가져오기
        entryRef.child("sideEffectTime").setValue(selectedTime); // DrugTime 저장
        entryRef.child("sideEffectType").setValue(selectedEffects); // 부작용 내용 저장


        // 저장이 완료되었다는 메시지 표시
        Toast.makeText(this, "부작용 데이터가 저장되었습니다.", Toast.LENGTH_SHORT).show();


    }
    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return true;
    }


}