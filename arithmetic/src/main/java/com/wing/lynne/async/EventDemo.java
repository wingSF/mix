package com.wing.lynne.async;

import java.beans.PropertyChangeSupport;

public class EventDemo {


    /*
        EventObject   -> PropertyChangeEvent
        processor     -> PropertyChangeSupport
                            addPropertyChangeListener
                            firePropertyChange
        EventListener -> PropertyChangeListener
     */

    public static void main(String[] args) {

        Person person = new Person();

        PropertyChangeSupport support = new PropertyChangeSupport(person);

        support.addPropertyChangeListener(changeEvent -> System.out.println(changeEvent.getOldValue() + "->" + changeEvent.getNewValue()));
        support.addPropertyChangeListener(changeEvent -> System.out.println(changeEvent.getOldValue() + "->" + changeEvent.getNewValue()));
        support.addPropertyChangeListener("age", ageChangeEvent -> System.out.println(ageChangeEvent.getOldValue() + "->" + ageChangeEvent.getNewValue()));

//        support.firePropertyChange("name", "111", "222");
        support.firePropertyChange("age", "111", "222");


    }

    public static class Person {

        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
