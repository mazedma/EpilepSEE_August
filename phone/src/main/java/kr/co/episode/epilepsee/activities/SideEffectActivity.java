package kr.co.episode.epilepsee.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import kr.co.episode.epilepsee.R;
import kr.co.episode.epilepsee.databinding.ActivitySeizureBinding;
import kr.co.episode.epilepsee.databinding.ActivitySideEffectBinding;

public class SideEffectActivity extends AppCompatActivity {

    Button DrugdateButton;
    ActivitySideEffectBinding activitySideEffectBinding;

    private Button btnSideEffectSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySideEffectBinding = ActivitySideEffectBinding.inflate(getLayoutInflater());
        setContentView(activitySideEffectBinding.getRoot());

        DrugdateButton = activitySideEffectBinding.DrugdateButton;

        //액션바에 백 버튼 추가
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle("약물 부작용"); // 화면 제목 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼


        DrugdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { showDatePickerDialog();}

            });

        //저장버튼
        btnSideEffectSave = findViewById(R.id.btnSideEffectSave);
        btnSideEffectSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //저장 데이터베이스 코드 구현
                // 예시: Firebase Realtime Database에 저장하는 코드
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                String userId = "사용자 식별자"; // 사용자 식별자를 여기에 설정
                String currentDateKey = selectedDate; // 날짜를 키로 사용
                databaseReference.child("users").child(userId).child("SideEffectData").child(currentDateKey).setValue(selectedEffects);

                // 저장이 완료되었다는 메시지 표시
                Toast.makeText(this, "부작용 데이터가 저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });


}
    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

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
        }
    }
    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return true;
    }


}