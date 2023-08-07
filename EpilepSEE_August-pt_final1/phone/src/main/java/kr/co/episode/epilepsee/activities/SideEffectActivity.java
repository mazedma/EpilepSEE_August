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