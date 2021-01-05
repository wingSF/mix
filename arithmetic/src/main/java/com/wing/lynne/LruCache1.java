package com.wing.lynne;

import java.util.HashMap;
import java.util.Map;

/**
 * 设计LRU缓存结构
 * <p>
 * 该结构在构造时确定大小，假设大小为K，并有如下两个功能
 * set(key, value)：将记录(key, value)插入该结构
 * get(key)：返回key对应的value值
 * [要求]
 * set和get方法的时间复杂度为O(1)
 * 某个key的set或get操作一旦发生，认为这个key的记录成了最常使用的。
 * 当缓存的大小超过K时，移除最不经常使用的记录，即set或get最久远的。
 */
public class LruCache1 {

    private Object[] objectArray;
    private Map<Object, Integer> indexMap;
    private Map<Integer, Object> keyMap;

    private Integer putIndex;

    //使用一个字符串来记录访问顺序
    /**
     *  todo  3，33，333，223，233.....，这样一定是可以的，但是好low啊，所以写 {@link LruCache2}
      */
    private StringBuffer accessLog;

    public LruCache1(int k) {
        objectArray = new Object[k];
        putIndex = Integer.valueOf(0);
        indexMap = new HashMap((int) (k / 0.75));
        keyMap = new HashMap((int) (k / 0.75));
        accessLog = new StringBuffer();
    }

    public void put(Object k, Object value) {

        if (indexMap.containsKey(k)) {
            Integer index = indexMap.get(k);
            objectArray[index] = value;

            //新插入数据修改accessLog
        } else {

            //未达到上限时的put
            if (putIndex < objectArray.length) {
                indexMap.put(k, putIndex);
                keyMap.put(putIndex, k);
                objectArray[putIndex] = value;

                putIndex++;
            } else {

                //找到accesslog中的最后一次访问index，todo 待实现
                Integer lastIndex = 0;
                //删除旧数据
                Object key = keyMap.remove(lastIndex);
                indexMap.remove(key);

                indexMap.put(k, lastIndex);
                keyMap.put(putIndex, k);

                objectArray[lastIndex] = value;

                //重新写accessLog
            }
        }
    }

    public Object get(Object k) {
        Integer arrayIndex = indexMap.get(k);
        if (arrayIndex == null) {
            return -1;
        }

        //访问修改accessLog

        return objectArray[arrayIndex];
    }

}
