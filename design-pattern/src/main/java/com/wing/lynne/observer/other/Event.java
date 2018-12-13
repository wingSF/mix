package com.wing.lynne.observer.other;

import java.lang.reflect.Method;

public class Event {

    private Object source;

    private Object target;

    private Method callBack;

    private String trigger;

    private long time;

    public Event(Object target, Method callBack) {
        this.target = target;
        this.callBack = callBack;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getCallBack() {
        return callBack;
    }

    public void setCallBack(Method callBack) {
        this.callBack = callBack;
    }

    public String getTrigger() {
        return trigger;
    }

    public Event setTrigger(String trigger) {
        this.trigger = trigger;
        return this;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Event{" +
                "source=" + source +
                ", target=" + target +
                ", callBack=" + callBack +
                ", trigger='" + trigger + '\'' +
                ", time=" + time +
                '}';
    }
}
