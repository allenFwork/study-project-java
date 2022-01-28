package com.study.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.List;

// 通过 @ConfigurationProperties 来接受yml配置的信息注入到该类中, prefix是yml中配置的前缀
@ConfigurationProperties(prefix = "user")
public class User {

    private Long id;

    private String name;

    private Date birthDate;

    private List<String> pets;

    private List<String> maps;

    private Power power;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<String> getPets() {
        return pets;
    }

    public void setPets(List<String> pets) {
        this.pets = pets;
    }

    public List<String> getMaps() {
        return maps;
    }

    public void setMaps(List<String> maps) {
        this.maps = maps;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", pets=" + pets +
                ", maps=" + maps +
                ", power=" + power +
                '}';
    }
}
