package com.masalaboratory.vegetable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Image {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "save_path")
    private String savePath;

    @Column(name = "url")
    private String url;

    public int getId() {
        return id;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Image [id=" + id + ", savePath=" + savePath + ", url=" + url + "]";
    }
    
}