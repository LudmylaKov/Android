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
    private String tagName;

    private int colorValue;

    public Tag() {
    }

    public Tag(String tagName, int colorValue) {
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

    public int getColorValue() {
        return colorValue;
    }

    public void setColorValue(int colorValue) {
        this.colorValue = colorValue;
    }
}
