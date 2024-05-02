package com.example.luanvantn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import model.DatabaseHelper;
import model.MdSchedule;

public class AdminScheduleCRUD extends AppCompatActivity {

    private Button updateSchedule,deleteSchedule;
    private MdSchedule selectedSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_schedule_crud);

        selectedSchedule = getIntent().getParcelableExtra("selectedSchedule");

        updateSchedule = findViewById(R.id.buttonEditSchedule);
        deleteSchedule = findViewById(R.id.buttonDeleteSchedule);

        if(selectedSchedule != null)
        {

            TextView txtScheduleId = findViewById(R.id.txtScheduleId);
            txtScheduleId.setText("Mã lịch học: " + selectedSchedule.getScheduleId());

            TextView txtScheduleClass = findViewById(R.id.txtScheduleClassName);
            txtScheduleClass.setText("Lớp học: " + selectedSchedule.getClassName());

            TextView txtSchedulePhase = findViewById(R.id.txtSchedulePhase);
            txtSchedulePhase.setText("Ca: " + selectedSchedule.getPhaseName());

            TextView txtScheduleTime = findViewById(R.id.txtScheduleTime);
            txtScheduleTime.setText("Thời gian: " + selectedSchedule.getTimeName());

            TextView txtScheduleDay = findViewById(R.id.txtScheduleDay);
            txtScheduleDay.setText("Ngày: " + selectedSchedule.getDay());

            TextView txtScheduleRoom = findViewById(R.id.txtScheduleRoom);
            txtScheduleRoom.setText("Phòng: " + selectedSchedule.getRoomName());

            TextView txtScheduleTeacher = findViewById(R.id.txtScheduleTeacher);
            txtScheduleTeacher.setText("Giảng viên: " + selectedSchedule.getTeacherName());
        }

        updateSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateScheduleInt = new Intent(AdminScheduleCRUD.this,UpdateScheduleActivity.class);
                updateScheduleInt.putExtra("selectedSchedule",selectedSchedule);
                startActivity(updateScheduleInt);
            }
        });

        deleteSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog();
            }
        });
    }
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xoá lịch học");
        builder.setMessage("Bạn có chắc chắn muốn xoá lịch học này ?");
        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteSelectedSchedule();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void deleteSelectedSchedule() {
        DatabaseHelper databaseHelper = new DatabaseHelper(AdminScheduleCRUD.this);
        boolean isDeleted = databaseHelper.deleteSchedule(selectedSchedule.getScheduleId());

        if (isDeleted)
        {
            Toast.makeText(AdminScheduleCRUD.this, "Đã xóa lich học", Toast.LENGTH_SHORT).show();
            Intent intentBack = new Intent(AdminScheduleCRUD.this,AdminScheduleActivity.class);
            startActivity(intentBack);
            finish();
        }
        else
        {
            Toast.makeText(AdminScheduleCRUD.this, "Lỗi khi xóa lich học", Toast.LENGTH_SHORT).show();
        }
    }
}