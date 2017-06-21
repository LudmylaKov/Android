package com.project.me.notes.model;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Sazumi on 20.06.2017.
 */


public class Note extends RealmObject {
    @PrimaryKey
    private int id;
    private Long timestamp;
    private String title;
    private String text;
    private List<String> tags;
    private List<Media> media;
    private Notification notification;

    public Note(Long timestamp, String title, String text, List<String> tags, List<Media> media, Notification notification) {
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}

