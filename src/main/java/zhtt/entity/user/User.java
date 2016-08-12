package zhtt.entity.user;

import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zhtt on 2016/8/5.
 */
public class User {

    /**
     * 主键
     */
    private ObjectId _id;

    /**
     * uuid
     */
    private String uuid= UUID.randomUUID().toString();

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private int age;

    /**
     * 创建时间
     */
    private Date createTime=new Date();

    /**
     * 所属机构
     */
    private String orgId;

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

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "{USERS:{_id:"+this._id+", name:"+this.name+",age:"+this.age+"}}";
    }

    public Update toUpdate(){
        BasicDBObject set=new BasicDBObject();
        BasicDBObject value=new BasicDBObject();
        if(this.name!=null){
            value.put("name",name);
        }/*
        if(this.username!=null){
            basicDBObject.put("$set", new BasicDBObject("username",username));
        }*/
        if(this.password!=null){
            value.put("password", password);
        }
        if(this.orgId!=null){
            value.put("orgId", orgId);
        }
        if(this.age!=0){
            value.put("age", age);
        }
        set.put("$set", value);
        Update update=new BasicUpdate(set);
        return update;
    }
}

