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
import model.MdTeacher;

public class AdminTeacherCRUD extends AppCompatActivity {

    private Button updateTeacher,deleteTeacher;
    private TextView txtId,txtName,txtEmail,txtPhone,txtSoLop;
    private MdTeacher selectedTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_teacher_crud);

        updateTeacher = findViewById(R.id.buttonEditTeacher);
        deleteTeacher = findViewById(R.id.buttonDeleteTeacher);

        selectedTeacher = getIntent().getParcelableExtra("selectedTeacher");

        if(selectedTeacher != null)
        {
            txtId = findViewById(R.id.txtTeacherId);
            txtId.setText("Mã giảng viên: " + selectedTeacher.getId());

            txtName = findViewById(R.id.txtTeacherName);
            txtName.setText("Họ tên: " + selectedTeacher.getName());

            txtEmail = findViewById(R.id.txtTeacherEmail);
            txtEmail.setText("Email: " + selectedTeacher.getEmail());

            txtPhone = findViewById(R.id.txtTeacherPhone);
            txtPhone.setText("SĐT liên hệ: " + selectedTeacher.getPhone());

            txtSoLop = findViewById(R.id.txtTeacherSoLop);
            txtSoLop.setText("Số lớp đang giảng dạy: " +  selectedTeacher.getSoLop());
        }

        updateTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateTeacherInt = new Intent(AdminTeacherCRUD.this,UpdateTeacherActivity.class);
                updateTeacherInt.putExtra("selectedTeacher",selectedTeacher);
                startActivity(updateTeacherInt);
            }
        });

        deleteTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog();
            }
        });
    }
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xoá giảng viên");
        builder.setMessage("Bạn có chắc chắn muốn xoá thông tin GV này ?");
        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteSelectedTeacher();
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

    private void deleteSelectedTeacher() {
        DatabaseHelper databaseHelper = new DatabaseHelper(AdminTeacherCRUD.this);
        boolean isDeleted = databaseHelper.deleteTeacher(selectedTeacher.getId());

        if (isDeleted)
        {
            Toast.makeText(AdminTeacherCRUD.this, "Đã xóa GV thành công", Toast.LENGTH_SHORT).show();
            Intent intentBack = new Intent(AdminTeacherCRUD.this,AdminTeacherActivity.class);
            startActivity(intentBack);
            finish();
        }
        else
        {
            Toast.makeText(AdminTeacherCRUD.this, "Lỗi khi xóa GV", Toast.LENGTH_SHORT).show();
        }
    }
}