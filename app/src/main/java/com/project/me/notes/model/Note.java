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
    private Long timeStamp;
    private String title;
    @Required
    private String text;
    private Tag tag;
    private RealmList<Media> media;
    private Notification notification;
    private boolean isAudio;
    private boolean isVideo;
    private boolean isLik;

    public Note() {
    }

    public Note(int id, Long timeStamp, String title, String text, Tag tag, RealmList<Media> media, Notification notification,
                boolean isAudio, boolean isVideo, boolean isLik) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.title = title;
        this.text = text;
        this.tag = tag;
        this.media = media;
        this.notification = notification;
        this.isAudio = isAudio;
        this.isVideo = isVideo;
        this.isLik = isLik;
    }
    public Note(Long timeStamp, String title, String text, Tag tag, RealmList<Media> media, Notification notification,
                boolean isAudio, boolean isVideo, boolean isLik) {

        this.timeStamp = timeStamp;
        this.title = title;
        this.text = text;
        this.tag = tag;
        this.media = media;
        this.notification = notification;
        this.isAudio = isAudio;
        this.isVideo = isVideo;
        this.isLik = isLik;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
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

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public boolean isAudio() {
        return isAudio;
    }

    public void setAudio(boolean audio) {
        isAudio = audio;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public boolean isLik() {
        return isLik;
    }

    public void setLik(boolean lik) {
        isLik = lik;
    }
}

