package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import model.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private EditText editUsername;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUsername = findViewById(R.id.userEmail);
        editTextPassword = findViewById(R.id.userPassword);

        Button buttonLogin = findViewById(R.id.loginBtn);
        TextView forgetPass = findViewById(R.id.forgetPassword);

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resetPass = new Intent(MainActivity.this,ResetPassActivity.class);
                startActivity(resetPass);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Lấy giá trị email và mật khẩu từ EditText
                String email = editUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Kiểm tra xem email và mật khẩu có hợp lệ hay không
                if (DatabaseHelper.authenticateUser(MainActivity.this, email, password))
                {
                    // Lấy roleId của người dùng
                    int roleId = DatabaseHelper.getUserRoleId(MainActivity.this, email);
                    // Phân quyền dựa trên roleId
                    if (roleId == 1)
                    {
                        // Thực hiện các hành động cho admin
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent admin_intent = new Intent(MainActivity.this,AdminActivity.class);
                        startActivity(admin_intent);
                        finish();
                    }
                    else if (roleId == 2) {
                        // Nhay vao user view
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent user_intent = new Intent(MainActivity.this,UserActivity.class);
                        startActivity(user_intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Role không hợp lê", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Sai email đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
//    private boolean isValidCredentials(String email, String password)
//    {
//        // Kiểm tra điều kiện hợp lệ của email và mật khẩu
//        // Ở đây chỉ là ví dụ đơn giản, bạn có thể tùy chỉnh theo yêu cầu của mình
//        return email.equals("Admin") && password.equals("123456");
//    }
//    private boolean isValidUserCredentials(String name, String pass)
//    {
//        // Kiểm tra điều kiện hợp lệ của email và mật khẩu
//        // Ở đây chỉ là ví dụ đơn giản, bạn có thể tùy chỉnh theo yêu cầu của mình
//        return name.equals("User") && pass.equals("12345");
//    }
}