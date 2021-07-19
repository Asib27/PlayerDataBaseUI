/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.util;

/**
 *
 * @author USER
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import static javafx.stage.FileChooser.ExtensionFilter;
import javax.imageio.ImageIO;

public class ImageUtil {
    public static void saveToFile(Image image) {
        // Ask the user for the file name
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image file name");
        fileChooser.setInitialFileName("untitled");
        ExtensionFilter pngExt = new ExtensionFilter("PNG Files", "*.png");
        ExtensionFilter jpgExt = new ExtensionFilter("JPEG Files", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().addAll(pngExt, jpgExt);
        
        File outputFile = fileChooser.showSaveDialog(null);
        if (outputFile == null) {
            return;
        }
        

        ExtensionFilter selectedExt = fileChooser.getSelectedExtensionFilter();
        String imageFormat = "png";
        
        if (selectedExt == jpgExt) {
            imageFormat = "jpg";
        }
        // Check for the file extension. Add oen, iff not specified
        String fileName = outputFile.getName().toLowerCase();

        switch (imageFormat) {
            case "jpg" -> {
                if (!fileName.endsWith(".jpeg") && !fileName.endsWith(".jpg")) {
                    outputFile = new File(outputFile.getParentFile(),
                            outputFile.getName() + ".jpg");
                }
             }
            case "png" -> {
                if (!fileName.endsWith(".png")) {
                    outputFile = new File(outputFile.getParentFile(),
                            outputFile.getName() + ".png");
                }       
            }
        }

        // Convert the image to a buffered image
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            // Save the image to the file
            boolean write = ImageIO.write(bImage, imageFormat, outputFile);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}

 
