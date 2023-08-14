package kr.co.episode.epilepsee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import kr.co.episode.epilepsee.R;
import kr.co.episode.epilepsee.dataModel.SeizureViewModel;

public class FifthFragment extends Fragment {

    Button seizurePredictedTrue;
    Button seizurePredictedFalse;

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
                seizureViewModel.setSeizurePredicted(true);
            }
        });
        seizurePredictedFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seizureViewModel.setSeizurePredicted(false);
            }
        });
        return rootView;
    }
}
