package com.project.me.notes.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Sazumi on 20.06.2017.
 */


public class Note extends RealmObject {
    @PrimaryKey
    private int id;
    @Required
    private Long timestamp;
    private String title;
    @Required
    private String text;
    private RealmList<Tags> tags;
    private RealmList<Media> media;
    private Notification notification;

    public Note() {
    }

    public Note(Long timestamp, String title, String text, RealmList<Tags> tags, RealmList<Media> media, Notification notification) {
        this.timestamp = timestamp;
        this.title = title;
        this.text = text;
        this.tags = tags;
        this.media = media;
        this.notification = notification;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public RealmList<Tags> getTags() {
        return tags;
    }

    public void setTags(RealmList<Tags> tags) {
        this.tags = tags;
    }

    public RealmList<Media> getMedia() {
        return media;
    }

    public void setMedia(RealmList<Media> media) {
        this.media = media;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}

