package zhtt.entity.templeate;

/**
 * Created by zhtt on 2016/9/14.
 */
public class DataStatisticsTemplate {

    /**
     * 数据库中作为标记为删除的字段,某些数据，因为有其他数据相关联的原因不方便直接删除，所以做标记删除
     *
     */
    public static final String DELETE_MAEK = "dismissed";

    /**
     * 节点的类型对应的值
     */
    public class Type{
        /**
         * 根节点
         */
        public static final String ROOT="root";
        /**
         * 分组节点
         */
        public static final String GROUP="group";
        /**
         * 数据项节点
         */
        public static final String FIELD="field";
        /**
         * 本机构汇总下级后的数据
         */
        public static final String org="org";
        /**
         * 本机构内部的数据
         */
        public static final String headquarters="headquarters";
    }

    /**
     * 每天需要录入的数据
     */
    public class DataKey{
        /**
         * 数据类型：本机构内部的、本机构汇总的
         */
        public static final String dataType="dataType";
        /**
         * 哪一天的数据
         */
        public static final String date="date";
        /**
         * 数据的创建时间
         */
        public static final String datetime="datetime";
        /**
         * 数据的所属机构
         */
        public static final String orgId="orgId";
        /**
         * 数据的所属机构
         */
        public static final String orgName="orgName";
        /**
         * 数据的创建机构（有可能上级为下级创建）
         */
        public static final String createOrgId="createOrgId";
        /**
         * 数据的接收机构：下级汇总上报的数据、本级内部数据
         */
        public static final String receiveOrgId="receiveOrgId";
        /**
         * 数据的统计数字
         */
        public static final String content="content";
        /**
         * 数据的上报状态
         */
        public static final String reportState="reportState";
        /**
         * 数据的下发状态（有可能上级为下级创建或修改）
         */
        public static final String issueState="issueState";
    }

    public class DocTree{
        /**
         * 本模板的主键（由于_id为ObjectId，使用起来不方便)
         */
        public static final String uuid="uuid";
        /**
         * 本模板的的所属机构（机构为根机构或分机构）
         */
        public static final String orgId="orgId";
        /**
         * node_id
         */
        public static final String node_id="node_id";
        /**
         * node_id
         */
        public static final String datetime="datetime";
        /**
         * node_id
         */
        public static final String children="children";
    }

    /**
     * 根节点的字段
     */
    public class RootKey{
        /**
         * 创建时间
         */
        public static final String datetime="datetime";
        /**
         * 用于描述节点类型
         */
        public static final String type="type";
        /**
         * 本模板的总列数
         */
        public static final String colspan="colspan";
        /**
         * 本模板的名称
         */
        public static final String name="name";
        /**
         * 本模板的的所属机构（机构为根机构或分机构）
         */
        public static final String orgId="orgId";
        /**
         * 本模板的主键（由于_id为ObjectId，使用起来不方便)
         */
        public static final String uuid="uuid";
    }

    /**
     * 分组节点的字段
     */
    public class GroupKey{
        /**
         * 创建时间
         */
        public static final String datetime="datetime";
        /**
         * 用于描述节点类型
         */
        public static final String type="type";
        /**
         * 本字段所占列数
         */
        public static final String colspan="colspan";
        /**
         * 本列的名称
         */
        public static final String name="name";
        /**
         * 本列的父节点uuid
         */
        public static final String parentId="parentId";
        /**
         * 本列的的所属机构（机构为根机构或分机构）
         */
        public static final String orgId="orgId";
        /**
         * 本列的主键（由于_id为ObjectId，使用起来不方便)
         */
        public static final String uuid="uuid";
    }

    /**
     * 数据项节点的字段
     */
    public class FieldKey{
        /**
         * 创建时间
         */
        public static final String datetime="datetime";
        /**
         * 用于描述节点类型
         */
        public static final String type="type";
        /**
         * 本字段所占列数
         */
        public static final String colspan="colspan";
        /**
         * 本列的名称
         */
        public static final String name="name";
        /**
         * 本列的父节点uuid
         */
        public static final String parentId="parentId";
        /**
         * 本列的的所属机构（机构为根机构或分机构）
         */
        public static final String orgId="orgId";
        /**
         * 本列的主键（由于_id为ObjectId，使用起来不方便)
         */
        public static final String uuid="uuid";
        /**
         * 计量单位
         */
        public static final String unit="unit";
        /**
         * 小数位数
         */
        public static final String decimalDigits="decimalDigits";
        /**
         * 最大值
         */
        public static final String maxNumber="maxNumber";
        /**
         * 最小值
         */
        public static final String minNumber="minNumber";
        /**
         * 阀值提醒
         */
        public static final String beyondRemind="beyondRemind";
    }
}
