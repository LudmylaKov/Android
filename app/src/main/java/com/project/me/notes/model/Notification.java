package com.project.me.notes.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Sazumi on 21.06.2017.
 */

public class Notification extends RealmObject {
    @PrimaryKey
    private int id;
    private Long dateNotification;
    private boolean isNotification;

    public Notification(Long dateNotification, boolean isNotification) {
        this.dateNotification = dateNotification;
        this.isNotification = isNotification;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getDateNotification() {
        return dateNotification;
    }

    public void setDateNotification(Long dateNotification) {
        this.dateNotification = dateNotification;
    }

    public boolean isNotification() {
        return isNotification;
    }

    public void setNotification(boolean notification) {
        isNotification = notification;
    }
}

