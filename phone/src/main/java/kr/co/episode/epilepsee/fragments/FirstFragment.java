package kr.co.episode.epilepsee.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import kr.co.episode.epilepsee.R;
import kr.co.episode.epilepsee.dataModel.SeizureViewModel;

public class FirstFragment extends Fragment {

    Button timeButton;
    Button dateButton;
    TextView seizureTimeTextView;
    TextView seizureDateTextView;

    private SeizureViewModel seizureViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_first, container, false);


        timeButton = rootView.findViewById(R.id.DrugTimeButton);
        dateButton = rootView.findViewById(R.id.dateButton);
        seizureTimeTextView = rootView.findViewById(R.id.seizureDrugTimeTextView);
        seizureDateTextView = rootView.findViewById(R.id.seizureDateTextView);

        // ViewModelProvider를 통해 SeizureViewModel 인스턴스를 가져오기.
        seizureViewModel = new ViewModelProvider(requireActivity()).get(SeizureViewModel.class);

        //현재 날짜와 시간 가져오기
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm",Locale.getDefault());

        String selectedDate = dateFormat.format(calendar.getTime());
        String selectedTime = timeFormat.format(calendar.getTime());

        //textView와 데이터베이스의 초기값 배정
        seizureDateTextView.setText(selectedDate);
        seizureTimeTextView.setText(selectedTime);
        seizureViewModel.setSeizureDate(selectedDate);
        seizureViewModel.setSeizureTime(selectedTime);

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        return rootView;
    }


    public void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getChildFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getChildFragmentManager(), "datePicker");
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
            // 비정적 컨텍스트에서 seizureModel에 접근하도록 변경
            FirstFragment parentFragment = (FirstFragment) getParentFragment();
            if (parentFragment != null){
                parentFragment.seizureViewModel.setSeizureTime(selectedTime);
            }
            //화면에 선택한 시간 표시
            TextView seizureTimeTextView = getActivity().findViewById(R.id.seizureDrugTimeTextView);
            seizureTimeTextView.setText(selectedTime);
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public DatePickerDialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);

            // Format the selected date
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String selectedDate = dateFormat.format(calendar.getTime());
            // 비정적 컨텍스트에서 seizureModel에 접근하도록 변경
            FirstFragment parentFragment = (FirstFragment) getParentFragment();
            if (parentFragment != null){
                parentFragment.seizureViewModel.setSeizureDate(selectedDate);
            }
            //화면에 선택한 날짜 표시
            TextView seizureDateTextView = getActivity().findViewById(R.id.seizureDateTextView);
            seizureDateTextView.setText(selectedDate);
        }
    }
}





