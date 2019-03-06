package cn.richinfo.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis工具类
 */
public class JedisUtils {

    private final static Logger logger = LoggerFactory.getLogger(JedisUtils.class);
    private final static int DEF_TIME = 30*60;
    private final static int ACTIVITY_TIME = 10*24*60*60;


    private static JedisPool jedisPool;

    static {
        try {
            if (jedisPool == null) {
                JedisPoolConfig config = new JedisPoolConfig();
                //最大连接数, 默认8个
                config.setMaxTotal(6500);
                //控制一个pool最多有多少个状态为idle(空闲)的jedis实例；
                config.setMaxIdle(200);
                //表示idle object evitor两次扫描之间要sleep的毫秒数；
                config.setTimeBetweenEvictionRunsMillis(30000);
                //表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义；
                config.setMinEvictableIdleTimeMillis(30000);
                //在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的；
                config.setTestOnBorrow(true);
                //表示当borrow一个jedis实例时，最大的等待时间
                config.setMaxWaitMillis(5000);

                jedisPool = new JedisPool(config, Global.redisip, Global.redisport, 60, Global.redispassword);

            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }


    public static JedisPool getJedisPool() {
        return jedisPool;
    }

    public static Jedis getJedis() {
        Jedis Jedis = jedisPool.getResource();
        Jedis.select(Global.redisdb);
        return Jedis;
    }

    public static void returnResource(Jedis jedis) {
        if (null != jedis) {
            jedisPool.returnResource(jedis);
        }
    }

    public static void returnBrokenResource(Jedis jedis) {
        if (null != jedis) {
            jedisPool.returnBrokenResource(jedis);
        }
    }

    public static String getValue(String key) {
        Jedis jedis = null;
        try {
            jedis = JedisUtils.getJedis();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("异常:JedisUtils-getValue,String-key:" + key + e.getMessage());
        } finally {
            JedisUtils.returnResource(jedis);
        }
        return null;
    }

    public static void setValue(String key, String value, int second) {
        Jedis jedis = null;
        try {
            jedis = JedisUtils.getJedis();
            jedis.setex(key, second <= 0 ? DEF_TIME : ACTIVITY_TIME, value);
        } catch (Exception e) {
            logger.error("异常:JedisUtils-setValue,String-key:" + key + ",String-value:" + value + ",second:" + second + e.getMessage());
        } finally {
            JedisUtils.returnResource(jedis);
        }
    }

    public static void setTimeValue(String key, String value, int second) {
        Jedis jedis = null;
        try {
            jedis = JedisUtils.getJedis();
            jedis.setex(key, second, value);
        } catch (Exception e) {
            logger.error("异常:JedisUtils-setValue,String-key:" + key + ",String-value:" + value + ",second:" + second + e.getMessage());
        } finally {
            JedisUtils.returnResource(jedis);
        }
    }

    public static Object getObject(String key) {
        Object result = null;
        Jedis jedis = null;
        try {
            jedis = JedisUtils.getJedis();
            if (jedis != null && jedis.exists(key.getBytes(Global.UTF8))) {
                result = SerializeUtil.unserialize(jedis.get(key.getBytes(Global.UTF8)));
            }
        } catch (Exception e) {
            logger.error("redis getObject exception  : ", e);
        } finally {
            JedisUtils.returnResource(jedis);
        }
        return result;
    }

    public static boolean setObject(String key, Object value, int second) {
        Jedis jedis = null;
        try {
            jedis = JedisUtils.getJedis();
            if (jedis != null) {
                jedis.setex(key.getBytes(Global.UTF8), second <= 0 ? DEF_TIME : ACTIVITY_TIME, SerializeUtil.serialize(value));
                return true;
            }
        } catch (Exception e) {
            logger.error("redis setObject key[" + key + "]exception: ", e);
        } finally {
            JedisUtils.returnResource(jedis);
        }
        return false;
    }
    public static boolean setTimeObject(String key, Object value, int second) {
        Jedis jedis = null;
        try {
            jedis = JedisUtils.getJedis();
            if (jedis != null) {
                jedis.setex(key.getBytes(Global.UTF8), second, SerializeUtil.serialize(value));
                return true;
            }
        } catch (Exception e) {
            logger.error("redis setObject key[" + key + "]exception: ", e);
        } finally {
            JedisUtils.returnResource(jedis);
        }
        return false;
    }
    public static void del(String key) {
        Jedis jedis = null;
        try {
            jedis = JedisUtils.getJedis();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
        } catch (Exception e) {
            logger.error("异常:JedisUtils-del,del-String-key:" + key + e.getMessage());
        } finally {
            JedisUtils.returnResource(jedis);
        }
    }

    public static void setex(String key, String value, int second) {
        Jedis jedis = null;
        try {
            jedis = JedisUtils.getJedis();
            jedis.setex(key, second, value);
        } catch (Exception e) {
            logger.error("异常:JedisUtils-setex,String-key:" + key + ",String-value:" + value + ",second:" + second + e.getMessage());
        } finally {
            JedisUtils.returnResource(jedis);
        }
    }
}
