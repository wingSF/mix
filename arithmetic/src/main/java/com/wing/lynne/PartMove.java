package com.wing.lynne;

import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * 要求空间复杂度o(1)，时间复杂度o(n)
 */

public class PartMove {

    public static void main(String[] args) {

        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int k = 9;
        partMove(array, k);

        System.out.println(Arrays.toString(array));

    }

    private static void partMove(int[] array, int k) {

        //o1的中间变量
        int temp = 0;

        //按照k，分段对数据进行移动
        for (int i = 0; i < array.length; i++) {
            temp = array[i];
            array[i] = array[i % k];
            array[i % k] = temp;
        }

        //对于前k个元素，完成最后移动，向前移动 array.length%k 次
        for (int i = 0; i < array.length % k; i++) {
            partArrayMove(array, k);
        }
    }

    /**
     * 对与给定的数组，将前k个数据，向前进行循环移动
     * @param array
     * @param k
     */
    private static void partArrayMove(int[] array, int k) {
        int temp = 0;
        for (int i = 0; i < k; i++) {

            //第一个元素保存在temp中
            if(i==0){
                temp = array[i];
                array[i]=array[i+1];
            //最后一个元素，用temp覆盖，完成向前移动
            }else if(i==k-1){
                array[i]=temp;
            //中间部分，后项覆盖前一项
            }else{
                array[i]=array[i+1];
            }
        }

    }
}
