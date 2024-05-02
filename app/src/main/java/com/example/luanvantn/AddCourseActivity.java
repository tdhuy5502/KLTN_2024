package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import model.DatabaseHelper;
import model.DatabaseSchedule;

public class AddCourseActivity extends AppCompatActivity {

    private Button addCourseBtn;
    private EditText coursename,ontimeclass,classqty,beginDay, endDay;
    private final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        addCourseBtn = findViewById(R.id.addCourseButton);

        coursename = findViewById(R.id.courseName);
        ontimeclass = findViewById(R.id.onTimeQty);
        classqty = findViewById(R.id.classQty);
        beginDay = findViewById(R.id.beginDay);
        endDay = findViewById(R.id.endDay);

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCourse();
            }
        });
    }
    private void addCourse()
    {
        String courseName = coursename.getText().toString().trim();
        String soBuoiTxt = ontimeclass.getText().toString().trim();
        String soLopTxt = classqty.getText().toString().trim();
        String beginDayTxt = beginDay.getText().toString().trim();
        String endDayTxt = endDay.getText().toString().trim();

        if (TextUtils.isEmpty(courseName) || TextUtils.isEmpty(soBuoiTxt) || TextUtils.isEmpty(soLopTxt)
                || TextUtils.isEmpty(beginDayTxt) || TextUtils.isEmpty(endDayTxt)) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        int soBuoi = Integer.parseInt(soBuoiTxt);
        int soLop = Integer.parseInt(soLopTxt);

        // Kiểm tra định dạng ngày tháng nhập vào
        if (!isValidDateFormat(beginDayTxt) || !isValidDateFormat(endDayTxt)) {
            Toast.makeText(this, "Định dạng ngày tháng không hợp lệ. Vui lòng sử dụng định dạng dd/MM/yyyy.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thêm dữ liệu vào HelperCourse
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        long result = dbHelper.addCourse(courseName, soBuoi, soLop, beginDayTxt, endDayTxt);

        // Hiển thị thông báo hoặc thực hiện hành động khác sau khi thêm thành công
        if (result == -1)
        {
            Toast.makeText(this, "Thêm khoá học không thành công.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Khoá học đã được thêm thành công.", Toast.LENGTH_SHORT).show();
            Intent backToList = new Intent(AddCourseActivity.this,AdminCourseActivity.class);
            startActivity(backToList);
            finish();
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
}