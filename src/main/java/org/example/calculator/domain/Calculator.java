package org.example.calculator.domain;

import org.example.calculator.tobe.*;

import java.util.List;

public class Calculator {

    private static final List<ArithmeticOperator> arithmeticOperators
            = List.of(
            new AdditionOperator()
            , new SubtractionOperator()
            , new MultiplicationOperator()
            , new DivisionOperator()
    );

    public static int calculate(PositiveNumber operand1, String operator, PositiveNumber operand2) {

        /* List.of를 통해 주입받은 4개의 Operator 구현체 객체들 중 매개변수 operator에 해당하는 실제 구현체를 필터링 */
        return arithmeticOperators.stream()
                .filter(arithmeticOperator -> arithmeticOperator.supports(operator))
                .map(arithmeticOperator -> arithmeticOperator.calculate(operand1, operand2))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 사칙연산이 아닙니다."));
    }

}
