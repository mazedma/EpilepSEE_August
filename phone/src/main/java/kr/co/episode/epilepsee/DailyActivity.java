package kr.co.episode.epilepsee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class DailyActivity extends AppCompatActivity {

    private ListView timeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        // ActionBar에 백 버튼을 활성화합니다.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Intent에서 클릭한 날짜를 받아옵니다.
        Intent intent = getIntent();
        String selectedDate = intent.getStringExtra("selectedDate");

        // 날짜를 표시할 TextView를 찾아옵니다.
        TextView dateTextView = findViewById(R.id.dateTextView);
        dateTextView.setText("선택한 날짜: " + selectedDate);

        // Firebase Realtime Database 참조 생성
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        // 선택한 날짜에 해당하는 노드로 참조
        DatabaseReference selectedDateReference = databaseReference.child(selectedDate);



        // ValueEventListener를 사용하여 menstrualData의 데이터를 가져옵니다.
        selectedDateReference.child("menstrualData").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // "menstrualBool" 필드가 존재하는 경우에만 처리
                    boolean menstrualBool = dataSnapshot.child("menstrualBool").getValue(Boolean.class);

                    // "O" 또는 "X"를 표시할 TextView를 찾아옵니다.
                    TextView menstrualTextView = findViewById(R.id.menstrualTextView);

                    // menstrualBool 값에 따라 "O" 또는 "X"를 설정합니다.
                    if (menstrualBool) {
                        menstrualTextView.setText("O");
                    } else {
                        menstrualTextView.setText("X");
                    }
                } else {
                    // 데이터가 없을 때 처리할 내용 추가
                    // 예를 들어, TextView에 "데이터 없음" 또는 기본 값을 설정할 수 있습니다.
                    TextView menstrualTextView = findViewById(R.id.menstrualTextView);
                    menstrualTextView.setText("데이터 없음");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 에러 처리
            }
        });

        // ValueEventListener를 사용하여 StatusData의 데이터를 가져옵니다.
        selectedDateReference.child("StatusData").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 기본값 설정
                int mood = 0;
                int sleepQuality = 0;

                // mood와 sleepQuality 값을 가져와서 TextView에 설정합니다.

                DataSnapshot moodSnapshot = dataSnapshot.child("mood");
                if (moodSnapshot.exists()) {
                    mood = moodSnapshot.getValue(Integer.class);
                    // mood 값에 대한 처리 추가
                    ProgressBar moodProgressBar = findViewById(R.id.moodProgressBar);
                    moodProgressBar.setProgress(mood);
                }

                DataSnapshot sleepQualitySnapshot = dataSnapshot.child("sleepQuality");
                if (sleepQualitySnapshot.exists()) {
                    sleepQuality = sleepQualitySnapshot.getValue(Integer.class);
                    // sleepQuality 값에 대한 처리 추가
                    ProgressBar sleepQualityProgressBar = findViewById(R.id.sleepQualityProgressBar);
                    sleepQualityProgressBar.setProgress(sleepQuality);
                }


                // mood와 sleepQuality 값을 ProgressBar에 설정합니다.
                ProgressBar moodProgressBar = findViewById(R.id.moodProgressBar);
                moodProgressBar.setProgress(mood);
                ProgressBar sleepQualityProgressBar = findViewById(R.id.sleepQualityProgressBar);
                sleepQualityProgressBar.setProgress(sleepQuality);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 에러 처리
            }
        });

        // seizureData 및 sideEffectData 가져오기
        // 시간 정보와 키값을 저장할 리스트 생성
        List<String> timeList = new ArrayList<>();

        // ListView를 찾아옵니다.
        timeListView = findViewById(R.id.timeListView);

        // seizureData 및 sideEffectData 가져오기
        selectedDateReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // seizureData 가져오기
                DataSnapshot seizureDataSnapshot = dataSnapshot.child("seizureData");
                if(seizureDataSnapshot.exists()) {
                    for (DataSnapshot snapshot : seizureDataSnapshot.getChildren()) {
                        String seizureKey = snapshot.getKey();
                        String seizureTime = snapshot.child("seizureTime").getValue(String.class);
                        Log.d("SeizureData", "Seizure Key: " + seizureKey + ", Seizure Time: " + seizureTime);
                        timeList.add("Seizure Key: " + seizureKey + "\nSeizure Time: " + seizureTime);
                    }
                }


                // sideEffectData 가져오기
                DataSnapshot sideEffectDataSnapshot = dataSnapshot.child("sideEffectData");
                if(sideEffectDataSnapshot.exists()) {
                    for (DataSnapshot snapshot : sideEffectDataSnapshot.getChildren()) {
                        String sideEffectKey = snapshot.getKey();
                        String sideEffectTime = snapshot.child("sideEffectTime").getValue(String.class);
                        timeList.add("Side Effect Key: " + sideEffectKey + "\nSide Effect Time: " + sideEffectTime);
                    }
                }

                // timeList를 사용하여 ListView 어댑터를 설정하고 표시
                ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(DailyActivity.this, android.R.layout.simple_list_item_1, timeList);
                timeListView.setAdapter(timeAdapter);

                // ListView 아이템 클릭 이벤트 처리
                timeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedText = timeList.get(position);
                        String[] parts = selectedText.split("\n");
                        String keyAndTime = parts[0]; // "Seizure Key: ..." 또는 "Side Effect Key: ..."
                        String key = keyAndTime.split(": ")[1]; // "Seizure Key" 또는 "Side Effect Key" 뒤의 값을 가져옴

                        // SeizureSummaryActivity로 이동하거나 SideEffectSummaryActivity로 이동
                        Intent summaryIntent;
                        if (keyAndTime.startsWith("Seizure Key")) {
                            summaryIntent = new Intent(DailyActivity.this, SeizureSummaryActivity.class);
                        } else {
                            summaryIntent = new Intent(DailyActivity.this, SideEffectSummaryActivity.class);
                        }

                        // 선택된 키값을 인텐트로 전달
                        summaryIntent.putExtra("selectedKey", key);
                        summaryIntent.putExtra("selectedDate",selectedDate); //이나수정
                        startActivity(summaryIntent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 에러 처리
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
