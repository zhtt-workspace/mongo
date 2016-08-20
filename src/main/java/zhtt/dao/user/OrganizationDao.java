package zhtt.dao.user;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import zhtt.entity.user.Organization;
import zhtt.service.util.TableConfig;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by zhtt on 2016/8/11.
 */
@Service
public class OrganizationDao {

    private final Class<Organization> $class=Organization.class;

    @Autowired
    MongoOperations mongoTemplate;

    /**
     * 保存
     * @param organization
     */
    public void save(Organization organization){
        mongoTemplate.save(organization, TableConfig.ORGANIZATION);
    }

    /**
     * 查询
     * @param query
     * @return
     */
    public Organization findOne(Query query){
        return mongoTemplate.findOne(query,$class, TableConfig.ORGANIZATION);
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
        WriteResult writeResult=mongoTemplate.updateFirst(query, update,$class, TableConfig.ORGANIZATION);
        return writeResult;
    }
    /**
     * 查询
     * @param query
     * @return
     */
    public List<Organization> query(Query query){
        return mongoTemplate.find(query,$class, TableConfig.ORGANIZATION);
    }

    public List<Organization> query(Query query,DBObject fieldsObject){
        return query(new BasicQuery(query.getQueryObject(),fieldsObject));
    }
}
