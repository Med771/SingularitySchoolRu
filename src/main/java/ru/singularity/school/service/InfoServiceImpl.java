package ru.singularity.school.service;

import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl {
    public long getArithmeticSum(long num) {
        // int sum = Stream.iterate(1, a -> a +1) .limit(1_000_000) .reduce(0, (a, b) -> a + b );
        // Данный стрим не оптимизирован и работает за О(n)
        // Мы можем преоброзовать в арфметическую сумму
        // работает за O(1)
        return (1 + num) * num / 2;
    }
}
