
package model;

public class Projects {
    private String projectID;
    private String devID;
    private String projectName;
    private int durationMonth;
    private String startDate;

    public Projects() {
    }

    public Projects(String projectID, String devID, String projectName, int durationMonth, String startDate) {
        this.projectID = projectID;
        this.devID = devID;
        this.projectName = projectName;
        this.durationMonth = durationMonth;
        this.startDate = startDate;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getDevID() {
        return devID;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getDuration_Month() {
        return durationMonth;
    }

    public void setDuration_Month(int durationMonth) {
        this.durationMonth = durationMonth;
    }

    public String getStart_date() {
        return startDate;
    }

    public void setStart_date(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Projects{" + "projectID=" + projectID + ", devID=" + devID + ", projectName=" + projectName + ", durationMonth=" + durationMonth + ", startDate=" + startDate + '}';
    }
    
}
