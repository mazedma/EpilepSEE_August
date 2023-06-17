package kr.co.episode.epilepsee;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class SecondFragment extends Fragment {
    private Button btnPartialSeizure;
    private Button btnGeneralSeizure;
    private String selectedButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_second, container, false);

        btnPartialSeizure = rootView.findViewById(R.id.btnPartialSeizure);
        btnGeneralSeizure = rootView.findViewById(R.id.btnGeneralSeizure);

        btnPartialSeizure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedButton = "partial";
            }
        });

        btnGeneralSeizure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedButton = "general";
            }
        });

        return rootView;
    }

    public String getSelectedButton(){
        return selectedButton;
    }
        //데이터 저장 함수 작성
}


