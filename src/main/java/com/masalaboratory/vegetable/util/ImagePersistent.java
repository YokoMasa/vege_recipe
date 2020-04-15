package com.masalaboratory.vegetable.util;

import java.io.IOException;
import java.awt.image.BufferedImage;

import com.masalaboratory.vegetable.model.Image;

import org.springframework.web.multipart.MultipartFile;

public interface ImagePersistent {

    public Image save(MultipartFile file) throws IOException;

    public Image save(BufferedImage image) throws IOException;

    public void delete(Image image) throws IOException;

}