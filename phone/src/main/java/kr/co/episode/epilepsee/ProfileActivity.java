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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


import kr.co.episode.epilepsee.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {


    private DatabaseReference databaseReference;
    private EditText etName;
    private Button btnUserDataSave;

    //Calendar
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        }
    };

    ActivityProfileBinding activityProfileBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(activityProfileBinding.getRoot());
        // Firebase Database 초기화
        databaseReference = FirebaseDatabase.getInstance().getReference();

        etName = findViewById(R.id.etName);
        btnUserDataSave = findViewById(R.id.btnUserDataSave);

        btnUserDataSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = etName.getText().toString();
                // Firebase Database에 사용자 이름 저장
                if (!userName.isEmpty()) {
                    // "users"라는 노드 아래에 사용자 이름을 저장합니다.
                    databaseReference.child("users").setValue(userName);


                    Toast.makeText(ProfileActivity.this, "이름이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfileActivity.this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }

        });
        // 액션바 설정
        getSupportActionBar().setTitle("프로필 설정"); // 화면 제목 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼


        activityProfileBinding.editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ProfileActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

}