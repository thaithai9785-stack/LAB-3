
package model;

public class Developers {
    private String devID;
    private String devName;
    private String progammingLanguage;
    private int salary;

    public Developers() {
    }

    public Developers(String devID, String devName, String progammingLanguage, int salary) {
        this.devID = devID;
        this.devName = devName;
        this.progammingLanguage = progammingLanguage;
        this.salary = salary;
    }

    public String getDevID() {
        return devID;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getProgammingLanguage() {
        return progammingLanguage;
    }

    public void setProgammingLanguage(String progammingLanguage) {
        this.progammingLanguage = progammingLanguage;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Developers{" + "devID=" + devID + ", devName=" + devName + ", progammingLanguage=" + progammingLanguage + ", salary=" + salary + '}';
    }
    
    
}
