package com.example.codetribe1.sizanani.dto;

import java.io.Serializable;

/**
 * Created by geoffrey on 1/17/16.
 */
public class CategoryDTO implements Serializable {
    private Integer category_ID;
    private String cat_type;
    private String cat_name;
    private String cat_location;
    private String description;

    public CategoryDTO() {
    }

    public CategoryDTO(Integer category_ID, String cat_type, String cat_name, String cat_location, String description) {
        this.category_ID = category_ID;
        this.cat_type = cat_type;
        this.cat_name = cat_name;
        this.cat_location = cat_location;
        this.description = description;
    }

    public Integer getCategory_ID() {
        return category_ID;
    }

    public void setCategory_ID(Integer category_ID) {
        this.category_ID = category_ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCat_location() {
        return cat_location;
    }

    public void setCat_location(String cat_location) {
        this.cat_location = cat_location;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_type() {
        return cat_type;
    }

    public void setCat_type(String cat_type) {
        this.cat_type = cat_type;
    }
}
