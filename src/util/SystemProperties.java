package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class SystemProperties {
    private Properties language= new Properties();
    private Properties system= new Properties();
    private static SystemProperties systemProperties;
    private String lang="es.properties";
    private String dirLang="lang/es.properties";

    public static SystemProperties getInstance(){
        if(systemProperties == null){
            systemProperties =new SystemProperties();
        }

        return systemProperties;
    }

    private SystemProperties(){
        load(language);
        load(system);
    }
    private void load(Properties properties){
        try {
            
            InputStream in = getClass().getClassLoader().getResourceAsStream(dirLang);//ClassLoader.getSystemResourceAsStream(dir);//

            if(in==null) {
                properties.load(new FileInputStream(dirLang));
            }else {
                BufferedReader input = new BufferedReader(new InputStreamReader(in, "ISO-8859-1"));//"UTF-8"));//
                properties.load(input);
            }
        } catch (FileNotFoundException exc) {
            System.err.println("Error FileNotFoundException SystemProperties : "+exc);
        } catch (IOException exc) {
            System.err.println("Error IOException SystemProperties : "+exc);
        }
    }

    public String getValue(String value){
        String val = language.getProperty(value,null);
        /*if(val!=null) {
            try {
                System.out.println(new String(val.getBytes("ISO-8859-1"), "UTF-8"));
            }catch (UnsupportedEncodingException exc){}
        }*/
        return val;
    }

    public String getValueSystem(String value){
        String val = system.getProperty(value,null);
        return val;
    }

    public void setValue(String key, String value){
        system.setProperty(key,value);
    }
    
    public void setLanguaje(String lang){
        this.lang = lang;
        this.dirLang="lang/"+lang;
    }
}

