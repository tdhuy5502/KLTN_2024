package com.example.luanvantn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView chitietLich, chitietKH, chitietGv, logout,gioithieu;
    private ImageView userSchedule,userTeacher,userCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userSchedule = findViewById(R.id.searchScheduleUserBtn);
        userTeacher = findViewById(R.id.searchTeacherUserBtn);
        userCourse = findViewById(R.id.searchCourseUserBtn);

        drawerLayout = findViewById(R.id.drawerLayout);
        chitietLich = findViewById(R.id.chitiet_lich);
        chitietKH = findViewById(R.id.chitiet_mon);
        chitietGv = findViewById(R.id.chitiet_gv);
        gioithieu = findViewById(R.id.gioithieu);
        logout = findViewById(R.id.logout_text);

        chitietLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent schedule_view = new Intent(UserActivity.this,ScheduleActivity.class);
                startActivity(schedule_view);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        chitietKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent course_view = new Intent(UserActivity.this,CourseActivity.class);
                startActivity(course_view);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        chitietGv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent teacher_view = new Intent(UserActivity.this,TeacherActivity.class);
                startActivity(teacher_view);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        gioithieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent overview = new Intent(UserActivity.this,CourseDetailActivity.class);
                startActivity(overview);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutConfirmationDialog();
            }
        });

        userSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent schedule = new Intent(UserActivity.this,UserSearchSchedule.class);
                startActivity(schedule);
            }
        });

        userTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent teacher = new Intent(UserActivity.this,UserSearchTeacher.class);
                startActivity(teacher);
            }
        });

        userCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent course = new Intent(UserActivity.this,UserSearchCourse.class);
                startActivity(course);
            }
        });
    }
    public void onBackPressed() {
        // Xử lý khi người dùng nhấn nút Back
        showLogoutConfirmationDialog();
    }

    private void showLogoutConfirmationDialog() {
        if (!isFinishing()) { // Kiểm tra xem hoạt động còn tồn tại không trước khi hiển thị dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xác nhận đăng xuất");
            builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");
            builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Xử lý khi người dùng đăng xuất
                    performLogout();
                }
            });
            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Đóng dialog và không thực hiện hành động nào
                    dialog.dismiss();
                }
            });
            builder.show();
        }
    }

    private void performLogout() {
        // Xử lý đăng xuất ở đây, ví dụ: chuyển đến màn hình đăng nhập
        Intent intent = new Intent(UserActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Kết thúc hoạt động hiện tại (UserActivity)
    }
}