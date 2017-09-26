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
    private boolean isVideo;
    private boolean isPicture;
    private int fontSize;
    private String textColor;
    
    public Note() {
        this.isVideo = false;
        this.isPicture = false;
        this.fontSize = ConstantType.FONT_SIZE_MEDIUM;
        this.media = new RealmList<Media>();
        this.textColor = ConstantType.TEXT_COLOR_BLACK;
    }

    public Note(Long timeStamp, String title, String text, Tag tag, RealmList<Media> media,
                boolean isVideo, boolean isPicture) {

        this.timeStamp = timeStamp;
        this.title = title;
        this.text = text;
        this.tag = tag;
        this.media = media;
        this.isVideo = isVideo;
        this.textColor = ConstantType.TEXT_COLOR_BLACK;
        this.isPicture = isPicture;
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

    public void setMedia() {
        this.media = new RealmList<Media>();
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public boolean isPicture() {
        return isPicture;
    }

    public void setPicture(boolean picture) {
        isPicture = picture;
    }
}

