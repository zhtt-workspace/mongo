package zhtt.util;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by zhtt on 2016/8/5.
 */
public class ApplicationInitializeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        WebApplicationContext applicationContext = getApplicationContext(event);
        try {
            SystemConfig.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    private WebApplicationContext getApplicationContext(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        return applicationContext;
    }
}
