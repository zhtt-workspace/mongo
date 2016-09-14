package zhtt.entity.templeate;

/**
 * Created by zhtt on 2016/9/14.
 */
public class DataStatisticsTemplate {

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
