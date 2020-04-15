package com.masalaboratory.vegetable.model;

public class Image {

    private String savePath;
    private String url;

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
        return "Image [savePath=" + savePath + ", url=" + url + "]";
    }
    
}