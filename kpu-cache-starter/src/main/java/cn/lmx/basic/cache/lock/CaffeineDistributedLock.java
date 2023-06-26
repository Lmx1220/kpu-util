package cn.lmx.basic.cache.lock;


import cn.lmx.basic.lock.DistributedLock;

/**
 * @author lmx
 * @version 1.0
 * @description: 分布式锁 只能用redis实现 写这个类的目的，只是为了防止代码启动报错
 * @date 2023/7/4 14:27
 */
public class CaffeineDistributedLock implements DistributedLock {
    @Override
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
        return true;
    }

    @Override
    public boolean releaseLock(String key) {
        return true;
    }
}
