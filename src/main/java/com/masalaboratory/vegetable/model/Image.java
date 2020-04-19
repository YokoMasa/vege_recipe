package com.masalaboratory.vegetable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.masalaboratory.vegetable.util.SavedImage;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Image) {
            Image i = (Image) o;
            return url.equals(i.getUrl()) && savePath.equals(i.getSavePath());
        } else {
            return false;
        }
    }
    
}