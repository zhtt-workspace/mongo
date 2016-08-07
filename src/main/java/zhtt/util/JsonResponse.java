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

    public JsonResponse(Object data) {
        this.data = data;
    }

    public JsonResponse(JsonResponseStatusEnum status, String message) {
        this.status = status.toString();
        this.message = message;
    }

    public JsonResponse(JsonResponseStatusEnum status, String message, Object data) {
        this.status = status.toString();
        this.message = message;
        this.data = data;
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
