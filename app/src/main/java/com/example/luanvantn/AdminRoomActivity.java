package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import model.Course;
import model.DatabaseHelper;
import model.MdRoom;

public class AdminRoomActivity extends AppCompatActivity {

    private ListView listRoom;
    private Button addRoom,backBtn;
    private ArrayAdapter<MdRoom> roomAdapter;
    private DatabaseHelper databaseHelper;
    private List<MdRoom> roomNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_room);

        listRoom = findViewById(R.id.listViewRoom);
        databaseHelper = new DatabaseHelper(this);

        roomNames = databaseHelper.getAllRooms();
        roomAdapter = new ArrayAdapter<MdRoom>(this, android.R.layout.simple_list_item_1,roomNames);

        listRoom.setAdapter(roomAdapter);

        listRoom.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                MdRoom selectedRoom = (MdRoom) listRoom.getItemAtPosition(i);
                Intent roomCRUD = new Intent(AdminRoomActivity.this,AdminRoomCRUD.class);
                roomCRUD.putExtra("selectedRoom",(Parcelable) selectedRoom);
                startActivity(roomCRUD);
                Toast.makeText(AdminRoomActivity.this, "Chon phong hoc", Toast.LENGTH_SHORT).show();
            }
        });

        addRoom = findViewById(R.id.buttonAddRoom);
        backBtn = findViewById(R.id.backRoomBtn);

        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addRoomIntent = new Intent(AdminRoomActivity.this,AddRoomActivity.class);
                startActivity(addRoomIntent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToDashboard = new Intent(AdminRoomActivity.this,AdminActivity.class);
                startActivity(backToDashboard);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại ListView khi quay trở lại từ màn hình trước
        roomAdapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Gọi finish() nếu bạn muốn kết thúc hoạt động hiện tại khi quay lại màn hình trước
        finish();
    }

}