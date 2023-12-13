package org.example;

/*
Создайте абстрактный класс "Animal" с полями "name" и "age".
Реализуйте два класса-наследника от "Animal" (например, "Dog" и "Cat") с уникальными полями и методами.
Создайте массив объектов типа "Animal" и с использованием Reflection API выполните следующие действия:
Выведите на экран информацию о каждом объекте.
Вызовите метод "makeSound()" у каждого объекта, если такой метод присутствует.
 */

import org.example.animals.Animal;
import org.example.animals.Cat;
import org.example.animals.Fish;
import org.example.animals.Turtle;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ReflectiveOperationException {

        Class<?> dog = Class.forName("org.example.animals.Dog");
        Constructor<?>[] constructors = dog.getConstructors();
        Object myDog = constructors[0].newInstance("Шарик", 3, "белый");

        Animal[] animals = {new Cat("Васька", 4, "рыжий"),
                (Animal) myDog,
                new Fish("Клаус", 1, "синий"),
                new Turtle("Глория", 17, false, 1)};

        Object myTurtle = animals[3];
        Field field = myTurtle.getClass().getDeclaredField("speedCmS");
        field.setAccessible(true);
        field.setInt(myTurtle, 3);

        for (Animal animal : animals) {
            System.out.println(animal);
        }

        for (Animal animal : animals) {
            System.out.println(">>>>>");
            getInfo1(animal);
            System.out.println("--------");
            getInfo2(animal);
            System.out.println();
        }

    }

    public static void getInfo1(Object obj) throws ClassNotFoundException,
            IllegalAccessException, InvocationTargetException {
        System.out.println(">>> " + obj.getClass().getSimpleName());
        Class<?> animalClass = Class.forName(obj.getClass().getName());
        // System.out.println(animalClass.getName().split("\\.")[3]);

        List<Field> fields = new LinkedList<>();
        fields.addAll(Arrays.asList(animalClass.getSuperclass().getDeclaredFields()));
        fields.addAll(Arrays.asList(animalClass.getDeclaredFields()));
        System.out.println("> Поля: ");
        for (Field field : fields) {
            field.setAccessible(true);
            System.out.printf("%s: %s\n", field.getName(), field.get(obj));
        }

        List<Method> methods = new LinkedList<>();
        methods.addAll(Arrays.asList(animalClass.getSuperclass().getDeclaredMethods()));
        methods.addAll(Arrays.asList(animalClass.getDeclaredMethods()));
        System.out.println("> Методы: ");
        for (Method method : methods) {
            if (method.getName().equals("makeSound")) {
                method.setAccessible(true);
                System.out.print("makeSound(): ");
                method.invoke(obj);
            } else if (method.getName().equals("getDistance")) {
                method.setAccessible(true);
                System.out.println("getDistance(10 сек.): "
                        + method.invoke(obj, 10) + " см");
            } else System.out.println(method.getName());
        }
    }

    public static void getInfo2(Object obj) throws ClassNotFoundException,
            IllegalAccessException, InvocationTargetException {
        System.out.println(">>> " + obj.getClass().getSimpleName());
        Class<?> animalClass = Class.forName(obj.getClass().getName());
        // System.out.println(animalClass.getName().split("\\.")[3]);

        List<Member> members = new LinkedList<>();
        members.addAll(Arrays.asList(animalClass.getSuperclass().getDeclaredFields()));
        members.addAll(Arrays.asList(animalClass.getDeclaredFields()));
        members.addAll(Arrays.asList(animalClass.getSuperclass().getDeclaredMethods()));
        members.addAll(Arrays.asList(animalClass.getDeclaredMethods()));
        for (Member member : members) {
            if (member.getClass().toString().contains("Field")) {
                Field field = (Field) member;
                field.setAccessible(true);
                System.out.printf("(Поле) %s: %s\n", field.getName(), field.get(obj));
            } else if (member.getClass().getSimpleName().equals("Method")) {
                Method method = (Method) member;
                if (member.getName().equals("makeSound")) {
                    // Method method = animalClass.getDeclaredMethod(member.getName());
                    method.setAccessible(true);
                    System.out.print("(Метод) makeSound(): ");
                    method.invoke(obj);
                } else if (member.getName().equals("getDistance")) {
                    // Method method = animalClass.getDeclaredMethod(member.getName(), int.class);
                    method.setAccessible(true);
                    System.out.println("(Метод) getDistance(10 сек.): "
                            + method.invoke(obj, 10) + " см");
                } else System.out.println("(Метод) " + member.getName());
            }
        }
    }
}

/*
Cat{name='Васька', age=4, color='рыжий'}
Dog{name='Шарик', age=3, color='белый'}
Fish{name='Клаус', age=1, color='синий'}
Turtle{name='Глория', age=17, SeaTurtle:false, speed=3 см/с}
>>>>>
>>> Cat
> Поля:
name: Васька
age: 4
color: рыжий
> Методы:
toString
toString
makeSound(): (Cat) Васька сказал Мяу!
--------
>>> Cat
(Поле) name: Васька
(Поле) age: 4
(Поле) color: рыжий
(Метод) toString
(Метод) toString
(Метод) makeSound(): (Cat) Васька сказал Мяу!

>>>>>
>>> Dog
> Поля:
name: Шарик
age: 3
color: белый
> Методы:
toString
toString
makeSound(): (Dog) Шарик сказал Гаф!
--------
>>> Dog
(Поле) name: Шарик
(Поле) age: 3
(Поле) color: белый
(Метод) toString
(Метод) toString
(Метод) makeSound(): (Dog) Шарик сказал Гаф!

>>>>>
>>> Fish
> Поля:
name: Клаус
age: 1
color: синий
> Методы:
toString
toString
--------
>>> Fish
(Поле) name: Клаус
(Поле) age: 1
(Поле) color: синий
(Метод) toString
(Метод) toString

>>>>>
>>> Turtle
> Поля:
name: Глория
age: 17
isSeaTurtle: false
speedCmS: 3
> Методы:
toString
toString
getDistance(10 сек.): 30 см
--------
>>> Turtle
(Поле) name: Глория
(Поле) age: 17
(Поле) isSeaTurtle: false
(Поле) speedCmS: 3
(Метод) toString
(Метод) toString
(Метод) getDistance(10 сек.): 30 см
 */
