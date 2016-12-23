package com.piteravto.rockabilla.checkingvehicles.structure;

/**
 * Created by MishustinAI on 19.12.2016.
 * Один пункт меню, имя - отмечен или нет
 */

public class MenuItem{

    private String name;
    private int value;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public int getValue() {
        return value;
    }

    public MenuItem(String name, int value) {

        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

}
