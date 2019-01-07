package com.wing.lynne;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 假设一个背包中能放下最大 M 单位的物品
 * 提供一个length为 L 的数组，数组中每个元素表示 N 单位的物品
 * 求最大程度填充背包的方案
 */
public class FillPackage {

    public static void main(String[] args) {
        int max = 30;
        int[] array = new int[10];

        Map<String, Integer> result = findMaxResult(max, array);

        for (Map.Entry entry : result.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }

    }

    private static Map<String, Integer> findMaxResult(int max, int[] array) {
        Map<String, Integer> result = new HashMap<>();

        int maxResult = 0;

        //先排序，便于后续计算
        Arrays.sort(array);

        for (int i = 0; i < array.length; i++) {

            int[] number = findMaxResultWithCount(max, array, i);

            StringBuilder currentKey = new StringBuilder();
            int currentValue = 0;

            for (int j = 0; j < number.length; j++) {
                currentKey.append("+");
                currentKey.append(number[j]);

                currentValue += number[j];
            }

            String currentKeyString = currentKey.toString().replaceFirst("\\+", "");

            result.put(currentKeyString, currentValue);

            maxResult = maxResult > currentValue ? maxResult : currentValue;
        }

        //过滤保留最大的value值

        return result;
    }


    /**
     * 找到固定个数的最大组合
     *
     * @param max   最大值
     * @param array 参考数据
     * @param i     元素个数
     * @return 返回个数为i的最大值组合
     */
    private static int[] findMaxResultWithCount(int max, int[] array, int i) {

        while (true) {
            for (int j = 0; j < i; j++) {

            }
        }

    }
}
