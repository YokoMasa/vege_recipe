package com.masalaboratory.vegetable.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.masalaboratory.vegetable.model.Image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class LocalImagePersistent implements ImagePersistent {

    @Value("${localImageSaver.savePath}")
    private String savePath;

    @Value("${localImageSaver.rootUrl}")
    private String rootUrl;

    public String getSavePath() {
        return savePath;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    private String createFileName(String ext) {
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Long.toString(System.currentTimeMillis()) + "." + ext;
    }

    private File createSaveFile(String fileName) throws IOException {
        File root = ResourceUtils.getFile(savePath);
        return new File(root, fileName);
    }

    private String extractExt(String fileName) {
        int i = fileName.lastIndexOf(".");
        return fileName.substring(i + 1);
    }

    private String createUrl(String fileName) {
        if (rootUrl.endsWith("/")) {
            return rootUrl + fileName;
        }
        return rootUrl + "/" + fileName;
    }

    @Override
    public Image save(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String fileName = createFileName(extractExt(originalFileName));
        System.out.println(fileName);

        File dest = createSaveFile(fileName);
        file.transferTo(dest);

        Image i = new Image();
        i.setSavePath(dest.getAbsolutePath());
        i.setUrl(createUrl(fileName));
        return i;
    }

    @Override
    public Image save(BufferedImage image) throws IOException {
        String fileName = createFileName("png");
        File dest = createSaveFile(fileName);

        ImageIO.write(image, "png", dest);

        Image i = new Image();
        i.setSavePath(dest.getAbsolutePath());
        i.setUrl(createUrl(fileName));
        return i;
    }

    @Override
    public void delete(Image image) throws IOException {
        File file = new File(image.getSavePath());
        file.delete();
    }


}