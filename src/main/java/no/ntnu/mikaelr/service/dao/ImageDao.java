package no.ntnu.mikaelr.service.dao;

import no.ntnu.mikaelr.util.Constants;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ImageDao {

    // TODO: Error handling
    public String uploadImage(byte[] byteArray) {

        try {

            File imageFolder = new File("images");

            if (imageFolder.mkdir()) {
                System.out.println("Image folder was created");
            }

            String imageName = generateString(20);

            int resultWidth = 500;
            int resultHeight = 500;

            ByteArrayInputStream highResInputStream = new ByteArrayInputStream(byteArray);
            BufferedImage inputImage = ImageIO.read(highResInputStream);

            int width = inputImage.getWidth();
            int height = inputImage.getHeight();

            // Scale in respect to width or height?
            Scalr.Mode scaleMode = Scalr.Mode.AUTOMATIC;

            // find out which side is the shortest
            int maxSize = 0;
            if (height > width) {
                // scale to width
                scaleMode = Scalr.Mode.FIT_TO_WIDTH;
                maxSize = resultWidth;
            } else if (width >= height) {
                scaleMode = Scalr.Mode.FIT_TO_HEIGHT;
                maxSize = resultHeight;
            }

            BufferedImage highResImage = Scalr.resize(inputImage, Scalr.Method.AUTOMATIC, scaleMode, maxSize);
            String highResImagePath = "images/" + imageName + ".jpg";
            File highResOutputFile = new File(highResImagePath);
            ImageIO.write(highResImage, "jpg", highResOutputFile);

            ByteArrayInputStream lowResInputStream = new ByteArrayInputStream(byteArray);
            BufferedImage lowResImage = Scalr.resize(ImageIO.read(lowResInputStream), Scalr.Method.QUALITY, scaleMode, maxSize/2);
            String lowResImagePath = "images/" + imageName + "_thumbnail.jpg";
            File lowResOutputFile = new File(lowResImagePath);
            ImageIO.write(lowResImage, "jpg", lowResOutputFile);

            return Constants.SERVER_ADDRESS + highResImagePath;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private String generateString(int length) {

        Random random = new Random();
        String characters = "abcdefghijklmnopqrstuvwqyz";

        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }

}
