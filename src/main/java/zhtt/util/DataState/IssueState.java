package zhtt.util.DataState;

/**
 * Created by zhtt on 2016/9/19.
 * 数据的上报状态
 */
public enum IssueState {
    /**
     * 未上报
     */
    noissue(0),

    /**
     * 上报中
     */
    issueing(1),

    /**
     * 已上报
     */
    issueed(2);

    private final int state;

    IssueState(int state) {
        this.state = state;
    }

    public static String getValueString(IssueState state){
        switch (state) {
            case noissue:
                return "noissue";
            case issueing:
                return "issueing";
            case issueed:
                return "issueed";
            default:
                throw new RuntimeException("没有值: " + state);
        }
    }
}
