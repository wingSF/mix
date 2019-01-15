package com.wing.lynne.collectionDemo;

/**
 * 合并俩个单向链表
 * question by 易波
 */
public class MergeLinkedList {

    public static void main(String[] args) {

        LinkedDemo targetElement1 = new LinkedDemo(1);
        LinkedDemo targetElement2 = new LinkedDemo(11);
        LinkedDemo targetElement3 = new LinkedDemo(111);
        LinkedDemo targetElement4 = new LinkedDemo(1111);
        LinkedDemo targetElement5 = new LinkedDemo(11111);

        targetElement1.next = targetElement2;
        targetElement2.next = targetElement3;
        targetElement3.next = targetElement4;
        targetElement4.next = targetElement5;

        LinkedDemo newElement1 = new LinkedDemo(2);
        LinkedDemo newElement2 = new LinkedDemo(22);
        LinkedDemo newElement3 = new LinkedDemo(222);
        LinkedDemo newElement4 = new LinkedDemo(2222);
        LinkedDemo newElement5 = new LinkedDemo(22222);

        newElement1.next = newElement2;
        newElement2.next = newElement3;
        newElement3.next = newElement4;
        newElement4.next = newElement5;

        printLinkedDemo("targetElement1:", targetElement1);
        printLinkedDemo("newElement1:", newElement1);

/**================调用排序方法区域，注意每次执行，只允许一个方法合并链表===================================*/
//        LinkedDemo sortResult1 = sort1(targetElement1, newElement1);
//        printLinkedDemo("合并结果:", sortResult1);

        LinkedDemo sortResult2 = sort2(targetElement1, newElement1);
        printLinkedDemo("合并结果:", sortResult2);
/**===============================================================================================*/

    }

    /**
     * create by 易波
     * @param targetElement1
     * @param newElement1
     * @return
     */
    private static LinkedDemo sort2(LinkedDemo targetElement1, LinkedDemo newElement1) {

        if (targetElement1 == null) {
            return newElement1;
        }

        if (newElement1 == null) {
            return targetElement1;
        }

        LinkedDemo result = null;

        if (newElement1.value <= targetElement1.value) {

            result = newElement1;

            newElement1.next = sort2(newElement1.next, targetElement1);

        } else {

            result = targetElement1;

            targetElement1.next = sort2(targetElement1.next, newElement1);

        }

        return result;
    }

    /**
     * 自己写的实现，都tm不好意思给别人看了。。。。。。。
     * 留着把，这就是耻辱
     *
     * @param targetElement1 目标链表的表头
     * @param newElement1    新链表的表头
     * @return
     */
    private static LinkedDemo sort1(LinkedDemo targetElement1, LinkedDemo newElement1) {

        //第一步，确定新链表的第一个元素在目标链表中的位置
        //第二步，递归合并链表元素
        /**
         * 为什么要分为俩步走，第一种情况比较极端，此时新链表的头节点在目标链表中的相对外置不固定
         * 当把新链表的第一个元素插入目标链表的时候，可以确定接下来新插入的元素，一定在之前刚插入的元素的下方
         */
        if (targetElement1 == null) {
            return targetElement1;
        }

        if (newElement1 == null) {
            return targetElement1;
        }

        //将新链表中的第一个元素插入到目标链表中
        LinkedDemo[] tempMergeArray = insertFirstToTargetList(targetElement1, newElement1);

        if (tempMergeArray[1] == null) {
            return targetElement1;
        }

        //如有需要，继续合并链表，此时只有可能新链表为空，进行非空校验
        if (tempMergeArray[0] == null) {
            return targetElement1;
        }

        mergeLinkedList(tempMergeArray[0], tempMergeArray[1]);

        return targetElement1;
    }

    /**
     * 将新链表中的第一个元素，插入到目标链表中
     *
     * @param targetElement1 目标链表的表头节点
     * @param newElement1    新链表的表头接待你
     * @return 返回一个长度为3的链表对象数组，[0]是新链表的表头  [1]是目标链表的表头，且[1]位置的元素，一定为比[0]位置的大 [2]用来记录链表的头，用于后续输出
     * 如果[1]的位置为空，则说明新链表中的最小元素>目标链表中的最大元素
     */
    private static LinkedDemo[] insertFirstToTargetList(LinkedDemo targetElement1, LinkedDemo newElement1) {

        LinkedDemo[] linkedDemoArray = new LinkedDemo[3];

        LinkedDemo targetCurrentHead = targetElement1;

        while (targetCurrentHead.next != null) {

            //新链表的第一个元素比目标链表的第一个元素还小
            if (newElement1.value < targetCurrentHead.value) {

                LinkedDemo newHeadNext = newElement1.next;
                newElement1.next = targetCurrentHead;

                linkedDemoArray[0] = newHeadNext;
                linkedDemoArray[1] = newElement1;
                linkedDemoArray[2] = newElement1;

                return linkedDemoArray;

                //这种情况下，新链表的表头要插入到目标链表的第二个元素的位置
            } else if (newElement1.value >= targetCurrentHead.value && newElement1.value < targetCurrentHead.next.value) {

                //目标链表的第二个元素
                LinkedDemo targetHeadNext = targetCurrentHead.next;
                //新链表的第二个元素
                LinkedDemo newNextHead = newElement1.next;

                targetCurrentHead.next = newElement1;
                newElement1.next = targetHeadNext;

                linkedDemoArray[0] = newNextHead;
                linkedDemoArray[1] = newElement1;
                linkedDemoArray[2] = targetElement1;

                return linkedDemoArray;

            } else {
                targetCurrentHead = targetCurrentHead.next;
            }
        }

        System.out.println("新链表中的最小的元素>目标链表中最大的元素,直接连接");

        targetCurrentHead.next = newElement1;

        linkedDemoArray[0] = targetElement1;

        return linkedDemoArray;

    }

    /**
     * 此处newList > target
     *
     * @param newList
     * @param target
     * @return
     */
    private static void mergeLinkedList(LinkedDemo newList, LinkedDemo target) {

        //如果新链表的表头为空，直接返回
        if (newList == null) {
            return;
        }

        //如果目标链表的第二个元素为空，直接挂机
        if (target.next == null) {
            target.next = newList;
            return;
        }

        //如果新链表的表头大于目标链表的第二个元素
        if (newList.value > target.next.value) {

            mergeLinkedList(newList, target.next);

        } else {//如果新链表的表头 大于目标链表的第一个元素，小于第二个元素，插入

            //记录目标链表的第二个元素
            LinkedDemo targetHeadNext = target.next;
            //记录新链表的第二个元素
            LinkedDemo newListNext = newList.next;

            target.next = newList;
            newList.next = targetHeadNext;

            if (newListNext != null) {
                mergeLinkedList(newListNext, newList);
            }
        }

    }

    private static void printLinkedDemo(String prefix, LinkedDemo linkedDemo) {

        if (linkedDemo == null) {
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(linkedDemo.value);

        LinkedDemo soutNext = linkedDemo.next;
        while (soutNext != null) {
            stringBuilder.append("-");
            stringBuilder.append(soutNext.value);
            soutNext = soutNext.next;
        }

        System.out.println(prefix + ":" + stringBuilder.toString());

    }

    static class LinkedDemo {

        public int value;
        public LinkedDemo next;

        public LinkedDemo(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "LinkedDemo{" +
                    "value=" + value +
                    '}';
        }
    }

}





