package com.mn.lock;

import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * @author maning
 * @date 2019-06-25
 * redis作分布式锁的缺点：
 * （1）需要自己不断去尝试获取锁，比较消耗性能；
 * （2）如果redis为主从部署，有可能从节点还没来得及从主同步，主挂了，从顶上，
 * 那么从节点没有设同步到数据，会出现多人拿到锁
 * （3）如果是redis获取锁的那个客户端bug了或者挂了，那么只能等待超时时间之后才能释放锁
 */
public class RedisLockUtils
{
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * key，我们使用key来当锁，因为key是唯一的。
     * value，我们传的是requestId，比较requestId的原因为只能释放自己的锁，不可以释放别人的锁。
     * requestId可以使用UUID.randomUUID().toString()方法生成。
     * 第三个参数为nxxx，这个参数我们填的是NX，意思是SET IF NOT EXIST，即当key不存在时可以设置成功，
     * 第四个为expx，这个参数我们传的是PX，意思是我们要给这个key加一个过期的设置，
     * 第五个为time，与第四个参数相呼应，代表key的过期时间。
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, long expireTime)
    {
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        if (LOCK_SUCCESS.equals(result))
        {
            return true;
        }
        return false;
    }

    /**
     * 释放分布式锁
     * 首先获取锁对应的value值，检查是否与requestId相等，
     * 如果相等则解锁，比较requestId的原因为只能释放自己的锁，不可以释放别人的锁。
     * 使用Lua语言来实现的原因是要确保上述操作是原子性的。
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId)
    {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result))
        {
            return true;
        }
        return false;

    }

}
