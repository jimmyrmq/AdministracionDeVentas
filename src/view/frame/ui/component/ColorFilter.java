package view.frame.ui.component;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ColorFilter {

    public static Color getColor(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        //System.out.print(r+" "+g+" "+b+" ");
        if(r == 0)
            r=50;
        if(g == 0)
            g=50;
        if(b == 0)
            b=50;
        r += (r*5)/100;
        g += (g*5)/100;
        b += (b*5)/100;

        if(r<0)
            r = 0;
        else if(r>255)
            r=255;

        if(g<0)
            g = 0;
        else if(g>255)
            g=255;

        if(b<0)
            b = 0;
        else if(b>255)
            b=255;

        //System.out.println(r+" "+g+" "+b+" ");
        Color rtn = new Color(r,g,b,color.getAlpha());

        return rtn;
    }

    public static BufferedImage filterImage(String fileName, Color color, boolean filterCol){

        boolean filter = color!=null;

        BufferedImage image = null;

        if(filter) {
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            File fime = new File(fileName);
            if (fime.exists()) {
                try {
                    image = ImageIO.read(fime);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (image != null) {
                    int alto = image.getHeight();
                    int ancho = image.getHeight();
                    //Color array[][] = new Color[ancho][alto];
                    Color col;
                    for (int y = 0; y < alto; y++) {
                        for (int x = 0; x < ancho; x++) {
                            int srcPixel = image.getRGB(x, y);
                            //int p = (col.getBlue()+col.getGreen()+col.getRed())/3;
                            //System.out.println(col.getAlpha());

                            int alpha = (srcPixel >> 24) & 0xff;
                        /*int red = (srcPixel >> 16) & 0xff;
                        int green = (srcPixel >> 8) & 0xff;
                        int blue = (srcPixel >> 0) & 0xff;

                       /*if(srcPixel!=0){
                           array[y][x]=new Color(255,255,255,1);
                       }else*/
                            if (filterCol)
                                col = getColor(new Color(red, green, blue, alpha));
                            else
                                col = new Color(red, green, blue, alpha);

                            image.setRGB(x, y, col.getRGB());
                        }
                    }

                }
            }
        }
        return image;
    }
}
