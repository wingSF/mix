package com.wing.lynne.powerMock;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalMatchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.tests.utils.PowerMockTestNotifier;

import java.util.ArrayList;
import java.util.List;


@RunWith(PowerMockRunner.class)
@PrepareForTest({String.class})
public class ListTest {

    @Test
    public void testSize() {
        Integer expected = 100;

        //用于模拟指定类的对象实例
        List list = PowerMockito.mock(List.class);

        PowerMockito.when(list.size()).thenReturn(expected);
        Integer actual = list.size();
        Assert.assertEquals("返回值相等", expected, actual);


        String expectedString = "lynne";
        //当被模拟类是final标识时，需要配合一下俩个注解
        //@RunWith(PowerMockRunner.class)
        //@PrepareForTest({String.class})
        String wing = PowerMockito.mock(String.class);
        PowerMockito.when(wing.toString()).thenReturn(expectedString);
        String result = wing.toString();
        Assert.assertEquals(expectedString, result);
    }

    @Test
    public void testGet() {
        int index = 0;
        Integer expected = 100;
        List<Integer> list = PowerMockito.mock(List.class);
        PowerMockito.when(list.get(index)).thenReturn(expected);

        Integer actual = list.get(index);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetException() {
        int index = -1;
        Integer expect = 100;
        List<Integer> list = PowerMockito.mock(List.class);
        PowerMockito.when(list.get(index)).thenThrow(new IndexOutOfBoundsException());

        Integer actual = list.get(index);
        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testGetAnswer() {
        int index = 1;
        Integer expect = 100;
        List<Integer> mockList = PowerMockito.mock(List.class);
        PowerMockito.when(mockList.get(index)).thenAnswer(invocation -> {
            Integer value = invocation.getArgument(0);
            return value * 100;
        });

        Integer actual = mockList.get(index);
        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testCallRealMethod() {
        int index = 0;
        Integer expect = 100;
        List<Integer> oldList = new ArrayList<>();
        oldList.add(expect);

        List<Integer> spyList = PowerMockito.spy(oldList);
        PowerMockito.when(spyList.get(index)).thenCallRealMethod();

        Integer actual = spyList.get(index);
        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testDoReturnWhen() {
        int index = 0;
        Integer expect = 100;
        List<Integer> mockList = PowerMockito.mock(List.class);
        PowerMockito.doReturn(expect).when(mockList).get(index);
//      PowerMockito.doReturn(expect).when(mockList.get(index));这种写法会报错

        Integer actual = mockList.get(index);
        Assert.assertEquals(expect, actual);

//        PowerMockito.doThrow(new IndexOutOfBoundsException()).when(mockList).get(-1);
        PowerMockito.doAnswer(invocationOnMock -> {
            Integer argument = invocationOnMock.getArgument(0);
            return argument * 100;
        }).when(mockList).get(index);

        List<Integer> spyList = PowerMockito.spy(mockList);
        PowerMockito.doCallRealMethod().when(spyList).get(index);
    }

    @Test
    public void testParam() {
        int index = 0;
        Integer expect = 100;
        List<Integer> mockList = PowerMockito.mock(List.class);
        PowerMockito.when(mockList.get(Mockito.anyInt())).thenReturn(expect);
        Integer actual = mockList.get(index);
        Assert.assertEquals(expect, actual);

        PowerMockito.when(mockList.get(AdditionalMatchers.geq(0))).thenReturn(expect);
        PowerMockito.when(mockList.get(AdditionalMatchers.lt(0))).thenThrow(new IndexOutOfBoundsException());
    }

    @Test
    public void testVerify(){
        List<Integer> mockList = PowerMockito.mock(List.class);
        PowerMockito.doNothing().when(mockList).clear();
        mockList.clear();
        Mockito.verify(mockList).clear();
        Mockito.verify(mockList,Mockito.times(1)).clear();
    }
}
