package com.wing.lynne.controller;

import com.wing.lynne.po.User;
import org.redisson.api.RLiveObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.CountDownLatch;

@Controller
@RequestMapping(value = "/wing")
public class UserController {

    private static String rid = System.currentTimeMillis() + "";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    @Autowired
    private RLiveObjectService rLiveObjectService;

    @RequestMapping("/add")
    public void addUser(String username, int age, String address) {

        User newUser = User.builder()
                .name(username)
                .age(age)
                .address(address)
                .build();

        newUser.setRedisKey(rid);

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
        user.setAddress(System.currentTimeMillis() + "");

    }

}
