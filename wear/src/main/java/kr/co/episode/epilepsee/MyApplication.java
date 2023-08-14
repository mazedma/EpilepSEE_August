package kr.co.episode.epilepsee;

import android.app.Application;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Firebase 초기화
        FirebaseApp.initializeApp(this);

        // Firebase Realtime Database의 오프라인 기능 활성화
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}

