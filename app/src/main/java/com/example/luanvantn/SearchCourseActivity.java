package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.Course;
import model.DatabaseHelper;
import model.MdTeacher;

public class SearchCourseActivity extends AppCompatActivity {

    private EditText courseName;
    private Button searchBtn;
    private ListView listView;
    private ArrayAdapter<Course> adapter;
    private List<Course> courseList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);

        courseName = findViewById(R.id.txtSearchCourse);
        searchBtn = findViewById(R.id.btnSearchCourse);

        listView = findViewById(R.id.listSearchCourse);

        databaseHelper = new DatabaseHelper(this);
        courseList = new ArrayList<>();
        courseList = databaseHelper.getAllCourses();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
        listView.setAdapter(adapter);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchQuery = courseName.getText().toString().trim().toUpperCase(Locale.ROOT);
                if (!searchQuery.isEmpty()) {
                    searchCourse(searchQuery);
                } else {
                    Toast.makeText(SearchCourseActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailCourse = new Intent(SearchCourseActivity.this,AdminCourseCRUD.class);
                Course selectedCourse = courseList.get(i);
                detailCourse.putExtra("selectedCourse",selectedCourse);
                startActivity(detailCourse);
            }
        });
    }
    private void searchCourse(String query) {
        List<Course> result = databaseHelper.searchCourseByName(query);
        courseList.clear();
        courseList.addAll(result);
        adapter.notifyDataSetChanged();
    }
}