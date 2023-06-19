package kr.co.episode.epilepsee.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import kr.co.episode.epilepsee.MainActivity;
import kr.co.episode.epilepsee.R;
import kr.co.episode.epilepsee.databinding.ActivitySeizureBinding;
import kr.co.episode.epilepsee.fragments.FirstFragment;
import kr.co.episode.epilepsee.fragments.SeventhFragment;

public class SeizureActivity extends AppCompatActivity {

    ActivitySeizureBinding activitySeizureBinding;
    private Button nextButton;
    private Button prevButton;

    private NavController navController;

    //멤버 변수
    private int recordId;
    private String startDate;
    private String startTime;
    private String seizureType;
    private int preDetection;
    private String location;
    private int sleepState;
    private String duration;
    private String recoveryTime;
    private String medicationUsed;
    private String reactionAfter;
    private String symptomBody;
    private String symptomMovement;
    private String symptomEyes;
    private String symptomMouth;
    private String symptomSkinColor;
    private String symptomSuddenUrination;
    private Bundle bundle;

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
                            // 첫 번째 프래그먼트에서 입력된 데이터를 SeventhFragment의 멤버 변수에 저장
                            FirstFragment firstFragment = new FirstFragment();
                            startDate = firstFragment.getStartDate();


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
                        case R.id.seventhFragment:
                            returnToHomeScreen();
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


