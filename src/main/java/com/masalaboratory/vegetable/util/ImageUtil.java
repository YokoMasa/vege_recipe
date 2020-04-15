package com.masalaboratory.vegetable.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;

public final class ImageUtil {

    static class ImageInformation {
        int orientation;
        int width;
        int height;
    
        ImageInformation(int orientation, int width, int height) {
            this.orientation = orientation;
            this.width = width;
            this.height = height;
        }
    
        public String toString() {
            return String.format("%dx%d,%d", this.width, this.height, this.orientation);
        }
    }

    public static ImageInformation readImageInformation(File file) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        Directory directory = metadata.getDirectory(ExifIFD0Directory.class);
        JpegDirectory jpegDirectory = metadata.getDirectory(JpegDirectory.class);
    
        int orientation = 1;
        try {
            orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
            int width = jpegDirectory.getImageWidth();
            int height = jpegDirectory.getImageHeight();
            return new ImageInformation(orientation, width, height);
        } catch (MetadataException me) {
            //me.printStackTrace();
            return null;
        }
    }

    public static AffineTransform getExifTransformation(ImageInformation info) {

        AffineTransform t = new AffineTransform();
    
        switch (info.orientation) {
        case 1:
            break;
        case 2: // Flip X
            t.scale(-1.0, 1.0);
            t.translate(-info.width, 0);
            break;
        case 3: // PI rotation 
            t.translate(info.width, info.height);
            t.rotate(Math.PI);
            break;
        case 4: // Flip Y
            t.scale(1.0, -1.0);
            t.translate(0, -info.height);
            break;
        case 5: // - PI/2 and Flip X
            t.rotate(-Math.PI / 2);
            t.scale(-1.0, 1.0);
            break;
        case 6: // -PI/2 and -width
            t.translate(info.height, 0);
            t.rotate(Math.PI / 2);
            break;
        case 7: // PI/2 and Flip
            t.scale(-1.0, 1.0);
            t.translate(-info.height, 0);
            t.translate(0, info.width);
            t.rotate(  3 * Math.PI / 2);
            break;
        case 8: // PI / 2
            t.translate(0, info.width);
            t.rotate(  3 * Math.PI / 2);
            break;
        }
    
        return t;
    }

    public static BufferedImage transformImage(BufferedImage image, AffineTransform transform) throws Exception {

        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
    
        BufferedImage destinationImage = op.createCompatibleDestImage(image, (image.getType() == BufferedImage.TYPE_BYTE_GRAY) ? image.getColorModel() : null );
        Graphics2D g = destinationImage.createGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, destinationImage.getWidth(), destinationImage.getHeight());
        destinationImage = op.filter(image, destinationImage);
        return destinationImage;
    }

    public static BufferedImage createResizedImage(File file, int size) throws Exception {
        BufferedImage image = ImageIO.read(file);
        int rawWidth = image.getWidth();
        int rawHeight = image.getHeight();
        int width = 0;
        int height = 0;
        if (rawHeight < rawWidth) {
            width = size;
            float factor = (float) rawHeight / rawWidth;
            height = (int) (factor * width);
        } else {
            height = size;
            float factor = (float) rawWidth / rawHeight;
            width = (int) (factor * height);
        }

        BufferedImage temp = new BufferedImage(width, height, image.getType());
        Graphics2D g = temp.createGraphics();
        g.drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        g.dispose();

        AffineTransform t = null;
        ImageInformation info = readImageInformation(file);
        if (info != null) {
            info.width = width;
            info.height = height;
            System.out.println(info.toString());
            t = getExifTransformation(info);
            return transformImage(temp, t);
        } else {
            return temp;
        }
    }

}