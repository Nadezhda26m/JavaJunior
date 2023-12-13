package org.example.animals;

public class Turtle extends Animal {
    boolean isSeaTurtle;
    private final int speedCmS;

    public Turtle(String name, int age, boolean isSeaTurtle, int speedCmS) {
        super(name, age);
        this.isSeaTurtle = isSeaTurtle;
        this.speedCmS = speedCmS;
    }

    private int getDistance(int timeSec) {
        return speedCmS * timeSec;
    }

    @Override
    public String toString() {
        return "Turtle{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", SeaTurtle:" + isSeaTurtle +
                ", speed=" + speedCmS +
                " см/с}";
    }
}
