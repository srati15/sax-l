package context;

import manager.DaoManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener()
public class ContextListener implements ServletContextListener{

    public void contextInitialized(ServletContextEvent sce) {
      sce.getServletContext().setAttribute("manager", new DaoManager());
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
