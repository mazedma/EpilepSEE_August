package kr.co.episode.epilepsee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import kr.co.episode.epilepsee.activities.MedicationScheduleActivity;
import kr.co.episode.epilepsee.activities.PeriodRecordActivity;
import kr.co.episode.epilepsee.activities.SeizureActivity;
import kr.co.episode.epilepsee.activities.SideEffectActivity;
import kr.co.episode.epilepsee.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    private MaterialCalendarView homeScreenCalendarView;
    private DatabaseReference databaseReference;
    private TextView UserNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        FirebaseApp.initializeApp(this); // Firebase 초기화



        /** 홈화면 버튼 기능 설정
         *  menu,add,status
         */
        // menu popup 버튼 연결
        activityMainBinding.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu()); // menu xml에서 메뉴 리스트 가져오는듯


                //아이템 클릭 리스너
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.menu_profile){
//                            Toast.makeText(MainActivity.this, "프로필 클릭", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                            startActivity(intent);
                        }
                        else {
//                            Toast.makeText(MainActivity.this, "대발작 감지 기능", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                            startActivity(intent);
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        // add popup 버튼 연결
        activityMainBinding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                getMenuInflater().inflate(R.menu.popup_add,popupMenu.getMenu()); // menu xml에서 메뉴 리스트 가져오는듯

                //아이템 클릭 리스너
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.add_seizure){
                            Toast.makeText(MainActivity.this, "발작 선택", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), SeizureActivity.class);
                            startActivity(intent);
                        }
                        else if (menuItem.getItemId() == R.id.add_s_effect) {
                            Toast.makeText(MainActivity.this, "부작용 선택", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), SideEffectActivity.class);
                            startActivity(intent);
                        }
                        else if (menuItem.getItemId() == R.id.add_drug) {
                            Toast.makeText(MainActivity.this, "약물 선택", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MedicationScheduleActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "생리 선택", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), PeriodRecordActivity.class);
                            startActivity(intent);
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        /** 달력 설정
         * 날짜를 누르면 DailyActivity로 이동하는 기능 구현
         */
        homeScreenCalendarView = findViewById(R.id.homeScreenCalendarView);
        // Firebase 데이터베이스 레퍼런스를 초기화합니다.
        databaseReference = FirebaseDatabase.getInstance().getReference();
        // 홈 화면의 캘린더 뷰에 날짜 선택 리스너를 설정합니다.
        homeScreenCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                // 클릭한 날짜 정보를 문자열로 만듭니다.
                String selectedDate = String.format("%d-%02d-%02d", date.getYear(), date.getMonth() + 1, date.getDay());

                // 새로운 Activity로 전달할 Intent를 생성합니다.
                Intent intent = new Intent(MainActivity.this, DailyActivity.class);

                // 클릭한 날짜를 Intent에 추가합니다.
                intent.putExtra("selectedDate", selectedDate);

                // 새로운 Activity를 시작합니다.
                startActivity(intent);
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 존재하는 모든 날짜를 저장할 Set을 초기화합니다.
                Set<CalendarDay> existingDates = new HashSet<>();
        // 데이터베이스에서 날짜 값을 가져와 Set에 추가합니다.
        for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
            // 날짜 노드인지 확인
            if (dateSnapshot.getKey() != null && dateSnapshot.getKey().matches("\\d{4}-\\d{2}-\\d{2}")) {
                String dateString = dateSnapshot.getKey();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                try {
                    Date date = dateFormat.parse(dateString);
                    CalendarDay calendarDay = CalendarDay.from(date);
                    existingDates.add(calendarDay);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        // 가져온 날짜 값을 기반으로 CalendarView에 회색 점을 표시합니다.
        for (CalendarDay day : existingDates) {
            homeScreenCalendarView.addDecorator(new EventDotDecorator(Color.parseColor("#6528F7"),existingDates));
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        // 데이터베이스 오류 처리
        Toast.makeText(MainActivity.this, "데이터베이스 오류: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
    }
});
    }
}

