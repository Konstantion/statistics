package com.cnu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Lab3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть номер студента (N):");
        int n = scanner.nextInt();
        int size = n + 10;

        // Генерація випадкової послідовності
        Random random = new Random();
        List<Integer> sequence = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            sequence.add(random.nextInt(n + 1) + 1); // Значення від 1 до N+1
        }

        // Виведення вихідних даних
        System.out.println("Вихідні дані:");
        System.out.println(sequence);

        // Сортування послідовності
        List<Integer> sortedSequence = sequence.stream().sorted().collect(Collectors.toList());
        System.out.println("Впорядкована послідовність:");
        System.out.println(sortedSequence);

        // Обчислення моди
        int mode = findMode(sequence);
        System.out.println("Мода: " + mode);

        // Обчислення медіани
        double median = findMedian(sortedSequence);
        System.out.println("Медіана: " + median);

        // Обчислення середнього арифметичного
        double mean = sequence.stream().mapToInt(Integer::intValue).average().orElse(0.0);
        System.out.println("Середнє арифметичне: " + mean);

        // Перевірка обчислення моди
        System.out.println("\nПеревірка обчислення моди:");
        checkMode(sequence, mode);
    }

    private static int findMode(List<Integer> sequence) {
        Map<Integer, Long> frequencyMap = sequence.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        return frequencyMap.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .orElseThrow(() -> new RuntimeException("Не знайдено моду"))
                .getKey();
    }

    private static double findMedian(List<Integer> sortedSequence) {
        int size = sortedSequence.size();
        if (size % 2 == 0) {
            return (sortedSequence.get(size / 2 - 1) + sortedSequence.get(size / 2)) / 2.0;
        } else {
            return sortedSequence.get(size / 2);
        }
    }

    private static void checkMode(List<Integer> sequence, int mode) {
        Map<Integer, Long> frequencyMap = sequence.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        System.out.println("Таблиця частот значень:");
        frequencyMap.forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println("Розрахована мода: " + mode);
        long maxFrequency = frequencyMap.get(mode);
        System.out.println("Частота моди: " + maxFrequency);

        boolean isCorrect = frequencyMap.values().stream().allMatch(freq -> freq <= maxFrequency);
        if (isCorrect) {
            System.out.println("Перевірка успішна: мода визначена правильно.");
        } else {
            System.out.println("Помилка: мода визначена некоректно.");
        }
    }}
