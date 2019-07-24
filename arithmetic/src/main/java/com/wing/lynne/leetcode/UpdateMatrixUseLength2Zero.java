package com.wing.lynne.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/01-matrix/
 */
public class UpdateMatrixUseLength2Zero {

    public static void main(String[] args) {

        int[][] matrix = new int[10][];

        int[] arr0 = {1, 0, 1, 1, 0, 0, 1, 0, 0, 1};
        int[] arr1 = {0, 1, 1, 0, 1, 0, 1, 0, 1, 1};
        int[] arr2 = {0, 0, 1, 0, 1, 0, 0, 1, 0, 0};
        int[] arr3 = {1, 0, 1, 0, 1, 1, 1, 1, 1, 1};
        int[] arr4 = {0, 1, 0, 1, 1, 0, 0, 0, 0, 1};
        int[] arr5 = {0, 0, 1, 0, 1, 1, 1, 0, 1, 0};
        int[] arr6 = {0, 1, 0, 1, 0, 1, 0, 0, 1, 1};
        int[] arr7 = {1, 0, 0, 0, 1, 1, 1, 1, 0, 1};
        int[] arr8 = {1, 1, 1, 1, 1, 1, 1, 0, 1, 0};
        int[] arr9 = {1, 1, 1, 1, 0, 1, 0, 0, 1, 1};

        matrix[0] = arr0;
        matrix[1] = arr1;
        matrix[2] = arr2;
        matrix[3] = arr3;
        matrix[4] = arr4;
        matrix[5] = arr5;
        matrix[6] = arr6;
        matrix[7] = arr7;
        matrix[8] = arr8;
        matrix[9] = arr9;

        int[][] resultNo1 = updateMatrixNo1(matrix);
        int[][] result1 = updateMatrixMy1(matrix);


        System.out.println();

    }

    /**
     * 网站上最快的那个答案
     * <p>
     * 思路分析
     * 三层for循环嵌套
     * <p>
     * 最外层先不解释
     * <p>
     * 里面俩层是常规的二维数组遍历
     * 如果是1的，不做处理
     * 如果是别的数字，判断它的上下左右四个位置，是否有0，如果有，更新为-1
     * <p>
     * 接下来解释第三层
     * 最外层的for循环执行一次结束后，集合中会出现以下几种元素
     * 0
     * 1 上下左右都没有0
     * -1 上下左右中出现了0
     * 最外层for循环执行第二次
     * 如果是1的，不做处理
     * 如果上下左右出现了-1，则更新为-2
     * 最外层for循环执行第三次
     * 如果是1的，不做处理
     * 如果上下左右出现了-2，则更新为-3
     * 。。。。。
     * 经历若干次之后，如果没有数据再发生变化
     * 将所有的数据 * -1 ，负数变成正数
     * <p>
     * 我操，这么神奇
     *
     * @param matrix 入参矩阵
     * @return 返回更新后的矩阵
     */
    private static int[][] updateMatrixNo1(int[][] matrix) {
        if (matrix == null) {
            return null;
        }
        int maxI = matrix.length;
        int maxJ = matrix[0].length;
        for (int x = 1; x < maxI + maxJ - 1; x++) {
            boolean flag = false;
            for (int i = 0; i < maxI; i++) {
                for (int j = 0; j < maxJ; j++) {

                    if (matrix[i][j] != 1) {
                        continue;
                    }

                    if (0 <= i - 1 && i - 1 < maxI) {
                        if (matrix[i - 1][j] == x * -1 + 1) {
                            matrix[i][j] = x * -1;
                            flag = true;
                        }
                    }
//                    if (0 <= i + 1 && i + 1 < maxI) {
                    if (i + 1 < maxI) {
                        if (matrix[i + 1][j] == x * -1 + 1) {
                            matrix[i][j] = x * -1;
                            flag = true;
                        }
                    }
                    if (0 <= j - 1 && j - 1 < maxJ) {
                        if (matrix[i][j - 1] == x * -1 + 1) {
                            matrix[i][j] = x * -1;
                            flag = true;
                        }
                    }
//                    if (0 <= j + 1 && j + 1 < maxJ) {
                    if (j + 1 < maxJ) {
                        if (matrix[i][j + 1] == x * -1 + 1) {
                            matrix[i][j] = x * -1;
                            flag = true;
                        }
                    }

                }
            }
            if (!flag) {
                break;
            }
        }
        for (int i = 0; i < maxI; i++) {
            for (int j = 0; j < maxJ; j++) {
                matrix[i][j] *= -1;
            }
        }
        return matrix;

    }

    /**
     * 虽然实现了功能，但是在执行时发现，超过时间限制
     * 且从算法的角度将，这样的尝试时盲目的
     * <p>
     * 矩阵-是m*n的结构，所以用二维数组表示的时候，所以行数组的长度是一样的
     * <p>
     * fuck 审题不细致啊，人家说了，矩阵是 0 和 1 组成的，ciao
     *
     * @param matrix 入参矩阵
     * @return 返回更新后的矩阵
     */
    private static int[][] updateMatrixMy1(int[][] matrix) {
        //用来记录计算结果的数组
        int[][] matrixCopyOuter = new int[matrix.length][];


        for (int x = 0; x < matrix.length; x++) {
            int[] matrixCopyInner = new int[matrix[x].length];
            for (int y = 0; y < matrix[x].length; y++) {

                if (matrix[x][y] == 0) {
                    matrixCopyInner[y] = 0;
                    continue;
                }

                //记录查询多少次找到最近的zero
                int count = 0;
                boolean flag = true;
                List<String> startPosition = new ArrayList<>();
                startPosition.add(x + "," + y);
                while (flag) {

                    List<String> tempPosition = new ArrayList<>();

                    for (String str : startPosition) {
                        String[] positionInfo = str.split(",");
                        if (isNearZero(Integer.valueOf(positionInfo[0]), Integer.valueOf(positionInfo[1]), matrix)) {
                            matrixCopyInner[y] = ++count;
                            flag = false;
                            break;
                        } else {
                            // 上
                            if (Integer.valueOf(positionInfo[0]) - 1 >= 0) {
                                tempPosition.add((Integer.valueOf(positionInfo[0]) - 1) + "," + Integer.valueOf(positionInfo[1]));
                            }
                            // 下
                            if (Integer.valueOf(positionInfo[0]) + 1 <= matrix.length - 1) {
                                tempPosition.add((Integer.valueOf(positionInfo[0]) + 1) + "," + Integer.valueOf(positionInfo[1]));
                            }
                            // 左
                            if (Integer.valueOf(positionInfo[1]) - 1 >= 0) {
                                tempPosition.add(Integer.valueOf(positionInfo[0]) + "," + (Integer.valueOf(positionInfo[1]) - 1));
                            }
                            // 右
                            if (Integer.valueOf(positionInfo[1]) + 1 <= matrix[x].length - 1) {
                                tempPosition.add(Integer.valueOf(positionInfo[0]) + "," + (Integer.valueOf(positionInfo[1]) + 1));
                            }
                        }
                    }

                    startPosition = tempPosition;
                    count++;

                }


            }
            matrixCopyOuter[x] = matrixCopyInner;
        }
        return matrixCopyOuter;
    }

    /**
     * 判断距离当前元素为1的位置，是否有0存在
     *
     * @param x      横坐标
     * @param y      纵坐标
     * @param matrix 入参矩阵
     * @return 返回ture，表示距离为1有0，false则没有
     */
    private static boolean isNearZero(int x, int y, int[][] matrix) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("坐标有误");
        }

        //上
        if (x - 1 >= 0 && matrix[x - 1][y] == 0) {
            return true;
        }

        //左
        if (y - 1 >= 0 && matrix[x][y - 1] == 0) {
            return true;
        }

        //右
        if (y + 1 <= matrix[x].length - 1 && matrix[x][y + 1] == 0) {
            return true;
        }

        //下
        if (x + 1 <= matrix.length - 1 && matrix[x + 1][y] == 0) {
            return true;
        }

        return false;
    }

}
