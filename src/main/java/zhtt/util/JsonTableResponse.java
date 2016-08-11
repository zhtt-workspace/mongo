package zhtt.util;

import java.util.List;

/**
 * Created by zhtt on 2016/8/7.
 */
public class JsonTableResponse {

    /**
     * 总数
     */
    private long total;

    /**
     * 状态
     */
    private String status = JsonResponseStatusEnum.SUCCESS.toString();

    /**
     * 消息
     */
    private String message = "";

    /**
     * 数据集合
     */
    private List<?> rows;

    /**
     * 成功的时候指定总条数及数据列表
     * @param total
     * @param rows
     */
    public JsonTableResponse(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    /**
     * 失败的时候说明原因
     * @param message
     */
    public JsonTableResponse(String message) {
        this.status=JsonResponseStatusEnum.ERROR.toString();
        this.message = message;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
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

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
