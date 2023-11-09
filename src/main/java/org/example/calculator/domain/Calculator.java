package org.example.calculator.domain;

import org.example.calculator.tobe.*;

import java.util.List;

public class Calculator {
    public static int calculate(int operand1, String operator, int operand2) {
        if (operator.equals("+")) {
            return operand1 + operand2;
        }
        if (operator.equals("-")) {
            return operand1 - operand2;
        }
        if (operator.equals("*")) {
            return operand1 * operand2;
        }
        if (operator.equals("/")) {
            return operand1 / operand2;
        }
        return 0;
    }

    public static int arithmateCalculate(int operand1, String operator, int operand2) {
        return EnumArithmeticOperator.calculate(operand1, operator, operand2);
    }

    private static final List<ArithmeticOperator> arithmeticOperators
            = List.of(
            new AdditionOperator()
            , new SubtractionOperator()
            , new MultiplicationOperator()
            , new DivisionOperator()
    );

    public static int arithmateImplCalculate(int operand1, String operator, int operand2) {

        /* List.of를 통해 주입받은 4개의 Operator 구현체 객체들 중 매개변수 operator에 해당하는 실제 구현체를 필터링 */
        return arithmeticOperators.stream()
                .filter(arithmeticOperator -> arithmeticOperator.supports(operator))
                .map(arithmeticOperator -> arithmeticOperator.calculate(operand1, operand2))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 사칙연산이 아닙니다."));
    }

    public static int arithmateImplCalculate(PositiveNumber operand1, String operator, PositiveNumber operand2) {

        /* List.of를 통해 주입받은 4개의 Operator 구현체 객체들 중 매개변수 operator에 해당하는 실제 구현체를 필터링 */
        return arithmeticOperators.stream()
                .filter(arithmeticOperator -> arithmeticOperator.supports(operator))
                .map(arithmeticOperator -> arithmeticOperator.calculate(operand1, operand2))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 사칙연산이 아닙니다."));
    }

}
