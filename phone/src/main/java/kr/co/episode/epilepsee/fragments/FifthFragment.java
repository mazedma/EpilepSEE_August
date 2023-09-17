package kr.co.episode.epilepsee.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import kr.co.episode.epilepsee.R;
import kr.co.episode.epilepsee.dataModel.SeizureViewModel;

public class FifthFragment extends Fragment {

    Button seizurePredictedTrue;
    Button seizurePredictedFalse;

    private Button selectedButton;
    private SeizureViewModel seizureViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_fifth, container, false);

        seizurePredictedTrue = rootView.findViewById(R.id.seizurePredictedTrue);
        seizurePredictedFalse = rootView.findViewById(R.id.seizurePredictedFalse);

        // ViewModelProvider를 통해 SeizureViewModel 인스턴스를 가져오기.
        seizureViewModel = new ViewModelProvider(requireActivity()).get(SeizureViewModel.class);

        seizurePredictedTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    selectButton(seizurePredictedTrue, "#6528F7");
                    seizureViewModel.setSeizurePredicted(true);
                } catch (Exception e) {
                    Log.e("FifthFragment", "Error in onClick for seizurePredictedTrue: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        seizurePredictedFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectButton(seizurePredictedFalse, "#6528F7");
                seizureViewModel.setSeizurePredicted(false);
            }
        });
        return rootView;
    }
    private void selectButton(Button button, String backgroundColor) {
        if (selectedButton != null) {
            // 다른 버튼을 선택하면 이전 버튼의 색상을 원래대로 되돌리기
            selectedButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_ripple_round));
        }
        // 선택한 버튼의 클릭 효과 적용
        Drawable drawable = ContextCompat.getDrawable(requireContext(), R.drawable.button_ripple_round);
        drawable.setColorFilter(Color.parseColor(backgroundColor), PorterDuff.Mode.SRC);
        button.setBackground(drawable);

        selectedButton = button;
    }
}
