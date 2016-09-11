package zhtt.service.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhtt.service.util.DataStatisticsTemplateManager;

/**
 * Created by zhtt on 2016/9/10.
 * 本表单负责增删改
 */
@Service
public class DataStatisticsTemplateFormService {

    @Autowired
    private DataStatisticsTemplateManager dataStatisticsTemplateManager;
}
