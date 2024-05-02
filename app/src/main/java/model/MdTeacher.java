package model;

import android.os.Parcel;
import android.os.Parcelable;

public class MdTeacher implements Parcelable {
    private int id;
    private String name;
    private String email;
    private String phone;
    private int soLop;

    public MdTeacher(int id, String name, String email, String phone, int soLop) {
        this.id=id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.soLop = soLop;
    }

    protected MdTeacher(Parcel in) {
        id = in.readInt();
        name = in.readString();
        email = in.readString();
        phone = in.readString();
        soLop = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeInt(soLop);
    }

    public static final Creator<MdTeacher> CREATOR = new Creator<MdTeacher>() {
        @Override
        public MdTeacher createFromParcel(Parcel in) {
            return new MdTeacher(in);
        }

        @Override
        public MdTeacher[] newArray(int size) {
            return new MdTeacher[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSoLop() {
        return soLop;
    }

    public void setSoLop(int soLop) {
        this.soLop = soLop;
    }

    @Override
    public String toString()
    {
        return "MGV: " + id +" - "+ "Tên: " + name;
    }


}