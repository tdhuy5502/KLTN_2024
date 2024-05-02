package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.Course;
import model.DatabaseHelper;
import model.MdTeacher;

public class AdminTeacherActivity extends AppCompatActivity {

    private ListView listTeacher;
    private Button addTeacher,backBtn;
    private List<MdTeacher> teacherList;
    private DatabaseHelper databaseHelper;
    private ArrayAdapter<MdTeacher> teacherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_teacher);

        listTeacher = findViewById(R.id.listViewTeacher);

        databaseHelper = new DatabaseHelper(this);

        teacherList = databaseHelper.getAllTeachers();

        teacherAdapter = new ArrayAdapter<MdTeacher>(this,android.R.layout.simple_list_item_1,teacherList);

        listTeacher.setAdapter(teacherAdapter);

        listTeacher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent teacherCRUD = new Intent(AdminTeacherActivity.this,AdminTeacherCRUD.class);
                MdTeacher selectedTeacher = (MdTeacher) listTeacher.getItemAtPosition(i);
                teacherCRUD.putExtra("selectedTeacher",selectedTeacher);
                startActivity(teacherCRUD);
                Toast.makeText(AdminTeacherActivity.this, "Da chon giang vien", Toast.LENGTH_SHORT).show();
            }
        });

        addTeacher = findViewById(R.id.buttonAddTeacher);
        backBtn = findViewById(R.id.backTeacherBtn);

        addTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTeacherIntent = new Intent(AdminTeacherActivity.this,AddTeacherActivity.class);
                startActivity(addTeacherIntent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToDashboard = new Intent(AdminTeacherActivity.this,AdminActivity.class);
                startActivity(backToDashboard);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        //Update lại ListView khi quay trở lại từ màn hình trước
        teacherAdapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Gọi finish() nếu bạn muốn kết thúc hoạt động hiện tại khi quay lại màn hình trước
        finish();
    }

}