import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class imagesToAscii {

    public static char[] asciiCode = new char[] { '\'', '_', '.', ',', '-', '=', '+', ':', ';', 'c', 'b', 'a', '!', '?', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '$', 'W', '#', '@', 'Ñ' };
    private static final double ASPECT_RATIO = 2.0;
    
    public static void main(String[] args) {
        try {

        Scanner scanner = new Scanner(System.in);

        //get filePath
        System.out.println("Please enter the image file path (e.g., emily1.jpeg): ");
        String filePath = scanner.nextLine().trim();

        BufferedImage image = ImageIO.read(new File(filePath));
        
        //get orientation
        System.out.println("Is the image in landscape or portrait mode? (Enter 'L' for landscape, 'P' for portrait): ");
        String input = scanner.nextLine().trim().toUpperCase();
        
        // Rotate the image if it's in portrait mode
        if (input.equals("P")) {
            System.out.println("Portrait image detected. Rotating...");
            image = rotateImage(image); 
        } else {
            System.out.println("Image is already in landscape mode.");
        }
        
        System.out.println("How many pixels wide would you like the image to be? (1000 recommended)");
        int newWidth = scanner.nextInt();

        //calculate aspect ratio and newHeight
        double aspectRatio = (double) image.getWidth() / image.getHeight();
        int newHeight = (int) (newWidth / (aspectRatio * 1.55));  
        
        //Making new image template
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        resizedImage.getGraphics().drawImage(image.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH), 0, 0, null);
            
        String fileName = "ascii_art.txt";
        FileWriter fw = new FileWriter(fileName);
        toAscii(resizedImage, fw);
        System.out.println("Success! File written to: " + fileName);
            fw.close();
        } catch (IOException e) {
            // print exception error
        
            
            System.out.println(e.getMessage());
        }

    }

    private static BufferedImage rotateImage(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage rotatedImage = new BufferedImage(height, width, img.getType());
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                rotatedImage.setRGB(height - 1 - y, x, img.getRGB(x, y));
            }
        }
        return rotatedImage;
    }

    private static void toAscii(BufferedImage img, FileWriter fw) {

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                int pixel = img.getRGB(x, y); // get RGB values for each pixel

                // a,r,g, or b values anded with 1 to store them to avg later
                int a = (pixel >> 24) & 0xff; //0xff = 1111 1111
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;

                // calculate avg
                int gray = (r + g + b) / 3;
                
                int index = (gray * (asciiCode.length - 1)) / 255;
                try {
                    fw.write(asciiCode[index]);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

            }
            //goTo next line
            try {
                fw.write(System.lineSeparator());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        
    }

}
