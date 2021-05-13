package com.wing.lynne.powerMock;

import com.wing.lynne.UserServiceForPowerMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Method;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UserServiceForPowerMock.class)
public class UserServiceForPowerMockTest {

    @InjectMocks
    private UserServiceForPowerMock userService;

    @Test
    public void testGetUserCount(){
        long expect = 1000L;

        UserServiceForPowerMock userService = PowerMockito.spy(new UserServiceForPowerMock());

        PowerMockito.when(userService.getUserCount()).thenReturn(expect);
        long actual = userService.getUserCount();
        Assert.assertEquals(expect,actual);

        //这种场景下，不会调用目标方法
        PowerMockito.doReturn(expect).when(userService).getUserCount();
        actual = userService.getUserCount();
        Assert.assertEquals(expect,actual);
    }

    @Test
    public void testSetField(){
        long expect = 200L;
//        ReflectionTestUtils.setField(userService,"userLimit",expect);
        Whitebox.setInternalState(userService,"userLimit",expect);
        long actual = userService.getUserLimit();
        Assert.assertEquals(expect,actual);
    }

    @Test
    public void testPrivate() throws Exception {
        long userId = 100;
        boolean expect = true;
        UserServiceForPowerMock userService = PowerMockito.spy(new UserServiceForPowerMock());
        PowerMockito.when(userService,"isSuperUser",userId).thenReturn(!expect);
        boolean actual = userService.isNotSuperUser(userId);
        Assert.assertEquals(expect,actual);

        PowerMockito.stub(PowerMockito.method(UserServiceForPowerMock.class,"isSuperUser",Long.class)).toReturn(!expect);
        actual = userService.isNotSuperUser(userId);
        Assert.assertEquals(expect,actual);

        Method method = PowerMockito.method(UserServiceForPowerMock.class, "isSuperUser", Long.class);
        Object actualObject = method.invoke(userService, userId);
        Assert.assertEquals(!expect,actualObject);
    }
}
