package kr.co.episode.epilepsee;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

import kr.co.episode.epilepsee.activities.MedicationScheduleActivity;
import kr.co.episode.epilepsee.activities.PeriodRecordActivity;
import kr.co.episode.epilepsee.activities.SeizureActivity;
import kr.co.episode.epilepsee.activities.SideEffectActivity;
import kr.co.episode.epilepsee.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        FirebaseApp.initializeApp(this); // Firebase 초기화

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


        activityMainBinding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // 클릭한 날짜 정보를 문자열로 만듭니다.
                String selectedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);

                // 새로운 Activity로 전달할 Intent를 생성합니다.
                Intent intent = new Intent(MainActivity.this, DailyActivity.class);

                // 클릭한 날짜를 Intent에 추가합니다.
                intent.putExtra("selectedDate", selectedDate);

                // 새로운 Activity를 시작합니다.
                startActivity(intent);
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
    }
}