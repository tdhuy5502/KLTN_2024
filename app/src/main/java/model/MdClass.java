package model;

import android.os.Parcel;
import android.os.Parcelable;

public class MdClass implements Parcelable {
    private int id;
    private String name;
    private int courseId;
    private String courseName;
    private int studentQty;
    private int teacherId;
    private String teacherName;
    private String courseBegin;
    private String courseEnd;

    public MdClass() {

    }

    public MdClass(int id, String name, int courseId, int studentQty, int teacherId) {
        this.id = id;
        this.name = name;
        this.courseId = courseId;
        this.studentQty = studentQty;
        this.teacherId = teacherId;
    }

    protected MdClass(Parcel in) {
        id = in.readInt();
        name = in.readString();
        courseId = in.readInt();
        courseName = in.readString();
        studentQty = in.readInt();
        teacherId = in.readInt();
        teacherName = in.readString();
        courseBegin = in.readString();
        courseEnd = in.readString();
    }

    public static final Creator<MdClass> CREATOR = new Creator<MdClass>() {
        @Override
        public MdClass createFromParcel(Parcel in) {
            return new MdClass(in);
        }

        @Override
        public MdClass[] newArray(int size) {
            return new MdClass[size];
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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getStudentQty() {
        return studentQty;
    }

    public void setStudentQty(int studentQty) {
        this.studentQty = studentQty;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getCourseBegin() {
        return courseBegin;
    }

    public String getCourseEnd() {
        return courseEnd;
    }

    public void setCourseBegin(String courseBegin) {
        this.courseBegin = courseBegin;
    }

    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(courseId);
        dest.writeString(courseName); // Thêm dòng này
        dest.writeInt(studentQty);
        dest.writeInt(teacherId);
        dest.writeString(teacherName); // Thêm dòng này
        dest.writeString(courseBegin);
        dest.writeString(courseEnd);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Lớp: " + name + " - GV: " + teacherName;
    }
}