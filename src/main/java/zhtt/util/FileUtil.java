package zhtt.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import zhtt.service.util.MongoCollectionsManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by zhtt on 2016/8/29.
 */
public class FileUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String getLocalFileLineStr(String path) throws IOException{
        InputStream is =FileUtil.class.getResourceAsStream(path);
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer=new StringBuffer();
        String str="";
        while((str=bufferedReader.readLine())!=null){
            buffer.append(str+"\n");
        }
        return buffer.toString();
    }

    public static List<BasicDBObject> readBasicDBObjectsFrom(String jsonFileName) {
        List<BasicDBObject> res = null;
        InputStream is = MongoCollectionsManager.class.getResourceAsStream(jsonFileName);
        try {
            res = mapper.readValue(is, new TypeReference<List<BasicDBObject>>(){});
        }
        catch (JsonParseException e) {
            throw new RuntimeException(e);
        }
        catch (JsonMappingException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return res;
    }

}
