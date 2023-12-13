package org.example.animals;

public class Fish extends Animal {
    String color;

    public Fish(String name, int age, String color) {
        super(name, age);
        this.color = color;
    }

    @Override
    public String toString() {
        return "Fish{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", color='" + color + '\'' +
                '}';
    }
}
