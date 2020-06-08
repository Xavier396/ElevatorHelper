package com.example.elevatorhelper.pojo;

/**
 * @author yhjzs
 */
public class Issue {
    private Integer id;
    private String elevatorLocation;
    private String elevatorIssue;
    private String reportTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getElevatorLocation() {
        return elevatorLocation;
    }

    public void setElevatorLocation(String elevatorLocation) {
        this.elevatorLocation = elevatorLocation;
    }

    public String getElevatorIssue() {
        return elevatorIssue;
    }

    public void setElevatorIssue(String elevatorIssue) {
        this.elevatorIssue = elevatorIssue;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }
}
