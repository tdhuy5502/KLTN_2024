package model;

import android.os.Parcel;
import android.os.Parcelable;

public class MdSchedule implements Parcelable {
    private int scheduleId;
    private int classId;
    private String className;
    private int courseId;
    private String courseName;
    private String courseEnd;
    private int phaseId;
    private String phaseName;
    private int timeId;
    private String timeName;
    private String day;
    private int roomId;
    private String roomName;
    private int teacherId;
    private String teacherName;


    public MdSchedule() {
        // Constructor mặc định
    }

    protected MdSchedule(Parcel in) {
        scheduleId = in.readInt();
        classId = in.readInt();
        className = in.readString();
        courseId = in.readInt();
        courseName = in.readString();
        courseEnd = in.readString();
        phaseId = in.readInt();
        phaseName = in.readString();
        timeId = in.readInt();
        timeName = in.readString();
        day = in.readString();
        roomId = in.readInt();
        roomName = in.readString();
        teacherId = in.readInt();
        teacherName = in.readString();
    }

    public static final Creator<MdSchedule> CREATOR = new Creator<MdSchedule>() {
        @Override
        public MdSchedule createFromParcel(Parcel in) {
            return new MdSchedule(in);
        }

        @Override
        public MdSchedule[] newArray(int size) {
            return new MdSchedule[size];
        }
    };

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }

    public int getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(int phaseId) {
        this.phaseId = phaseId;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public String getTimeName() {
        return timeName;
    }

    public void setTimeName(String timeName) {
        this.timeName = timeName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(scheduleId);
        dest.writeInt(classId);
        dest.writeString(className);
        dest.writeInt(courseId);
        dest.writeString(courseName);
        dest.writeString(courseEnd);
        dest.writeInt(phaseId);
        dest.writeString(phaseName);
        dest.writeInt(timeId);
        dest.writeString(timeName);
        dest.writeString(day);
        dest.writeInt(roomId);
        dest.writeString(roomName);
        dest.writeInt(teacherId);
        dest.writeString(teacherName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Lớp: " + className + " - Ngày: " + day + " - Thời gian: " + timeName + " - Phòng: " + roomName;
    }
}
