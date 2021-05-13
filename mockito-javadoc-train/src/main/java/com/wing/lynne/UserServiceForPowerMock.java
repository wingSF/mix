package com.wing.lynne;


import com.sun.corba.se.impl.orbutil.ObjectUtility;

public class UserServiceForPowerMock {

    private long userLimit;
    private long superUserId;

    public long getUserCount() {
        System.out.println("method call");
        return 0L;
    }

    public long getUserLimit() {
        return userLimit;
    }

    private boolean isSuperUser(Long userId) {
        return superUserId == userId;
    }

    public boolean isNotSuperUser(Long userId) {
        return !isSuperUser(userId);
    }
}
