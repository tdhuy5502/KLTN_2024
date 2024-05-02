package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import model.Course;
import model.DatabaseHelper;
import model.MdClass;
import model.MdPhase;
import model.MdRoom;
import model.MdSchedule;
import model.MdTeacher;
import model.MdTime;

public class UpdateScheduleActivity extends AppCompatActivity {

    private Button updateSchedule,comeBack;
    private Spinner spinnerClass,spinnerCourse,spinnerPhase,spinnerTime,spinnerRoom,spinnerTeacher;
    private MdSchedule selectedSchedule;
    private EditText txtUpdateDay;
    private ArrayAdapter<MdClass> classAdapter;
    private ArrayAdapter<Course> courseAdapter;
    private ArrayAdapter<MdPhase> phaseAdapter;
    private ArrayAdapter<MdTime> timeAdapter;
    private ArrayAdapter<MdRoom> roomAdapter;
    private ArrayAdapter<MdTeacher> teacherAdapter;
    private DatabaseHelper databaseHelper;
    private final String DATE_FORMAT = "d/M/yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_schedule);

        updateSchedule = findViewById(R.id.updateScheduleButton);
        comeBack = findViewById(R.id.cancelUpdateSchedule);

        spinnerClass = findViewById(R.id.UpdateSpinnerClass);
        spinnerCourse = findViewById(R.id.UpdateSpinnerCourse);
        spinnerPhase = findViewById(R.id.UpdateSpinnerPhase);
        spinnerTime = findViewById(R.id.UpdateSpinnerTime);
        spinnerRoom = findViewById(R.id.UpdateSpinnerRoom);
        spinnerTeacher = findViewById(R.id.UpdateSpinnerTeacher);

        txtUpdateDay = findViewById(R.id.txtUpdateDay);

        classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        phaseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        roomAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        teacherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);

        spinnerClass.setAdapter(classAdapter);
        spinnerPhase.setAdapter(phaseAdapter);
        spinnerTime.setAdapter(timeAdapter);
        spinnerRoom.setAdapter(roomAdapter);
        spinnerTeacher.setAdapter(teacherAdapter);
        spinnerCourse.setAdapter(courseAdapter);

        // Khởi tạo database helper
        databaseHelper = new DatabaseHelper(this);

        //Bắt đối tượng đc gửi từ Intent qua;
        selectedSchedule = getIntent().getParcelableExtra("selectedSchedule");

        //Spinner Class
        List<MdClass> classList = databaseHelper.getAllClasses();
        classAdapter.addAll(classList);
        int classPosition = getClassPosition(selectedSchedule.getClassId(), classList);
        spinnerClass.setSelection(classPosition);

        //Spinner Class
        List<Course> courseList = databaseHelper.getAllCourses();
        courseAdapter.addAll(courseList);
        int coursePosition = getCoursePosition(selectedSchedule.getCourseId(), courseList);
        spinnerCourse.setSelection(coursePosition);

        //Spinner Phase
        List<MdPhase> phaseList = databaseHelper.getAllPhases();
        phaseAdapter.addAll(phaseList);
        int phasePosition = getPhasePosition(selectedSchedule.getPhaseId(), phaseList);
        spinnerPhase.setSelection(phasePosition);

        //Spinner Time
        List<MdTime> timeList = databaseHelper.getAllTimes();
        timeAdapter.addAll(timeList);
        int timePosition = getTimePosition(selectedSchedule.getTimeId(), timeList);
        spinnerTime.setSelection(timePosition);

        //Spinner Room
        List<MdRoom> roomList = databaseHelper.getAllRooms();
        roomAdapter.addAll(roomList);
        int roomPosition = getRoomPosition(selectedSchedule.getRoomId(), roomList);
        spinnerRoom.setSelection(roomPosition);

        //Spinner Teacher
        List<MdTeacher> teacherList = databaseHelper.getAllTeachers();
        teacherAdapter.addAll(teacherList);
        int teacherPosition = getTeacherPosition(selectedSchedule.getTeacherId(), teacherList);
        spinnerTeacher.setSelection(teacherPosition);

        txtUpdateDay.setText(selectedSchedule.getDay());

        updateSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelectedSchedule(selectedSchedule);
            }
        });

        comeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void updateSelectedSchedule(MdSchedule selectedSchedule) {
        // Lấy thông tin mới từ các Spinner hoặc EditText
        MdClass selectedClass = (MdClass) spinnerClass.getSelectedItem();
        Course selectedCourse = (Course) spinnerCourse.getSelectedItem();
        MdPhase selectedPhase = (MdPhase) spinnerPhase.getSelectedItem();
        MdTime selectedTime = (MdTime) spinnerTime.getSelectedItem();
        MdRoom selectedRoom = (MdRoom) spinnerRoom.getSelectedItem();
        MdTeacher selectedTeacher = (MdTeacher) spinnerTeacher.getSelectedItem();
        String day = txtUpdateDay.getText().toString();

        if (!isValidDateFormat(day)) {
            Toast.makeText(UpdateScheduleActivity.this, "Vui lòng nhập đúng ngày", Toast.LENGTH_SHORT).show();
        } else {
            int phaseId = selectedPhase.getId();
            int timeId = selectedTime.getId();

            // Kiểm tra xem thời gian mới được chọn có phù hợp với pha hay không
            if (!isTimeInPhase(timeId, phaseId)) {
                Toast.makeText(UpdateScheduleActivity.this, "Thời gian không phù hợp với ca học đã chọn", Toast.LENGTH_SHORT).show();
            } else {
                // Kiểm tra ngày học nằm trong khoảng ngày của khóa học
                String[] startAndEndDate = databaseHelper.getCourseStartAndEndDate(selectedCourse.getId());
                String courseStartDate = startAndEndDate[0];
                String courseEndDate = startAndEndDate[1];
                if (compareDates(day, courseStartDate) < 0 || compareDates(day, courseEndDate) > 0) {
                    Toast.makeText(UpdateScheduleActivity.this, "Ngày học không nằm trong khoảng ngày của khóa học", Toast.LENGTH_SHORT).show();
                } else if (!isClassInCourse(selectedClass, selectedCourse)) {
                    Toast.makeText(UpdateScheduleActivity.this, "Lớp học không thuộc vào khóa học", Toast.LENGTH_SHORT).show();
                } else {
                    // Cập nhật thông tin lịch học vào đối tượng selectedSchedule
                    selectedSchedule.setPhaseId(phaseId);
                    selectedSchedule.setCourseId(selectedCourse.getId());
                    selectedSchedule.setTimeId(timeId);
                    selectedSchedule.setClassId(selectedClass.getId());
                    selectedSchedule.setTeacherId(selectedTeacher.getId());
                    selectedSchedule.setRoomId(selectedRoom.getId());
                    selectedSchedule.setDay(day);

                    // Thực hiện cập nhật thông tin lịch học trong cơ sở dữ liệu
                    boolean isUpdated = databaseHelper.updateSchedule(selectedSchedule);

                    // Hiển thị thông báo tùy thuộc vào kết quả cập nhật
                    if (isUpdated) {
                        Toast.makeText(this, "Cập nhật lịch học thành công", Toast.LENGTH_SHORT).show();
                        Intent backToList = new Intent(UpdateScheduleActivity.this, AdminScheduleActivity.class);
                        startActivity(backToList);
                    } else {
                        Toast.makeText(this, "Không thể cập nhật lịch học do trùng khớp thông tin", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private boolean isClassInCourse(MdClass selectedClass, Course selectedCourse) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String query = "SELECT * FROM class WHERE id = ? AND course_id = ?";
        String[] selectionArgs = {String.valueOf(selectedClass.getId()), String.valueOf(selectedCourse.getId())};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        boolean isInCourse = cursor.getCount() > 0;
        cursor.close();
        return isInCourse;
    }

    private boolean isTimeInPhase(int timeId, int phaseId) {
        // Kiểm tra xem thời gian có thuộc pha không
        if ((phaseId == 1 && (timeId == 3 || timeId == 4)) || (phaseId == 2 && (timeId == 1 || timeId == 2))) {
            return false; // Không hợp lệ nếu thời gian không thuộc pha
        }
        return true; // Hợp lệ nếu thời gian thuộc pha
    }

    private int getClassPosition(int classId, List<MdClass> classList) {
        for (int i = 0; i < classList.size(); i++) {
            if (classList.get(i).getId() == classId) {
                return i;
            }
        }
        return -1;
    }
    private int getCoursePosition(int courseId, List<Course> courseList) {
        for (int i = 0; i < courseList.size(); i++) {
            if (courseList.get(i).getId() == courseId) {
                return i;
            }
        }
        return -1;
    }
    private int getPhasePosition(int phaseId, List<MdPhase> phaseList) {
        for (int i = 0; i < phaseList.size(); i++) {
            if (phaseList.get(i).getId() == phaseId) {
                return i;
            }
        }
        return -1;
    }
    private int getTimePosition(int timeId, List<MdTime> timeList) {
        for (int i = 0; i < timeList.size(); i++) {
            if (timeList.get(i).getId() == timeId) {
                return i;
            }
        }
        return -1;
    }
    private int getRoomPosition(int roomId, List<MdRoom> roomList) {
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getId() == roomId) {
                return i;
            }
        }
        return -1;
    }
    private int getTeacherPosition(int teacherId, List<MdTeacher> teacherList) {
        for (int i = 0; i < teacherList.size(); i++) {
            if (teacherList.get(i).getId() == teacherId) {
                return i;
            }
        }
        return -1;
    }

    private boolean isValidDateFormat(String date) {
        // Tách ngày thành các phần riêng biệt
        String[] parts = date.split("/");
        // Kiểm tra số phần tử của ngày
        if (parts.length == 3) {
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            // Kiểm tra nếu ngày hoặc tháng nhỏ hơn 10 và không có số 0 ở đầu
            if ((day < 10 && !parts[0].startsWith("0")) || (month < 10 && !parts[1].startsWith("0"))) {
                return false;
            }
        } else {
            // Trả về false nếu không có đủ 3 phần tử ngày/tháng/năm
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
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