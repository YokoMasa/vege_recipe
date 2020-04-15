package com.masalaboratory.vegetable.service;

import com.masalaboratory.vegetable.model.Image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    public Image save(MultipartFile file) throws Exception;

    public Image createResizedImageFrom(Image image, int size) throws Exception;

    public void delete(Image image) throws Exception;

}