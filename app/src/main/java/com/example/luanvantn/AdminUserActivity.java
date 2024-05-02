package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.DatabaseHelper;

public class AdminUserActivity extends AppCompatActivity {

    private ListView listUser;
    private Button addUser;
    private ArrayAdapter<String> userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);

        // Truy vấn dữ liệu từ cơ sở dữ liệu
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("user", null, null, null, null, null, null);

        // Tạo danh sách các email người dùng từ dữ liệu Cursor
        List<String> userEmailList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do
            {
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                userEmailList.add(email);
            }
            while (cursor.moveToNext());
            cursor.close();
        }

        // Tạo Adapter
        userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userEmailList);

        listUser = findViewById(R.id.listViewUser);

        listUser.setAdapter(userAdapter);

        listUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedUser = userEmailList.get(i);

                // Tạo Intent để chuyển dữ liệu qua activity chi tiết
                Intent crudIntent = new Intent(AdminUserActivity.this,AdminUserCRUD.class);
                crudIntent.putExtra("selectedUser", selectedUser);
                startActivity(crudIntent);
                Toast.makeText(AdminUserActivity.this, "Chon tai khoan", Toast.LENGTH_SHORT).show();
            }
        });

        addUser = findViewById(R.id.buttonAddUser);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(AdminUserActivity.this,RegisterActivity.class);
                startActivity(addIntent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại ListView khi quay trở lại từ màn hình trước
        userAdapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Gọi finish() nếu bạn muốn kết thúc hoạt động hiện tại khi quay lại màn hình trước
        finish();
    }

}