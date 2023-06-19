package kr.co.episode.epilepsee.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "EpSeeDataBase.db";
    private static final int DATABASE_VERSION = 1;

    //테이블 이름 선언
    private static final String TABLE_USER_PROFILE = "user_profile";
    private static final String TABLE_APP_SETTINGS = "app_settings";
    private static final String TABLE_SEIZURE_RECORD = "seizure_record";
    private static final String TABLE_SIDE_EFFECTS = "side_effects";
    private static final String TABLE_PERIOD_RECORD = "period_record";
    private static final String TABLE_MEDICATION_SCHEDULE = "medication_schedule";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //사용자 프로필 테이블 생성
        String createProfileTable = "CREATE TABLE IF NOT EXISTS "+ TABLE_USER_PROFILE +
                "(" + "PROFILE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "AGE INTEGER," +
                "GENDER TEXT," +
                "ONSET_DATE TEXT);";

        sqLiteDatabase.execSQL(createProfileTable);

        //세팅 테이블 생성
        String createSettingsTable = "CREATE TABLE IF NOT EXISTS " + TABLE_APP_SETTINGS +
                "(" + "SETTING_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DETECTION_ENABLED INTEGER," +
                "VOICE_GUIDANCE_ENABLED INTEGER," +
                "VOICE_GUIDANCE_COMMENT TEXT," +
                "AUTOMATIC_REPORT_ENABLED INTEGER);";

        sqLiteDatabase.execSQL(createSettingsTable);

        //발작 기록 테이블 생성
        String createSeizureTable = "CREATE TABLE IF NOT EXISTS " + TABLE_SEIZURE_RECORD +
                "(" + "RECORD_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "START_DATE TEXT," +
                "START_TIME TEXT," +
                "SEIZURE_TYPE TEXT," +
                "PRE_DETECTION INTEGER," +
                "LOCATION TEXT," +
                "SLEEP_STATE INTEGER," +
                "DURATION TEXT," +
                "RECOVERY_TIME TEXT," +
                "MEDICATION_USED TEXT," +
                "REACTION_AFTER TEXT," +
                "SYMPTOM_BODY TEXT," +
                "SYMPTOM_MOVEMENT TEXT," +
                "SYMPTOM_EYES TEXT," +
                "SYMPTOM_MOUTH TEXT," +
                "SYMPTOM_SKIN_COLOR TEXT," +
                "SYMPTOM_SUDDEN_URINATION TEXT);";

        sqLiteDatabase.execSQL(createSeizureTable);

        //약물 부작용 테이블 생성
        String createSideEffectsTable = "CREATE TABLE IF NOT EXISTS " + TABLE_SIDE_EFFECTS +
                "(" + "SIDE_EFFECT_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DATE TEXT," +
                "SIDE_EFFECT_TYPE TEXT," +
                "INTENSITY TEXT);";
        sqLiteDatabase.execSQL(createSideEffectsTable);
        //생리 기록 테이블 생성
        String createPeriodTable = "CREATE TABLE IF NOT EXISTS " + TABLE_PERIOD_RECORD +
                "(" + "INPUT_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "MENSTRUATION_DATE TEXT);";

        sqLiteDatabase.execSQL(createPeriodTable);

        //약물 복용 테이블 생성
        String createMedicationTable = "CREATE TABLE IF NOT EXISTS " + TABLE_MEDICATION_SCHEDULE +
                "(" + "INTAKE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DAILY_FREQUENCY INTEGER," +
                "PER_INTAKE_COUNT INTEGER," +
                "INTAKE_TIME TEXT," +
                "INTAKE_TIMING TEXT," +
                "MEDICATION TEXT," +
                "DURATION TEXT);";

        sqLiteDatabase.execSQL(createMedicationTable);

    }

    //사용자 프로필 데이터 추가,수정,삭제,조회
    public void addUserProfile(String name, int age, String gender, String onsetDate ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("AGE", age);
        values.put("GENDER", gender);
        values.put("ONSET_DATE", onsetDate);
        db.insert(TABLE_USER_PROFILE, null, values);
        db.close();
    }
    public void updateUserProfile(int profileId, String name, int age, String gender, String onsetDate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("AGE", age);
        values.put("GENDER", gender);
        values.put("ONSET_DATE", onsetDate);
        db.update(TABLE_USER_PROFILE, values, "PROFILE_ID=?", new String[]{String.valueOf(profileId)});
        db.close();
    }
    public void deleteUserProfile(int profileId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USER_PROFILE, "PROFILE_ID=?", new String[]{String.valueOf(profileId)});
        db.close();
    }
    public Cursor getUserProfiles() {
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USER_PROFILE;
        return db.rawQuery(selectQuery, null);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스 버전 업그레이드 시 수행할 작업을 작성해주세요
        // 예시로 기존 테이블을 삭제하고 새로운 테이블을 생성하는 작업을 수행합니다.

    }
}

