package org.example;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

// @JsonIgnoreProperties(ignoreUnknown = true)
// @JsonIgnoreProperties(value = {"GPA"})
public class Student implements Externalizable {
// public class Student implements Serializable {

    String name;
    int age;
    // @JsonIgnore
    transient double GPA; // средний балл

    public Student(String name, int age, double GPA) {
        this.name = name;
        this.age = age;
        this.GPA = GPA;
    }

    public Student() {}

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Имя: " + name +
               "\nВозраст: " + age +
               "\nСредний балл (transient): " + GPA;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeInt(age);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        age = in.readInt();
    }
}
