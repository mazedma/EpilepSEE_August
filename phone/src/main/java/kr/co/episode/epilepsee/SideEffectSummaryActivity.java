package kr.co.episode.epilepsee;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;

public class SideEffectSummaryActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private TextView sideEffectInfoTextView;
    private TextView sideEffectTimeTextView;
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
        sideEffectTimeTextView = findViewById(R.id.sideEffectTimeTextView);

        DatabaseReference sideEffectDataReference = databaseReference.child(selectedDate).child("sideEffectData").child(selectedKey);

        sideEffectDataReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String sideEffectTime = (String) dataSnapshot.child("sideEffectTime").getValue();
                    sideEffectTimeTextView.setText("부작용 발생 시각: " + sideEffectTime);

                    List<String> sideEffects = (List<String>) dataSnapshot.child("sideEffectType").getValue();

                    if (sideEffects != null && !sideEffects.isEmpty()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String sideEffect : sideEffects) {
                            stringBuilder.append(sideEffect).append("\n");
                        }
                        sideEffectInfoTextView.setText(stringBuilder.toString());
                    } else {
                        sideEffectInfoTextView.setText("부작용 데이터가 없습니다.");
                    }
                } else {
                    // 데이터가 없는 경우 처리
                    sideEffectInfoTextView.setText("데이터가 없습니다.");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 에러 처리
                Log.e("SideEffectSummaryActivity", "부작용 데이터 가져오기 실패: " + databaseError.getMessage()); // 에러 로그 출력
            }
        });

        //액션바에 백 버튼 추가
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle("부작용 정보"); // 화면 제목 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼
    }
    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return true;
    }

}