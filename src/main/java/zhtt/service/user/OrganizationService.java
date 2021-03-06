package zhtt.service.user;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import zhtt.dao.user.OrganizationDao;
import zhtt.entity.user.Organization;
import zhtt.util.FileUtil;
import zhtt.util.JsonResponse;
import zhtt.util.JsonResponseStatusEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
            return JsonResponse.error("编码已用尽，最多支持100个子节点，请重新调整当前结构！");
        }else{
            organization.setCode(newCode);
            dao.save(organization);
            if(organization.getCode().endsWith("00")){
                updateLeaf(organization.getParentId(),false);
            }
            return JsonResponse.success(organization);
        }
    }

    /**
     * 更新机构
     * @param organization
     * @return
     */
    public JsonResponse update(Organization organization){
        Query query=new Query();
        query.addCriteria(Criteria.where("uuid").is(organization.getUuid()));
        WriteResult writeResult=dao.update(query,organization.toUpdate());
        return  JsonResponse.success(organization);
    }

    /**
     * 删除机构
     * @param uuid
     */
    public void delete(List<String> uuid){
        dao.delete(uuid);
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
     *
     * @param code
     * @param parentId
     * @param name
     * @param limit
     * @param skip
     * @return
     */
    public List<Organization> query(String code,String parentId,String name,int limit,int skip){
        Query query=new Query();
        query.addCriteria(Criteria.where("name").regex(name));
        if(parentId==null||"".equals(parentId)){
            if(!(code==null||"".equals(code))){
                query.addCriteria(Criteria.where("code").regex(code));
            }
        }else{
            query.addCriteria(Criteria.where("parentId").is(parentId));
        }
        query.limit(limit);
        query.skip(skip);
        /** 排序 **/
        Sort.Direction direction=true? Sort.Direction.ASC: Sort.Direction.DESC;
        query.with(new Sort(direction,"sort"));
        return dao.query(query);
    }

    /**
     * 查询总数
     * @param code
     * @param parentId
     * @param name
     * @return
     */
    public long count(String code,String parentId,String name){
        Query query=new Query();
        query.addCriteria(Criteria.where("name").regex(".*"+name+".*"));
        if(parentId==null||"".equals(parentId)){
            if(!(code==null||"".equals(code))){
                query.addCriteria(Criteria.where("code").regex(code));
            }
        }else{
            query.addCriteria(Criteria.where("parentId").is(parentId));
        }
        return dao.count(query);
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

    public void regTest(){
        String code="00";
        Query query=new Query(Criteria.where("code").regex(code));

        Query query1 =new BasicQuery("{code: {$regex : '^" + code + ".{2}$'} }");

        Pattern pattern = Pattern.compile("^"+code+".{2}$");//, Pattern.CASE_INSENSITIVE
        Criteria criteria = new Criteria("code").regex(pattern.toString());
        Query query2=new Query(criteria);

        List<Organization> orgList=dao.query(query1);
        for(Organization org:orgList){
            System.out.println("org.code:"+org.getCode());
        }
    }

    /**
     * 获取当前机构的根节点
     * @param code
     * @return
     */
    public BasicDBObject getCurrentRootOrg(String code){
        try{
            String finalizer = FileUtil.getJavaScricptFunctionFromFile(this, "/js/org-getCurrentRootOrg.js");
            DBObject key = new BasicDBObject("orgType", true);
            List<String> orgTypeList=new ArrayList<String>();
            orgTypeList.add(Organization.ROOT);
            orgTypeList.add(Organization.ORG);
            DBObject filter = new BasicDBObject("$in", orgTypeList);
            DBObject filterCond = new BasicDBObject("orgType", filter);
            DBObject initialCode = new BasicDBObject("code",code);
            List<BasicDBObject> dbObjectList =  dao.group(key, filterCond, initialCode, finalizer);
            if(dbObjectList.size()==2){
                BasicDBObject org1=dbObjectList.get(0);
                BasicDBObject org2=dbObjectList.get(1);
                String code1=org1.getString("rootParentCode");
                String code2=org2.getString("rootParentCode");
                if(code1==null&&code2!=null){
                    return org2;
                }else if(code2==null&&code1!=null){
                    return org1;
                }else if(code2!=null&&code1!=null){
                    if(code2.length()>code1.length()){
                        return org2;
                    }else{
                        return org1;
                    }
                }
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Organization> queryJuniorOrgNameAndUuidList(String parentId,List<String> uuidList){
        Query query =new Query();
        query.addCriteria(Criteria.where("parentId").is(parentId));
        query.addCriteria(Criteria.where("orgType").is(Organization.ORG));
        if(uuidList!=null&&uuidList.size()>0){
            query.addCriteria(Criteria.where("uuid").nin(uuidList));
        }
        BasicDBObject fieldsObject=new BasicDBObject();
        fieldsObject.put("uuid", 1);
        fieldsObject.put("name", 1);
        List<Organization> orgList=dao.query(query,fieldsObject);
        return orgList;
    }

}
