package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class AddClassActivity extends AppCompatActivity {

    private EditText editTextClassName, editTextStudentQty;
    private Spinner spinnerCourse, spinnerTeacher;
    private Button buttonAddClass;
    private DatabaseHelper databaseHelper;

    private List<Course> courseList;
    private List<MdTeacher> teacherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        editTextClassName = findViewById(R.id.editTextClassName);
        editTextStudentQty = findViewById(R.id.editTextStudentQty);
        spinnerCourse = findViewById(R.id.spinnerCourse);
        spinnerTeacher = findViewById(R.id.spinnerTeacher);
        buttonAddClass = findViewById(R.id.btnAddClass);

        databaseHelper = new DatabaseHelper(this);

        // Load danh sách khóa học từ CSDL và đổ vào spinner
        courseList = databaseHelper.getAllCourses();
        ArrayAdapter<Course> courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseList);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourse.setAdapter(courseAdapter);

        // Load danh sách giáo viên từ CSDL và đổ vào spinner
        teacherList = databaseHelper.getAllTeachers();
        ArrayAdapter<MdTeacher> teacherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teacherList);
        teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeacher.setAdapter(teacherAdapter);

        buttonAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClassByView();
            }
        });
    }

    private void addClassByView() {
        String className = editTextClassName.getText().toString();
        String studentQtyString = editTextStudentQty.getText().toString().trim();
        int studentQty = Integer.parseInt(studentQtyString);

        if (className.isEmpty()) {
            Toast.makeText(this, "Please enter class name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (studentQtyString.isEmpty()) {
            Toast.makeText(this, "Please enter student quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        Course selectedCourse = (Course) spinnerCourse.getSelectedItem();
        MdTeacher selectedTeacher = (MdTeacher) spinnerTeacher.getSelectedItem();

        if (selectedCourse == null || selectedTeacher == null) {
            Toast.makeText(this, "Please select course and teacher", Toast.LENGTH_SHORT).show();
            return;
        }

        int courseId = selectedCourse.getId();
        int teacherId = selectedTeacher.getId();

        MdClass mdClass = new MdClass();
        mdClass.setName(className);
        mdClass.setCourseId(courseId);
        mdClass.setStudentQty(studentQty);
        mdClass.setTeacherId(teacherId);

        boolean isSuccess = databaseHelper.addClass(mdClass);
        if (isSuccess) {
            Toast.makeText(this, "Class added successfully", Toast.LENGTH_SHORT).show();
            Intent backToList = new Intent(AddClassActivity.this,AdminClassActivity.class);
            startActivity(backToList);
            finish();
        }
        else
        {
            Toast.makeText(this, "Failed to add class", Toast.LENGTH_SHORT).show();
        }
    }
}