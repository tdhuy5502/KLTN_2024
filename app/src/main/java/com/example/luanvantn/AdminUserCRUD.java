package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import model.DatabaseHelper;

public class AdminUserCRUD extends AppCompatActivity {

    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_crud);

        Intent intent = getIntent();
        String selectedUser = intent.getStringExtra("selectedUser");
        if (intent != null && intent.hasExtra("selectedUser")) {
            userEmail = intent.getStringExtra("selectedUser");
            // Hiển thị thông tin về người dùng với email tương ứng
            TextView userDetailsTextView = findViewById(R.id.userDetailsTextView);
            userDetailsTextView.setText("Email người dùng: " + selectedUser);
        }
    }
}