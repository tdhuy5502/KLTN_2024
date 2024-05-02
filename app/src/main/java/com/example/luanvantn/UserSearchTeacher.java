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

import model.DatabaseHelper;
import model.MdTeacher;

public class UserSearchTeacher extends AppCompatActivity {

    private EditText teacherName;
    private Button searchBtn;
    private ListView listView;
    private ArrayAdapter<MdTeacher> adapter;
    private List<MdTeacher> teacherList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search_teacher);

        teacherName = findViewById(R.id.userSearchTeacher);
        searchBtn = findViewById(R.id.btnUserSearchTeacher);

        listView = findViewById(R.id.listUserTeacher);

        databaseHelper = new DatabaseHelper(this);
        teacherList = new ArrayList<>();
        teacherList = databaseHelper.getAllTeachers();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, teacherList);
        listView.setAdapter(adapter);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchQuery = teacherName.getText().toString().trim().toUpperCase(Locale.ROOT);
                if (!searchQuery.isEmpty()) {
                    searchTeacher(searchQuery);
                } else {
                    Toast.makeText(UserSearchTeacher.this, "Vui lòng nhập đầy đủ tên", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailTeacher = new Intent(UserSearchTeacher.this,UserTeacherDetail.class);
                MdTeacher selectedTeacher = teacherList.get(i);
                detailTeacher.putExtra("selectedTeacher",selectedTeacher);
                startActivity(detailTeacher);
            }
        });
    }
    private void searchTeacher(String query) {
        List<MdTeacher> result = databaseHelper.searchTeacherByName(query);
        teacherList.clear();
        teacherList.addAll(result);
        adapter.notifyDataSetChanged();
    }
}