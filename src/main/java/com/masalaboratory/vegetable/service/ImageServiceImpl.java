package com.masalaboratory.vegetable.service;

import java.awt.image.BufferedImage;
import java.io.File;

import com.masalaboratory.vegetable.model.Image;
import com.masalaboratory.vegetable.util.ImagePersistent;
import com.masalaboratory.vegetable.util.ImageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    @Qualifier("localImagePersistent")
    private ImagePersistent imagePersistent;

    @Override
    public Image save(MultipartFile file) throws Exception {
        return imagePersistent.save(file);
    }

    @Override
    public Image createResizedImageFrom(Image image, int size) throws Exception {
        File f = new File(image.getSavePath());
        BufferedImage resized = ImageUtil.createResizedImage(f, size);
        return imagePersistent.save(resized);
    }

    @Override
    public void delete(Image image) throws Exception {
        imagePersistent.delete(image);
    }

}