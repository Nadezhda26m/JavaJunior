package org.example.animals;

public abstract class Animal {
    public String name;
    protected int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "";
    }
}
