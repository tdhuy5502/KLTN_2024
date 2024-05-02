package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import model.Course;
import model.DatabaseHelper;

public class CourseActivity extends AppCompatActivity {

    private ListView listCourse;
    private ArrayAdapter<Course> courseAdapter;
    private DatabaseHelper databaseHelper;
    private List<Course> courseNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        listCourse = findViewById(R.id.listUserCourse);
        databaseHelper = new DatabaseHelper(this);

        courseNames = databaseHelper.getAllCourses();

        // Tạo adapter để hiển thị danh sách tên khóa học
        courseAdapter = new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_1, courseNames);

        listCourse.setAdapter(courseAdapter);

        listCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Course selectedCourse = (Course) listCourse.getItemAtPosition(i);
                Intent courseCRUD = new Intent(CourseActivity.this,UserCourseDetail.class);
                courseCRUD.putExtra("selectedCourse", (Parcelable) selectedCourse);
                startActivity(courseCRUD);
                Toast.makeText(CourseActivity.this, "Chon khoa hoc", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void onBackPressed() {
        super.onBackPressed();
        // Gọi finish() nếu bạn muốn kết thúc hoạt động hiện tại khi quay lại màn hình trước
        finish();
    }
}