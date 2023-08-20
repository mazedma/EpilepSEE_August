package kr.co.episode.epilepsee.fragments;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import kr.co.episode.epilepsee.R;

import kr.co.episode.epilepsee.dataModel.SeizureViewModel;
import kr.co.episode.epilepsee.databinding.ActivitySeizureBinding;

public class SeventhFragment extends Fragment {


    private SeizureViewModel seizureViewModel;
    private Button buttonSeizureSave;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_seventh, container, false);
        buttonSeizureSave = rootView.findViewById(R.id.buttonSeizureSave);

        seizureViewModel = new ViewModelProvider(requireActivity()).get(SeizureViewModel.class);

        //발작 후 반응 체크박스
        CheckBox checkHeadache = rootView.findViewById(R.id.checkHeadache);
        CheckBox checkConfusion = rootView.findViewById(R.id.checkConfusion);
        CheckBox checkParalysis = rootView.findViewById(R.id.checkParalysis);
        CheckBox checkWeakness = rootView.findViewById(R.id.checkWeakness);
        CheckBox checkMusclePain = rootView.findViewById(R.id.checkMusclePain);
        CheckBox checkDeepSleep = rootView.findViewById(R.id.checkDeepSleep);
        CheckBox checkNausea = rootView.findViewById(R.id.checkNausea);

        //
        return rootView;
    }



}
