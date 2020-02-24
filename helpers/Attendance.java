package app.helpers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Attendance {
    private String teacherName,className,hour,attended,ywd;
    private int tcid;

    private Button showStudents;
    public Attendance(int tcid,String teacherName, String className, String hour, String attended,String ywd,Button showStudents) {
        this.teacherName = teacherName;
        this.className = className;
        this.hour = hour;
        this.attended = attended;
        this.ywd = ywd;
        this.tcid=tcid;
        this.showStudents=showStudents;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getAttended() {
        return attended;
    }

    public void setAttended(String attended) {
        this.attended = attended;
    }

    public String getYwd() {
        return ywd;
    }

    public void setYwd(String ywd) {
        this.ywd = ywd;
    }

    public int getTcid() {
        return tcid;
    }

    public void setTcid(int tcid) {
        this.tcid = tcid;
    }

    public Button getShowStudents() {
        return showStudents;
    }

    public void setShowStudents(Button showStudents) {
        this.showStudents = showStudents;
    }
}
