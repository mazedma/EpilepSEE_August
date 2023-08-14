package kr.co.episode.epilepsee.fragments;

import android.os.Bundle;
import android.util.Log;
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

public class ThirdFragment extends Fragment {
    private Button general1;
    private Button general2;
    private Button general3;
    private Button general4;

    private SeizureViewModel seizureViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_third, container, false);

        general1 = rootView.findViewById(R.id.general1);
        general2 = rootView.findViewById(R.id.general2);
        general3 = rootView.findViewById(R.id.general3);
        general4 = rootView.findViewById(R.id.general4);

        // ViewModelProvider를 통해 SeizureViewModel 인스턴스를 가져오기.
        seizureViewModel = new ViewModelProvider(requireActivity()).get(SeizureViewModel.class);

        general1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seizureViewModel.setSeizureTypeSecondary("전신성 긴장간대발작");
            }
        });

        general2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seizureViewModel.setSeizureTypeSecondary("전신성 소발작");
            }
        });

        general3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seizureViewModel.setSeizureTypeSecondary("간대성 근경련 발작");
            }
        });

        general4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seizureViewModel.setSeizureTypeSecondary("무긴장성 발작");
            }
        });
        return rootView;
    }
}
