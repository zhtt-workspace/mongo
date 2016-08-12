package zhtt.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import zhtt.entity.user.Organization;
import zhtt.entity.user.User;
import zhtt.service.util.TableConfig;

/**
 * Created by zhtt on 2016/8/11.
 */
@Service(value = "organizationService")
public class OrganizationService {

    private final Class<User> $class=User.class;

    @Autowired
    MongoOperations mongoTemplate;

    public void save(Organization organization){
        mongoTemplate.save(organization, TableConfig.ORGANIZATION);
    }
}
