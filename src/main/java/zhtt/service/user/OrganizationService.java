package zhtt.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import zhtt.dao.user.OrganizationDao;
import zhtt.entity.user.Organization;
import zhtt.entity.user.User;
import zhtt.service.util.TableConfig;

import java.util.List;

/**
 * Created by zhtt on 2016/8/11.
 */
@Service(value = "organizationService")
public class OrganizationService {

    @Autowired
    private OrganizationDao dao;

    public void save(Organization organization){
        dao.save(organization);
    }

    public Organization getByUuid(String uuid){
        return dao.findOne(new Query(Criteria.where("uuid").is(uuid)));
    }

    public List<Organization> getByParentId(String uuid){
        return dao.query(new Query(Criteria.where("parentId").is(uuid)));
    }

    public Organization getRoot(){
        return dao.findOne(new Query(Criteria.where("parentId").is(null)));
    }
}
