package com.wing.lynne.observer.other;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EventListener {

    protected Map<Enum,Event> events = new HashMap<>();

    public void addListener(Enum eventType, Object target, Method callBack){
        events.put(eventType,new Event(target,callBack));
    }

    protected void trigger(Enum call){
        if(!events.containsKey(call)){
            return;
        }

        trigger(events.get(call).setTrigger(call.toString()));
    }

    private void trigger(Event event){

        event.setSource(this);
        event.setTime(System.currentTimeMillis());

        try {
            event.getCallBack().invoke(event.getTarget(),event);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
