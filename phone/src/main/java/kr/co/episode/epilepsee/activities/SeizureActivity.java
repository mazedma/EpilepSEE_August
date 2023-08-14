package kr.co.episode.epilepsee.activities;
//깃헙 브랜치 연동 확인 23.08.07 이이나
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Date;

import kr.co.episode.epilepsee.MainActivity;
import kr.co.episode.epilepsee.R;
import kr.co.episode.epilepsee.dataModel.SeizureData;
import kr.co.episode.epilepsee.databinding.ActivitySeizureBinding;
import kr.co.episode.epilepsee.fragments.FirstFragment;
import kr.co.episode.epilepsee.fragments.SeventhFragment;

public class SeizureActivity extends AppCompatActivity {

    ActivitySeizureBinding activitySeizureBinding;
    private Button nextButton;
    private Button prevButton;

    private NavController navController;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySeizureBinding = ActivitySeizureBinding.inflate(getLayoutInflater());
        setContentView(activitySeizureBinding.getRoot());

        // Firebase 데이터베이스 레퍼런스 생성
        databaseReference = FirebaseDatabase.getInstance().getReference();
        // 노드 생성 및 저장
        createAndSaveSeizureNode();
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
//                            FirstFragment firstFragment = new FirstFragment();
//                            startDate = firstFragment.getStartDate();


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

    private void createAndSaveSeizureNode() {
        // 사용자 입력을 받아 데이터 모델에 저장
        SeizureData seizureData = new SeizureData();
        seizureData.setSeizureTime(new Date());
        seizureData.setSeizureTypePrimary("부분 발작");
        seizureData.setSeizureTypeSecondary("단순부분발작"); // 발작 유형(구분2)
        seizureData.setSeizurePredicted(true); // 발작 예측
        seizureData.setSeizureLocation("집"); // 발생 장소
        seizureData.setSeizureDuringSleep(false); // 수면중 여부
        seizureData.setSeizureDuration("03:22"); // 발작 지속 시간
        seizureData.setRecoveryTime("15"); // 회복에 걸린 시간
        seizureData.setEmergencyMedication("약물 이름"); // 사용된 긴급 약물
        seizureData.setSeizureReaction("두통"); // 발작 후 반응
        seizureData.setSeizureSymptomBody("전체"); // 경련 증상-몸
        seizureData.setSeizureSymptomMovement("움찔거림"); // 경련 증상-움직임
        seizureData.setSeizureSymptomEyes("눈동자 위"); // 경련 증상-눈
        seizureData.setSeizureSymptomMouth("입마름"); // 경련 증상-입
        seizureData.setSeizureSymptomSkinColor("청색증"); // 경련 증상-피부색
        seizureData.setSeizureSymptomSuddenUrinationDefecation("소변"); // 경련 증상-갑작스러운 배뇨 배변
        seizureData.setSeizureMemo("추가 기록사항"); // 메모

        // Firebase에 데이터 저장
        String key = databaseReference.child("seizure_records").push().getKey();
        databaseReference.child("seizure_records").child(key).setValue(seizureData);
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


