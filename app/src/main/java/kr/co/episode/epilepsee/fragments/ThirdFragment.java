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

import kr.co.episode.epilepsee.R;

public class ThirdFragment extends Fragment {
    private String seizureType;
    private Button general1;
    private Button general2;
    private Button general3;
    private Button general4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_third, container, false);


        return rootView;
    }
    private String getSelectedSeizure(){
        String selectedSeizure = "";


        if(general1.isSelected()){
            selectedSeizure = "전신성 긴장간대발작";
        } else if (general2.isSelected()) {
            selectedSeizure = "전신성 소발작";
        } else if (general3.isSelected()) {
            selectedSeizure = "간대성근경련발작";
        } else if (general4.isSelected()) {
            selectedSeizure = "무긴장성 발작";
        }

        return selectedSeizure;
    }

}
