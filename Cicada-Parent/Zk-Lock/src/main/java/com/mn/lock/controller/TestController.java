package com.mn.lock.controller;

import com.mn.lock.core.ZookeeperDistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController
{
    @Autowired
    ZookeeperDistributedLock zookeeperDistributedLock;

    @GetMapping("lock")
    public String getLock()
    {
        zookeeperDistributedLock.tryLock("1");
        return "OK";
    }

    @GetMapping("unlock")
    public String unlock(@RequestParam("path") String path)
    {
        zookeeperDistributedLock.unlock(path);
        return "OK";
    }

    @GetMapping("init")
    public String init()
    {
        zookeeperDistributedLock.init();
        return "OK";
    }
}
