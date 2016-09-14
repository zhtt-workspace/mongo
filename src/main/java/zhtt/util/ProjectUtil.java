package zhtt.util;

import java.util.UUID;

/**
 * Created by zhtt on 2016/9/13.
 */
public class ProjectUtil {

    public static String randomUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
