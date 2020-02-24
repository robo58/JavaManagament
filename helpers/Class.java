package app.helpers;

import javafx.scene.control.Button;

public class Class {
    private int id,ects,semestar;
    private String name;
    private Button updatebtn,delbtn;
    public Class(int id, String name, int ects, int semestar,Button updatebtn,Button delbtn) {
        this.id = id;
        this.ects = ects;
        this.semestar = semestar;
        this.name = name;
        this.updatebtn=updatebtn;
        this.delbtn=delbtn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public int getSemestar() {
        return semestar;
    }

    public void setSemestar(int semestar) {
        this.semestar = semestar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Button getDelbtn() {
        return delbtn;
    }

    public void setDelbtn(Button delbtn) {
        this.delbtn = delbtn;
    }

    public Button getUpdatebtn() {
        return updatebtn;
    }

    public void setUpdatebtn(Button updatebtn) {
        this.updatebtn = updatebtn;
    }
}
