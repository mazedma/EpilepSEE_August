package kr.co.episode.epilepsee.fragments;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import org.w3c.dom.Text;

import kr.co.episode.epilepsee.R;
import kr.co.episode.epilepsee.dataModel.SeizureViewModel;

public class SecondFragment extends Fragment {
    private Button btnPartialSeizure;
    private Button btnGeneralSeizure;

    private SeizureViewModel seizureViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_second, container, false);

        btnPartialSeizure = rootView.findViewById(R.id.btnPartialSeizure);
        btnGeneralSeizure = rootView.findViewById(R.id.btnGeneralSeizure);

        // ViewModelProvider를 통해 SeizureViewModel 인스턴스를 가져오기.
        seizureViewModel = new ViewModelProvider(requireActivity()).get(SeizureViewModel.class);

        btnPartialSeizure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // "부분발작" 값을 seizureViewModel의 seizureTypePrimary 필드에 저장
                seizureViewModel.setSeizureTypePrimary("부분발작");

                // 네번째 화면으로 이동
                Bundle bundle = new Bundle();
                bundle.putString("seizureType", "부분발작");
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_secondFragment_to_fourthFragment_partialSeizure, bundle);
            }
        });

        btnGeneralSeizure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // "전신발작" 값을 seizureViewModel의 seizureTypePrimary 필드에 저장
                seizureViewModel.setSeizureTypePrimary("전신발작");

                //세번째 화면으로 이동
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_secondFragment_to_thirdFragment);
            }


        });

        return rootView;
    }
}


