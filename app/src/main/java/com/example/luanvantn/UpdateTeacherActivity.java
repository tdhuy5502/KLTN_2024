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

public class UpdateTeacherActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private EditText editTextClassQty;
    private Button buttonUpdate,backBtn;
    private MdTeacher selectedTeacher;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher);

        selectedTeacher = getIntent().getParcelableExtra("selectedTeacher");
        buttonUpdate = findViewById(R.id.UpdateTeacherButton);
        backBtn = findViewById(R.id.cancelUpdateTeacher);
        editTextName = findViewById(R.id.UpdateTeacherName);
        editTextEmail = findViewById(R.id.UpdateTeacherEmail);
        editTextPhone = findViewById(R.id.UpdateTeacherPhone);
        editTextClassQty = findViewById(R.id.UpdateTeacherSoLop);

        if(selectedTeacher != null)
        {
            editTextName.setText(selectedTeacher.getName());
            editTextEmail.setText(selectedTeacher.getEmail());
            editTextPhone.setText(selectedTeacher.getPhone());
            editTextClassQty.setText(String.valueOf(selectedTeacher.getSoLop()));
        }

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teacherName = editTextName.getText().toString().trim();
                String teacherEmail = editTextEmail.getText().toString().trim();
                String teacherPhone = editTextPhone.getText().toString().trim();
                int classQty = Integer.parseInt(editTextClassQty.getText().toString().trim());

                databaseHelper = new DatabaseHelper(UpdateTeacherActivity.this);
                boolean isUpdated = databaseHelper.updateTeacher(selectedTeacher.getId(),teacherName,teacherEmail,teacherPhone,classQty);

                if (isUpdated)
                {
                    Toast.makeText(UpdateTeacherActivity.this, "Đã cập nhật thông tin GV", Toast.LENGTH_SHORT).show();
                    Intent backtoList = new Intent(UpdateTeacherActivity.this,AdminTeacherActivity.class);
                    startActivity(backtoList);
                    finish();
                }
                else
                {
                    Toast.makeText(UpdateTeacherActivity.this, "Lỗi khi cập nhật thông tin GV", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}