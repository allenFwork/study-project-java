package com.study.entity;

public class Power {

    private Long id;
    private String powerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPowerId() {
        return powerId;
    }

    public void setPowerId(String powerId) {
        this.powerId = powerId;
    }

    @Override
    public String toString() {
        return "Power{" +
                "id=" + id +
                ", powerId='" + powerId + '\'' +
                '}';
    }
}
