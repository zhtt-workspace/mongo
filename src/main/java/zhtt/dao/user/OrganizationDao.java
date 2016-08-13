package zhtt.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import zhtt.entity.user.Organization;
import zhtt.service.util.TableConfig;

import java.util.List;

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

    public Organization findOne(Query query){
        return mongoTemplate.findOne(query,$class, TableConfig.ORGANIZATION);
    }

    public List<Organization> query(Query query){
        return mongoTemplate.find(query,$class, TableConfig.ORGANIZATION);
    }


}
