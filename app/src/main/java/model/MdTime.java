package model;

import android.os.Parcel;
import android.os.Parcelable;

public class MdTime implements Parcelable {
    private int id;
    private String classTime;

    public MdTime() {
    }

    public MdTime(int id, String classTime) {
        this.id = id;
        this.classTime = classTime;
    }

    protected MdTime(Parcel in) {
        id = in.readInt();
        classTime = in.readString();
    }

    public static final Creator<MdTime> CREATOR = new Creator<MdTime>() {
        @Override
        public MdTime createFromParcel(Parcel in) {
            return new MdTime(in);
        }

        @Override
        public MdTime[] newArray(int size) {
            return new MdTime[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(classTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return classTime;
    }
}
