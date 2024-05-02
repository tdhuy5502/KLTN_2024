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
import model.MdSchedule;
import model.MdTeacher;

public class SearchTeacherAvtivity extends AppCompatActivity {

    private EditText teacherName;
    private Button searchBtn;
    private ListView listView;
    private ArrayAdapter<MdTeacher> adapter;
    private List<MdTeacher> teacherList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_teacher_avtivity);

        teacherName = findViewById(R.id.txtSearchTeacher);
        searchBtn = findViewById(R.id.btnSearchTeacher);

        listView = findViewById(R.id.listSearchTeacher);

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
                    Toast.makeText(SearchTeacherAvtivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailTeacher = new Intent(SearchTeacherAvtivity.this,AdminTeacherCRUD.class);
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