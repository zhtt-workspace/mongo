package zhtt.service.user;

import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import zhtt.dao.user.OrganizationDao;
import zhtt.entity.user.Organization;
import zhtt.entity.user.User;
import zhtt.service.util.TableConfig;
import zhtt.util.JsonResponse;
import zhtt.util.JsonResponseStatusEnum;

import java.util.ArrayList;
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
    public JsonResponse save(Organization organization){
        String newCode=buildOrgCode(organization.getCode(),organization.getParentId());
        if(newCode==null){
            return new JsonResponse(JsonResponseStatusEnum.ERROR,"编码已用尽，最多支持100个子节点，请重新调整当前结构！");
        }else{
            organization.setCode(newCode);
            dao.save(organization);
            if(organization.getCode().endsWith("00")){
                updateLeaf(organization.getParentId(),false);
            }
            return new JsonResponse(organization);
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
     * 检查此code是否存在，如果存在则生成一个新code
     * @param code
     * @return
     */
    public String buildOrgCode(String code,String parentId){
        if(code.endsWith("00")){
            return code;
        }else{
            Query query =new Query();
            query.addCriteria(Criteria.where("parentId").is(parentId));
            BasicDBObject fieldsObject=new BasicDBObject();
            fieldsObject.put("code", 1);
            fieldsObject.put("sort", 1);
            List<Organization> orgList=dao.query(query,fieldsObject);
            List<String> codeList=new ArrayList<String>();
            for(Organization org:orgList){
                codeList.add(org.getCode());
            }
            if(codeList.contains(code)){
                String parentCode=code.substring(0,code.length()-2);
                int childCount=codeList.size();
                for(int i=childCount;i<100;i++){
                    String newCode=""+parentCode+(childCount>9?childCount:"0"+childCount);
                    if(codeList.contains(newCode)){
                        continue;
                    }else{
                        return newCode;
                    }
                }
                for(int i=0;i<childCount;i++){
                    String newCode=""+parentCode+(childCount>9?childCount:"0"+childCount);
                    if(codeList.contains(newCode)){
                        continue;
                    }else{
                        return newCode;
                    }
                }
            }else{
                return code;
            }
            return null;
        }
    }
}
