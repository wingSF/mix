package com.wing.lynne.user;

import com.alibaba.fastjson.JSON;
import com.wing.lynne.IdGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
public class UserServiceTest {

    private static final String userCreateVOString  = "{\"name\":\"test\"}";
    private static final String userCreateDOString  = "{\"id\":1,\"name\":\"test\"}";
    private static final String userModifyDOString  = "{\"id\":1,\"name\":\"test\"}";

    @Mock
    private UserDao userDao;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private UserService userService;

    @Before
    public void beforeTest(){
        Whitebox.setInternalState(userService,"canModify",Boolean.TRUE);
    }

    @Test
    public void testCreateUserWithNew(){
        Mockito.doReturn(null).when(userDao).getIdByName(Mockito.anyString());

        Long userId = 1L;
        Mockito.doReturn(userId).when(idGenerator).next();

        UserVO createUser = JSON.parseObject(userCreateVOString,UserVO.class);

        Assert.assertEquals(userId,userService.createUser(createUser));

        Mockito.verify(userDao).getIdByName(createUser.getName());
        Mockito.verify(idGenerator).next();

        ArgumentCaptor<UserDO> userCreateCaptor = ArgumentCaptor.forClass(UserDO.class);
        Mockito.verify(userDao).create(userCreateCaptor.capture());


        Assert.assertEquals(userCreateDOString, JSON.toJSONString(userCreateCaptor.getValue()));

        //确认所有的地方都被verify了，没有落下的
        Mockito.verifyNoMoreInteractions(idGenerator);
    }

    @Test
    public void testCreateUserWithOld(){
        Long userId = 1L;
        Mockito.doReturn(userId).when(userDao).getIdByName(Mockito.anyString());

        UserVO userCreate = JSON.parseObject(userCreateVOString, UserVO.class);
        Assert.assertEquals(userId,userService.createUser(userCreate));

        Mockito.verify(userDao).getIdByName(userCreate.getName());

        ArgumentCaptor<UserDO> userDOArgumentCaptor = ArgumentCaptor.forClass(UserDO.class);
        Mockito.verify(userDao).modify(userDOArgumentCaptor.capture());

        Assert.assertEquals(userModifyDOString, JSON.toJSONString(userDOArgumentCaptor.getValue()));

        Mockito.verifyZeroInteractions(idGenerator);
        Mockito.verifyNoMoreInteractions(userDao);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreateUserException() {
        Whitebox.setInternalState(userService, "canModify", Boolean.FALSE);

        Long userId = 1L;
        Mockito.doReturn(userId).when(userDao).getIdByName(Mockito.anyString());

        UserVO createUser = JSON.parseObject(userCreateVOString, UserVO.class);
        userService.createUser(createUser);
    }
}
