package org.example.calculator.tobe;

import org.example.calculator.domain.PositiveNumber;

public interface ArithmeticOperator {
    boolean supports(String operator);
    int calculate(int operand1, int operand2);
    int calculate(PositiveNumber operand1, PositiveNumber operand2);
}
