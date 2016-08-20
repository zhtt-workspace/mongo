package zhtt.service.user;

import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

    /**
     * 保存机构
     * @param organization
     */
    public void save(Organization organization){
        dao.save(organization);
        if(organization.getCode().endsWith("00")){
            updateLeaf(organization.getParentId(),false);
        }
    }

    /**
     * 增加节点、删除子节点
     * 将父节点的 是否为叶子节点字段修改为false/true
     */
    public WriteResult updateLeaf(String uuid,boolean leaf){
        Query query=new Query();
        query.addCriteria(Criteria.where("uuid").is(uuid));
        WriteResult writeResult=dao.update(query,Update.update("leaf", leaf));
        return writeResult;
    }

    /**
     * 通过UUID获取机构
     * @param uuid
     * @return
     */
    public Organization getByUuid(String uuid){
        return dao.findOne(new Query(Criteria.where("uuid").is(uuid)));
    }

    /**
     * 根据parentId获取机构记录
     * @param uuid
     * @return
     */
    public List<Organization> getByParentId(String uuid){
        return dao.query(new Query(Criteria.where("parentId").is(uuid==null?"":uuid)));
    }

    /**
     * 获取根节点
     * @return
     */
    public Organization getRoot(){
        return dao.findOne(new Query(Criteria.where("parentId").is("-1")));
    }
}
