package model;
import android.os.Parcel;
import android.os.Parcelable;

public class MdRoom implements Parcelable {
    private int id;
    private String name;
    private String status;
    private String category;

    // Constructor, getters và setters
    public MdRoom(int id, String name, String status, String category) {
        this.id=id;
        this.name = name;
        this.status = status;
        this.category = category;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Parcelable implementation
    protected MdRoom(Parcel in) {
        id = in.readInt();
        name = in.readString();
        status = in.readString();
        category = in.readString();
    }

    public static final Creator<MdRoom> CREATOR = new Creator<MdRoom>() {
        @Override
        public MdRoom createFromParcel(Parcel in) {
            return new MdRoom(in);
        }

        @Override
        public MdRoom[] newArray(int size) {
            return new MdRoom[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(status);
        dest.writeString(category);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Phòng học: " + name + " - Kiểu phòng học: " + category;
    }
}