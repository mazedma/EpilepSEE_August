package kr.co.episode.epilepsee;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

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

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySeizureBinding = ActivitySeizureBinding.inflate(getLayoutInflater());
        setContentView(activitySeizureBinding.getRoot());
        //버튼 찾기
        nextButton = activitySeizureBinding.nextButton;
        prevButton = activitySeizureBinding.prevButton;


        //이전 버튼 설정
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(SeizureActivity.this, R.id.nav_host_fragment);
                navController.navigateUp();
            }
        });
        //다음 버튼 설정
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(SeizureActivity.this, R.id.nav_host_fragment);
                NavDestination currentDestination = navController.getCurrentDestination();

                if (currentDestination != null) {
                    int currentDestinationId = currentDestination.getId();

                    switch (currentDestinationId) {
                        case R.id.firstFragment:
                            navController.navigate(R.id.action_firstFragment_to_secondFragment);
                            break;
                        case R.id.secondFragment:
                            navController.navigate(R.id.action_secondFragment_to_thirdFragment);
                            break;
                        case R.id.thirdFragment:
                            navController.navigate(R.id.action_thirdFragment_to_fifthFragment);
                            break;
                        case R.id.fourthFragment:
                            navController.navigate(R.id.action_fourthFragment_to_fifthFragment);
                            break;
                        case R.id.fifthFragment:
                            navController.navigate(R.id.action_fifthFragment_to_sixthFragment);
                            break;
                        case R.id.sixthFragment:
                            navController.navigate(R.id.action_sixthFragment_to_seventhFragment);
                            break;
                        case R.id.sevenFragment:
                            // 마지막 프래그먼트에 도달했을 때 처리할 로직 작성
                            break;
                    }
                }

            }
        });


        //액션바에 백 버튼 추가
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle("발작 기록"); // 화면 제목 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼

    }
    // 액션바의 백 버튼 클릭 이벤트 처리
    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return true;
    }
    // 메인 액티비티로 이동
    private void returnToHomeScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}


