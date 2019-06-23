package com.mn.lock.core;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component("zkLock")
public class ZookeeperDistributedLock
{
    @Autowired
    CuratorFramework zkClient;

    @Value("${zk.local.path:zk.lock.}")
    String zkLockPath;


    public boolean tryLock(String id)
    {
        try
        {
            String currentPath = zkClient.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(zkLockPath + "/" + id);
            System.out.println("--------->this current path :" + currentPath);
            List<String> nodes = zkClient.getChildren().forPath(zkLockPath);
            Collections.sort(nodes);
            //如果自己创建的是最小节点，则说明拿到锁
            if (currentPath.equals(zkLockPath + "/" + nodes.get(0)))
            {
                return true;
            }
            //如果自己创建的不是最小节点，则创建监听器去监听比自己小的节点
            else
            {
                int previousLockIndex = -1;
                for (int i = 0; i < nodes.size(); i++)
                {
                    if (currentPath.equals(zkLockPath + "/" + nodes.get(i)))
                    {
                        previousLockIndex = i - 1;
                    }
                }
                waiForLock(zkLockPath + "/" + nodes.get(previousLockIndex));
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }


    private void waiForLock(String beforePath)
    {
        CountDownLatch cdl = new CountDownLatch(1);
        System.out.println("对[" + beforePath + "]设置监听");
        NodeCache nodeCache = new NodeCache(zkClient, beforePath);
        //注册一个监听器
        try
        {
            nodeCache.start(true);
            nodeCache.getListenable().addListener(() -> {
                System.out.println("监听的节点已经删除，释放。。。。。");
                cdl.countDown();
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            if (zkClient.checkExists().forPath(beforePath) != null)
            {
                cdl.await();
                System.out.println("countDownLatch已经释放，，本线程不在被阻塞。。。");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                nodeCache.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void unlock(String currentPath)
    {
        try
        {
            zkClient.delete().guaranteed().deletingChildrenIfNeeded().forPath(currentPath);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void init()
    {
        try
        {
            zkClient.create().forPath(zkLockPath);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
