package com.jamie.framework.controller;

import com.jamie.framework.lock.zookeeper.ZookeeperCuratorFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lizheng
 * @date: 15:46 2019/10/30
 * @Description: TestController
 */
@RestController
@RequestMapping("/test")
public class TestController {
    /*@Autowired
    private RedisDcsLock redisDcsLock;

    @RequestMapping("/lock1")
    public String lock1() throws InterruptedException {
        redisDcsLock.acquire("test");
        Thread.sleep(5000);
        redisDcsLock.release("test");
        return  "lock1";
    }

    @RequestMapping("/lock2")
    public String lock2() throws InterruptedException {
        redisDcsLock.acquire("test");
        Thread.sleep(1000);
        redisDcsLock.release("test");
        return  "lock1";
    }


    private int a = 0;

    @RequestMapping("/lock3")
    public int lock3() throws InterruptedException {
        redisDcsLock.acquire("lock3");
        if (a < 200) {
            Thread.sleep(500);
            a++;
        }
        System.out.println(a);
        redisDcsLock.release("lock3");
        return  a;
    }

    @RequestMapping("/lock3Clear")
    public int lock3Clear() throws InterruptedException {
        a = 0;
        return  a;
    }*/

    @Autowired
    ZookeeperCuratorFactory zookeeperCuratorFactory;
    @RequestMapping("/zookeeper")
    public String zookeeper() throws Exception {
        CuratorFramework curatorFramework = zookeeperCuratorFactory.getCuratorFramework();
        curatorFramework.start();
        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/123");
        zookeeperCuratorFactory.close(curatorFramework);
        return "123";
    }

}
