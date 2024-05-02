package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import model.MdTeacher;

public class UserTeacherDetail extends AppCompatActivity {

    private MdTeacher selectedTeacher;
    private TextView txtId,txtName,txtEmail,txtPhone,txtSoLop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_teacher_detail);

        selectedTeacher = getIntent().getParcelableExtra("selectedTeacher");

        if(selectedTeacher != null)
        {
            txtId = findViewById(R.id.userTeacherId);
            txtId.setText("Mã giảng viên: " + selectedTeacher.getId());

            txtName = findViewById(R.id.userTeacherName);
            txtName.setText("Họ tên: " + selectedTeacher.getName());

            txtEmail = findViewById(R.id.userTeacherEmail);
            txtEmail.setText("Email: " + selectedTeacher.getEmail());

            txtPhone = findViewById(R.id.userTeacherPhone);
            txtPhone.setText("SĐT liên hệ: " + selectedTeacher.getPhone());

            txtSoLop = findViewById(R.id.userTeacherSoLop);
            txtSoLop.setText("Số lớp đang giảng dạy: " +  selectedTeacher.getSoLop());
        }
    }
}