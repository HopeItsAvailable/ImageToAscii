import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class imagesToAscii {


    public static void main(String[] args) {
        try {

        char[] asciiCode = new char[] { 'Ñ', '@', '#', 'W', '$', '9', '8', '7', '6', '5', '4', '3', '2', '1', '0', '?','!','a', 'b', 'c', ';', ':', '+', '=', '-', ',', '.', '_', '\'' };
        BufferedImage image = ImageIO.read(new File("emily1.jpeg"));;

        int newWidth = 1000;
        //int newWidth = 80;
        int newHeight = (image.getHeight() * newWidth) / image.getWidth();
        
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        resizedImage.getGraphics().drawImage(image.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH), 0, 0, null);


        // read Image
        //resizedImage = ImageIO.read(new File("phone.jpeg")); // new buffered image to process image
            
            FileWriter fw = new FileWriter("ascii_art.txt");
            toAscii(resizedImage, fw);
            fw.close();
        } catch (IOException e) {
            // print exception error
        
            
            System.out.println(e.getMessage());
        }

    }

    private static void toGreyscale(BufferedImage img) {
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int pixel = img.getRGB(x, y); // get RGB values for each pixel

                // a,r,g, or b values anded with 1 to store them to avg later
                int a = (pixel >> 24) & 0xff;
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;

                // calculate avg
                int gray = (r + g + b) / 3;

                // replace rgb values with avg to convert to greyscale
                pixel = (a << 24) | (gray << 16) | (gray << 8) | gray;

                img.setRGB(x, y, pixel);
            }
        }
    }

    private static void toAscii(BufferedImage img, FileWriter fw) {
        char[] asciiCode = new char[] { 'Ñ', '@', '#', 'W', '$', '9', '8', '7', '6', '5', '4', '3', '2', '1', '0', '?','!','a', 'b', 'c', ';', ':', '+', '=', '-', ',', '.', '_', '\'' };

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {

                int pixel = img.getRGB(x, y); // get RGB values for each pixel

                // a,r,g, or b values anded with 1 to store them to avg later
                int a = (pixel >> 24) & 0xff;
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
                //System.out.print(asciiCode[index]);


            }
            try {
                fw.write(System.lineSeparator());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
