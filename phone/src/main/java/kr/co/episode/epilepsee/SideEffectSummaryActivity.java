package kr.co.episode.epilepsee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SideEffectSummaryActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private TextView sideEffectInfoTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_effect_summary);

        //Firebase Database 참조 가져오기
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Intent로부터 parentKey 값을 가져옵니다.
        Intent intent = getIntent();
        String selectedKey = intent.getStringExtra("selectedKey");
        String selectedDate = intent.getStringExtra("selectedDate");
        sideEffectInfoTextView = findViewById(R.id.sideEffectInfoTextView);

        DatabaseReference sideEffectDataReference = databaseReference.child(selectedDate).child("sideEffectData").child(selectedKey);

        sideEffectDataReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 부작용 데이터를 리스트로 변환
                List<String> sideEffects = (List<String>) dataSnapshot.getValue();

                if (sideEffects != null && !sideEffects.isEmpty()) {
                    // List<String>을 문자열로 변환하여 TextView에 설정합니다.
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String sideEffect : sideEffects) {
                        stringBuilder.append(sideEffect).append("\n");
                    }
                    sideEffectInfoTextView.setText(stringBuilder.toString());
                } else {
                    // 부작용 데이터가 없는 경우 메시지를 표시합니다.
                    sideEffectInfoTextView.setText("부작용 데이터가 없습니다.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 에러 처리
            }
        });
    }
}