package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import model.DatabaseHelper;
import model.MdSchedule;

public class SearchScheduleActivity extends AppCompatActivity {

    private EditText teacherName,minDayRange,maxDayRange;
    private Button searchBtn2;
    private ListView listView;
    private ArrayAdapter<MdSchedule> adapter;
    private List<MdSchedule> scheduleList;
    private DatabaseHelper databaseHelper;
    private final String DATE_FORMAT = "dd/mm/yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_schedule);

        teacherName = findViewById(R.id.txtSearchSchedule);
        minDayRange = findViewById(R.id.txtMinDaySchedule);
        maxDayRange = findViewById(R.id.txtMaxDaySchedule);
        searchBtn2 = findViewById(R.id.btnSearchScheduleByDayRange);
        listView = findViewById(R.id.listSearchSchedule);

        databaseHelper = new DatabaseHelper(this);
        scheduleList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scheduleList);
        listView.setAdapter(adapter);

        searchBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSearch();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailSchedule = new Intent(SearchScheduleActivity.this,AdminScheduleCRUD.class);
                MdSchedule selectedSchedule = scheduleList.get(i);
                detailSchedule.putExtra("selectedSchedule",selectedSchedule);
                startActivity(detailSchedule);
            }
        });
    }

    private void performSearch() {
        String minDay = minDayRange.getText().toString();
        String maxDay = maxDayRange.getText().toString();
        String trName = teacherName.getText().toString().trim();

        if (!isValidDateFormat(minDay) || !isValidDateFormat(maxDay)) {
            Toast.makeText(SearchScheduleActivity.this, "Vui lòng nhập đúng định dạng ngày (dd/MM/yyyy)", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gọi phương thức để tìm kiếm lịch học trong khoảng thời gian
        List<MdSchedule> result;
        if (!TextUtils.isEmpty(trName)) {
            result = databaseHelper.getScheduleByDateRangeAndTeacher(minDay, maxDay, trName);
        } else {
            result = databaseHelper.getScheduleByDateRangeAndTeacher(minDay, maxDay, trName);
        }

        // Xóa danh sách hiện tại
        scheduleList.clear();
        // Thêm kết quả tìm được vào danh sách
        scheduleList.addAll(result);
        adapter.notifyDataSetChanged();
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
        }
        else
        {
            // Trả về false nếu không có đủ 3 phần tử ngày/tháng/năm
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try
        {
            dateFormat.parse(date);
            return true;
        }
        catch (ParseException e) {
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