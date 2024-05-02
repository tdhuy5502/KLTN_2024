package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private Button buttonBack, buttonAdd;
    private EditText roleId,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonBack = findViewById(R.id.ComeBackButton);
        buttonAdd = findViewById(R.id.registerButton);
        roleId = findViewById(R.id.roleid);
        email = findViewById(R.id.addEmail);
        password = findViewById(R.id.addPassword);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(RegisterActivity.this,AdminUserActivity.class);
                startActivity(backIntent);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String roleIdText = roleId.getText().toString().trim();
                String emailText = email.getText().toString().trim();
                String passText = password.getText().toString().trim();
                if (!roleIdText.isEmpty() || !emailText.isEmpty() || !passText.isEmpty())
                {
                    try {
                        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                        SQLiteDatabase db = databaseHelper.getWritableDatabase();
                        ContentValues addContent = new ContentValues();

                        int role_Id = Integer.parseInt(roleIdText);

                        addContent.put("role_id", role_Id);
                        addContent.put("email", emailText);
                        addContent.put("password", passText);
                        long newUserId = db.insert("user", null, addContent);
                        db.close();
                        if (newUserId != -1)
                        {
                            Toast.makeText(getApplicationContext(), "Thêm người dùng thành công", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Thêm người dùng thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        Toast.makeText(getApplicationContext(), "Vui lòng nhập một số nguyên cho role_id", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Vui lòng đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}