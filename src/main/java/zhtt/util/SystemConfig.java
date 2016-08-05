package zhtt.util;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by zhtt on 2016/8/5.
 */
public class SystemConfig {

    private static String systemname="Spring MongoDB平台";

    public static void init(){
        try{
            Properties properties = new Properties();
            File file= ResourceUtils.getFile("classpath:" + File.separator + "application.properties");
            FileInputStream in = new FileInputStream(file);
            properties.load(in);
            in.close();

            systemname=properties.getProperty("systemname");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
