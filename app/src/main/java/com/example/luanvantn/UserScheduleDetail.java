package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import model.MdSchedule;

public class UserScheduleDetail extends AppCompatActivity {

    private MdSchedule selectedSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_schedule_detail);

        selectedSchedule = getIntent().getParcelableExtra("selectedSchedule");

        if(selectedSchedule != null)
        {
            TextView txtScheduleId = findViewById(R.id.userScheduleId);
            txtScheduleId.setText("Mã lịch học: " + selectedSchedule.getScheduleId());

            TextView txtScheduleClass = findViewById(R.id.userScheduleClassName);
            txtScheduleClass.setText("Lớp học: " + selectedSchedule.getClassName());

            TextView txtSchedulePhase = findViewById(R.id.userSchedulePhase);
            txtSchedulePhase.setText("Ca: " + selectedSchedule.getPhaseName());

            TextView txtScheduleTime = findViewById(R.id.userScheduleTime);
            txtScheduleTime.setText("Thời gian: " + selectedSchedule.getTimeName());

            TextView txtScheduleDay = findViewById(R.id.userScheduleDay);
            txtScheduleDay.setText("Ngày: " + selectedSchedule.getDay());

            TextView txtScheduleRoom = findViewById(R.id.userScheduleRoom);
            txtScheduleRoom.setText("Phòng: " + selectedSchedule.getRoomName());

            TextView txtScheduleTeacher = findViewById(R.id.userScheduleTeacher);
            txtScheduleTeacher.setText("Giảng viên: " + selectedSchedule.getTeacherName());
        }
    }
}