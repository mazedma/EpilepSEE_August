package kr.co.episode.epilepsee;

import android.content.Context;
import android.os.Bundle;
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
    private boolean isPartialSeizureClicked = false;
    private boolean isGeneralSeizureClicked = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_second, container, false);

        TextView textView7 = rootView.findViewById(R.id.textView7);
        textView7.setText("isPartialSeizureClicked: "+ (isPartialSeizureClicked ? "true" : "false"));

        btnPartialSeizure = rootView.findViewById(R.id.btnPartialSeizure);

        btnPartialSeizure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPartialSeizureClicked = true;  // 부분 발작 버튼이 클릭되었음을 표시
                isGeneralSeizureClicked = false;  // 전신 발작 버튼은 클릭되지 않았음을 표시
                textView7.setText("isPartialSeizureClicked: "+ (isPartialSeizureClicked ? "true" : "false"));
            }
        });
        btnGeneralSeizure = rootView.findViewById(R.id.btnGeneralSeizure);
        btnGeneralSeizure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPartialSeizureClicked = false;  // 부분 발작 버튼은 클릭되지 않았음을 표시
                isGeneralSeizureClicked = true;  // 전신 발작 버튼이 클릭되었음을 표시
                textView7.setText("isPartialSeizureClicked: "+ (isPartialSeizureClicked ? "true" : "false"));

            }

        });

        return rootView;
    }
        public boolean isPartialSeizureClicked() {
            return isPartialSeizureClicked;
        }

        public boolean isGeneralSeizureClicked() {
            return isGeneralSeizureClicked;
        }


        //데이터 저장 함수 작성
}


