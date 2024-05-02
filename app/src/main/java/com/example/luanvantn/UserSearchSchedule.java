package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import model.DatabaseHelper;
import model.MdSchedule;

public class UserSearchSchedule extends AppCompatActivity {

    private EditText scheduleName,txtStartRange,txtEndRange;
    private Button searchBtn;
    private ListView listView;
    private ArrayAdapter<MdSchedule> adapter;
    private List<MdSchedule> scheduleList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search_schedule);

        scheduleName = findViewById(R.id.userSearchScheduleByTeacher);
        txtStartRange = findViewById(R.id.userMinDaySchedule);
        txtEndRange = findViewById(R.id.userMaxDaySchedule);
        searchBtn = findViewById(R.id.userBtnSearchScheduleByDayRange);

        listView = findViewById(R.id.listUserSchedule);

        databaseHelper = new DatabaseHelper(this);
        scheduleList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scheduleList);
        listView.setAdapter(adapter);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               performSearch();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailSchedule = new Intent(UserSearchSchedule.this,UserScheduleDetail.class);
                MdSchedule selectedSchedule = scheduleList.get(i);
                detailSchedule.putExtra("selectedSchedule",selectedSchedule);
                startActivity(detailSchedule);
            }
        });
    }
    private void performSearch() {
        String minDay = txtStartRange.getText().toString();
        String maxDay = txtEndRange.getText().toString();
        String trName = scheduleName.getText().toString();

        if (isValidDateFormat(minDay) && isValidDateFormat(maxDay)) {
            // Gọi phương thức để tìm kiếm lịch học trong khoảng thời gian
            List<MdSchedule> result = databaseHelper.getScheduleByDateRangeAndTeacher(minDay, maxDay,trName);
            for (MdSchedule schedule : result) {
                Log.d("Schedule", schedule.toString()); // Đẩy thông tin mỗi lịch học ra Logcat
            }
            // Xóa danh sách hiện tại
            scheduleList.clear();
            // Thêm kết quả tìm được vào danh sách
            scheduleList.addAll(result);
            adapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(UserSearchSchedule.this, "Vui lòng nhập đúng định dạng ngày", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidDateFormat(String date) {
        // Tách ngày thành các phần riêng biệt
        String[] parts = date.split("/");
        // Kiểm tra số phần tử của ngày
        if (parts.length == 3) {
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            // Kiểm tra nếu ngày hoặc tháng nhỏ hơn 10 và không có số 0 ở đầu
            if ((day < 10 && !parts[0].startsWith("0")) || (month < 10 && !parts[1].startsWith("0"))) {
                return false;
            }
        } else {
            // Trả về false nếu không có đủ 3 phần tử ngày/tháng/năm
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private int compareDates(String date1, String date2) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            // Kiểm tra và thêm số 0 nếu ngày và tháng < 10
            if (date1.charAt(1) == '/') {
                date1 = "0" + date1;
            }
            if (date2.charAt(1) == '/') {
                date2 = "0" + date2;
            }
            Date d1 = dateFormat.parse(date1);
            Date d2 = dateFormat.parse(date2);
            return d1.compareTo(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu có lỗi xảy ra
    }
}