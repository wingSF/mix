package com.wing.lynne;

import java.util.Arrays;


public class QuickSort {

    private static int[] array = {20, 11, 110, 4, 6, 9, 77, 2, 6, 55, 3, 4, 5, 200};


    /**
     * 第一个版本的方法，易懂，但是新new了数组，会有空间占用
     * 1.数组切分的时候，生成的新数据长度需要指定为原长度-1，注意俩个都需要指定该长度
     * 2.数组元素聚合的时候，要先把标兵的值拿出来，因为聚合过程中，可能会被覆盖
     */
    private static void quickSortFirst(int[] array) {

        //1.特殊数组的处理
        if (array == null || array.length == 0 || array.length == 1) {
            return;
        }

        //2.获取分段中间索引以及索引位的数值
        int length = array.length;
        int flag = length / 2;
        int flagNumber = array[flag];

        //3.初始化新数组存放截断产生的新数组
        int[] biggerArray = new int[length - 1];
        int biggerArrayIndex = 0;
        int[] smallerArray = new int[length - 1];
        int smallerArrayIndex = 0;

        //4.遍历旧数组，给新初始化的数组赋值
        for (int i = 0; i < length; i++) {
            if (i == flag) {
                continue;
            }

            if (array[i] > array[flag]) {
                biggerArray[biggerArrayIndex] = array[i];
                biggerArrayIndex++;
            } else {
                smallerArray[smallerArrayIndex] = array[i];
                smallerArrayIndex++;
            }
        }

        //5.对新产生的数组进行截取，将0位全部丢弃
        biggerArray = Arrays.copyOfRange(biggerArray, 0, biggerArrayIndex);
        smallerArray = Arrays.copyOfRange(smallerArray, 0, smallerArrayIndex);

        //6.对新产生的数组进行递归处理
        if (biggerArray.length != 0) {
            quickSortFirst(biggerArray);
        }
        if (smallerArray.length != 0) {
            quickSortFirst(smallerArray);
        }

        //7.对老数据重新进行赋值操作
        for (int i = 0; i < smallerArray.length; i++) {
            array[i] = smallerArray[i];
        }
        array[smallerArray.length] = flagNumber;
        for (int i = 0; i < biggerArray.length; i++) {
            array[i + smallerArray.length + 1] = biggerArray[i];
        }

        return;
    }

    /**
     * 第二个版本的方法
     * 1.数组切分的时候，生成的新数据长度需要指定为原长度-1，注意俩个都需要指定该长度
     * 2.数组元素聚合的时候，要先把标兵的值拿出来，因为聚合过程中，可能会被覆盖
     * 具体分析请查看第三个版本的方法注释中的分析
     */
    private static void quickSortSecond(int[] array, int start, int end) {

        //1.简单校验
        //1.1特殊数组的处理
        if (array == null || array.length == 0 || array.length == 1) {
            return;
        }
        //1.2特殊边界值的处理
        if (end == start) {
            return;
        }

        //2.设置标兵为start位的数据
        int currentIndex = start;
        //3.设置标记位，用于记录比标兵大的元素有多少个
        int biggerNumber = 0;

        //4.遍历start+1到end中的数据，与标兵比较
        for (int i = start + 1; i < end + 1; i++) {

            //5.如果后面有比标兵小的数据
            if (array[i] < array[currentIndex]) {

                //5.1判断是否检测到有比标兵大的数据
                if (biggerNumber > 0) {
                    //5.1.1如果有 分四步走
                    //第一步：取出当前位置的数据
                    int smallerNumber = array[i];
                    //第二步：将标兵的后一位数字替换当前位置
                    array[i] = array[currentIndex + 1];
                    //第三步：用标兵替换标兵的后一位
                    array[currentIndex + 1] = array[currentIndex];
                    //第四部：将当前位置的数据，写到之前标兵的所在位置
                    array[currentIndex] = smallerNumber;
                } else {
                    //5.1.2如果没有，标兵与当前元素交换位置，传统三步走
                    int temp = array[i];
                    array[i] = array[currentIndex];
                    array[currentIndex] = temp;
                }

                //5.2标兵的所在位置变更
                currentIndex++;

            } else {//6.如果当前元素比标兵大，记录比标兵大的个数标志+1

                biggerNumber++;
            }
        }

        //7.如果currentIndex=start，排后面的；如果currentIndex=end，排前面的；如果在中间的，分俩段排
        if (currentIndex == start) {

            quickSortSecond(array, start + 1, end);

        } else if (currentIndex == end) {

            quickSortSecond(array, start, end - 1);

        } else {

            quickSortSecond(array, start, currentIndex);
            quickSortSecond(array, currentIndex + 1, end);

        }

        return;
    }

    /**
     * 第三个版本的方法
     * 基于第二个版本进行改进
     * 第二个版本中，每次数据交换会二个或者三个数据，且三个数据的情况居多，会有性能浪费
     * 本次改进主要位置为数据交换时的操作步骤
     *
     * 排序核心
     *     第一个版本的排序思路
     *         根据标兵，将数组中的其它元素，分为俩组，一组比标兵大，一组比标兵小
     *     第二个版本的排序思路
     *         由于第一个版本中，分为俩组的时候，产生了新的数组占用，需要优化，即不再new数组
     *         考虑在一个数组遍历中，通过交换实现分组的效果，即最终达到[比标兵小的n个数，标兵，比标兵大的n个数]，然后递归排序
     *         取第一个做为标兵，依次遍历后续元素
     *             如果比标兵大，记录比标兵大的元素个数
     *             如果比标兵小
     *                 如果比标兵大的数据为0，直接交换标兵和目标元素
     *                 如果比标兵大的数据为n
     *                     将当前元素取出
     *                     将标兵的后一位覆盖当前元素
     *                     标兵放到边兵的后一位
     *                     temp元素覆盖标兵位
     *                 ex: 3  4  5  1
     *                     标兵为3，当遍历到元素1的时候，比标兵大的元素有2个，
     *                     1.temp = array[now](array[now]=1)
     *                     2.标兵的后一位4，占据一个位置  array[now]=array[标兵index+1]
     *                     3.标兵覆盖标兵的后一位  array[标兵index+1]=array[标兵index]
     *                     4.now覆盖标兵位置 array[标兵index]=temp
     *                     结果: 1  3  5  4
     *                     后续排序   1     3(标兵)   5   4
     *     第三个版本的排序思路
     *         基于第二个排序思路中发现，每次元素交换会发生>=2次交换，调整下思路，交换次数
     *         之前的交换过程中，标兵一直在移动，现在用一个index值，记录小于标兵的最后一个元素，等一次遍历全部结束后，再将标兵插入到数组中，我们称这个index为分区index
     *
     *             如果标兵的后一位比标兵大
     *                 记录 partionIndex 的位置为标兵的位置
     *             如果标兵的后一位比标兵小
     *                 记录 partionIndex 的位置为标兵的后一位
     *             从标兵的后俩位开始遍历
     *                 如果元素比标兵大，不做处理，继续下一位
     *                 如果元素比标兵小
     *                     如果 partionIndex 的位置与当前位置 不相等
     *                         交换partionIndex+1 和 当前位置 的俩个元素
     *                         partionIndex+1
     */
    public static void quickSortThird(int[] array, int start, int end) {

        if(end==start){
            return;
        }

        if(end-start==1){
            if(array[start]>array[end]){
                int temp= end;
                array[end]=array[start];
                array[start]=temp;
            }
            return;
        }

        int flag = array[start];
        int partionIndex = 0;

        if (array[start + 1] > flag) {
            partionIndex = start;
        } else {
            partionIndex = start + 1;
        }

        for(int i= start+2;i<end;i++){

            if(array[i]<flag){
                if(i!=partionIndex){
                    int temp = array[i];
                    array[i]= array[partionIndex+1];
                    array[partionIndex+1]=temp;
                    partionIndex++;
                }
            }
        }

        if(partionIndex!=start){
            int temp = array[partionIndex];
            array[partionIndex]=flag;
            array[start]=temp;
        }

        quickSortThird(array,start,partionIndex);
        quickSortThird(array,partionIndex+1,end);
    }


    // 奥哥写的  记录下来
    //@Author 陈绍奥
    public void sort(int[] nums, int start, int end) {

        int left = start;   // 必须要保存变量，后面要用 start 和 end
        int right = end;

        // 递归结束条件
        // left == right 的时候，两指针重叠，不走while，就是当前 boy
        if (left > right) {
            return;
        }

        int boy = nums[left];   // 士兵出列

        while (left < right) {  // 左右所有都要交换

            while (left < right && nums[right] > boy) {
                right--;
            }

            if (left < right) { // 判断没有越界
                nums[left] = nums[right];
                left++;
            }

            while (left < right && nums[left] < boy) {
                left++;
            }

            if (left < right) {
                nums[right] = nums[left];
                right--;
            }

        }
        // 这个是递归关键步
        nums[left] = boy;   // 士兵入列

        int mid = left;
        sort(nums, start, mid - 1);  // mid 值已经确认了，+1 -1 干两边的事情。
        sort(nums, mid + 1, end);
    }

    public static void main(String[] args) {

//      quickSortFirst(array);
//      quickSortSecond(array, 0, array.length - 1);
        quickSortThird(array,0,array.length-1);

        System.out.println("-------------");


        for (int element : array) {
            System.out.print(element + "-");
        }
        System.out.println();
        System.out.println("-------------");
    }

}
