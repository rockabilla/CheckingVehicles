package com.piteravto.rockabilla.checkingvehicles.structure;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Created by MishustinAI on 19.12.2016.
 */

public class MenuItem{

    private String name;
    private int value;
    //private static final long serialVersionUID = 1L;

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
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

    /*private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(this.getName());
        out.writeInt(this.getValue());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.setName((String)in.readObject());
        this.setValue(in.readInt());
    }

    private void readObjectNoData() throws ObjectStreamException {
        // nothing to do
    }*/
}
