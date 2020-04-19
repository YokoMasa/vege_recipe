package com.masalaboratory.vegetable.controller;

import com.masalaboratory.vegetable.controller.helper.ImageSaveHelper;
import com.masalaboratory.vegetable.model.Image;
import com.masalaboratory.vegetable.util.SavedImage;

import org.springframework.beans.factory.annotation.Autowired;

public class ImageHandlingController {

    @Autowired
    private ImageSaveHelper imageSaveHelper;

    protected Image updateImage(Image target, SavedImage newImage) throws Exception {
        if (target != null) {
            imageSaveHelper.delete(toSavedImage(target));
        } else {
            target = new Image();
        }
        target.setSavePath(newImage.savePath);
        target.setUrl(newImage.url);
        return target;
    }

    protected Image toImage(SavedImage si) {
        Image i = new Image();
        i.setSavePath(si.savePath);
        i.setUrl(si.url);
        return i;
    }

    protected SavedImage toSavedImage(Image i) {
        return new SavedImage(i.getSavePath(), i.getUrl());
    }

}