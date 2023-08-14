package kr.co.episode.epilepsee.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kr.co.episode.epilepsee.R;
import kr.co.episode.epilepsee.dataModel.SeizureViewModel;

public class SixthFragment extends Fragment {

    private int selectedMinute = 0;
    private int selectedSecond = 0;

    private Spinner recoveryTimeSpinner;
    private TextView recoveryTimeTextView;

    //발생장소 버튼
    private Button placeButton1;
    private Button placeButton2;
    private Button placeButton3;
    private Button placeButton4;
    private Button placeButton5;
    private CheckBox sleepCheckbox;

    private EditText emergencyMedicationEditText;
    private Button addButton;
    private TextView emergencyMedicationListTextView;
    private List<String> addedMedications = new ArrayList<>(); // 약물 이름 저장하는 리스트
    private SeizureViewModel seizureViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.frament_sixth, container, false);

        // ViewModelProvider를 통해 SeizureViewModel 인스턴스를 가져오기.
        seizureViewModel = new ViewModelProvider(requireActivity()).get(SeizureViewModel.class);

        //발생 장소 버튼 초기화
        placeButton1 = rootView.findViewById(R.id.placeButton1);
        placeButton2 = rootView.findViewById(R.id.placeButton2);
        placeButton3 = rootView.findViewById(R.id.placeButton3);
        placeButton4 = rootView.findViewById(R.id.placeButton4);
        placeButton5 = rootView.findViewById(R.id.placeButton5);

        //발생장소 버튼에 이벤트 처리
        placeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seizureViewModel.setSeizureLocation("집");
            }
        });

        placeButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seizureViewModel.setSeizureLocation("학교");
            }
        });
        placeButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seizureViewModel.setSeizureLocation("직장");
            }
        });

        placeButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seizureViewModel.setSeizureLocation("이동 중");
            }
        });
        // 기타를 사용자로부터 입력 받을 경우, 아래 수정 필요함.
        placeButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seizureViewModel.setSeizureLocation("기타");
            }
        });

        // 수면 중 여부 체크박스 초기화
        sleepCheckbox = rootView.findViewById(R.id.sleepCheckbox);
        // 수면 중 여부 체크박스 클릭 이벤트 처리
        sleepCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                seizureViewModel.setSeizureDuringSleep(isChecked);
            }
        });

        //회복에 걸린 시간 선택
        recoveryTimeSpinner = rootView.findViewById(R.id.recoveryTimeSpinner);
        recoveryTimeTextView =rootView.findViewById(R.id.recoveryTimeTextView);
        // 선택지 배열 생성
        String[] recoveryTimes = {"5분", "10분", "15분", "20분", "25분", "30분", "30분 이상"};
        //어댑터 생성 및 설정
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),android.R.layout.simple_spinner_item, recoveryTimes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recoveryTimeSpinner.setAdapter(adapter);

        //아이템 선택 이벤트 처리
        recoveryTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String recoveryTime = recoveryTimes[position];
                recoveryTimeTextView.setText(recoveryTime);
                //ViewModel에 저장
                seizureViewModel.setRecoveryTime(recoveryTime);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //아무것도 선택하지 않았을 때
                String defaultRecoveryTime = "5분";
                recoveryTimeTextView.setText(defaultRecoveryTime);
                //ViewModel에 저장
                seizureViewModel.setRecoveryTime(defaultRecoveryTime);
            }
        });

        //발작 지속 시간선택
        Button timeSet = rootView.findViewById(R.id.timeSet);
        timeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        //EditText, addButton, TextView 초기화 (사용된 긴급약물)
        emergencyMedicationEditText = rootView.findViewById(R.id.emergencyMedicationEditText);
        addButton = rootView.findViewById(R.id.addButton);
        emergencyMedicationListTextView = rootView.findViewById(R.id.emergencyMedicationListTextView);

        // 추가 버튼 클릭 이벤트 처리
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String medicine = emergencyMedicationEditText.getText().toString();
                if(!medicine.isEmpty()){
                    addedMedications.add(medicine);
                    updateEmergencyMedicationListTextView();
                    emergencyMedicationEditText.setText("");
                }
            }
        });
        return rootView;

    }


    private  void showTimePickerDialog(){
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedMinute = minute;
                        selectedSecond = 0; // Reset the selected second to 0

                        //선택한 총 지속 시간 계산
                        int totalDuration = (hourOfDay * 60) + minute;

                        String formattedDuration = String.format("%02d:%02d", hourOfDay, minute);
                        // UI 업데이트하여 선택한 지속 시간 표시
                        recoveryTimeTextView.setText(formattedDuration);

                        // 선택한 지속 시간을 SeizureViewModel에 저장
                        seizureViewModel.setSeizureDuration(formattedDuration);
                    }
                },
                hour,
                minute,
                true
        );

        timePickerDialog.show();

    }
    // emergencyMedicationListTextView 업데이트
    private void updateEmergencyMedicationListTextView(){
        StringBuilder medicationsText = new StringBuilder();
        for (String medication : addedMedications){
            medicationsText.append("- ").append(medication).append("\n");

        }
        emergencyMedicationListTextView.setText(medicationsText.toString());

        seizureViewModel.setEmergencyMedication(medicationsText.toString());
    }
    //여기까지 완료
}
