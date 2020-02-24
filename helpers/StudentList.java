package app.helpers;

import javafx.scene.control.Button;

public class StudentList {
    private String studentName,indexNum;
    private boolean attended;
    private Button btn;
    public StudentList(String studentName, String indexNum, boolean attended,Button btn) {
        this.studentName = studentName;
        this.indexNum = indexNum;
        this.attended = attended;
        this.btn = btn;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(String indexNum) {
        this.indexNum = indexNum;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
