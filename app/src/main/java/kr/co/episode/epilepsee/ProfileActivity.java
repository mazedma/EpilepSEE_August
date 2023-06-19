package kr.co.episode.epilepsee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import kr.co.episode.epilepsee.database.DatabaseHelper;
import kr.co.episode.epilepsee.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    //데이터 베이스
    private EditText etName, etAge, etOnsetDate;
    private RadioGroup radioGroupGender;
    private DatabaseHelper dbHelper;
    private Button buttonSave;
    private Button buttonUpdate;


    //Calendar
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    ActivityProfileBinding activityProfileBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(activityProfileBinding.getRoot());

        // 액션바 설정
        getSupportActionBar().setTitle("Profile"); // 화면 제목 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼


        activityProfileBinding.editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ProfileActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dbHelper = new DatabaseHelper(this);
        etName = activityProfileBinding.etName;
        etAge = activityProfileBinding.etAge;
        etOnsetDate = activityProfileBinding.editDate;
        radioGroupGender = activityProfileBinding.radioGroupGender;
        buttonSave = activityProfileBinding.buttonSave;
        buttonUpdate = activityProfileBinding.buttonUpdate;

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserProfile();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProfile();
            }
        });

        //사용자 프로필 데이터베이스 조회
        Cursor cursor = dbHelper.getUserProfiles();

        //커서를 첫번째 레코드로 이동
        if (cursor.moveToFirst()) {
            // 컬럼 인덱스를 가져올 때 컬럼 이름을 사용하여 조회
            int nameColumnIndex = cursor.getColumnIndex("NAME");
            int ageColumnIndex = cursor.getColumnIndex("AGE");
            int genderColumnIndex = cursor.getColumnIndex("GENDER");
            int onsetDateColumnIndex = cursor.getColumnIndex("ONSET_DATE");

            // 컬럼 인덱스가 올바르게 가져와졌는지 확인
            if (nameColumnIndex != -1 && ageColumnIndex != -1 && genderColumnIndex != -1 && onsetDateColumnIndex != -1) {
                // 레코드에서 필요한 데이터 가져오기
                String name = cursor.getString(nameColumnIndex);
                int age = cursor.getInt(ageColumnIndex);
                String gender = cursor.getString(genderColumnIndex);
                String onsetDate = cursor.getString(onsetDateColumnIndex);

                // 가져온 데이터를 각 항목에 설정
                etName.setText(name);
                etAge.setText(String.valueOf(age));
                if (gender.equals("남성")) {
                    radioGroupGender.check(R.id.MaleButton);
                } else if (gender.equals("여성")) {
                    radioGroupGender.check(R.id.FemaleButton);
                }
                etOnsetDate.setText(onsetDate);
            }
        }

        // 커서 닫기
        cursor.close();
    }

    private void saveUserProfile(){
        String name = etName.getText().toString().trim();
        String ageStr = etAge.getText().toString().trim();
        String date = etOnsetDate.getText().toString().trim();
        String gender = getSelectedGender();

        if(name.isEmpty() || ageStr.isEmpty() || date.isEmpty()){
            Toast.makeText(this,"모든 필드를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        int age = Integer.parseInt(ageStr);

        //사용자 프로필 데이터베이스에 저장
        dbHelper.addUserProfile(name, age, gender, date);
        Toast.makeText(this, "사용자 프로필이 저장되었습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private String getSelectedGender(){
        int selectedId = radioGroupGender.getCheckedRadioButtonId();

        if(selectedId == R.id.MaleButton){
            return "남성";
        }else if(selectedId == R.id.FemaleButton){
            return "여성";
        }

        return "";
    }

    private void updateUserProfile(){
        String name = etName.getText().toString().trim();
        String ageStr = etAge.getText().toString().trim();
        String date = etOnsetDate.getText().toString().trim();
        String gender = getSelectedGender();

        if (name.isEmpty() || ageStr.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        int age = Integer.parseInt(ageStr);
        // 사용자 프로필 데이터베이스 업데이트
        dbHelper.updateUserProfile(1,name, age, gender, date);
        Toast.makeText(this, "사용자 프로필이 수정되었습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }


    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //출력 형식 2021/07/26
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.KOREA);

        activityProfileBinding.editDate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }
}