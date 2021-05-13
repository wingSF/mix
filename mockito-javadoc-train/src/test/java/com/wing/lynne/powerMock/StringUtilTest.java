package com.wing.lynne.powerMock;

import com.wing.lynne.StringUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({StringUtil.class})
public class StringUtilTest {

    @Test
    public void testStringUtilClass(){

        String string = null;
        boolean expect = true;
        PowerMockito.spy(StringUtil.class);
        PowerMockito.when(StringUtil.isEmpty(string)).thenReturn(!expect);

        //调用了isEmpty方法
        boolean actual = StringUtil.isNotEmpty(string);
        //没有调用isEmpty方法
        boolean empty = StringUtil.isEmpty(string);

        Assert.assertEquals(expect,actual);
    }

    @Test
    public void testStringUtilObject(){

//        String string = "wing";
//        boolean expect = true;
//        PowerMockito.spy(string);
//        PowerMockito.when(StringUtil.isEmpty(string)).thenReturn(!expect);
//
//        //调用了isEmpty方法
//        boolean actual = StringUtil.isNotEmpty(string);
//        //没有调用isEmpty方法
//        boolean empty = StringUtil.isEmpty(string);
//
//        Assert.assertEquals(expect,actual);
    }

    @Test
    public void testParam(){
        String a = "abc";
        String b = "d";

        boolean expect = true;

        PowerMockito.spy(StringUtil.class);
        PowerMockito.when(StringUtil.startWith(Mockito.anyString(),Mockito.eq(b))).thenReturn(expect);

        boolean actual = StringUtil.startWith(a, b);
        Assert.assertEquals(expect,actual);
    }
}
