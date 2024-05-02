package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseSchedule extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "LVTN.db";

    // Tên bảng và các cột trong bảng class

    private static final String CLASS_ID = "id";
    private static final String CLASS_NAME = "class_name";
    private static final String CLASS_COURSE_ID = "course_id";
    private static final String CLASS_SV_QTY = "sv_qty";
    private static final String CLASS_TEACHER_ID = "teacher_id";

    // Tên bảng và các cột trong bảng course
    private static final String COURSE_ID = "id";
    private static final String COURSE_NAME = "coursename";
    private static final String COURSE_SO_BUOI = "sb_qty";
    private static final String COURSE_SO_LOP = "class_qty";

    // Tên bảng và các cột trong bảng room

    private static final String ROOM_ID = "id";
    private static final String ROOM_NAME = "roomname";
    private static final String ROOM_STATUS = "status";
    private static final String ROOM_TYPE = "type";

    // Tên bảng và các cột trong bảng teacher

    private static final String TEACHER_ID = "id";
    private static final String TEACHER_NAME = "name";
    private static final String TEACHER_EMAIL = "email";
    private static final String TEACHER_PHONE = "phone";
    private static final String TEACHER_SO_LOP = "solop";

    // Tên bảng và các cột trong bảng phase

    private static final String PHASE_ID = "id";
    private static final String PHASE_NAME = "name";

    // Tên bảng và các cột trong bảng time

    private static final String TIME_ID = "id";
    private static final String TIME_CLASSTIME = "classtime";

    // Tên bảng và các cột trong bảng schedule

    private static final String SCHEDULE_ID = "id";
    private static final String SCHEDULE_CLASS_ID = "class_id";
    private static final String SCHEDULE_PHASE_ID = "phase_id";
    private static final String SCHEDULE_TIME_ID = "time_id";
    private static final String SCHEDULE_DAY = "day";
    private static final String SCHEDULE_ROOM_ID = "room_id";
    private static final String SCHEDULE_TEACHER_ID = "teacher_id";

    private static final String CREATE_CLASS_TABLE = "CREATE TABLE  class " + "("
            + CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CLASS_NAME + " TEXT,"
            + CLASS_COURSE_ID + " INTEGER,"
            + CLASS_SV_QTY + " INTEGER,"
            + CLASS_TEACHER_ID + " INTEGER,"
            + "FOREIGN KEY(" + CLASS_COURSE_ID + ") REFERENCES  course  (" + COURSE_ID + ") ON UPDATE CASCADE,"
            + "FOREIGN KEY(" + CLASS_TEACHER_ID + ") REFERENCES teacher  (" + TEACHER_ID + ") ON UPDATE CASCADE"
            + ")";

    private static final String CREATE_COURSE_TABLE = "CREATE TABLE course" + "("
            + COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COURSE_NAME + " TEXT,"
            + COURSE_SO_BUOI + " INTEGER,"
            + COURSE_SO_LOP + " INTEGER"
            + ")";

    private static final String CREATE_ROOM_TABLE = "CREATE TABLE room" + "("
            + ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ROOM_NAME + " TEXT,"
            + ROOM_STATUS + " TEXT,"
            + ROOM_TYPE + " TEXT"
            + ")";
    private static final String CREATE_TEACHER_TABLE = "CREATE TABLE teacher " + "("
            + TEACHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TEACHER_NAME + " TEXT,"
            + TEACHER_EMAIL + " TEXT,"
            + TEACHER_PHONE + " TEXT,"
            + TEACHER_SO_LOP + " INTEGER"
            + ")";
    private static final String CREATE_PHASE_TABLE = "CREATE TABLE phase" + "("
            + PHASE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PHASE_NAME + " TEXT"
            + ")";
    private static final String CREATE_TIME_TABLE = "CREATE TABLE time"  + "("
            + TIME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TIME_CLASSTIME + " TEXT"
            + ")";

    private static final String CREATE_SCHEDULE_TABLE = "CREATE TABLE schedule" + "("
            + SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SCHEDULE_CLASS_ID + " INTEGER,"
            + SCHEDULE_PHASE_ID + " INTEGER,"
            + SCHEDULE_TIME_ID + " INTEGER,"
            + SCHEDULE_DAY + " TEXT,"
            + SCHEDULE_ROOM_ID + " INTEGER,"
            + SCHEDULE_TEACHER_ID + " INTEGER,"
            + "FOREIGN KEY(" + SCHEDULE_CLASS_ID + ") REFERENCES  class (" + CLASS_ID + ") ON UPDATE CASCADE,"
            + "FOREIGN KEY(" + SCHEDULE_PHASE_ID + ") REFERENCES phase (" + PHASE_ID + ") ON UPDATE CASCADE,"
            + "FOREIGN KEY(" + SCHEDULE_TIME_ID + ") REFERENCES time (" + TIME_ID + ") ON UPDATE CASCADE,"
            + "FOREIGN KEY(" + SCHEDULE_ROOM_ID + ") REFERENCES room (" + ROOM_ID + ") ON UPDATE CASCADE,"
            + "FOREIGN KEY(" + SCHEDULE_TEACHER_ID + ") REFERENCES teacher (" + TEACHER_ID + ") ON UPDATE CASCADE"
            + ")";

    public DatabaseSchedule(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_CLASS_TABLE);

        // Tạo bảng course
        db.execSQL(CREATE_COURSE_TABLE);

        // Tạo bảng room

        db.execSQL(CREATE_ROOM_TABLE);

        // Tạo bảng teacher

        db.execSQL(CREATE_TEACHER_TABLE);

        // Tạo bảng phase

        db.execSQL(CREATE_PHASE_TABLE);
        // Thêm dữ liệu mẫu vào bảng phase
        addPhase(db, "Ca sáng");
        addPhase(db, "Ca chiều");

        // Tạo bảng time

        db.execSQL(CREATE_TIME_TABLE);
        // Thêm dữ liệu mẫu vào bảng time
        addTime(db, "07:00 - 09:00");
        addTime(db, "09:00 - 11:00");
        addTime(db, "13:00 - 15:00");
        addTime(db, "15:00 - 17:00");

        // Tạo bảng schedule
        db.execSQL(CREATE_SCHEDULE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop các bảng cũ nếu tồn tại và tạo lại chúng
        db.execSQL("DROP TABLE IF EXISTS schedule");
        db.execSQL("DROP TABLE IF EXISTS phase");
        db.execSQL("DROP TABLE IF EXISTS time");
        db.execSQL("DROP TABLE IF EXISTS room");
        db.execSQL("DROP TABLE IF EXISTS teacher");
        db.execSQL("DROP TABLE IF EXISTS class");
        db.execSQL("DROP TABLE IF EXISTS course");
        onCreate(db);
    }

    // Phương thức thêm dữ liệu vào bảng time
    private void addTime(SQLiteDatabase db, String classtime) {
        ContentValues values = new ContentValues();
        values.put(TIME_CLASSTIME, classtime);
        db.insert("time", null, values);
    }

    // Phương thức thêm dữ liệu vào bảng phase
    private void addPhase(SQLiteDatabase db, String name) {
        ContentValues values = new ContentValues();
        values.put(PHASE_NAME, name);
        db.insert("phase", null, values);
    }

    public void addCourse(String courseName, int soBuoi, int soLop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COURSE_NAME, courseName);
        values.put(COURSE_SO_BUOI, soBuoi);
        values.put(COURSE_SO_LOP, soLop);
        db.insert("course", null, values);
        db.close();
    }
}
