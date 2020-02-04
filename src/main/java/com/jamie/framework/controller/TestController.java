package com.jamie.framework.controller;

import com.jamie.framework.accesslimit.annotation.RateAccessLimit;
import com.jamie.framework.accesslimit.annotation.RedisAccessLimit;
import com.jamie.framework.bean.User;
import com.jamie.framework.datasource.DynamicDataSourceContextHolder;
import com.jamie.framework.lock.zookeeper.ZookeeperCuratorFactory;
import com.jamie.framework.service.UserServiceI;
import com.jamie.framework.service.impl.TestService;
import com.jamie.framework.util.api.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lizheng
 * @date: 15:46 2019/10/30
 * @Description: TestController
 */
@RestController
@RequestMapping("/test")
@Api(value = "/TestController", description = "测试使用Controller")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("test1")
    @ApiOperation(
            value = "测试一",
            notes = "测试一说明",
            response = ApiResult.class)
    public ApiResult test1() {
        return ApiResult.ok(testService.test1());
    }

    @RequestMapping("test2")
    public ApiResult test2() {
        return ApiResult.ok(testService.test2());
    }

    @RedisAccessLimit
    @RequestMapping("testRedisAccessLimit")
    public ApiResult testRedisAccessLimit() {
        return ApiResult.ok("testRedisAccessLimit");
    }

    @RateAccessLimit(qps = 1)
    @RequestMapping(value = "testRateAccessLimit")
    public ApiResult testRateAccessLimit() {
        return ApiResult.ok("testRateAccessLimit");
    }

    @RateAccessLimit(qps = 3)
    @RequestMapping(value = "testRateAccessLimit2")
    public ApiResult testRateAccessLimit2() {
        return ApiResult.ok("testRateAccessLimit2");
    }

    @Autowired
    UserServiceI userServiceI;

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "transactionalTest")
    public ApiResult transactionalTest() {
        User user = new User();
        user.setName("Tx test1");
        user.setAddr("1111");
        user.setAge(18);
        userServiceI.save(user);
        User user2 = new User();
        user2.setName("Tx test2");
        user2.setAddr("22222");
        user2.setAge(22);
        // 测试异常
        int a = 1 / 0;
        DynamicDataSourceContextHolder.setDataSourceType("slave1");
        userServiceI.save(user2);
        DynamicDataSourceContextHolder.clearDataSourceType();

        return ApiResult.ok();
    }

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
