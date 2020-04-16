package com.masalaboratory.vegetable.util;

import java.io.IOException;
import java.awt.image.BufferedImage;

import org.springframework.web.multipart.MultipartFile;

public interface ImagePersistent {

    public SavedImage save(MultipartFile file) throws IOException;

    public SavedImage save(BufferedImage image) throws IOException;

    public void delete(SavedImage image) throws IOException;

}