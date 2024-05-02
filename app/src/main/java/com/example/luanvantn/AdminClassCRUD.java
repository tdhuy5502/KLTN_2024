package com.example.luanvantn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import model.DatabaseHelper;
import model.MdClass;

public class AdminClassCRUD extends AppCompatActivity {

    private MdClass selectedClass;
    private Button updateClass,deleteClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_class_crud);

        updateClass = findViewById(R.id.buttonEditClass);
        deleteClass = findViewById(R.id.buttonDeleteClass);
        selectedClass = getIntent().getParcelableExtra("selectedClass");


            // Kiểm tra xem dữ liệu selectedClass có null hay không
            if(selectedClass != null)
            {
                Log.d("AdminClassCRUD", "Selected Class ID: " + selectedClass.getId());
                Log.d("AdminClassCRUD", "Selected Class Name: " + selectedClass.getName());
                Log.d("AdminClassCRUD", "Selected Course Name: " + selectedClass.getCourseName());
                Log.d("AdminClassCRUD", "Selected Teacher Name: " + selectedClass.getTeacherName());
                Log.d("AdminClassCRUD", "Selected Student Quantity: " + selectedClass.getStudentQty());

                // Tiếp tục ánh xạ dữ liệu vào TextViews
                // ...
                TextView txtClassId = findViewById(R.id.txtClassId);
                txtClassId.setText("Mã lớp: " + selectedClass.getId());

                TextView txtClassName = findViewById(R.id.txtClassName);
                txtClassName.setText("Tên lớp: " + selectedClass.getName());

                TextView txtClassCourse = findViewById(R.id.txtClassCourseId);
                txtClassCourse.setText("Khoá học: " + selectedClass.getCourseName());

                TextView txtClassSV_Qty = findViewById(R.id.txtClassSV_Qty);
                txtClassSV_Qty.setText("Số sinh viên: " + selectedClass.getStudentQty());

                TextView txtClassTeacher = findViewById(R.id.txtClassTeacherId);
                txtClassTeacher.setText("GV giảng dạy: " + selectedClass.getTeacherName());

                TextView txtCourseBegin = findViewById(R.id.txtClassCourseBegin);
                txtCourseBegin.setText("Ngày bắt đầu khoá học: " + selectedClass.getCourseBegin());

                TextView txtCourseEnd = findViewById(R.id.txtClassCourseEnd);
                txtCourseEnd.setText("Ngày kết thúc khoá học: " + selectedClass.getCourseEnd());
            }


        updateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateClass = new Intent(AdminClassCRUD.this,UpdateClassActivity.class);
                updateClass.putExtra("selectedClass",selectedClass);
                startActivity(updateClass);
            }
        });

        deleteClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog();
            }
        });
    }
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xoá lớp học");
        builder.setMessage("Bạn có chắc chắn muốn xoá lớp học này ?");
        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteSelectedClass();
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
    private void deleteSelectedClass() {
        DatabaseHelper databaseHelper = new DatabaseHelper(AdminClassCRUD.this);
        boolean isDeleted = databaseHelper.deleteClass(selectedClass.getId());

        if (isDeleted)
        {
            Toast.makeText(AdminClassCRUD.this, "Đã xóa lớp học", Toast.LENGTH_SHORT).show();
            Intent intentBack = new Intent(AdminClassCRUD.this,AdminClassActivity.class);
            startActivity(intentBack);
            finish();
        }
        else
        {
            Toast.makeText(AdminClassCRUD.this, "Lỗi khi xóa lớp học", Toast.LENGTH_SHORT).show();
        }
    }
}