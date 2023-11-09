package org.example.counter;

/**
 * 예상치 못한 값이 발생된다.
 * 싱글톤 객체에서 상태를 유지하기위해 설계하게 되면 문제가 발생할 수 있다.
 * 싱글톤 객체이며 멀티스레드 환경에서 하나의 객체(자원)을 공유하게 되면, 우리가 뜻하지 않는 레이스 컨디션
 * 즉, 원치않는 결과가 나올 수 있다.
 * [레이스컨디션] : 여러 프로세스 혹은 스레드가 동시에 하나의 자원에 접근하기 위해 경쟁하는 상태
 */
public class RaceConditionDemo {
    public static void main(String[] args) {
        Counter counter = new Counter();

        Thread t1 = new Thread(counter, "Thread-1");
        Thread t2 = new Thread(counter, "Thread-2");
        Thread t3 = new Thread(counter, "Thread-3");

        t1.start();
        t2.start();
        t3.start();
    }
}
