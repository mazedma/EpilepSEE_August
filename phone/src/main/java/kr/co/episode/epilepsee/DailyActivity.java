package kr.co.episode.epilepsee;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    // "seizureTime" 및 "sideEffectTime"을 모두 보관하는 사용자 지정 객체를 생성합니다.
    public class TimeData {
        private String seizureTime;
        private String sideEffectTime;

        public TimeData(String seizureTime, String sideEffectTime) {
            this.seizureTime = seizureTime;
            this.sideEffectTime = sideEffectTime;
        }

        public String getSeizureTime() {
            return seizureTime;
        }

        public String getSideEffectTime() {
            return sideEffectTime;
        }
    }

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
        final ListView timeListView = findViewById(R.id.timeListView);

// Firebase에서 해당 날짜의 데이터를 가져와서 화면에 출력합니다.
        databaseReference.child(selectedDate).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        DataSnapshot seizureDataSnapshot = dataSnapshot.child("seizureData");
                        DataSnapshot sideEffectDataSnapshot = dataSnapshot.child("sideEffectData");

                        final ArrayList<TimeData> timeDataList = new ArrayList<>();

                        // "seizureTime" 데이터를 가져옵니다.
                        for (DataSnapshot childSnapshot : seizureDataSnapshot.getChildren()) {
                            String seizureTime = childSnapshot.child("seizureTime").getValue(String.class);
                            timeDataList.add(new TimeData(seizureTime, null));
                        }
                        // "sideEffectTime" 데이터를 가져옵니다.
                        for (DataSnapshot childSnapshot : sideEffectDataSnapshot.getChildren()) {
                            String sideEffectTime = childSnapshot.child("sideEffectTime").getValue(String.class);
                            timeDataList.add(new TimeData(null, sideEffectTime));
                        }

                        // "seizureTime" 및 "sideEffectTime"을 모두 표시하는 사용자 지정 어댑터를 생성합니다.
                        ArrayAdapter<TimeData> adapter = new ArrayAdapter<>(DailyActivity.this, android.R.layout.simple_list_item_1, timeDataList) {
                            @NonNull
                            @Override
                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);
                                TextView textView = view.findViewById(android.R.id.text1);

                                // 해당 데이터가 "seizureTime" 또는 "sideEffectTime"에 따라 텍스트를 설정합니다.
                                TimeData timeData = timeDataList.get(position);
                                if (timeData.getSeizureTime() != null) {
                                    textView.setText("[발작] " + timeData.getSeizureTime());
                                } else if (timeData.getSideEffectTime() != null) {
                                    textView.setText("[부작용] " + timeData.getSideEffectTime());
                                }

                                return view;
                            }
                        };

                        timeListView.setAdapter(adapter);

                        // ListView의 항목 클릭 이벤트 처리
                        timeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                TimeData selectedTimeData = timeDataList.get(position);

                                if (selectedTimeData.getSeizureTime() != null) {
                                    // 발작 시간에 대한 클릭 처리
                                    // ...
                                } else if (selectedTimeData.getSideEffectTime() != null) {
                                    // 부작용 시간에 대한 클릭 처리
                                    // ...
                                }
                            }
                        });
                    } else {
                        // 선택한 날짜에 대한 데이터가 없을 때 처리
                        Log.d("DailyActivity", "선택한 날짜에 대한 데이터가 없습니다.");
                    }
                } else {
                    // 데이터를 가져오는 동안 발생한 오류 처리
                    Exception exception = task.getException();
                    Log.e("DailyActivity", "데이터를 가져오는 동안 오류 발생: " + exception.getMessage());
                }
            }
        });

    }
}

