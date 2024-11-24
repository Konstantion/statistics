package com.cnu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Lab2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть номер студента (N):");
        int n = scanner.nextInt();
        int size = n + 10;

        // Генерація випадкової послідовності
        Random random = new Random();
        List<Integer> sequence = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            sequence.add(random.nextInt(5) + 1); // Значення від 1 до 5
        }

        // Виведення вихідних даних
        System.out.println("Вихідні дані:");
        System.out.println(sequence);

        // Варіаційний ряд
        List<Integer> sortedSequence = sequence.stream().sorted().toList();
        System.out.println("Варіаційний ряд:");
        System.out.println(sortedSequence);

        // Статистичний розподіл
        Map<Integer, Long> frequencyMap = sequence.stream()
                .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));

        System.out.println("Статистичний розподіл (елемент: частота):");
        frequencyMap.forEach((key, value) -> System.out.println(key + ": " + value));

        // Інтегральна частота
        Map<Integer, Double> integralFrequency = new TreeMap<>();
        double cumulativeFrequency = 0.0;
        for (Map.Entry<Integer, Long> entry : frequencyMap.entrySet()) {
            cumulativeFrequency += entry.getValue();
            integralFrequency.put(entry.getKey(), cumulativeFrequency / size);
        }

        System.out.println("Інтегральна частота (елемент: частота):");
        integralFrequency.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}
