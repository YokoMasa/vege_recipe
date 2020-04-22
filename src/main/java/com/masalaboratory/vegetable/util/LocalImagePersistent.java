package com.masalaboratory.vegetable.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
    public SavedImage save(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String fileName = createFileName(extractExt(originalFileName));
        System.out.println(fileName);

        File dest = createSaveFile(fileName);
        file.transferTo(dest);

        return new SavedImage(dest.getAbsolutePath(), createUrl(fileName));
    }

    @Override
    public SavedImage save(BufferedImage image) throws IOException {
        String fileName = createFileName("png");
        File dest = createSaveFile(fileName);

        ImageIO.write(image, "png", dest);
        return new SavedImage(dest.getAbsolutePath(), createUrl(fileName));
    }

    @Override
    public void delete(SavedImage image) throws IOException {
        File file = new File(image.savePath);
        file.delete();
    }

    @Override
    public void deleteAll() throws IOException {
        File file = ResourceUtils.getFile(getSavePath());
        deleteFile(file);
    }

    private void deleteFile(File f) {
        if (f.isDirectory()) {
            for (File file: f.listFiles()) {
                deleteFile(file);
            }
        } else {
            f.delete();
        }
    }

    @Override
    public boolean exists(SavedImage image) throws IOException {
        File file = new File(image.savePath);
        return file.exists();
    }

    


}