package view.frame.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FontHelper {
    public FontHelper(){}

    public Font font(float f){
        return font("Segoe UI",f);
    }

    public Font font(String fnt,float f){
        String ur = "font/"+fnt+".ttf";
        InputStream is = this.getClass().getResourceAsStream(ur);
        Font font = null;//new Font("Tahoma",0,(int)f);
        if(is==null){
            File ff = new File(ur);
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, ff);//new File("font/"+fnt+".ttf"));
            } catch (IOException | FontFormatException exc) {
                System.out.println("Error Font: " + exc);
            }
        }
        else{
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, is);//new File("font/"+fnt+".ttf"));
            } catch (IOException | FontFormatException exc) {
                System.out.println("Error Font: " + exc);
            }
        }
        font = font.deriveFont(f);

        return font;
    }
}
