package kr.co.episode.epilepsee;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DailyActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        // ActionBar에 백 버튼을 활성화합니다.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Firebase 데이터베이스 레퍼런스 설정
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Intent에서 클릭한 날짜를 받아옵니다.
        Intent intent = getIntent();
        final String selectedDate = intent.getStringExtra("selectedDate");

        // 날짜를 표시할 TextView를 찾아옵니다.
        final TextView dateTextView = findViewById(R.id.dateTextView);

// Firebase에서 해당 날짜의 데이터를 가져와서 화면에 출력합니다.
        databaseReference.child(selectedDate).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        // 해당 날짜의 데이터를 가져옵니다.
                        DataSnapshot seizureDataSnapshot = dataSnapshot.child("seizureData");

                        // seizureTime을 저장할 문자열 목록을 만듭니다.
                        ArrayList<String> seizureTimeList = new ArrayList<>();

                        // seizureData 하위의 모든 자식 노드를 순회합니다.
                        for (DataSnapshot childSnapshot : seizureDataSnapshot.getChildren()) {
                            // 각 자식 노드에서 seizureTime 값을 가져와 목록에 추가합니다.
                            String seizureTime = childSnapshot.child("seizureTime").getValue(String.class);
                            seizureTimeList.add(seizureTime);
                        }

                        // seizureTime 목록을 화면에 출력하거나 다른 작업을 수행하세요.
                        TextView seizureTimeTextView = findViewById(R.id.seizureTimeTextView);
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String time : seizureTimeList) {
                            stringBuilder.append(time).append("\n");
                        }
                        seizureTimeTextView.setText(stringBuilder.toString());

                        // selectedDate를 dateTextView에 출력
                        dateTextView.setText("선택한 날짜: " + selectedDate);
                    } else {
                        // 해당 날짜의 데이터가 없을 경우 처리
                        Log.d("DailyActivity", "해당 날짜의 데이터가 없습니다.");
                    }
                } else {
                    // 데이터를 가져오는 도중 오류가 발생한 경우 처리
                    Exception exception = task.getException();
                    Log.e("DailyActivity", "데이터를 가져오는 도중 오류가 발생했습니다: " + exception.getMessage());
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // ActionBar의 백 버튼을 누를 때
        if (id == android.R.id.home) {
            finish(); // 현재 Activity를 종료합니다.
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

