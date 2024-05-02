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

import model.Course;
import model.DatabaseHelper;

public class AdminCourseCRUD extends AppCompatActivity {

    private Button updateCourse,deleteCourse;
    private TextView txtCourseId,txtCourseName,txtSoBuoiHoc,txtSoLop,txtBeginDay,txtEndDay;
    private Course selectedCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course_crud);

        updateCourse = findViewById(R.id.buttonEditCourse);
        deleteCourse = findViewById(R.id.buttonDeleteCourse);

        // Lấy Intent đã gửi từ view trc
        selectedCourse = getIntent().getParcelableExtra("selectedCourse");

        if (selectedCourse != null) {
            txtCourseId = findViewById(R.id.maKH);
            txtCourseId.setText("Mã khoá học: " + String.valueOf(selectedCourse.getId()));

            txtCourseName = findViewById(R.id.tenKH);
            txtCourseName.setText("Tên khoá học: " + selectedCourse.getCourseName());

            txtSoBuoiHoc = findViewById(R.id.sobuoihoc);
            txtSoBuoiHoc.setText("Số buổi học: " + String.valueOf(selectedCourse.getSoBuoi()));

            txtSoLop = findViewById(R.id.solophoc);
            txtSoLop.setText("Số lớp thuộc khoá học: " +  String.valueOf(selectedCourse.getSoLop()));

            txtBeginDay = findViewById(R.id.ngaybatdau);
            txtBeginDay.setText("Ngày bắt đầu: " + selectedCourse.getBeginDay());

            txtEndDay = findViewById(R.id.ngayketthuc);
            txtEndDay.setText("Ngày kết thúc: " + selectedCourse.getEndDay());
        }

        updateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateCourseInt = new Intent(AdminCourseCRUD.this,UpdateCourseActivity.class);
                updateCourseInt.putExtra("selectedCourse", selectedCourse);
                startActivity(updateCourseInt);
            }
        });

        deleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog();
            }
        });
    }
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xoá khoá học");
        builder.setMessage("Bạn có chắc chắn muốn xoá khoá học này ?");
        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteSelectedCourse();
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

    private void deleteSelectedCourse() {
        DatabaseHelper databaseHelper = new DatabaseHelper(AdminCourseCRUD.this);
        boolean isDeleted = databaseHelper.deleteCourse(selectedCourse.getId());

        if (isDeleted)
        {
            Toast.makeText(AdminCourseCRUD.this, "Đã xóa khoá học", Toast.LENGTH_SHORT).show();
            Intent intentBack = new Intent(AdminCourseCRUD.this,AdminCourseActivity.class);
            startActivity(intentBack);
            finish();
        }
        else
        {
            Toast.makeText(AdminCourseCRUD.this, "Lỗi khi xóa khoá học", Toast.LENGTH_SHORT).show();
        }
    }
}