package com.example.luanvantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import model.Course;
import model.DatabaseHelper;

public class UpdateCourseActivity extends AppCompatActivity {

    private Button updateBtn,backBtn;
    private EditText editTextName,editTextSobuoi,editTextSoLop,edtBegin,edtEnd;
    private Course selectedCourse;
    private final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);

        updateBtn = findViewById(R.id.UpdateButton);
        backBtn = findViewById(R.id.cancelUpdateCourse);
        editTextName = findViewById(R.id.courseNameUpdate);
        editTextSobuoi = findViewById(R.id.onTimeQtyUpdate);
        editTextSoLop = findViewById(R.id.classQtyUpdate);
        edtBegin = findViewById(R.id.beginDayUpdate);
        edtEnd = findViewById(R.id.endDayUpdate);

        Intent intent = getIntent();
        selectedCourse = intent.getParcelableExtra("selectedCourse");
        if (selectedCourse != null) {
            editTextName.setText(selectedCourse.getCourseName());
            editTextSobuoi.setText(String.valueOf(selectedCourse.getSoBuoi()));
            editTextSoLop.setText(String.valueOf(selectedCourse.getSoLop()));
            edtBegin.setText(selectedCourse.getBeginDay());
            edtEnd.setText(selectedCourse.getEndDay());
        }

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseName = editTextName.getText().toString().trim();
                int soBuoiHoc = Integer.parseInt(editTextSobuoi.getText().toString().trim());
                int soLop = Integer.parseInt(editTextSoLop.getText().toString().trim());
                String beginDayTxt = edtBegin.getText().toString().trim();
                String endDayTxt = edtEnd.getText().toString().trim();

                // Kiểm tra định dạng ngày tháng nhập vào
                if (!isValidDateFormat(beginDayTxt) || !isValidDateFormat(endDayTxt)) {
                    Toast.makeText(UpdateCourseActivity.this, "Định dạng ngày tháng không hợp lệ. Vui lòng sử dụng định dạng dd/MM/yyyy.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Cập nhật thông tin khoá học trong cơ sở dữ liệu
                DatabaseHelper databaseHelper = new DatabaseHelper(UpdateCourseActivity.this);
                boolean isUpdated = databaseHelper.updateCourse(selectedCourse.getId(), courseName, soBuoiHoc, soLop,beginDayTxt,endDayTxt);

                if (isUpdated)
                {
                    Toast.makeText(UpdateCourseActivity.this, "Đã cập nhật thông tin khoá học", Toast.LENGTH_SHORT).show();
                    Intent backtoList = new Intent(UpdateCourseActivity.this,AdminCourseActivity.class);
                    startActivity(backtoList);
                    finish();
                }
                else
                {
                    Toast.makeText(UpdateCourseActivity.this, "Lỗi khi cập nhật thông tin khoá học", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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