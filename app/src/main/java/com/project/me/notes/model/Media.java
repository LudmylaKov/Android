package com.project.me.notes.model;

import java.io.File;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Sazumi on 21.06.2017.
 */

public class Media extends RealmObject {
    @PrimaryKey
    private int id;
   // private RealmList<File> audio;
    private List<File> audio;
    private List<File> video;
    private List<String> links;

    public Media(List<File> audio, List<File> video, List<String> links) {
        this.audio = audio;
        this.video = video;
        this.links = links;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<File> getAudio() {
        return audio;
    }

    public void setAudio(List<File> audio) {
        this.audio = audio;
    }

    public List<File> getVideo() {
        return video;
    }

    public void setVideo(List<File> video) {
        this.video = video;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }
}

