package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Course implements Parcelable {
    private int id;
    private String courseName;
    private int soBuoi;
    private int soLop;
    private String beginDay;
    private String endDay;

    public Course(int id, String courseName, int soBuoi, int soLop,String beginDay,String endDay) {
        this.id = id;
        this.courseName = courseName;
        this.soBuoi = soBuoi;
        this.soLop = soLop;
        this.beginDay = beginDay;
        this.endDay = endDay;
    }

    protected Course(Parcel in) {
        id = in.readInt();
        courseName = in.readString();
        soBuoi = in.readInt();
        soLop = in.readInt();
        beginDay = in.readString();
        endDay = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(courseName);
        dest.writeInt(soBuoi);
        dest.writeInt(soLop);
        dest.writeString(beginDay);
        dest.writeString(endDay);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getSoBuoi() {
        return soBuoi;
    }

    public void setSoBuoi(int soBuoi) {
        this.soBuoi = soBuoi;
    }

    public int getSoLop() {
        return soLop;
    }

    public void setSoLop(int soLop) {
        this.soLop = soLop;
    }

    // Getter and setter for beginDay
    public String getBeginDay() {
        return beginDay;
    }

    public void setBeginDay(String beginDay) {
        this.beginDay = beginDay;
    }

    // Getter and setter for endDay
    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    @Override
    public String toString()
    {
        return "MKH: " + id + " - " + "Tên KH: " + courseName + " - Bắt đầu: " + beginDay + " - Kết thúc: " + endDay;
    }
}