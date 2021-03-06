package net.stackoverflow.spectre.demo;

import java.util.Random;

public class Math {

    public Wrapper add(Double a, Double b) {
        return new Wrapper(a + b);
    }

    public Wrapper sub(Double a, Double b) {
        return new Wrapper(a - b);
    }

    public static void main(String[] args) {
        Math math = new Math();
        while (true) {
            Random random = new Random();
            Double a = random.nextDouble();
            Double b = random.nextDouble();
            System.out.println("a: " + a + ", b: " + b + ", result: " + math.add(a, b));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
