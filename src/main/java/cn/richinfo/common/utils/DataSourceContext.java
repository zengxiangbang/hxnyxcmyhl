package cn.richinfo.common.utils;


import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import redis.clients.jedis.JedisPool;

/**
 * 数据源上下午工具类
 */
public class DataSourceContext {
	private final static Logger logger=Logger.getLogger(DataSourceContext.class);

	private static JedisPool jedisPool;//jedis连接池

	public static Boolean setDataSource(ApplicationContext ctx) {
		try
		{
			logger.info("DataSourceContext.setDataSource success");
			if(jedisPool==null)
			{
				jedisPool = (JedisPool) ctx.getBean("jedisPool");
				logger.info("DataSourceContext.setJedisPool success");
			}
			return true;
		}
		catch(Exception e)
		{
			logger.error("DataSourceContext设置DataSource异常:"+e.getMessage(),e);
			return null;
		}
	}

	public static JedisPool getJedisPool()
	{
		return jedisPool;
	}
}
