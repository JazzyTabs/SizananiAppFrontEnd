package com.example.codetribe1.sizanani.dto;

import java.io.Serializable;

/**
 * Created by geoffrey on 1/17/16.
 */
public class ServicesDTO implements Serializable {
    private Integer service_ID;
    private String firstname;
    private String surname;
    private String description;
    private String location;
    private String type;
    public ServicesDTO() {
    }

    public ServicesDTO(Integer service_ID, String firstname, String surname, String description, String location, String type) {
        this.service_ID = service_ID;
        this.firstname = firstname;
        this.surname = surname;
        this.description = description;
        this.location = location;
        this.type = type;
    }

    public Integer getService_ID() {
        return service_ID;
    }

    public void setService_ID(Integer service_ID) {
        this.service_ID = service_ID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
