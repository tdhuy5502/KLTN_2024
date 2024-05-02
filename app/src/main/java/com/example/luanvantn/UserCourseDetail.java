package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import model.Course;

public class UserCourseDetail extends AppCompatActivity {

    private TextView txtCourseId,txtCourseName,txtClassDay,txtClassQty,txtBeginDay,txtEndDay;
    private Course selectedCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_course_detail);

        txtCourseId = findViewById(R.id.userCourseId);
        txtCourseName = findViewById(R.id.userCourseName);
        txtClassDay = findViewById(R.id.userClassDay);
        txtClassQty = findViewById(R.id.userClassQty);
        txtBeginDay = findViewById(R.id.userBeginDay);
        txtEndDay = findViewById(R.id.userEndDay);

        selectedCourse = getIntent().getParcelableExtra("selectedCourse");

        if(selectedCourse != null)
        {
            txtCourseId.setText("Mã khoá học: " + String.valueOf(selectedCourse.getId()));
            txtCourseName.setText("Tên khoá học: " + selectedCourse.getCourseName());
            txtClassDay.setText("Số buổi học: " + String.valueOf(selectedCourse.getSoBuoi()));
            txtClassQty.setText("Số lớp thuộc khoá học: " +  String.valueOf(selectedCourse.getSoLop()));
            txtBeginDay.setText("Tên khoá học: " + selectedCourse.getBeginDay());
            txtEndDay.setText("Tên khoá học: " + selectedCourse.getEndDay());
        }
    }
}