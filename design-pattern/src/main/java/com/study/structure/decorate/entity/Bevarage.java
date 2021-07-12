package com.study.structure.decorate.entity;

/**
 * 饮料 - 抽象
 */
public abstract class Bevarage {

    protected String description = "饮料产品";

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public abstract Double price();
}
