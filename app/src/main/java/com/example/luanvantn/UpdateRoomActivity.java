package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.DatabaseHelper;
import model.MdRoom;

public class UpdateRoomActivity extends AppCompatActivity {

    private Button btnUpdateRoom,btnBack;
    private EditText txtRoomName,txtRoomStatus,txtRoomCate;
    private MdRoom selectedRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_room);

        btnUpdateRoom = findViewById(R.id.btnUpdateRoom);
        btnBack = findViewById(R.id.cancelUpdateRoom);

        txtRoomName = findViewById(R.id.txtUpdateRoomName);
        txtRoomStatus = findViewById(R.id.txtUpdateStatus);
        txtRoomCate = findViewById(R.id.txtUpdateCategory);

        Intent intent = getIntent();
        selectedRoom = intent.getParcelableExtra("selectedRoom");
        if(selectedRoom != null)
        {
            txtRoomName.setText(selectedRoom.getName());
            txtRoomStatus.setText(selectedRoom.getStatus());
            txtRoomCate.setText(selectedRoom.getCategory());
        }

        btnUpdateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roomName = txtRoomName.getText().toString();
                String roomStatus = txtRoomStatus.getText().toString();
                String roomCate = txtRoomCate.getText().toString();

                DatabaseHelper databaseHelper = new DatabaseHelper(UpdateRoomActivity.this);
                boolean isUpdated = databaseHelper.updateRoom(selectedRoom.getId(), roomName, roomStatus, roomCate);

                if (isUpdated)
                {
                    Toast.makeText(UpdateRoomActivity.this, "Đã cập nhật thông tin phòng học", Toast.LENGTH_SHORT).show();
                    Intent backtoList = new Intent(UpdateRoomActivity.this,AdminRoomActivity.class);
                    startActivity(backtoList);
                    finish();
                }
                else
                {
                    Toast.makeText(UpdateRoomActivity.this, "Lỗi khi cập nhật thông tin phòng học", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}