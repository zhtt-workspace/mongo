package zhtt.entity.user;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zhtt on 2016/8/5.
 */
public class Organization {

    /**
     * 主键
     */
    private ObjectId _id;

    /**
     * uuid
     */
    private String uuid= UUID.randomUUID().toString();

    /**
     * 编码，每一层级占用两位，用于like查询
     */
    private String code;

    /**
     * 机构名称
     */
    private String name;

    /**
     * 机构全称
     */
    private String fullName;

    /**
     * 父机构ID
     */
    private String parentId;

    /**
     * 排序
     */
    private int sort;

    /**
     * 类型
     * 每一层的根机构：-1
     * 每一层的代表机构：0
     */
    private int orgType=-1;

    /**
     * 创建时间
     */
    private Date createTime=new Date();

    /**
     * 叶子节点
     */
    private boolean leaf;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getOrgType() {
        return orgType;
    }

    public void setOrgType(int orgType) {
        this.orgType = orgType;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
