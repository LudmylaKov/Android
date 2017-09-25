package com.project.me.notes.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Sazumi on 29.07.2017.
 */

public class Tag extends RealmObject {
    @PrimaryKey
    private int id;
    @Required
    public String tagName;

    public String colorValue;

    public Tag() {
    }

    public Tag(String tagName, String colorValue) {
        this.tagName = tagName;
        this.colorValue = colorValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getColorValue() {
        return colorValue;
    }

    public void setColorValue(String colorValue) {
        this.colorValue = colorValue;
    }
}
