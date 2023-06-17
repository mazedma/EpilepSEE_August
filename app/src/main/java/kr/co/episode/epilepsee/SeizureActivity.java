package kr.co.episode.epilepsee;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import kr.co.episode.epilepsee.databinding.ActivitySeizureBinding;

public class SeizureActivity extends AppCompatActivity {

    ActivitySeizureBinding activitySeizureBinding;
    private Button nextButton;
    private Button prevButton;

    private List<Fragment> fragmentList;
    private int currentFragmentIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySeizureBinding = ActivitySeizureBinding.inflate(getLayoutInflater());
        setContentView(activitySeizureBinding.getRoot());
        nextButton = activitySeizureBinding.nextButton;
        prevButton = activitySeizureBinding.prevButton;

        //액션바에 백 버튼 추가
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle("발작 기록"); // 화면 제목 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼

        //이전 버튼 설정
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPreviousFragment();
            }
        });
        //다음 버튼 설정
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNextFragment();
            }
        });

        //프래그먼트 리스트 초기화

        fragmentList = new ArrayList<>();
        fragmentList.add(new FirstFragment());
        fragmentList.add(new SecondFragment());
        fragmentList.add(new ThirdFragment());
        fragmentList.add(new FourthFragment());
        fragmentList.add(new FifthFragment());
        fragmentList.add(new SixthFragment());
        fragmentList.add(new SeventhFragment());

        // 초기 프래그먼트 설정
        currentFragmentIndex = 0;
        Fragment initialFragment = fragmentList.get(currentFragmentIndex);
        replaceFragment(initialFragment);
        updateButtonVisibility();
    }

    private void showPreviousFragment(){
            if(currentFragmentIndex>0){
                currentFragmentIndex--;
                Fragment previousFragment = fragmentList.get(currentFragmentIndex);
                replaceFragment(previousFragment);
                updateButtonVisibility();
            }
        }
    private void showNextFragment() {
        if (currentFragmentIndex < fragmentList.size() - 1) {
            currentFragmentIndex++;
            Fragment nextFragment = fragmentList.get(currentFragmentIndex);
            replaceFragment(nextFragment);
            updateButtonVisibility();
        } else {
            returnToHomeScreen();
        }
        }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    private void updateButtonVisibility() {
        prevButton.setVisibility(currentFragmentIndex > 0 ? View.VISIBLE : View.INVISIBLE);
        nextButton.setVisibility(currentFragmentIndex < fragmentList.size() - 1 ? View.VISIBLE : View.INVISIBLE);
    }

    private void returnToHomeScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }



        // 액션바의 백 버튼 클릭 이벤트 처리
        @Override
        public boolean onSupportNavigateUp () {
            onBackPressed();
            return true;
        }

}
