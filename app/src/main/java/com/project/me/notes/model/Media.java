package com.project.me.notes.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Sazumi on 02.08.2017.
 */

public class Media extends RealmObject {
    @PrimaryKey
    private int id;
    @Required
    private String filePath;
    private int fileType;

    public Media() {
    }

    public Media(int id, String filePath, int fileType) {
        this.id = id;
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }
}
