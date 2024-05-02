package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.DatabaseHelper;
import model.MdClass;
import model.MdSchedule;

public class AdminScheduleActivity extends AppCompatActivity {

    private ListView listSchedule;
    private Button addSchedule,backBtn;
    private ArrayAdapter<MdSchedule> scheduleAdapter;
    private List<MdSchedule> scheduleList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_schedule2);

        databaseHelper = new DatabaseHelper(this);
        scheduleList = databaseHelper.getAllSchedule();

        scheduleAdapter = new ArrayAdapter<MdSchedule>(this, android.R.layout.simple_list_item_1,scheduleList);

        listSchedule = findViewById(R.id.listViewSchedule);

        listSchedule.setAdapter(scheduleAdapter);

        for (MdSchedule schedule : scheduleList) {
            Log.d("Schedule", schedule.toString()); // Đẩy thông tin ra Logcat
        }

        listSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent scheduleCRUD = new Intent(AdminScheduleActivity.this,AdminScheduleCRUD.class);
                MdSchedule selectedSchedule = scheduleList.get(i);
                scheduleCRUD.putExtra("selectedSchedule", selectedSchedule);
                startActivity(scheduleCRUD);
            }
        });

        addSchedule = findViewById(R.id.buttonAddSchedule);
        backBtn = findViewById(R.id.backScheduleBtn);

        addSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addScheduleIntent = new Intent(AdminScheduleActivity.this,AddScheduleActivity.class);
                startActivity(addScheduleIntent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToDashboard = new Intent(AdminScheduleActivity.this,AdminActivity.class);
                startActivity(backToDashboard);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại ListView khi quay trở lại từ màn hình trước
        scheduleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Gọi finish() nếu bạn muốn kết thúc hoạt động hiện tại khi quay lại màn hình trước
        finish();
    }

}