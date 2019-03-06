package cn.richinfo.common.listener;

import cn.richinfo.common.utils.Global;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

public class Log4jInit implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(Log4jInit.class);

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Log4jInit contextDestroyed!");
    }
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        // 得到servletContext对象的方法
        ServletContext sc = sce.getServletContext();
//        System.out.println(sc.getRealPath("/"));
        // 启动服务器的时候加载日志的配置文件
        String log4jConfigLocation = sc.getInitParameter("log4jConfigLocation");

        //方式一：
        InputStream istream = null;
        try {
            istream = Log4jInit.class.getResourceAsStream(log4jConfigLocation);
        } finally {
            if (istream != null) {
                init(istream, sc);
                try {
                    istream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        //方式二：
        // 指明文件的相对路径就能够得到文件的绝对路径
//        String path = sc.getRealPath("WEB-INF/classes/log4js.properties");
//        try {
//            init(new FileInputStream(path), sc);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        logger.info("log4j init successfull,{}", new java.util.Date());
        logger.info("log4jConfigLocation: {}", log4jConfigLocation);
        logger.info("projectCode: {}", Global.projectCode);
    }

    /**
     * @param istream 配置文件的路径输入流
     * @param sc      ServletContext对象
     */
    public void init(InputStream istream, ServletContext sc) {
//        FileInputStream istream = null;
        try {
            Properties props = new Properties();
            // 加载配置文件
//            istream = new FileInputStream(path);
            props.load(istream);

//            String logpath_public = props.getProperty("log4j.appender.public.File");
            String logpath_behaviorlog = props.getProperty("log4j.appender.behaviorlog.File");

            props.remove("log4j.appender.behaviorlog.File");
//            props.remove("log4j.appender.public.File");

            InetAddress addr = null;
            String port = "";
            MBeanServer mBeanServer = null;
            ArrayList<MBeanServer> mBeanServers = MBeanServerFactory.findMBeanServer(null);
            if (mBeanServers.size() > 0) {
                for (MBeanServer _mBeanServer : mBeanServers) {
                    mBeanServer = _mBeanServer;
                    break;
                }
            }
            if (mBeanServer == null) {
                throw new IllegalStateException("没有发现JVM中关联的MBeanServer.");
            }
            Set<ObjectName> objectNames = null;
            try {

                objectNames = mBeanServer.queryNames(new ObjectName("Catalina:type=Connector,*"), null);
            } catch (MalformedObjectNameException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if (objectNames == null || objectNames.size() <= 0) {
                throw new IllegalStateException(
                        "没有发现JVM中关联的MBeanServer : " + mBeanServer.getDefaultDomain() + " 中的对象名称.");
            }
            try {
                for (ObjectName objectName : objectNames) {
                    String protocol = (String) mBeanServer.getAttribute(objectName, "protocol");
                    if (protocol.equals("HTTP/1.1")) {
                        port = mBeanServer.getAttribute(objectName, "port").toString();

                    }
                    // String scheme = (String)
                    // mBeanServer.getAttribute(objectName,
                    // "scheme");
                    // int port = (Integer) mBeanServer.getAttribute(objectName,
                    // "port");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


//            logpath_public = logpath_public.replace("{projectcode}", Global.projectCode);
            logpath_behaviorlog = logpath_behaviorlog.replace("{projectcode}", Global.projectCode).replace("{port}", port);

//            props.put("log4j.appender.public.File", logpath_public);
            props.put("log4j.appender.behaviorlog.File", logpath_behaviorlog);
            // 加载文件流，加载Log4j文件的配置文件信息

            PropertyConfigurator.configure(props);
//            log.info("log.path_public:" + logpath_public);
            logger.info("log.path_behaviorlog: {}", logpath_behaviorlog);
        } catch (Exception ex) {
            try {
                throw new Exception(ex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            try {
                istream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
