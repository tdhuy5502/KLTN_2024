package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.DatabaseHelper;
import model.MdTeacher;

public class AddTeacherActivity extends AppCompatActivity {

    private Button BtnAddTeacher,BtnBack;
    private EditText TxtName,TxtPhone,TxtEmail,TxtClassQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        BtnAddTeacher = findViewById(R.id.addTeacherButton);
        BtnBack = findViewById(R.id.ComeBackButton);

        TxtName = findViewById(R.id.addTeacherName);
        TxtPhone = findViewById(R.id.addTeacherPhone);
        TxtEmail = findViewById(R.id.addTeacherEmail);
        TxtClassQty = findViewById(R.id.addclassQty);

        BtnAddTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTeacher();
            }
        });
    }
    private void addTeacher()
    {
        String name = TxtName.getText().toString().trim();
        String email = TxtEmail.getText().toString().trim();
        String phone = TxtPhone.getText().toString().trim();
        int classQty = Integer.parseInt(TxtClassQty.getText().toString());

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(AddTeacherActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // Tạo đối tượng giáo viên mới
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            boolean isSuccess = dbHelper.addTeacher(name,email,phone,classQty);
            if (isSuccess)
            {
                Toast.makeText(AddTeacherActivity.this, "Thêm giáo viên thành công", Toast.LENGTH_SHORT).show();
                Intent backtoList = new Intent(AddTeacherActivity.this,AdminTeacherActivity.class);
                startActivity(backtoList);
                finish();
            }
            else
            {
                Toast.makeText(AddTeacherActivity.this, "Thêm giáo viên thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }
}