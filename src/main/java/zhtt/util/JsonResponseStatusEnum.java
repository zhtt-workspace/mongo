package zhtt.util;

/**
 * Created by zhtt on 2016/8/6.
 */
public enum JsonResponseStatusEnum {

    SUCCESS("success"), ERROR("error");

    private final String text;

    JsonResponseStatusEnum(String str) {
        this.text = str;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
