package com.example.luanvantn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AdminActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView logout,schedule_menu,course_menu,teacher_menu,user_menu,class_menu,room_menu;
    private ImageView searchScheduleView,searchCourseView,searchTeacherView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        searchScheduleView = findViewById(R.id.adminScheduleViewBtn);
        searchCourseView = findViewById(R.id.adminCourseViewBtn);
        searchTeacherView = findViewById(R.id.adminTeacherViewBtn);

        drawerLayout = findViewById(R.id.drawerLayoutAdmin);
        logout = findViewById(R.id.dangxuat);
        schedule_menu = findViewById(R.id.quanly_lich);
        course_menu = findViewById(R.id.quanly_kh);
        teacher_menu = findViewById(R.id.quanly_gv);
        user_menu = findViewById(R.id.quanly_user);
        class_menu = findViewById(R.id.quanly_class);
        room_menu = findViewById(R.id.quanly_room);

        course_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent manage_course = new Intent(AdminActivity.this,AdminCourseActivity.class);
                startActivity(manage_course);

                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        schedule_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent manage_schedule = new Intent(AdminActivity.this,AdminScheduleActivity.class);
                startActivity(manage_schedule);

                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        teacher_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent manage_teacher = new Intent(AdminActivity.this,AdminTeacherActivity.class);
                startActivity(manage_teacher);

                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        user_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent manage_user = new Intent(AdminActivity.this,AdminUserActivity.class);
                startActivity(manage_user);

                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        class_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent manage_class = new Intent(AdminActivity.this,AdminClassActivity.class);
                startActivity(manage_class);

                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        room_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent manage_room = new Intent(AdminActivity.this,AdminRoomActivity.class);
                startActivity(manage_room);

                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutConfirmationDialog();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        searchScheduleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchScheduleView = new Intent(AdminActivity.this,SearchScheduleActivity.class);
                startActivity(searchScheduleView);
            }
        });

        searchCourseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchCourseView = new Intent(AdminActivity.this,SearchCourseActivity.class);
                startActivity(searchCourseView);
            }
        });

        searchTeacherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchTeacherView = new Intent(AdminActivity.this,SearchTeacherAvtivity.class);
                startActivity(searchTeacherView);
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
        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Kết thúc hoạt động hiện tại (UserActivity)
    }
}