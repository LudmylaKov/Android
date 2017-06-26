package com.project.me.notes.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Sazumi on 26.06.2017.
 */

public class AttachedFile extends RealmObject {
    @PrimaryKey
    private int id;
    @Required
    private String filePath;
    private String fileType;

    public AttachedFile() {
    }

    public AttachedFile(int id, String filePath, String fileType) {
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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
