package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Напишите программу, которая использует Stream API для обработки списка чисел.
        // Программа должна вывести на экран среднее значение всех четных чисел в списке.

        List<Integer> numbers = Arrays.asList(2, 5, 3, 8, 1, 9, 34, 21, 95, 4, 3, 28);
        // List<Integer> numbers = Arrays.asList(5, 3, 1, 9);

        int[] count = {0};
        double sum = numbers.stream().filter(num -> num % 2 == 0)
                .reduce(0, (num1, num2) -> {
                    count[0]++;
                    return num1 + num2;
                });
        System.out.println("avg: " + ((count[0] != 0) ? sum / count[0] : "Четные числа не найдены"));

        count[0] = 0;
        double avg = numbers.stream().filter(num -> num % 2 == 0)
                .mapToDouble(num -> num)
                .reduce(0.0, (x, y) -> {
                    int number = ++count[0];
                    return (x * (number - 1) + y) / number;
                });
        System.out.println("avg: " + ((avg != 0) ? avg : "Четные числа не найдены"));

        double average = numbers.stream().filter(num -> num % 2 == 0)
                .collect(Collectors.averagingInt(Integer::intValue));
        System.out.println("avg: " + ((average != 0) ? average : "Четные числа не найдены"));
    }
}
