package zhtt.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import org.apache.commons.io.IOUtils;
import zhtt.service.util.MongoCollectionsManager;

import java.io.*;
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

    /**
     * 得到obj对应的Java类同一目录下的JavaScript文件，并将之转换成字符串
     *
     * js文件必须和obj类在同一个目录下
     * js文件是UTF8编码的
     *
     * @param obj
     * @param fileName javascipt文件名
     * @return
     */
    public static String getJavaScricptFunctionFromFile(Object obj, String fileName) {
        InputStream js = obj.getClass().getResourceAsStream(fileName);
        StringWriter stringWriter = new StringWriter();
        String reduce = null;
        try {
            IOUtils.copy(js, stringWriter, "UTF-8");
            reduce = stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return reduce;
    }


    /**
     * 得到obj对应的Java类同一目录下的JavaScript文件，并将之转换成字符串
     *
     * js文件必须和obj类在同一个目录下
     * js文件是UTF8编码的
     *
     * @param fileName javascipt文件名
     * @return
     */
    public static String getJavaScricptFunctionFromFile(String fileName) {
        InputStream js = FileUtil.class.getClass().getResourceAsStream(fileName);
        StringWriter stringWriter = new StringWriter();
        String reduce = null;
        try {
            IOUtils.copy(js, stringWriter, "UTF-8");
            reduce = stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return reduce;
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
