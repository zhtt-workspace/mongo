package zhtt.util;

import java.io.Serializable;

/**
 * Created by zhtt on 2016/8/6.
 */
public class JsonResponse implements Serializable {

    /**
     * 状态
     */
    private String status = JsonResponseStatusEnum.SUCCESS.toString();

    /**
     * 消息
     */
    private String message = "";

    /**
     * 数据
     */
    private Object data = null;

    public JsonResponse() {
    }

    public static JsonResponse success(Object data) {
        JsonResponse jsonResponse=new JsonResponse();
        jsonResponse.setData(data);
        return jsonResponse;
    }

    public static JsonResponse error(String message) {
        JsonResponse jsonResponse=new JsonResponse();
        jsonResponse.setMessage(message);
        jsonResponse.setStatus(JsonResponseStatusEnum.ERROR.toString());
        return jsonResponse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
