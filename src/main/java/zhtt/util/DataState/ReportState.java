package zhtt.util.DataState;

/**
 * Created by zhtt on 2016/9/19.
 * 数据的上报状态
 */
public enum ReportState {
    /**
     * 未上报
     */
    noreport(0),

    /**
     * 上报中
     */
    reporting(1),

    /**
     * 已上报
     */
    reported(2);

    private final int state;

    ReportState(int state) {
        this.state = state;
    }
}
