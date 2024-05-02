package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LVTN.db";
    private static final int DATABASE_VERSION = 6;
    private final String DATE_FORMAT = "dd/MM/yyyy";

    // Câu lệnh tạo bảng user
    private static final String CREATE_TABLE_USER = "CREATE TABLE user " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " role_id INTEGER," +
            " email TEXT, " +
            "password TEXT," +
            " FOREIGN KEY (role_id) REFERENCES role(id))";

    // Câu lệnh tạo bảng role
    private static final String CREATE_TABLE_ROLE = "CREATE TABLE role " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " role_name TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

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
    private static final String COURSE_BEGIN = "begin_day";
    private static final String COURSE_END = "end_day";

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
    private static final String SCHEDULE_COURSE_ID = "course_id";
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
            + COURSE_SO_LOP + " INTEGER,"
            + COURSE_BEGIN + " TEXT,"
            + COURSE_END + " TEXT"
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
            + SCHEDULE_COURSE_ID + " INTEGER,"
            + SCHEDULE_PHASE_ID + " INTEGER,"
            + SCHEDULE_TIME_ID + " INTEGER,"
            + SCHEDULE_DAY + " TEXT,"
            + SCHEDULE_ROOM_ID + " INTEGER,"
            + SCHEDULE_TEACHER_ID + " INTEGER,"
            + "FOREIGN KEY(" + SCHEDULE_CLASS_ID + ") REFERENCES  class (" + CLASS_ID + ") ON UPDATE CASCADE,"
            + "FOREIGN KEY(" + SCHEDULE_COURSE_ID + ") REFERENCES  course (" + COURSE_ID + ") ON UPDATE CASCADE," //Mới
            + "FOREIGN KEY(" + SCHEDULE_PHASE_ID + ") REFERENCES phase (" + PHASE_ID + ") ON UPDATE CASCADE,"
            + "FOREIGN KEY(" + SCHEDULE_TIME_ID + ") REFERENCES time (" + TIME_ID + ") ON UPDATE CASCADE,"
            + "FOREIGN KEY(" + SCHEDULE_ROOM_ID + ") REFERENCES room (" + ROOM_ID + ") ON UPDATE CASCADE,"
            + "FOREIGN KEY(" + SCHEDULE_TEACHER_ID + ") REFERENCES teacher (" + TEACHER_ID + ") ON UPDATE CASCADE"
            + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Thực thi câu lệnh tạo bảng user và role khi cơ sở dữ liệu được tạo
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_ROLE);

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

        // Chèn dữ liệu mẫu vào bảng role
        ContentValues roleValues1 = new ContentValues();
        roleValues1.put("role_name", "Admin");
        db.insert("role", null, roleValues1);

        ContentValues roleValues2 = new ContentValues();
        roleValues2.put("role_name", "User");
        db.insert("role", null, roleValues2);

        //Chèn vào bảng user
        ContentValues values1 = new ContentValues();
        values1.put("role_id", 1); //Admin
        values1.put("email", "admin@gmail.com");
        values1.put("password", "123456");
        db.insert("user", null, values1);

        ContentValues values2 = new ContentValues();
        values2.put("role_id", 2); //User
        values2.put("email", "user@gmail.com");
        values2.put("password", "12345");
        db.insert("user", null, values2);
    }

    public static boolean authenticateUser(Context context, String email, String password) {
        SQLiteDatabase db = getDatabase(context);
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email = ? AND password = ?", new String[]{email, password});
        boolean result = cursor.moveToFirst();
        cursor.close();
        return result;
    }

    public void updatePasswordByEmail(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);
        db.update("user", values, "email" + " = ?", new String[]{email});
        db.close();
    }

    public static int getUserRoleId(Context context, String email) {
        SQLiteDatabase db = getDatabase(context);
        Cursor cursor = db.rawQuery("SELECT role_id FROM user WHERE email = ?", new String[]{email});
        int roleId = -1;
        if (cursor.moveToFirst()) {
            roleId = cursor.getInt(cursor.getColumnIndexOrThrow("role_id"));
        }
        cursor.close();
        return roleId;
    }

    private static SQLiteDatabase getDatabase(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        return dbHelper.getReadableDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý cập nhật cơ sở dữ liệu nếu có
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS role");
        db.execSQL("DROP TABLE IF EXISTS schedule");
        db.execSQL("DROP TABLE IF EXISTS phase");
        db.execSQL("DROP TABLE IF EXISTS time");
        db.execSQL("DROP TABLE IF EXISTS room");
        db.execSQL("DROP TABLE IF EXISTS teacher");
        db.execSQL("DROP TABLE IF EXISTS class");
        db.execSQL("DROP TABLE IF EXISTS " + "course");
        onCreate(db);
    }

    // Phương thức thêm dữ liệu vào bảng phase
    private void addPhase(SQLiteDatabase db, String name) {
        ContentValues values = new ContentValues();
        values.put(PHASE_NAME, name);
        db.insert("phase", null, values);
    }

    // Lấy danh sách tất cả các tên phase từ cơ sở dữ liệu
    public List<MdPhase> getAllPhases() {
        List<MdPhase> phaseList = new ArrayList<>();
        String selectQuery = "SELECT * FROM phase";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Duyệt qua các dòng kết quả và tạo đối tượng MdPhase từ mỗi dòng, sau đó thêm vào danh sách
        if (cursor.moveToFirst()) {
            do {
                MdPhase phase = new MdPhase();
                phase.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PHASE_ID)));
                phase.setName(cursor.getString(cursor.getColumnIndexOrThrow(PHASE_NAME)));
                // Thêm đối tượng MdPhase vào danh sách
                phaseList.add(phase);
            } while (cursor.moveToNext());
        }
        // Đóng cursor và database connection
        cursor.close();
        db.close();
        return phaseList;
    }

    // Phương thức thêm dữ liệu vào bảng time
    private void addTime(SQLiteDatabase db, String classtime) {
        ContentValues values = new ContentValues();
        values.put(TIME_CLASSTIME, classtime);
        db.insert("time", null, values);
    }

    // Lấy danh sách tất cả các tên time từ cơ sở dữ liệu
    public List<MdTime> getAllTimes() {
        List<MdTime> timeList = new ArrayList<>();
        String selectQuery = "SELECT * FROM time";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Duyệt qua các dòng kết quả và tạo đối tượng MdTime từ mỗi dòng, sau đó thêm vào danh sách
        if (cursor.moveToFirst()) {
            do {
                MdTime time = new MdTime();
                time.setId(cursor.getInt(cursor.getColumnIndexOrThrow(TIME_ID)));
                time.setClassTime(cursor.getString(cursor.getColumnIndexOrThrow(TIME_CLASSTIME)));
                // Thêm đối tượng MdTime vào danh sách
                timeList.add(time);
            } while (cursor.moveToNext());
        }
        // Đóng cursor và database connection
        cursor.close();
        db.close();
        return timeList;
    }



    //Them Khoa hoc
    public long addCourse(String courseName, int soBuoi, int soLop, String beginDay, String endDay) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COURSE_NAME, courseName);
        values.put(COURSE_SO_BUOI, soBuoi);
        values.put(COURSE_SO_LOP, soLop);
        values.put(COURSE_BEGIN, beginDay);
        values.put(COURSE_END, endDay);
        // Specify the columns in correct order
        long result = db.insert("course", null, values);

        db.close();
        return result;
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM course", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int courseId = cursor.getInt(cursor.getColumnIndexOrThrow(COURSE_ID));
                    String courseName = cursor.getString(cursor.getColumnIndexOrThrow(COURSE_NAME));
                    int soBuoi = cursor.getInt(cursor.getColumnIndexOrThrow(COURSE_SO_BUOI));
                    int soLop = cursor.getInt(cursor.getColumnIndexOrThrow(COURSE_SO_LOP));
                    String beginDay = cursor.getString(cursor.getColumnIndexOrThrow(COURSE_BEGIN));
                    String endDay = cursor.getString(cursor.getColumnIndexOrThrow(COURSE_END));
                    Course course = new Course(courseId, courseName, soBuoi, soLop,beginDay,endDay);
                    courses.add(course);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return courses;
    }

    public boolean updateCourse(int courseId, String newName, int newSobuoi, int newSoLop, String beginDay, String endDay) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COURSE_NAME, newName);
        values.put(COURSE_SO_BUOI, newSobuoi);
        values.put(COURSE_SO_LOP, newSoLop);
        values.put(COURSE_BEGIN, beginDay);
        values.put(COURSE_END, endDay);

        int result = db.update("course", values, COURSE_ID + "=?", new String[]{String.valueOf(courseId)});
        db.close();
        return result != -1;
    }

    public boolean deleteCourse(int courseId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("course", "id" + "=?", new String[]{String.valueOf(courseId)});
        db.close();
        return result != -1;
    }

    public List<Course> searchCourseByName(String name) {
        List<Course> courseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM course WHERE coursename LIKE '%" + name + "%'";
        Cursor cursor = db.rawQuery(query, null);

        // Duyệt qua các dòng kết quả và tạo đối tượng MdCourse tương ứng
        if (cursor.moveToFirst()) {
            do {
                int courseId = cursor.getInt(cursor.getColumnIndexOrThrow(COURSE_ID));
                String courseName = cursor.getString(cursor.getColumnIndexOrThrow(COURSE_NAME));
                int soBuoi = cursor.getInt(cursor.getColumnIndexOrThrow(COURSE_SO_BUOI));
                int soLop = cursor.getInt(cursor.getColumnIndexOrThrow(COURSE_SO_LOP));
                String beginDay = cursor.getString(cursor.getColumnIndexOrThrow(COURSE_BEGIN));
                String endDay = cursor.getString(cursor.getColumnIndexOrThrow(COURSE_END));
                Course course = new Course(courseId, courseName, soBuoi, soLop,beginDay,endDay);
                // Thêm MdCourse vào danh sách kết quả
                courseList.add(course);
            } while (cursor.moveToNext());
        }

        // Đóng cursor và database connection
        cursor.close();
        db.close();

        return courseList;
    }

    //Them sua Teacher
    public boolean addTeacher(String name,String email,String phone, int soLop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("phone", phone);
        values.put("solop", soLop);
        long result = db.insert("teacher", null, values);
        db.close();
        return result != -1;
    }

    public List<MdTeacher> getAllTeachers()
    {
        List<MdTeacher> teacherList = new ArrayList<>();

        String selectQuery = "SELECT * FROM teacher";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do
                {
                    int teacherId = cursor.getInt(cursor.getColumnIndexOrThrow(TEACHER_ID));
                    String teacherName = (cursor.getString(cursor.getColumnIndexOrThrow(TEACHER_NAME)));
                    String teacherEmail = cursor.getString(cursor.getColumnIndexOrThrow(TEACHER_EMAIL));
                    String teacherPhone = cursor.getString(cursor.getColumnIndexOrThrow(TEACHER_PHONE));
                    int teacherSoLop = cursor.getInt(cursor.getColumnIndexOrThrow(TEACHER_SO_LOP));
                    MdTeacher teacher = new MdTeacher(teacherId,teacherName,teacherEmail,teacherPhone,teacherSoLop);
                    teacherList.add(teacher);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return teacherList;
    }

    public boolean updateTeacher(int id, String name, String email, String phone, int classQty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("phone", phone);
        values.put("solop", classQty);

        int rowsAffected = db.update("teacher", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();

        return rowsAffected > 0;
    }

    public boolean deleteTeacher(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int rowsAffected = db.delete("teacher", "id = ?", new String[]{String.valueOf(id)});
        db.close();

        return rowsAffected > 0;
    }

    public List<MdTeacher> searchTeacherByName(String name) {
        List<MdTeacher> teacherList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM teacher WHERE name LIKE '%" + name + "%'";
        Cursor cursor = db.rawQuery(query, null);

        // Duyệt qua các dòng kết quả và tạo đối tượng MdTeacher tương ứng
        if (cursor.moveToFirst()) {
            do {
                int teacherId = cursor.getInt(cursor.getColumnIndexOrThrow(TEACHER_ID));
                String teacherName = (cursor.getString(cursor.getColumnIndexOrThrow(TEACHER_NAME)));
                String teacherEmail = cursor.getString(cursor.getColumnIndexOrThrow(TEACHER_EMAIL));
                String teacherPhone = cursor.getString(cursor.getColumnIndexOrThrow(TEACHER_PHONE));
                int teacherSoLop = cursor.getInt(cursor.getColumnIndexOrThrow(TEACHER_SO_LOP));
                MdTeacher teacher = new MdTeacher(teacherId,teacherName,teacherEmail,teacherPhone,teacherSoLop);
                // Thêm MdTeacher vào danh sách kết quả
                teacherList.add(teacher);
            } while (cursor.moveToNext());
        }

        // Đóng cursor và database connection
        cursor.close();
        db.close();

        return teacherList;
    }

    //Quan ly cac Room
    public boolean addRoom(String name,String status,String category)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("roomname", name);
        values.put("status", status);
        values.put("type", category);
        long result = db.insert("room", null, values);
        db.close();
        return result != -1;
    }

    public List<MdRoom> getAllRooms() {
        List<MdRoom> rooms = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM room", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do
                {
                    int roomId = cursor.getInt(cursor.getColumnIndexOrThrow(ROOM_ID));
                    String roomName = cursor.getString(cursor.getColumnIndexOrThrow(ROOM_NAME));
                    String roomStatus = cursor.getString(cursor.getColumnIndexOrThrow(ROOM_STATUS));
                    String roomType = cursor.getString(cursor.getColumnIndexOrThrow(ROOM_TYPE));
                    MdRoom room = new MdRoom(roomId, roomName, roomStatus, roomType);
                    rooms.add(room);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return rooms;
    }

    public boolean updateRoom(int roomId, String newName, String newStatus, String newCate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ROOM_NAME, newName);
        values.put(ROOM_STATUS, newStatus);
        values.put(ROOM_TYPE, newCate);

        int result = db.update("room", values, ROOM_ID + "=?", new String[]{String.valueOf(roomId)});
        db.close();
        return result != -1;
    }

    public boolean deleteRoom(int roomId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("room", ROOM_ID + "=?", new String[]{String.valueOf(roomId)});
        db.close();
        return result != -1;
    }

    //Quản lý các lớp học
    public boolean addClass(MdClass mdClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CLASS_NAME, mdClass.getName());
        values.put(CLASS_COURSE_ID, mdClass.getCourseId());
        values.put(CLASS_SV_QTY, mdClass.getStudentQty());
        values.put(CLASS_TEACHER_ID, mdClass.getTeacherId());

        long result = db.insert("class", null, values);
        db.close();

        return result != -1; // return true if insertion successful, false otherwise
    }

    public List<MdClass> getAllClasses() {
        List<MdClass> classList = new ArrayList<>();
        String selectQuery = "SELECT cl.*, t." + TEACHER_NAME + " AS teacher_name, c." + COURSE_NAME + " AS course_name, c." + COURSE_BEGIN + " AS course_begin, c." + COURSE_END + " AS course_end " +
                "FROM " + "class" + " cl " +
                "INNER JOIN " + "teacher" + " t ON cl." + CLASS_TEACHER_ID + " = t." + TEACHER_ID + " " +
                "INNER JOIN " + "course" + " c ON cl." + CLASS_COURSE_ID + " = c." + COURSE_ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);


        if (cursor.moveToFirst()) {
            do {
                int classId = cursor.getInt(cursor.getColumnIndexOrThrow(CLASS_ID));
                String className = cursor.getString(cursor.getColumnIndexOrThrow(CLASS_NAME));
                int teacherId = cursor.getInt(cursor.getColumnIndexOrThrow(CLASS_TEACHER_ID));
                String teacherName = cursor.getString(cursor.getColumnIndexOrThrow("teacher_name"));
                int studentQty = cursor.getInt(cursor.getColumnIndexOrThrow(CLASS_SV_QTY));
                int courseId = cursor.getInt(cursor.getColumnIndexOrThrow(CLASS_COURSE_ID));
                String courseName = cursor.getString(cursor.getColumnIndexOrThrow("course_name"));
                String courseBegin = cursor.getString(cursor.getColumnIndexOrThrow("course_begin"));
                String courseEnd = cursor.getString(cursor.getColumnIndexOrThrow("course_end"));

                Log.d("LuanVanTN", "Class ID: " + classId);
                Log.d("LuanVanTN", "Class Name: " + className);
                Log.d("LuanVanTN", "Teacher ID: " + teacherId);
                Log.d("LuanVanTN", "Teacher Name: " + teacherName);
                Log.d("LuanVanTN", "Student Quantity: " + studentQty);
                Log.d("LuanVanTN", "Course ID: " + courseId);
                Log.d("LuanVanTN", "Course Name: " + courseName);
                MdClass mdClass = new MdClass();
                mdClass.setId(classId);
                mdClass.setName(className);
                mdClass.setTeacherId(teacherId);
                mdClass.setTeacherName(teacherName);
                mdClass.setStudentQty(studentQty);
                mdClass.setCourseId(courseId);
                mdClass.setCourseName(courseName);
                mdClass.setCourseBegin(courseBegin);
                mdClass.setCourseEnd(courseEnd);
                classList.add(mdClass);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return classList;
    }

    public boolean updateClass(int classId, String newName, int courseId, int newQty, int teacherId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("class_name", newName);
        values.put("course_id", courseId);
        values.put("sv_qty", newQty);
        values.put("teacher_id", teacherId);

        // Câu lệnh UPDATE để cập nhật dữ liệu trong bảng class
        long result = db.update("class", values, "id" + " = ?", new String[]{String.valueOf(classId)});

        // Đóng kết nối database
        db.close();
        return result != -1; // return true if insertion successful, false otherwise
    }

    public boolean deleteClass(int classId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("class", CLASS_ID + "=?", new String[]{String.valueOf(classId)});
        db.close();
        return result != -1;
    }

    //Them & quan ly lich hoc
    public long addSchedule(MdSchedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Kiểm tra xem lịch trình đã tồn tại chưa
        if (isScheduleConflict(schedule))
        {
            return -1;
        }
        else
        {
            ContentValues values = new ContentValues();
            values.put("class_id", schedule.getClassId());
            values.put("course_id", schedule.getCourseId());
            values.put("phase_id", schedule.getPhaseId());
            values.put("time_id", schedule.getTimeId());
            values.put("day", schedule.getDay());
            values.put("room_id", schedule.getRoomId());
            values.put("teacher_id", schedule.getTeacherId());

            // Thêm lịch trình vào cơ sở dữ liệu
            long result = db.insert("schedule", null, values);
            db.close();
            return result;
        }
    }

    // Phương thức lấy tất cả các lịch học từ cơ sở dữ liệu
    public List<MdSchedule> getAllSchedule() {
        List<MdSchedule> scheduleList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT s.*, c.class_name AS class_name, cr.coursename AS course_name, p.name AS phase_name, t.classtime AS time_name, tr.name AS teacher_name, r.roomname AS room_name " +
                "FROM schedule s " +
                "INNER JOIN class c ON s.class_id = c.id " +
                "INNER JOIN course cr ON s.course_id = cr.id " +
                "INNER JOIN phase p ON s.phase_id = p.id " +
                "INNER JOIN time t ON s.time_id = t.id " +
                "INNER JOIN teacher tr ON s.teacher_id = tr.id " +
                "INNER JOIN room r ON s.room_id = r.id " +
                "WHERE cr.id = s.course_id"; // Thêm điều kiện để chỉ lấy lịch trình của các lớp học thuộc vào khóa học
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            Log.d("CursorCount", "Cursor count: " + cursor.getCount());
            Log.d("Query", "Query: " + query);
        }

        // Duyệt qua các dòng kết quả và tạo đối tượng MdSchedule từ mỗi dòng
        if (cursor.moveToFirst()) {
            do {
                MdSchedule schedule = new MdSchedule();
                schedule.setScheduleId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_ID)));
                schedule.setClassId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_CLASS_ID)));
                schedule.setClassName(cursor.getString(cursor.getColumnIndexOrThrow("class_name")));
                schedule.setCourseId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_COURSE_ID)));
                schedule.setCourseName(cursor.getString(cursor.getColumnIndexOrThrow("course_name")));
                schedule.setPhaseId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_PHASE_ID)));
                schedule.setPhaseName(cursor.getString(cursor.getColumnIndexOrThrow("phase_name")));
                schedule.setTimeId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_TIME_ID)));
                schedule.setTimeName(cursor.getString(cursor.getColumnIndexOrThrow("time_name")));
                schedule.setTeacherId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_TEACHER_ID)));
                schedule.setTeacherName(cursor.getString(cursor.getColumnIndexOrThrow("teacher_name")));
                schedule.setRoomId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_ROOM_ID)));
                schedule.setRoomName(cursor.getString(cursor.getColumnIndexOrThrow("room_name")));
                schedule.setDay(cursor.getString(cursor.getColumnIndexOrThrow(SCHEDULE_DAY)));
                scheduleList.add(schedule);
            } while (cursor.moveToNext());
        }

        // Đóng cursor và database connection
        cursor.close();
        db.close();

        return scheduleList;
    }

    public boolean updateSchedule(MdSchedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Kiểm tra xem lịch trình đã tồn tại chưa
        if (!isScheduleConflict(schedule)) {
            ContentValues values = new ContentValues();
            values.put(SCHEDULE_PHASE_ID, schedule.getPhaseId());
            values.put(SCHEDULE_COURSE_ID,schedule.getCourseId());
            values.put(SCHEDULE_TIME_ID, schedule.getTimeId());
            values.put(SCHEDULE_CLASS_ID, schedule.getClassId());
            values.put(SCHEDULE_COURSE_ID, schedule.getCourseId());
            values.put(SCHEDULE_TEACHER_ID, schedule.getTeacherId());
            values.put(SCHEDULE_ROOM_ID, schedule.getRoomId());
            values.put(SCHEDULE_DAY, schedule.getDay());

            // Câu lệnh WHERE để xác định lịch học cần được cập nhật
            String selection = SCHEDULE_ID + " = ?";
            String[] selectionArgs = {String.valueOf(schedule.getScheduleId())};

            // Thực hiện cập nhật và kiểm tra số dòng bị ảnh hưởng
            int rowsAffected = db.update("schedule", values, selection, selectionArgs);
            db.close();

            // Trả về true nếu có ít nhất một dòng được cập nhật thành công
            return rowsAffected > 0;
        } else {
            // Trả về false nếu lịch trình đã tồn tại
            return false;
        }
    }

    public String[] getCourseStartAndEndDate(int courseId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] startAndEndDate = new String[2];
        String query = "SELECT " + COURSE_BEGIN + ", " + COURSE_END + " FROM course WHERE " + COURSE_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(courseId)});
        if (cursor.moveToFirst()) {
            startAndEndDate[0] = cursor.getString(cursor.getColumnIndexOrThrow(COURSE_BEGIN));
            startAndEndDate[1] = cursor.getString(cursor.getColumnIndexOrThrow(COURSE_END));
        }
        cursor.close();
        return startAndEndDate;
    }

    // Phương thức kiểm tra xem có xung đột lịch học không
    private boolean isScheduleConflict(MdSchedule schedule) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT s.*, c.class_name AS class_name, cr.coursename AS course_name, p.name AS phase_name, t.classtime AS time_name, tr.name AS teacher_name, r.roomname AS room_name " +
                "FROM schedule s " +
                "INNER JOIN class c ON s.class_id = c.id " +
                "INNER JOIN course cr ON s.course_id = cr.id " +
                "INNER JOIN phase p ON s.phase_id = p.id " +
                "INNER JOIN time t ON s.time_id = t.id " +
                "INNER JOIN teacher tr ON s.teacher_id = tr.id " +
                "INNER JOIN room r ON s.room_id = r.id " +
                "WHERE " +
                "s." + SCHEDULE_TIME_ID + " = " + schedule.getTimeId() + " AND " +
                "s." + SCHEDULE_DAY + " = '" + schedule.getDay() + "' AND (" +
                "s." + SCHEDULE_CLASS_ID + " = " + schedule.getClassId() + " OR " +
                "s." + SCHEDULE_TEACHER_ID + " = " + schedule.getTeacherId() + ") AND " +
                "cr.id = " + schedule.getCourseId() + " AND " +  // Kiểm tra lớp học thuộc vào khóa học
                "s." + SCHEDULE_ID + " != " + schedule.getScheduleId(); //Kiểm tra hết trừ lịch trình đang được cập nhật

        // Thêm điều kiện kiểm tra xem hai lịch học có cùng phòng học không
        query += " AND s." + SCHEDULE_ROOM_ID + " = " + schedule.getRoomId();

        Cursor cursor = db.rawQuery(query, null);
        boolean conflict = cursor.getCount() > 0;
        cursor.close();

        // Kiểm tra xem giáo viên đã có lịch dạy cùng giờ hay chưa
        boolean teacherConflict = isTeacherScheduleConflict(schedule);

        return conflict || teacherConflict;
    }

    private boolean isTeacherScheduleConflict(MdSchedule schedule) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM schedule WHERE " +
                SCHEDULE_TIME_ID + " = " + schedule.getTimeId() + " AND " +
                SCHEDULE_DAY + " = '" + schedule.getDay() + "' AND " +
                SCHEDULE_TEACHER_ID + " = " + schedule.getTeacherId() + " AND " +
                SCHEDULE_ID + " != " + schedule.getScheduleId();

        Cursor cursor = db.rawQuery(query, null);
        boolean teacherConflict = cursor.getCount() > 0;
        cursor.close();

        return teacherConflict;
    }

    public boolean deleteSchedule(int scheduleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete("schedule", "id = ?", new String[]{String.valueOf(scheduleId)});
        db.close();
        return deletedRows > 0;
    }

//    public List<MdSchedule> searchScheduleByName(String name) {
//        List<MdSchedule> result = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT s.*, c.class_name AS class_name, p.name AS phase_name, t.classtime AS time_classtime, tr.name AS teacher_name, r.roomname AS room_name " +
//                "FROM schedule s " +
//                "INNER JOIN class c ON s.class_id = c.id " +
//                "INNER JOIN phase p ON s.phase_id = p.id " +
//                "INNER JOIN time t ON s.time_id = t.id " +
//                "INNER JOIN teacher tr ON s.teacher_id = tr.id " +
//                "INNER JOIN room r ON s.room_id = r.id " +
//                "WHERE c.class_name LIKE '%" + name + "%'";
//
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()) {
//            do {
//                MdSchedule schedule = new MdSchedule();
//                // Đọc thông tin lịch học từ cursor
//                schedule.setScheduleId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_ID)));
//                schedule.setClassId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_CLASS_ID)));
//                schedule.setClassName(cursor.getString(cursor.getColumnIndexOrThrow("class_name")));
//                schedule.setPhaseId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_PHASE_ID)));
//                schedule.setPhaseName(cursor.getString(cursor.getColumnIndexOrThrow("phase_name")));
//                schedule.setTimeId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_TIME_ID)));
//                schedule.setTimeName(cursor.getString(cursor.getColumnIndexOrThrow("time_classtime")));
//                schedule.setTeacherId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_TEACHER_ID)));
//                schedule.setTeacherName(cursor.getString(cursor.getColumnIndexOrThrow("teacher_name")));
//                schedule.setRoomId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_ROOM_ID)));
//                schedule.setRoomName(cursor.getString(cursor.getColumnIndexOrThrow("room_name")));
//                schedule.setDay(cursor.getString(cursor.getColumnIndexOrThrow(SCHEDULE_DAY)));
//                // Thêm lịch học vào danh sách kết quả
//                result.add(schedule);
//            }
//            while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return result;
//    }
    //Tìm kiếm lịch trong khoảng tgian
    public List<MdSchedule> getScheduleByDateRangeAndTeacher(String startDate, String endDate, String teacherName) {
        List<MdSchedule> scheduleList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Định dạng câu lệnh truy vấn
        String query;
        String[] queryParams;

        // Nếu teacherName được nhập
        if (!TextUtils.isEmpty(teacherName.trim())) {
            query = "SELECT s.*, " +
                    "c.class_name AS class_name, " +
                    "p.name AS phase_name, " +
                    "t.classtime AS time_classtime, " +
                    "tr.name AS teacher_name, " +
                    "r.roomname AS room_name " +
                    "FROM schedule s " +
                    "INNER JOIN class c ON s.class_id = c.id " +
                    "INNER JOIN phase p ON s.phase_id = p.id " +
                    "INNER JOIN time t ON s.time_id = t.id " +
                    "INNER JOIN teacher tr ON s.teacher_id = tr.id " +
                    "INNER JOIN room r ON s.room_id = r.id " +
                    "WHERE day BETWEEN ? AND ? " +
                    "AND tr.name LIKE ?";
            // Đặt các tham số truy vấn
            queryParams = new String[]{startDate, endDate, "%" + teacherName + "%"};
        } else {
            query = "SELECT s.*, " +
                    "c.class_name AS class_name, " +
                    "p.name AS phase_name, " +
                    "t.classtime AS time_classtime, " +
                    "tr.name AS teacher_name, " +
                    "r.roomname AS room_name " +
                    "FROM schedule s " +
                    "INNER JOIN class c ON s.class_id = c.id " +
                    "INNER JOIN phase p ON s.phase_id = p.id " +
                    "INNER JOIN time t ON s.time_id = t.id " +
                    "INNER JOIN teacher tr ON s.teacher_id = tr.id " +
                    "INNER JOIN room r ON s.room_id = r.id " +
                    "WHERE day BETWEEN ? AND ?";
            // Đặt các tham số truy vấn
            queryParams = new String[]{startDate, endDate};
        }

        // Thực hiện truy vấn
        Cursor cursor = db.rawQuery(query, queryParams);

        // Lặp qua các dòng kết quả và thêm vào danh sách nếu ngày học nằm trong khoảng và tên giáo viên phù hợp
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String scheduleDay = cursor.getString(cursor.getColumnIndexOrThrow(SCHEDULE_DAY));
                    // Sử dụng định dạng ngày chuẩn trong SQLite là "YYYY-MM-DD"
                    if (compareDates(scheduleDay, startDate) >= 0 && compareDates(scheduleDay, endDate) <= 0) {
                        MdSchedule schedule = new MdSchedule();
                        // Lấy dữ liệu từ cột tương ứng
                        schedule.setScheduleId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_ID)));
                        schedule.setClassId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_CLASS_ID)));
                        schedule.setClassName(cursor.getString(cursor.getColumnIndexOrThrow("class_name")));
                        schedule.setPhaseId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_PHASE_ID)));
                        schedule.setPhaseName(cursor.getString(cursor.getColumnIndexOrThrow("phase_name")));
                        schedule.setTimeId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_TIME_ID)));
                        schedule.setTimeName(cursor.getString(cursor.getColumnIndexOrThrow("time_classtime")));
                        schedule.setTeacherId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_TEACHER_ID)));
                        schedule.setTeacherName(cursor.getString(cursor.getColumnIndexOrThrow("teacher_name")));
                        schedule.setRoomId(cursor.getInt(cursor.getColumnIndexOrThrow(SCHEDULE_ROOM_ID)));
                        schedule.setRoomName(cursor.getString(cursor.getColumnIndexOrThrow("room_name")));
                        schedule.setDay(scheduleDay);
                        // Thêm lịch học vào danh sách
                        scheduleList.add(schedule);
                    }
            }
            while (cursor.moveToNext());
        }

        // Đóng con trỏ và trả về danh sách lịch học
        if (cursor != null) {
            cursor.close();
        }
        return scheduleList;
    }

    private int compareDates(String date1, String date2) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            // Kiểm tra và thêm số 0 nếu ngày và tháng < 10
            if (date1.charAt(1) == '/') {
                date1 = "0" + date1;
            }
            if (date2.charAt(1) == '/') {
                date2 = "0" + date2;
            }
            Date d1 = dateFormat.parse(date1);
            Date d2 = dateFormat.parse(date2);
            return d1.compareTo(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu có lỗi xảy ra
    }
}
