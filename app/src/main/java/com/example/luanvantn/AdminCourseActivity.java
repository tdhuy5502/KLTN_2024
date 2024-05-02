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

public class AdminCourseActivity extends AppCompatActivity {

    private ListView listCourse;
    private Button addCourse,backBtn;
    private ArrayAdapter<Course> courseAdapter;
    private DatabaseHelper databaseHelper;
    private List<Course> courseNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_course);

        listCourse = findViewById(R.id.listViewCourse);
        backBtn = findViewById(R.id.backCourseBtn);
        databaseHelper = new DatabaseHelper(this);

        courseNames = databaseHelper.getAllCourses();

        // Tạo adapter để hiển thị danh sách tên khóa học
        courseAdapter = new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_1, courseNames);

        listCourse.setAdapter(courseAdapter);

        listCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Course selectedCourse = (Course) listCourse.getItemAtPosition(i);
                Intent courseCRUD = new Intent(AdminCourseActivity.this,AdminCourseCRUD.class);
                courseCRUD.putExtra("selectedCourse", (Parcelable) selectedCourse);
                startActivity(courseCRUD);
                Toast.makeText(AdminCourseActivity.this, "Chon khoa hoc", Toast.LENGTH_SHORT).show();
            }
        });

        addCourse = findViewById(R.id.buttonAddCourse);

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCourseIntent = new Intent(AdminCourseActivity.this,AddCourseActivity.class);
                startActivity(addCourseIntent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToDashboard = new Intent(AdminCourseActivity.this,AdminActivity.class);
                startActivity(backToDashboard);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại ListView khi quay trở lại từ màn hình trước
        courseAdapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Gọi finish() nếu bạn muốn kết thúc hoạt động hiện tại khi quay lại màn hình trước
        finish();
    }

}