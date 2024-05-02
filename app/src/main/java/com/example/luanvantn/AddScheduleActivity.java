package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class AddScheduleActivity extends AppCompatActivity {

    private Spinner spinnerPhase;
    private Spinner spinnerTime;
    private Spinner spinnerClass;
    private Spinner spinnerCourse;
    private Spinner spinnerTeacher;
    private Spinner spinnerRoom;
    private ArrayAdapter<MdClass> classAdapter;
    private List<MdClass> classList;
    private ArrayAdapter<Course> courseAdapter;
    private List<Course> courseList;
    private ArrayAdapter<MdTeacher> teacherAdapter;
    private List<MdTeacher> teacherList;
    private ArrayAdapter<MdRoom> roomAdapter;
    private List<MdRoom> roomList;
    private ArrayAdapter<MdTime> timeAdapter;
    private List<MdPhase> phaseList;
    private ArrayAdapter<MdPhase> phaseAdapter;
    private List<MdTime> timeList;
    private EditText txtDay;
    private Button btnAddSchedule;
    private DatabaseHelper databaseHelper;
    private final String DATE_FORMAT = "dd/mm/yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        //Tìm Id và gán vào đối tượng view
        spinnerPhase = findViewById(R.id.AddSpinnerPhase);
        spinnerTime = findViewById(R.id.AddSpinnerTime);
        spinnerClass = findViewById(R.id.AddSpinnerClass);
        spinnerCourse = findViewById(R.id.AddSpinnerCourse);
        spinnerTeacher = findViewById(R.id.AddSpinnerTeacher);
        spinnerRoom = findViewById(R.id.AddSpinnerRoom);
        txtDay = findViewById(R.id.AddDay);
        btnAddSchedule = findViewById(R.id.btnAddSchedule);
        databaseHelper = new DatabaseHelper(this);

        // Lấy danh sách thời gian từ cơ sở dữ liệu
        timeList = databaseHelper.getAllTimes();
        // Lấy danh sách ca học từ cơ sở dữ liệu
        phaseList = databaseHelper.getAllPhases();
        // Lấy danh sách ca học từ cơ sở dữ liệu
        classList = databaseHelper.getAllClasses();
        // Lấy danh sách ca học từ cơ sở dữ liệu
        courseList = databaseHelper.getAllCourses();
        // Lấy danh sách ca học từ cơ sở dữ liệu
        teacherList = databaseHelper.getAllTeachers();
        // Lấy danh sách ca học từ cơ sở dữ liệu
        roomList = databaseHelper.getAllRooms();

        // Tạo adapter cho Spinner Time
        timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeList);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(timeAdapter);

        // Tạo adapter cho Spinner Phase
        phaseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, phaseList);
        phaseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPhase.setAdapter(phaseAdapter);

        // Tạo adapter cho Spinner Class
        classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classList);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClass.setAdapter(classAdapter);

        // Tạo adapter cho Spinner Course
        courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseList);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourse.setAdapter(courseAdapter);

        // Tạo adapter cho Spinner Teacher
        teacherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teacherList);
        teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeacher.setAdapter(teacherAdapter);

        // Tạo adapter cho Spinner Room
        roomAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomList);
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoom.setAdapter(roomAdapter);

        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSchedule();
            }
        });
    }

    // Phương thức thêm lịch trình
    private void addSchedule() {
        // Lấy thông tin từ các Spinner và EditText
        MdTime selectedTime = (MdTime) spinnerTime.getSelectedItem();
        MdPhase selectedPhase = (MdPhase) spinnerPhase.getSelectedItem();
        MdClass selectedClass = (MdClass) spinnerClass.getSelectedItem();
        Course selectedCourse = (Course) spinnerCourse.getSelectedItem();
        MdTeacher selectedTeacher = (MdTeacher) spinnerTeacher.getSelectedItem();
        MdRoom selectedRoom = (MdRoom) spinnerRoom.getSelectedItem();
        String day = txtDay.getText().toString();

        // Kiểm tra logic của dữ liệu
        if (selectedTime == null || selectedPhase == null || selectedClass == null || selectedTeacher == null || selectedRoom == null || day.isEmpty()) {
            Toast.makeText(AddScheduleActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else if (!isValidDateFormat(day)) {
            Toast.makeText(AddScheduleActivity.this, "Vui lòng nhập đúng ngày", Toast.LENGTH_SHORT).show();
        } else {
            // Kiểm tra xem thời gian được chọn có phù hợp với pha hay không
            int selectedTimeId = selectedTime.getId();
            int selectedPhaseId = selectedPhase.getId();
            if (!isTimeInPhase(selectedTimeId, selectedPhaseId)) {
                Toast.makeText(AddScheduleActivity.this, "Thời gian không phù hợp với ca học đã chọn", Toast.LENGTH_SHORT).show();
                return; // Nếu thời gian không phù hợp với pha, không thêm lịch trình vào cơ sở dữ liệu
            }

            // Kiểm tra xem lớp có thuộc khoá học không
            if (isClassInCourse(selectedClass, selectedCourse)) {
                // Lấy ngày bắt đầu & kết thúc của khóa học từ cơ sở dữ liệu
                String[] startAndEndDate = databaseHelper.getCourseStartAndEndDate(selectedCourse.getId());
                String courseStartDate = startAndEndDate[0];
                String courseEndDate = startAndEndDate[1];

                // Kiểm tra xem ngày học có vượt quá ngày kết thúc hoặc trước ngày bắt đầu của khóa học không
                if (compareDates(day, courseStartDate) >= 0 && compareDates(day, courseEndDate) <= 0) {
                    // Tạo đối tượng lịch trình mới
                    MdSchedule newSchedule = new MdSchedule();
                    newSchedule.setTimeId(selectedTime.getId());
                    newSchedule.setPhaseId(selectedPhase.getId());
                    newSchedule.setClassId(selectedClass.getId());
                    newSchedule.setCourseId(selectedCourse.getId());
                    newSchedule.setTeacherId(selectedTeacher.getId());
                    newSchedule.setRoomId(selectedRoom.getId());
                    newSchedule.setDay(day);

                    // Thêm lịch trình vào cơ sở dữ liệu
                    long result = databaseHelper.addSchedule(newSchedule);

                    // Kiểm tra kết quả và hiển thị thông báo tương ứng
                    if (result != -1) {
                        Toast.makeText(AddScheduleActivity.this, "Thêm lịch trình thành công", Toast.LENGTH_SHORT).show();
                        Intent backToList = new Intent(AddScheduleActivity.this, AdminScheduleActivity.class);
                        startActivity(backToList);
                        finish(); // Đóng màn hình AddScheduleActivity sau khi thêm thành công
                    } else {
                        Toast.makeText(AddScheduleActivity.this, "Thêm lịch trình thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddScheduleActivity.this, "Ngày học nằm ngoài khoảng thời gian của khóa học", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AddScheduleActivity.this, "Lớp không thuộc khoá học đã chọn", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isClassInCourse(MdClass selectedClass, Course selectedCourse) {
        DatabaseHelper dbHelper = new DatabaseHelper(AddScheduleActivity.this); // Thay context bằng context thích hợp của bạn
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM class WHERE id = ? AND course_id = ?";
        String[] selectionArgs = {String.valueOf(selectedClass.getId()), String.valueOf(selectedCourse.getId())};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        boolean isInCourse = cursor.getCount() > 0;
        cursor.close();
        return isInCourse;
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

    // Kiểm tra giờ học có thuộc phạm vi ca học hay không
    private boolean isTimeInPhase(int timeId, int phaseId) {
        // Kiểm tra xem thời gian có thuộc pha không
        if ((phaseId == 1 && (timeId == 3 || timeId == 4)) || (phaseId == 2 && (timeId == 1 || timeId == 2))) {
            return false; // Không hợp lệ nếu thời gian không thuộc pha
        }
        return true; // Hợp lệ nếu thời gian thuộc pha
    }
}