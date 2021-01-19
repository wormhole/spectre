package net.stackoverflow.spectre.demo;

public class Demo {

    public static void main(String[] args) {
        Person person = new Person();
        while (true) {
            person.say("world");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
