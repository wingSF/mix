package com.wing.lynne.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.reflect.TypeToken;
import com.wing.lynne.po.User;
import com.wing.lynne.po.temp.ConfigDetailInfo;
import com.wing.lynne.po.temp.OneLayerCheckItem;
import com.wing.lynne.po.temp.TaskInfo;
import com.wing.lynne.util.JsonUtil;
import lombok.Builder;
import lombok.Data;
import org.redisson.api.RLiveObjectService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

            if (tryLockResult) {
                TimeUnit.SECONDS.sleep(10);
//                TimeUnit.SECONDS.sleep(30);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " lock result " + tryLockResult);

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

    /**
     * 为了复现问题，可以参考{@link UserController#testProbleam(String)}方法注释
     */
    @RequestMapping("makeProbleam")
    public void makeProbleam() {

        TaskInfo.TaskColumnInfo column = TaskInfo.TaskColumnInfo.builder()
                .columnTitle("dafdafafd")
                .queryColumnName("adfafdafdafa")
                .build();


        ConfigDetailInfo.CalculateConfigInfo calculateConfigInfo = ConfigDetailInfo.CalculateConfigInfo.builder().build();
        calculateConfigInfo.setColumnId(1231);
        calculateConfigInfo.setColumnAsName("columnInfo.getColumnAsName()");
        calculateConfigInfo.setColumnTitle("columnInfo.getColumnTitle()");
        calculateConfigInfo.setLeftColumnAsName("configSet.getLeftOp()");
        calculateConfigInfo.setOperator((byte) 1);
        calculateConfigInfo.setRightColumnAsName("configSet.getRightOp()");
        calculateConfigInfo.setRatioType(3);
        calculateConfigInfo.setRatioPeriod(1);


        ConfigDetailInfo.ConfigColumnInfo info = ConfigDetailInfo.ConfigColumnInfo.builder()
                .columnId(1212)
                .columnAsName("configColumn.getColumnAsName()")
                .columnTitle("configColumn.getColumnTitle()")
                .group(false)
                .build();

        ConfigDetailInfo.ConfigSourceInfo configSourceInfo = ConfigDetailInfo.ConfigSourceInfo.builder().build();
        configSourceInfo.setTables(new ArrayList<>());
        OneLayerCheckItem tableCheck = OneLayerCheckItem.builder()
                .id(1231)
                .name("table.getTableName()")
                .build();
        configSourceInfo.getTables().add(tableCheck);
        configSourceInfo.getTables().add(tableCheck);
        configSourceInfo.getTables().add(tableCheck);
        configSourceInfo.getTables().add(tableCheck);


        List<ConfigDetailInfo.ConfigTimeFilterInfo> timeFilterInfoList = Lists.newArrayList();
        ConfigDetailInfo.ConfigTimeFilterInfo dafadfa = ConfigDetailInfo.ConfigTimeFilterInfo.builder()
                .timeFilterType((byte) 1)
                .timePatternType((byte) 1)
                .timeReplaceStr("configTimeClause.getStartReplaceStr() + SEMI_COLON + configTimeClause.getEndReplaceStr()")
                .timePatternStr("configTimeClause.getPatternStr()")
                .customValue(1)
                .build();
        timeFilterInfoList.add(dafadfa);
        timeFilterInfoList.add(dafadfa);
        timeFilterInfoList.add(dafadfa);
        timeFilterInfoList.add(dafadfa);
        timeFilterInfoList.add(dafadfa);
        timeFilterInfoList.add(dafadfa);
        timeFilterInfoList.add(dafadfa);
        timeFilterInfoList.add(dafadfa);

        ConfigDetailInfo.SqlConfigInfo sqlConfigInfo = ConfigDetailInfo.SqlConfigInfo.builder().build();
        sqlConfigInfo.setSourceList(Arrays.asList(configSourceInfo));
        sqlConfigInfo.setSql("sqlsfadafnfeanfiuaheuiaiuvbauibvauibveaibveakbvlkdjbaljkvbdkljsabvdkjabvjkdasbvkbasjkvbalkjbdvkabvdkjabvdkjabvkjlbakjdvbakjbvdajkbvjkabvkjdbavjkbdjkabvkdjabvdjkalbvjkabdjkablvjklabjkvbjdbjsakv");
        sqlConfigInfo.setConfigColumns(Arrays.asList(info, info, info, info, info));
        sqlConfigInfo.setConfigTimeFilters(timeFilterInfoList);


        ConfigDetailInfo.CompositeFieldInfo compositeFieldInfo = ConfigDetailInfo.CompositeFieldInfo.builder().build();
        compositeFieldInfo.setColumnId(121231);
        compositeFieldInfo.setColumnAsName("columnInfo.getColumnAsName()");
        compositeFieldInfo.setColumnTitle("columnInfo.getColumnTitle()");

        ConfigDetailInfo.ConfigSetInfo setInfo = ConfigDetailInfo.ConfigSetInfo.builder()
                .setId(1231)
                .type((byte) 1)
                .calculateConfig(calculateConfigInfo)
                .sqlConfig(sqlConfigInfo)
                .compositeFieldInfo(compositeFieldInfo)
                .build();

        TaskInfo.TaskTimeFilterInfo timeFilterInfo = TaskInfo.TaskTimeFilterInfo.builder()
                .filterType(1)
                .patternType(1)
                .timePattern("filterInfo.getTimePatternStr()")
                .startReplaceStr("startStr")
                .endReplaceStr("endStr")
                .customValue(3)
                .build();

        TaskInfo.QueryTaskConfigInfo queryTaskConfigInfo = TaskInfo.QueryTaskConfigInfo.builder()
                .columns(Arrays.asList(column, column, column, column, column, column, column, column, column))
                .groupColumns(Arrays.asList(column, column, column, column, column, column, column, column, column))
                .querySql("dafdafdadfafadfafdasdfafafafafashfuahfeuhafheuafhueahfleuafaheaufdafdafdadfafadfafdasdfafafafafashfuahfeuhafheuafhueahfleuafaheaufdafdafdadfafadfafdasdfafafafafashfuahfeuhafheuafhueahfleuafaheaufdafdafdadfafadfafdasdfafafafafashfuahfeuhafheuafhueahfleuafaheaufdafdafdadfafadfafdasdfafafafafashfuahfeuhafheuafhueahfleuafaheaufdafdafdadfafadfafdasdfafafafafashfuahfeuhafheuafhueahfleuafaheaufdafdafdadfafadfafdasdfafafafafashfuahfeuhafheuafhueahfleuafaheaufdafdafdadfafadfafdasdfafafafafashfuahfeuhafheuafhueahfleuafaheaufdafdafdadfafadfafdasdfafafafafashfuahfeuhafheuafhueahfleuafaheaufdafdafdadfafadfafdasdfafafafafashfuahfeuhafheuafhueahfleuafaheaufdafdafdadfafadfafdasdfafafafafashfuahfeuhafheuafhueahfleuafaheaufdafdafdadfafadfafdasdfafafafafashfuahfeuhafheuafhueahfleuafaheauf")
                .sourceDatabaseId(13)
                .sourceTables(Arrays.asList("ahhhaa", "adafdaf", "afdafda"))
                .timeFilters(Arrays.asList(timeFilterInfo, timeFilterInfo, timeFilterInfo, timeFilterInfo, timeFilterInfo))
                .build();


        TaskInfo.ConfigInfo taskConfig = TaskInfo.ConfigInfo.builder()
                .type(1)
                .queryTaskInfo(queryTaskConfigInfo)
                .build();

        TaskInfo.BasicInfo basicInfo = TaskInfo.BasicInfo.builder()
                .startTime(System.currentTimeMillis())
                .endTime(System.currentTimeMillis())
                .saveDatabaseId(114)
                .saveTableName("dafdaf")
                .timeGrain(1)
                .configInfo(taskConfig)
                .formId(114)
                .build();


        TaskInfo taskInfo = TaskInfo.builder()
                .redisKey(rid)
                .basicInfo(JsonUtil.toJson(basicInfo))
                .uniqueCode("unqadfa")
                .userId(33)
                .taskType((byte) 1)
                .stage(3)
                .status(2)
                .priority(1)
                .invokeTaskList(Lists.newArrayList())
                .taskTraceLogId(12312)
                .errorLog("")
                .startMillis(System.currentTimeMillis())
                .completeSubTask(Lists.newArrayList())
                .build();

        TaskInfo persist = rLiveObjectService.persist(taskInfo);


        System.out.println(persist);
    }


    /**
     * 结合{@link UserController#makeProbleam()}，再辅以三味真火，哦不辅以 ./redis-cli 执行monitor观察，可以发现redissionLiveObject的代码，每次get都是网络请求
     *
     * 技术没有好坏，取决于场景和使用的他的人！人！人！
     *
     * @param rid
     * @throws InterruptedException
     */
    @RequestMapping("testProbleam")
    public void testProbleam(String rid) throws InterruptedException {
        TaskInfo taskInfo = rLiveObjectService.get(TaskInfo.class, rid);

        TimeUnit.SECONDS.sleep(3);

//        CountDownLatch countDownLatch = new CountDownLatch(1);

//        for (int i = 0; i < 50; i++) {
//            new Thread(() -> {
//                try {
//                    countDownLatch.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {

            String taskInfoJson = JsonUtil.toJson(taskInfo);

            TimeUnit.SECONDS.sleep(3);

            Type type = new TypeToken<HashMap<String, Object>>() {
            }.getType();
            Map<String, Object> taskInfoMap = JsonUtil.fromJson(taskInfoJson, type);
            TimeUnit.SECONDS.sleep(3);
        }


        for (int i = 0; i < 10; i++) {

            TaskInfo.BasicInfo basicInfo = JsonUtil.fromJson(taskInfo.getBasicInfo(), TaskInfo.BasicInfo.class);


            System.out.println(basicInfo);
            TimeUnit.SECONDS.sleep(3);

        }


        for (int i = 0; i < 10; i++) {

            String basicInfo = taskInfo.getBasicInfo();

            System.out.println(basicInfo);

            TimeUnit.SECONDS.sleep(3);

            taskInfo.getRedisKey();

            TimeUnit.SECONDS.sleep(1);
            taskInfo.getErrorLog();

            TimeUnit.SECONDS.sleep(1);

        }


        long end = System.currentTimeMillis();

        System.out.println(Thread.currentThread().getName() + " cost " + (end - start));

//            }).start();

//        }

//        TimeUnit.SECONDS.sleep(5);
//
//        countDownLatch.countDown();


    }


    @Data
    @Builder
    static class UserHelper {
        User user;
    }
}
