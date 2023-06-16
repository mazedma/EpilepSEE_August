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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import kr.co.episode.epilepsee.databinding.ActivitySeizureBinding;

public class SeizureActivity extends AppCompatActivity {

    ActivitySeizureBinding activitySeizureBinding;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySeizureBinding = ActivitySeizureBinding.inflate(getLayoutInflater());
        setContentView(activitySeizureBinding.getRoot());

        nextButton = activitySeizureBinding.nextButton;

        //액션바에 백 버튼 추가
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle("발작 기록"); // 화면 제목 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment nextFragment;
                switch (getCurrentFragmentIndex()) {
                    case 0:
                        nextFragment = new SecondFragment();
                        break;
                    case 1:
                        SecondFragment secondFragment = (SecondFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                            if (secondFragment.isPartialSeizureClicked()) {
                                nextFragment = new FourthFragment();
                            } else if (secondFragment.isGeneralSeizureClicked()) {
                                nextFragment = new ThirdFragment();
                            } else {
                                return;
                            }

                        break;

                    default:
                        returnToHomeScreen();
                        return;
                }

                replaceFragment(nextFragment);
            }
        });
    }
        private int getCurrentFragmentIndex () {
            // 현재 표시된 프래그먼트의 인덱스를 반환하는 로직을 작성해주세요.
            FragmentManager fragmentManager = getSupportFragmentManager();
            List<Fragment> fragments = fragmentManager.getFragments();
            int lastIndex = fragments.size() - 1;
            for (int i = lastIndex; i >= 0; i--) {
                Fragment fragment = fragments.get(i);
                if (fragment != null && fragment.isVisible()) {
                    return i;
                }
            }
            return -1;
        }

        public void replaceFragment (Fragment fragment){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);

            fragmentTransaction.commit();
        }
        private void returnToHomeScreen () {
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
