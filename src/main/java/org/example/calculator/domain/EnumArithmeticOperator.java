package org.example.calculator.domain;

import java.util.Arrays;

public enum EnumArithmeticOperator {
    ADDITION("+") {
        @Override
        public int arithmateCalculate(int operand1, int operand2) {
            return operand1 + operand2;
        }
    }, SUBSTRACTION("-") {
        @Override
        public int arithmateCalculate(int operand1, int operand2) {
            return operand1 - operand2;
        }
    }, MULTIPLICATION("*") {
        @Override
        public int arithmateCalculate(int operand1, int operand2) {
            return operand1 * operand2;
        }
    }, DIVISION("/") {
        @Override
        public int arithmateCalculate(int operand1, int operand2) {
            if (operand2 == 0) throw new IllegalArgumentException("0으로는 나눌 수 없습니다.");
            return operand1 / operand2;
        }
    };

    private final String operator;

    EnumArithmeticOperator(String operator) {
        this.operator = operator;
    }

    public abstract int arithmateCalculate(final int operand1, final int operand2);
    public static int calculate(int operand1, String operator, int operand2) {

        /**
         * Arithmetic Operator의 Enum값중 매개변수 operator와 일치하는 하나를 가져온다.
         */
        EnumArithmeticOperator arithmeticOperator
                = Arrays.stream(values()) /* values() : 현재 enum에 존재하는 모든 enum값 */
                .filter(v -> v.operator.equals(operator)) /* enum값의 operator 즉 String value값이 인자값 operator와 일치하는 조건 */
                .findFirst() /* 일치하는것 반환 */
                .orElseThrow(() -> new IllegalArgumentException("올바른 사칙연산이 아닙니다.")); /* 일치하는 값이 없다면 Exception 발생 */

        return arithmeticOperator.arithmateCalculate(operand1, operand2);
    }
}
