package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import model.DatabaseHelper;
import model.MdSchedule;

public class ScheduleActivity extends AppCompatActivity {

    private ListView listSchedule;
    private Button viewSchedule;
    private ArrayAdapter<MdSchedule> scheduleAdapter;
    private List<MdSchedule> scheduleList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        databaseHelper = new DatabaseHelper(this);

        scheduleList = databaseHelper.getAllSchedule();

        scheduleAdapter = new ArrayAdapter<MdSchedule>(this, android.R.layout.simple_list_item_1,scheduleList);

        listSchedule = findViewById(R.id.listUserSchedule);

        listSchedule.setAdapter(scheduleAdapter);

        listSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent scheduleDetail = new Intent(ScheduleActivity.this,UserScheduleDetail.class);
                MdSchedule selectedSchedule = scheduleList.get(i);
                scheduleDetail.putExtra("selectedSchedule", selectedSchedule);
                startActivity(scheduleDetail);
            }
        });


    }
    public void onBackPressed() {
        super.onBackPressed();
        // Gọi finish() nếu bạn muốn kết thúc hoạt động hiện tại khi quay lại màn hình trước
        finish();
    }
}