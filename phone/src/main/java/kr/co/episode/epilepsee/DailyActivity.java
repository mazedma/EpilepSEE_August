package kr.co.episode.epilepsee;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

        // selectedDate를 dateTextView에 출력
        dateTextView.setText("선택한 날짜: " + selectedDate);

// ListView를 찾아옵니다.
        final ListView seizureTimeListView = findViewById(R.id.seizureTimeListView);

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
                        final ArrayList<String> seizureTimeList = new ArrayList<>();

                        // seizureData 하위의 모든 자식 노드를 순회합니다.
                        for (DataSnapshot childSnapshot : seizureDataSnapshot.getChildren()) {
                            // 각 자식 노드에서 seizureTime 값을 가져와 목록에 추가합니다.
                            String seizureTime = childSnapshot.child("seizureTime").getValue(String.class);
                            seizureTimeList.add(seizureTime);
                        }

                        // seizureTime 목록을 ListView에 표시합니다.
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(DailyActivity.this, android.R.layout.simple_list_item_1, seizureTimeList);
                        seizureTimeListView.setAdapter(adapter);

                        // ListView의 항목 클릭 이벤트 처리
                        seizureTimeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // 클릭한 항목의 seizureTime 가져오기
                                String selectedSeizureTime = seizureTimeList.get(position);

                                // 해당 seizureTime의 상위 노드의 키값 가져오기
                                String parentKey = getParentKey(selectedSeizureTime, dataSnapshot);

                                // 다음 액티비티로 데이터 전달
                                Intent intent = new Intent(DailyActivity.this, SeizureSummaryActivity.class);
                                intent.putExtra("parentKey", parentKey);
                                startActivity(intent);
                            }
                        });
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

    private String getParentKey(String targetValue, DataSnapshot dataSnapshot) {
        if (targetValue != null) {
            for (DataSnapshot snapshot : dataSnapshot.child("seizureData").getChildren()) {
                String seizureTime = snapshot.child("seizureTime").getValue(String.class);
                if (targetValue.equals(seizureTime)) {
                    return snapshot.getKey();
                }
            }
        }
        return null; // 대상 값이 null이거나 찾지 못한 경우
    }


}

