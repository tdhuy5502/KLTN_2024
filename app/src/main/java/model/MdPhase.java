package model;

import android.os.Parcel;
import android.os.Parcelable;

public class MdPhase implements Parcelable {
    private int id;
    private String name;

    public MdPhase() {
    }

    public MdPhase(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected MdPhase(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<MdPhase> CREATOR = new Creator<MdPhase>() {
        @Override
        public MdPhase createFromParcel(Parcel in) {
            return new MdPhase(in);
        }

        @Override
        public MdPhase[] newArray(int size) {
            return new MdPhase[size];
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return name;
    }

}