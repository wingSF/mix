package com.wing.lynne.controller;

import com.google.common.collect.Sets;
import com.wing.lynne.po.User;
import lombok.Builder;
import lombok.Data;
import org.redisson.api.RLiveObjectService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value = "/wing")
public class UserController {

    private static String rid = System.currentTimeMillis() + "";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private RLiveObjectService rLiveObjectService;
    private RedissonClient redissionClient;

    @Autowired
    public UserController(RLiveObjectService rLiveObjectService, RedissonClient redissionClient) {
        this.rLiveObjectService = rLiveObjectService;
        this.redissionClient = redissionClient;
    }

    @RequestMapping("/add")
    public void addUser(String username, int age, String address) {

        Set<String> tagSet = Sets.newHashSet();
        tagSet.add("aa");
        tagSet.add("bb");
        tagSet.add("cc");
        tagSet.add("dd");
        tagSet.add("ee");

        User newUser = User.builder()
                .redisKey(rid)
                .name(username)
                .age(age)
                .address(address)
                .tags(tagSet)
                .build();

        //此处注意，获取到的对象已经不再是原生User对象了
        User persist = rLiveObjectService.persist(newUser);

        //从缓存中读取到的数据也不再是原生User对象
        User cacheUser = rLiveObjectService.get(User.class, rid);

        cacheUser.setName("wing");
        cacheUser.setRedisKey(rid);

        //由于cacheUser不再是原生User类型，所以没有redisKey这个字段，再去调用save方法，会保存失败
        //而且这样的操作完全于live object思想不一致，结合下面的find updateCache可以正确理解实时对象
//        rLiveObjectService.persist(cacheUser);
        System.out.println(cacheUser);
    }


    /*
     *
     * 通过add方法新增live object之后
     *
     * 先调用find 再调用updateCache 再调用find
     * 观察address字段的变化
     *
     * 结论：live object模式下，对象的属性值的变更会被别的线程发现
     *
     * 根据{@link https://github.com/redisson/redisson/wiki/9.-%E5%88%86%E5%B8%83%E5%BC%8F%E6%9C%8D%E5%8A%A1#92-%E5%88%86%E5%B8%83%E5%BC%8F%E5%AE%9E%E6%97%B6%E5%AF%B9%E8%B1%A1live-object%E6%9C%8D%E5%8A%A1}
     * 官网的描述，这个变更是原子的
     *
     * 打开地址，ctrl+f "鉴于Redis是一个单线程的程序"
     *
     * */

    @RequestMapping("/find")
    @ResponseBody
    public User findUser() {

        User user = rLiveObjectService.get(User.class, rid);
        System.out.println(user);
        return user;
    }

    @RequestMapping("/updateCache")
    @ResponseBody
    public void updateCache() {

        User user = rLiveObjectService.get(User.class, rid);

        user.getTags().remove("aa");
        user.setAddress(System.currentTimeMillis() + "");

    }

    @RequestMapping("/getDistributedLock")
    public void getDistributedLock() {

        RLock rLock = redissionClient.getLock("wing");

        boolean tryLockResult = false;

        try {

            tryLockResult = rLock.tryLock(5, 20, TimeUnit.SECONDS);

            if(tryLockResult){
                TimeUnit.SECONDS.sleep(10);
//                TimeUnit.SECONDS.sleep(30);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()+" lock result "+tryLockResult);

    }

    @RequestMapping("/sendLiveObject")
    public void sendLiveObject(String rid) throws InterruptedException {

        //查询live object
        User user = rLiveObjectService.get(User.class, rid);
        //封装到另外一个对象里面
        UserHelper userHelperBuilder = UserHelper.builder().user(user).build();
        //传输到队列中
        LinkedBlockingQueue<UserHelper> queue = new LinkedBlockingQueue();
        queue.offer(userHelperBuilder);
        //从队列中取出来
        UserHelper userHelper = queue.take();

        userHelper.getUser().setName("lynne");
        //read属性
        System.out.println(userHelper.getUser());

    }


    @Data
    @Builder
    static class UserHelper{
        User user;
    }
}
