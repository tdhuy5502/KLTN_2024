package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import model.Course;
import model.DatabaseHelper;
import model.MdClass;
import model.MdTeacher;

public class UpdateClassActivity extends AppCompatActivity {

    private MdClass selectedClass;
    private Spinner spinnerTeacher;
    private Spinner spinnerCourse;
    private ArrayAdapter<MdTeacher> teacherAdapter;
    private ArrayAdapter<Course> courseAdapter;
    private DatabaseHelper databaseHelper;
    private Button btnUpdateClass,backBtn;
    private EditText txtNewClassName, txtNewStudQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_class);

        btnUpdateClass = findViewById(R.id.confirmEditClass);
        backBtn = findViewById(R.id.cancelUpdateClass);

        txtNewClassName = findViewById(R.id.updateClassName);
        txtNewStudQty = findViewById(R.id.updateStudentQty);

        // Khởi tạo spinner và adapter
        spinnerCourse = findViewById(R.id.spinnerUpdateCourse);
        spinnerTeacher = findViewById(R.id.spinnerUpdateTeacher);
        teacherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        spinnerTeacher.setAdapter(teacherAdapter);
        spinnerCourse.setAdapter(courseAdapter);

        // Khởi tạo database helper
        databaseHelper = new DatabaseHelper(this);

        // Lấy đối tượng MdClass được truyền từ Intent
        selectedClass = getIntent().getParcelableExtra("selectedClass");

        //Spinner Teacher
        List<MdTeacher> teacherList = databaseHelper.getAllTeachers();
        teacherAdapter.addAll(teacherList);
        int teacherPosition = getTeacherPosition(selectedClass.getTeacherId(), teacherList);
        spinnerTeacher.setSelection(teacherPosition);

        //Spinner Course
        List<Course> courseList = databaseHelper.getAllCourses();
        courseAdapter.addAll(courseList);
        int coursePosition = getCoursePosition(selectedClass.getCourseId(), courseList);
        spinnerCourse.setSelection(coursePosition);

        txtNewClassName.setText(selectedClass.getName());
        txtNewStudQty.setText(String.valueOf(selectedClass.getStudentQty()));

        btnUpdateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateClass();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private int getTeacherPosition(int teacherId, List<MdTeacher> teacherList) {
        for (int i = 0; i < teacherList.size(); i++) {
            if (teacherList.get(i).getId() == teacherId) {
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

    private void updateClass() {
        String className = txtNewClassName.getText().toString();
        int studentQty = Integer.parseInt(txtNewStudQty.getText().toString().trim());
        Course selectedCourse = (Course) spinnerCourse.getSelectedItem();
        MdTeacher selectedTeacher = (MdTeacher) spinnerTeacher.getSelectedItem();
        int selectedCourseId =  selectedCourse.getId();
        int selectedTeacherId = selectedTeacher.getId();

        DatabaseHelper databaseHelper = new DatabaseHelper(UpdateClassActivity.this);
        boolean isUpdated = databaseHelper.updateClass(selectedClass.getId(), className, selectedCourseId, studentQty, selectedTeacherId);

        // Hiển thị thông báo và kết thúc activity
        Toast.makeText(UpdateClassActivity.this, "Cập nhật lớp học thành công", Toast.LENGTH_SHORT).show();
        Intent backToList = new Intent(UpdateClassActivity.this,AdminClassActivity.class);
        startActivity(backToList);
        finish();
    }


}