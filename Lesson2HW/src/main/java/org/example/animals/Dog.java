package org.example.animals;

public class Dog extends Animal {
    String color;

    public Dog(String name, int age, String color) {
        super(name, age);
        this.color = color;
    }

    public void makeSound() {
        System.out.printf("(%s) %s сказал Гаф!\n", this.getClass().getSimpleName(), name);
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", color='" + color + '\'' +
                '}';
    }
}
