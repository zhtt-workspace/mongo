package zhtt.dao.user;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import zhtt.entity.user.Organization;
import zhtt.dao.TableConfig;

import java.util.List;

/**
 * Created by zhtt on 2016/8/11.
 */
@Service
public class OrganizationDao {

    private final Class<Organization> $class=Organization.class;

    private final String  table=TableConfig.ORGANIZATION;

    @Autowired
    MongoOperations mongoTemplate;

    /**
     * 保存
     * @param organization
     */
    public void save(Organization organization){
        mongoTemplate.save(organization, table);
    }

    /**
     * 查询
     * @param query
     * @return
     */
    public Organization findOne(Query query){
        return mongoTemplate.findOne(query,$class, table);
    }

    /**
     * 更新
     * @param organization
     * @return
     */
    public WriteResult update(Organization organization){
        Query query=new Query();
        query.addCriteria(Criteria.where("uuid").is(organization.getUuid()));
        WriteResult writeResult=update(query, organization.toUpdate());
        return writeResult;
    }

    /**
     * 更新
     * @param query
     * @param update
     * @return
     */
    public WriteResult update(Query query,Update update){
        WriteResult writeResult=mongoTemplate.updateFirst(query, update, $class, table);
        return writeResult;
    }

    /**
     * 查询
     * @param query
     * @return
     */
    public List<Organization> query(Query query){
        return mongoTemplate.find(query,$class, table);
    }

    /**
     * 查询机构，指定需要返回的字段
     * @param query
     * @param fieldsObject
     * @return
     */
    public List<Organization> query(Query query,DBObject fieldsObject){
        return query(new BasicQuery(query.getQueryObject(), fieldsObject));
    }

    /**
     * 删除机构
     * @param uuid
     */
    public void delete(List<String> uuid){
        mongoTemplate.remove(new Query(Criteria.where("uuid").in(uuid)),$class, table);
    }

    public long count(Query query){
        return mongoTemplate.count(query,table);
    }

    public List<BasicDBObject> group(DBObject key, DBObject filterCond, DBObject initialCode,  String finalizer){
        DBCollection collection=mongoTemplate.getCollection(table);
        return (List<BasicDBObject>)collection.group(key, filterCond, initialCode,  finalizer);
    }
}
