package kr.co.episode.epilepsee.fragments;



import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import kr.co.episode.epilepsee.MainActivity;
import kr.co.episode.epilepsee.R;

import kr.co.episode.epilepsee.dataModel.SeizureViewModel;
import kr.co.episode.epilepsee.databinding.ActivitySeizureBinding;

public class SeventhFragment extends Fragment {


    private SeizureViewModel seizureViewModel;
    private Button btnSeizureSave;
    private CheckBox checkHeadache, checkConfusion, checkParalysis, checkWeakness, checkMusclePain,
            checkDeepSleep, checkNausea;
    private RadioButton allSymptomBody, rightSymptomBody, leftSymptomBody, otherSymptomBody,
            twitchSymptomMovement, stiffnessSymptomMovement, otherSymptomMovement, aboveSymptomEyes,
            rightSymptomEyes, leftSymptomEyes, closedSymptomEyes, stareSymptomEyes, blinkSymptomEyes,
            otherSymptomEyes, tinglingSymptomMouth, droolingSymptomMouth, foamSymptomMouth,
            bitingSymptomMouth, otherSymptomMouth, blueSymptomSkinColor, otherSymptomSkinColor,
            urinationUrine, urinationStool;

    //체크박스 저장용 리스트
    private List<String> selectedItems = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_seventh, container, false);

        btnSeizureSave = rootView.findViewById(R.id.btnSeizureSave);

        seizureViewModel = new ViewModelProvider(requireActivity()).get(SeizureViewModel.class);

        //발작 후 반응 체크박스
        checkHeadache = rootView.findViewById(R.id.checkHeadache);
        checkConfusion = rootView.findViewById(R.id.checkConfusion);
        checkParalysis = rootView.findViewById(R.id.checkParalysis);
        checkWeakness = rootView.findViewById(R.id.checkWeakness);
        checkMusclePain = rootView.findViewById(R.id.checkMusclePain);
        checkDeepSleep = rootView.findViewById(R.id.checkDeepSleep);
        checkNausea = rootView.findViewById(R.id.checkNausea);
        //체크박스 리스너 설정
        checkHeadache.setOnCheckedChangeListener((buttonView, isChecked) -> handleCheckboxChange("두통", isChecked));
        checkConfusion.setOnCheckedChangeListener((buttonView, isChecked) -> handleCheckboxChange("혼란", isChecked));
        checkParalysis.setOnCheckedChangeListener((buttonView, isChecked) -> handleCheckboxChange("마비", isChecked));
        checkWeakness.setOnCheckedChangeListener((buttonView, isChecked) -> handleCheckboxChange("무기력증", isChecked));
        checkMusclePain.setOnCheckedChangeListener((buttonView, isChecked) -> handleCheckboxChange("근육통", isChecked));
        checkDeepSleep.setOnCheckedChangeListener((buttonView, isChecked) -> handleCheckboxChange("깊은 수면", isChecked));
        checkNausea.setOnCheckedChangeListener((buttonView, isChecked) -> handleCheckboxChange("메스꺼움", isChecked));

        //경련증상-몸
        allSymptomBody = rootView.findViewById(R.id.allSymptomBody);
        rightSymptomBody = rootView.findViewById(R.id.rightSymptomBody);
        leftSymptomBody = rootView.findViewById(R.id.leftSymptomBody);
        otherSymptomBody = rootView.findViewById(R.id.otherSymptomBody);

        //경련증상-움직임
        twitchSymptomMovement = rootView.findViewById(R.id.twitchSymptomMovement);
        stiffnessSymptomMovement = rootView.findViewById(R.id.stiffnessSymptomMovement);
        otherSymptomMovement = rootView.findViewById(R.id.otherSymptomMovement);

        //경련증상-눈
        aboveSymptomEyes = rootView.findViewById(R.id.aboveSymptomEyes);
        rightSymptomEyes = rootView.findViewById(R.id.rightSymptomEyes);
        leftSymptomEyes = rootView.findViewById(R.id.leftSymptomEyes);
        closedSymptomEyes = rootView.findViewById(R.id.closedSymptomEyes);
        stareSymptomEyes = rootView.findViewById(R.id.stareSymptomEyes);
        blinkSymptomEyes = rootView.findViewById(R.id.blinkSymptomEyes);
        otherSymptomEyes = rootView.findViewById(R.id.otherSymptomEyes);

        //경련증상- 입
        tinglingSymptomMouth = rootView.findViewById(R.id.tinglingSymptomMouth);
        droolingSymptomMouth = rootView.findViewById(R.id.droolingSymptomMouth);
        foamSymptomMouth = rootView.findViewById(R.id.foamSymptomMouth);
        bitingSymptomMouth = rootView.findViewById(R.id.bitingSymptomMouth);
        otherSymptomMouth = rootView.findViewById(R.id.otherSymptomMouth);

        //피부색
        blueSymptomSkinColor = rootView.findViewById(R.id.blueSymptomSkinColor);
        otherSymptomSkinColor = rootView.findViewById(R.id.otherSymptomSkinColor);

        //갑작스러운 배뇨
        urinationUrine = rootView.findViewById(R.id.urinationUrine);
        urinationStool = rootView.findViewById(R.id.urinationStool);

        //경련증상- 몸 라디오버튼 리스너+ ViewModel로 전달
        RadioGroup symptomBodyGroup = rootView.findViewById(R.id.symptomBodyGroup);
        symptomBodyGroup.setOnCheckedChangeListener((group,checkedId)->{
            String selectedBodySymptom;
            // 선택된 라디오버튼의 ID를 기반으로 값을 설정합니다.
            switch (checkedId) {
                case R.id.allSymptomBody:
                    selectedBodySymptom = "전체";
                    break;
                case R.id.rightSymptomBody:
                    selectedBodySymptom = "오른쪽";
                    break;
                case R.id.leftSymptomBody:
                    selectedBodySymptom = "왼쪽";
                    break;
                case R.id.otherSymptomBody:
                    selectedBodySymptom = "기타";
                    break;
                default:
                    selectedBodySymptom = null;
            }

            seizureViewModel.setSeizureSymptomBody(selectedBodySymptom);
        });

        //움직임 라디오그룹리스너+뷰모델로 전달
        RadioGroup symptomMovementGroup = rootView.findViewById(R.id.symptomMovementGroup);
        symptomMovementGroup.setOnCheckedChangeListener((group,checkedId)->{
            String selectedMovementSymptom;

            switch(checkedId){
                case R.id.twitchSymptomMovement:
                    selectedMovementSymptom = "움찔거림";
                    break;
                case R.id.stiffnessSymptomMovement:
                    selectedMovementSymptom = "뻣뻣해짐";
                    break;
                case R.id.otherSymptomMovement:
                    selectedMovementSymptom = "기타";
                    break;
                default:
                    selectedMovementSymptom = null;

            }
            seizureViewModel.setSeizureSymptomMovement(selectedMovementSymptom);
        });

        //눈 라디오그룹 리스너 + 뷰모델로 전달
        RadioGroup symptomEyesGroup = rootView.findViewById(R.id.symptomEyesGroup);
        symptomEyesGroup.setOnCheckedChangeListener((group, checkedId)->{
            String selectedEyesSymptom;

            switch(checkedId){
                case R.id.aboveSymptomEyes:
                    selectedEyesSymptom = "눈동자 위";
                    break;
                case R.id.rightSymptomEyes:
                    selectedEyesSymptom = "눈동자 오른쪽";
                    break;
                case R.id.leftSymptomEyes:
                    selectedEyesSymptom = "눈동자 왼쪽";
                    break;
                default:
                    selectedEyesSymptom = null;
            }
            seizureViewModel.setSeizureSymptomEyes(selectedEyesSymptom);
        });

        // 눈 증상2 리스너 및 뷰모델로 전달
        RadioGroup symptomEyesGroup2 = rootView.findViewById(R.id.symptomEyesGroup2);
        symptomEyesGroup2.setOnCheckedChangeListener((group,checkedId)->{
            String selectedEyesSymptom2;

            switch(checkedId){
                case R.id.closedSymptomEyes:
                    selectedEyesSymptom2 = "눈이 감김";
                    break;
                case R.id.stareSymptomEyes:
                    selectedEyesSymptom2 = "멍하게 응시";
                    break;
                case R.id.blinkSymptomEyes:
                    selectedEyesSymptom2 = "깜빡임";
                    break;
                case R.id.otherSymptomEyes:
                    selectedEyesSymptom2 = "기타";
                    break;
                default:
                    selectedEyesSymptom2 = null;
            }
            seizureViewModel.setSeizureSymptomEyes2(selectedEyesSymptom2);
        });

        // 입 증상 라디오 그룹 초기화
        RadioGroup symptomMouthGroup = rootView.findViewById(R.id.symptomMouthGroup);

        // 입 증상 라디오 그룹 리스너 설정
        symptomMouthGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String selectedMouthSymptom;  // 기본값은 null로 설정

            // 선택된 라디오버튼의 ID를 기반으로 값을 설정합니다.
            switch (checkedId) {
                case R.id.tinglingSymptomMouth:
                    selectedMouthSymptom = "입마름";
                    break;
                case R.id.droolingSymptomMouth:
                    selectedMouthSymptom = "침흘림";
                    break;
                case R.id.foamSymptomMouth:
                    selectedMouthSymptom = "거품";
                    break;
                case R.id.bitingSymptomMouth:
                    selectedMouthSymptom = "혀깨물음";
                    break;
                case R.id.otherSymptomMouth:
                    selectedMouthSymptom = "기타";
                    break;
                default:
                    selectedMouthSymptom = null;
            }
            // 선택된 값을 seizureViewModel에 저장
            seizureViewModel.setSeizureSymptomMouth(selectedMouthSymptom);
        });

        // 피부색 라디오 그룹 초기화
        RadioGroup symptomSkinColorGroup = rootView.findViewById(R.id.symptomSkinColorGroup);
        // 피부색 라디오 그룹 리스너 설정
        symptomSkinColorGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String selectedSkinColorSymptom;  // 기본값은 null로 설정

            // 선택된 라디오버튼의 ID를 기반으로 값을 설정합니다.
            switch (checkedId) {
                case R.id.blueSymptomSkinColor:
                    selectedSkinColorSymptom = "청색증";
                    break;
                case R.id.otherSymptomSkinColor:
                    selectedSkinColorSymptom = "기타";
                    break;
                default:
                    selectedSkinColorSymptom = null;
            }

            // 선택된 값을 seizureViewModel에 저장
            seizureViewModel.setSeizureSymptomSkinColor(selectedSkinColorSymptom);
        });

        //갑작스러운 배뇨
        RadioGroup symptomUrinationGroup = rootView.findViewById(R.id.symptomUrinationGroup);
        symptomUrinationGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String selectedUrinationSymptom;

            // 선택된 라디오버튼의 ID를 기반으로 값을 설정합니다.
            switch (checkedId) {
                case R.id.urinationUrine:
                    selectedUrinationSymptom = "소변";
                    break;
                case R.id.urinationStool:
                    selectedUrinationSymptom = "대변";
                    break;
                default:
                    selectedUrinationSymptom = null;
            }

            // 설정된 값을 ViewModel에 저장합니다.
            seizureViewModel.setSeizureSymptomSuddenUrinationDefecation(selectedUrinationSymptom);
        });


        //저장 버튼 클릭 이벤트 처리
            btnSeizureSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ViewModel에 저장된 데이터를 데이터베이스로 보내는 코드
                saveAllDataToFirebase();
            }
        });

        //[편의성(삭제 가능)] 초기값 설정 코드/ 화면 이동 시에도 체크 상태를 저장하기 위한 코드
        String initialBodySymptom = seizureViewModel.getSeizureSymptomBody();
        if (initialBodySymptom != null && !initialBodySymptom.isEmpty()) {
            switch (initialBodySymptom) {
                case "전체":
                    allSymptomBody.setChecked(true);
                    break;
                case "오른쪽":
                    rightSymptomBody.setChecked(true);
                    break;
                case "왼쪽":
                    leftSymptomBody.setChecked(true);
                    break;
                case "기타":
                    otherSymptomBody.setChecked(true);
                    break;
            }
        }
        String initialMovementSymptom = seizureViewModel.getSeizureSymptomMovement();
        if(initialMovementSymptom != null && !initialMovementSymptom.isEmpty()){
        switch(initialMovementSymptom){
            case "움찔거림":
                twitchSymptomMovement.setChecked(true);
                break;
            case "뻣뻣해짐":
                stiffnessSymptomMovement.setChecked(true);
                break;
            case "기타":
                otherSymptomMovement.setChecked(true);
                break;
            }
        }

        String initialEyesSymptom = seizureViewModel.getSeizureSymptomEyes();
        if(initialEyesSymptom !=null && !initialEyesSymptom.isEmpty()){
            switch(initialEyesSymptom) {
                case "눈동자 위":
                    aboveSymptomEyes.setChecked(true);
                    break;
                case "눈동자 오른쪽":
                    rightSymptomEyes.setChecked(true);
                    break;
                case "눈동자 왼쪽":
                    leftSymptomEyes.setChecked(true);
                    break;
            }
        }
        String initialEyesSymptom2 = seizureViewModel.getSeizureSymptomEyes2();
        if(initialEyesSymptom2 !=null && !initialEyesSymptom2.isEmpty()){
            switch(initialEyesSymptom2) {
                case "눈이 감김":
                    closedSymptomEyes.setChecked(true);
                    break;
                case "멍하게 응시":
                    stareSymptomEyes.setChecked(true);
                    break;
                case "깜빡임":
                    blinkSymptomEyes.setChecked(true);
                    break;
                case "기타":
                    otherSymptomEyes.setChecked(true);
                    break;
            }
        }
        String initialMouthSymptom = seizureViewModel.getSeizureSymptomMouth();
            if(initialMouthSymptom != null && !initialMouthSymptom.isEmpty()) {
                switch (initialMouthSymptom) {
                    case "입마름":
                        tinglingSymptomMouth.setChecked(true);
                        break;
                    case "침흘림":
                        droolingSymptomMouth.setChecked(true);
                        break;
                    case "거품":
                        foamSymptomMouth.setChecked(true);
                        break;
                    case "혀깨물음":
                        bitingSymptomMouth.setChecked(true);
                        break;
                    case "기타":
                        otherSymptomMouth.setChecked(true);
                        break;
                }
            }

            String initialSkinSymptom = seizureViewModel.getSeizureSymptomSkinColor();
            if (initialSkinSymptom != null && !initialSkinSymptom.isEmpty()){
                switch (initialSkinSymptom) {
                    case "청색증":
                        blueSymptomSkinColor.setChecked(true);
                        break;
                    case "기타":
                        otherSymptomSkinColor.setChecked(true);
                        break;
                }
            }

        String initialUrinationSymptom = seizureViewModel.getSeizureSymptomSuddenUrinationDefecation();
        if (initialUrinationSymptom != null && !initialUrinationSymptom.isEmpty()){
            switch (initialUrinationSymptom) {
                case "소변":
                    urinationUrine.setChecked(true);
                    break;
                case "대변":
                    urinationStool.setChecked(true);
                    break;
            }
        }

        return rootView;
    }

    //체크박스 리스트를 SeizureReaction에 추가
    private void handleCheckboxChange(String item, boolean isChecked){
        if(isChecked){
            //체크됐을 때 리스트에 추가
            selectedItems.add(item);
        }else {
            selectedItems.remove(item);
        }

        seizureViewModel.setSeizureReaction(selectedItems);
    }

    private void saveAllDataToFirebase(){
        //ViewModel을 통해서 데이터 가져오기
        SeizureViewModel seizureViewModel = new ViewModelProvider(requireActivity()).get(SeizureViewModel.class);

        //Firebase 데이터베이스 루트 레퍼런스 가져오기.
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        // 발작 데이터를 저장할 날짜를 가져옴 (yyyy-MM-dd 형식)
        String seizureDate = seizureViewModel.getSeizureDate();
        // 날짜를 기준으로 데이터 저장
        DatabaseReference dateReference = databaseReference.child(seizureDate).child("SeizureData");
        // 발작 시각 등의 데이터를 저장
        DatabaseReference seizureReference = dateReference.push();
        seizureReference.child("seizureTime").setValue(seizureViewModel.getSeizureTime());
        seizureReference.child("seizureTypePrimary").setValue(seizureViewModel.getSeizureTypePrimary());
        seizureReference.child("seizureTypeSecondary").setValue(seizureViewModel.getSeizureTypeSecondary());
        seizureReference.child("seizurePredicted").setValue(seizureViewModel.isSeizurePredicted());
        seizureReference.child("seizureLocation").setValue(seizureViewModel.getSeizureLocation());
        seizureReference.child("seizureDuringSleep").setValue(seizureViewModel.isSeizureDuringSleep());
        seizureReference.child("seizureDuration").setValue(seizureViewModel.getSeizureDuration());
        seizureReference.child("recoveryTime").setValue(seizureViewModel.getRecoveryTime());
        seizureReference.child("emergencyMedication").setValue(seizureViewModel.getEmergencyMedication());
        seizureReference.child("seizureReaction").setValue(seizureViewModel.getSeizureReaction());
        seizureReference.child("seizureSymptomBody").setValue(seizureViewModel.getSeizureSymptomBody());
        seizureReference.child("seizureSymptomMovement").setValue(seizureViewModel.getSeizureSymptomMovement());
        seizureReference.child("seizureSymptomEyes").setValue(seizureViewModel.getSeizureSymptomEyes());
        seizureReference.child("seizureSymptomEyes2").setValue(seizureViewModel.getSeizureSymptomEyes2());
        seizureReference.child("seizureSymptomMouth").setValue(seizureViewModel.getSeizureSymptomMouth());
        seizureReference.child("seizureSymptomSkinColor").setValue(seizureViewModel.getSeizureSymptomSkinColor());
        seizureReference.child("seizureSymptomSuddenUrinationDefecation").setValue(seizureViewModel.getSeizureSymptomSuddenUrinationDefecation());
        // 저장이 완료되었다는 메시지 표시
        Toast.makeText(requireContext(), "데이터가 저장되었습니다.", Toast.LENGTH_SHORT).show();
        // 메인 화면으로 이동
        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(mainIntent);
        requireActivity().finish(); // 현재 액티비티 종료
    }
}
