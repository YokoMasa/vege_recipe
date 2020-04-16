package com.masalaboratory.vegetable.controller.helper;

import java.awt.image.BufferedImage;
import java.io.File;

import com.masalaboratory.vegetable.util.ImagePersistent;
import com.masalaboratory.vegetable.util.ImageUtil;
import com.masalaboratory.vegetable.util.SavedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageSaveHelper {

    @Autowired
    @Qualifier("localImagePersistent")
    private ImagePersistent imagePersistent;

    public SavedImage save(MultipartFile file) throws Exception {
        return imagePersistent.save(file);
    }

    public SavedImage createResizedImageFrom(SavedImage image, int size) throws Exception {
        File f = new File(image.savePath);
        BufferedImage resized = ImageUtil.createResizedImage(f, size);
        return imagePersistent.save(resized);
    }

    public void delete(SavedImage image) throws Exception {
        imagePersistent.delete(image);
    }

}