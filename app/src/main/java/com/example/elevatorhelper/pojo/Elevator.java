package com.example.elevatorhelper.pojo;

import java.io.Serializable;

/**
 * @author yhjzs
 *
 * @date 2020-6-3
 */
public class Elevator  implements Serializable {
    private static final long serialVersionUID = 3829319987286118458L;
    private Integer id;
    private String elevatorNumber;
    /**状态0：正常；状态1、停止运行；状态2：施工中；状态3：其他；*/
    private Integer status;
    private String comment;

    public Elevator() {
    }

    public Elevator(String elevatorNumber, Integer status, String comment) {
        this.elevatorNumber = elevatorNumber;
        this.status = status;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getElevatorNumber() {
        return elevatorNumber;
    }

    public void setElevatorNumber(String elevatorNumber) {
        this.elevatorNumber = elevatorNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

