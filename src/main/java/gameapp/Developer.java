package gameapp;

import javax.persistence.*;

@Entity
public class Developer {

    private int devID;
    private String developerName;
    private String HQLocation;

    public Developer(String developerName, String HQLocation) {
        this.developerName = developerName;
        this.HQLocation = HQLocation;
    }

    public Developer() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getDevID() {
        return devID;
    }

    public String getHQLocation() {
        return HQLocation;
    }

    public void setHQLocation(String HQLocation) {
        this.HQLocation = HQLocation;
    }

    public void setDevID(int devID) {
        this.devID = devID;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    @Override
    public String toString() {
        return "\nDeveloper ID: " + devID +
                "\nDeveloper Name: " + developerName +
                "\nHQ Location: " + HQLocation;
    }
}
