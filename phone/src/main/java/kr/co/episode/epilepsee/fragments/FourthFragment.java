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

public class FourthFragment extends Fragment {

    private Button partial1;
    private Button partial2;
    private Button partial3;

    private SeizureViewModel seizureViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_fourth, container, false);

        partial1 = rootView.findViewById(R.id.partial1);
        partial2 = rootView.findViewById(R.id.partial2);
        partial3 = rootView.findViewById(R.id.partial3);

        // ViewModelProvider를 통해 SeizureViewModel 인스턴스를 가져오기.
        seizureViewModel = new ViewModelProvider(requireActivity()).get(SeizureViewModel.class);
        partial1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seizureViewModel.setSeizureTypeSecondary("단순 부분 발작");
            }
        });

        partial2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seizureViewModel.setSeizureTypeSecondary("복합 부분 발작");
            }
        });

        partial3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seizureViewModel.setSeizureTypeSecondary("2차성 전신 발작");
            }
        });
        return rootView;

    }
}
