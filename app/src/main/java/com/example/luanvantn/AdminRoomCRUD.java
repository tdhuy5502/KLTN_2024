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
import model.MdRoom;

public class AdminRoomCRUD extends AppCompatActivity {

    private Button updateRoom,deleteRoom;
    private MdRoom selectedRoom;
    private TextView txtRoomId,txtRoomName,txtRoomStatus,txtRoomCate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_room_crud);

        updateRoom = findViewById(R.id.buttonEditRoom);
        deleteRoom = findViewById(R.id.buttonDeleteRoom);

        selectedRoom = getIntent().getParcelableExtra("selectedRoom");

        if(selectedRoom != null)
        {
            txtRoomId = findViewById(R.id.txtRoomId);
            txtRoomId.setText("Mã phòng học: " + selectedRoom.getId());

            txtRoomName = findViewById(R.id.txtRoomName);
            txtRoomName.setText("Tên phòng học: " + selectedRoom.getName());

            txtRoomStatus = findViewById(R.id.txtRoomStatus);
            txtRoomStatus.setText("Tình trạng: " + selectedRoom.getStatus());

            txtRoomCate = findViewById(R.id.txtRoomCate);
            txtRoomCate.setText("Kiểu phòng học: " + selectedRoom.getCategory());
        }

        updateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateRoomInt = new Intent(AdminRoomCRUD.this,UpdateRoomActivity.class);
                updateRoomInt.putExtra("selectedRoom",selectedRoom);
                startActivity(updateRoomInt);
            }
        });

        deleteRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog();
            }
        });
    }
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xoá phòng học");
        builder.setMessage("Bạn có chắc chắn muốn xoá phòng học này ?");
        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteSelectedRoom();
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

    private void deleteSelectedRoom() {
        DatabaseHelper databaseHelper = new DatabaseHelper(AdminRoomCRUD.this);
        boolean isDeleted = databaseHelper.deleteRoom(selectedRoom.getId());

        if (isDeleted)
        {
            Toast.makeText(AdminRoomCRUD.this, "Đã xóa phòng học", Toast.LENGTH_SHORT).show();
            Intent intentBack = new Intent(AdminRoomCRUD.this,AdminRoomActivity.class);
            startActivity(intentBack);
            finish();
        }
        else
        {
            Toast.makeText(AdminRoomCRUD.this, "Lỗi khi xóa phòng học", Toast.LENGTH_SHORT).show();
        }
    }
}