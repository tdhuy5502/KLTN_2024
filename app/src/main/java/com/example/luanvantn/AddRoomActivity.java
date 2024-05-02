package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.DatabaseHelper;

public class AddRoomActivity extends AppCompatActivity {

    private Button addRoomBtn,backBtn;
    private EditText txtRoomName,txtRoomStatus,txtRoomCate;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        addRoomBtn = findViewById(R.id.addRoomButton);
        backBtn = findViewById(R.id.RoomBackButton);

        txtRoomName = findViewById(R.id.roomName);
        txtRoomStatus = findViewById(R.id.roomStatus);
        txtRoomCate = findViewById(R.id.roomCategory);

        addRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRoom();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addRoom()
    {
        String roomName = txtRoomName.getText().toString();
        String roomStt = txtRoomStatus.getText().toString();
        String roomCate = txtRoomCate.getText().toString();

        if (TextUtils.isEmpty(roomName) || TextUtils.isEmpty(roomStt) || TextUtils.isEmpty(roomCate)) {
            Toast.makeText(AddRoomActivity.this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHelper = new DatabaseHelper(this);
        boolean result = dbHelper.addRoom(roomName, roomStt, roomCate);
        if (result)
        {
            Toast.makeText(AddRoomActivity.this, "Thêm thông tin phòng học thành công", Toast.LENGTH_SHORT).show();
            Intent backtoList = new Intent(AddRoomActivity.this,AdminRoomActivity.class);
            startActivity(backtoList);
            finish();
        }
        else
        {
            Toast.makeText(AddRoomActivity.this, "Thêm thông tin thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}