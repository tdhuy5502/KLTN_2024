package com.example.luanvantn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import model.DatabaseHelper;
import model.MdClass;

public class AdminClassActivity extends AppCompatActivity {

    private ListView listClass;
    private Button addButton,backBtn;
    private ArrayAdapter<MdClass> classAdapter;
    private List<MdClass> classList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_class);

        listClass = findViewById(R.id.listViewClass);
        addButton = findViewById(R.id.buttonAddClass);
        backBtn = findViewById(R.id.backClassBtn);
        databaseHelper = new DatabaseHelper(this);

        // Lấy danh sách lớp học từ cơ sở dữ liệu
        classList = databaseHelper.getAllClasses();

        // Khởi tạo adapter và thiết lập cho ListView
        classAdapter = new ArrayAdapter<MdClass>(this, android.R.layout.simple_list_item_1, classList);
        listClass.setAdapter(classAdapter);

        // Xử lý sự kiện khi nhấn vào một mục trong ListView
        listClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent classCRUD = new Intent(AdminClassActivity.this, AdminClassCRUD.class);
                MdClass selectedClass = classList.get(i);
                classCRUD.putExtra("selectedClass", selectedClass);
                startActivity(classCRUD);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addClass = new Intent(AdminClassActivity.this,AddClassActivity.class);
                startActivity(addClass);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToDashboard = new Intent(AdminClassActivity.this,AdminActivity.class);
                startActivity(backToDashboard);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Làm mới danh sách lớp học từ cơ sở dữ liệu và cập nhật adapter
        classAdapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Gọi finish() nếu bạn muốn kết thúc hoạt động hiện tại khi quay lại màn hình trước
        finish();
    }

}