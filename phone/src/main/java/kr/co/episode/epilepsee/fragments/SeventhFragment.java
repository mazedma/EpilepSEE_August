package kr.co.episode.epilepsee.fragments;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kr.co.episode.epilepsee.R;

import kr.co.episode.epilepsee.databinding.ActivitySeizureBinding;

public class SeventhFragment extends Fragment {

    //브랜치 변경 확인


    private Button buttonSeizureSave;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_seventh, container, false);
        buttonSeizureSave = rootView.findViewById(R.id.buttonSeizureSave);


        return rootView;
    }



}
