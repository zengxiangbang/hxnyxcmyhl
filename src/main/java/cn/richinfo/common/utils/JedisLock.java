package cn.richinfo.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * 分布式同步锁
 */
public class JedisLock {

    private final Logger logger = LoggerFactory.getLogger(JedisLock.class);
    private Thread currentThread;
    private boolean locked = false;
    private String lockKey;
    /**
     * 过期时间，单位：秒
     */
    private int expires;

    public JedisLock(String lockKey) {
        this.lockKey = Global.projectCode + ":lock:" + lockKey;
        this.expires = 3;
    }

    /**
     * @param lockKey 锁
     * @param expires 超时时间，单位：秒
     */
    public JedisLock(String lockKey, int expires) {
        this.lockKey = Global.projectCode + ":lock:" + lockKey;
        this.expires = expires;
    }

    public boolean lock() {
        Jedis jedis = null;
        try {
            int timeout = 10000;
            jedis = JedisUtils.getJedis();
            while (timeout > 0) {
                long nowTime = System.currentTimeMillis();
                String expiresStr = String.valueOf(nowTime + expires * 1000);
                if (jedis.setnx(lockKey, expiresStr) == 1) {
                    jedis.expire(lockKey, expires);
                    locked = true;
                    currentThread = Thread.currentThread();
                    logger.info("获取redis分布式同步锁。线程id：{}，线程名：{}", currentThread.getId(), currentThread.getName());
                    return true;
                }
                // redis里的时间
                String currentValueStr = jedis.get(lockKey);
                if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {//表明已经超时了，原来的线程可能可能出现意外未能及时释放锁
                    String oldValueStr = jedis.getSet(lockKey, expiresStr);
                    //为什么会有下面这个判断呢？因为多线程情况下可能同时有多个线程在这一时刻发现锁过期，那么就会同时执行getSet获取锁操作，
                    //通过下面的比较，可以找到第一个执行getSet操作的线程，让其获得锁，其它的线程则重试
                    if (currentValueStr.equals(oldValueStr)) {
                        jedis.expire(lockKey, expires);
                        locked = true;
                        currentThread = Thread.currentThread();
                        logger.info("获取redis分布式同步锁。线程id：{}，线程名：{}", currentThread.getId(), currentThread.getName());
                        return true;
                    }
                }
                timeout -= 100;
                Thread.sleep(100);
            }
        } catch (Exception e) {
            logger.error("lock. redis上锁异常，{}", e.getMessage());
        } finally {
            JedisUtils.returnResource(jedis);
        }
        return false;
    }

    public void unlock() {
        Jedis jedis = null;
        try {
            if (currentThread != Thread.currentThread()) {
                return;
            }
            jedis = JedisUtils.getJedis();
            jedis.del(lockKey);
            locked = false;
            logger.info("释放redis分布式同步锁。线程id：{}，线程名：{}", currentThread.getId(), currentThread.getName());
            currentThread = null;
        } catch (Exception e) {
            logger.error("unlock. redis解锁异常，{}", e.getMessage());
        } finally {
            JedisUtils.returnResource(jedis);
        }
    }

    public boolean isLocked() {
        return locked;
    }
}
