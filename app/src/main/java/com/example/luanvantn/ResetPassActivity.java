package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.DatabaseHelper;

public class ResetPassActivity extends AppCompatActivity {

    private Button backButton,confirm;
    private EditText userEmail,oldPass,newPass,confirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        backButton = findViewById(R.id.backButton);
        confirm = findViewById(R.id.confirmButton);
        userEmail = findViewById(R.id.email);
        oldPass = findViewById(R.id.oldpassword);
        newPass = findViewById(R.id.newpassword);
        confirmPass = findViewById(R.id.newconfirmpassword);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(ResetPassActivity.this,MainActivity.class);
                startActivity(back);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = userEmail.getText().toString();
        String oldPassword = oldPass.getText().toString();
        String newPassword = newPass.getText().toString();
        String confirmPassword = confirmPass.getText().toString();

        // Kiểm tra xem email, mật khẩu mới và mật khẩu xác nhận đã được nhập hay chưa
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra xem mật khẩu mới và mật khẩu xác nhận có khớp nhau không
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu mới và mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thực hiện cập nhật mật khẩu dựa trên email
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.updatePasswordByEmail(email, newPassword);

        // Hiển thị thông báo cập nhật mật khẩu thành công
        Toast.makeText(this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();

        // Kết thúc activity và quay lại activity trước đó
        finish();
    }
}