package ru.otus.spring.service;

import java.util.Scanner;

public class ConsoleServiceImpl implements ConsoleService {

    private static final Scanner scanner = new Scanner(System.in);

    public String read() {
        return scanner.nextLine();
    }

    public void print(String msg) {
        System.out.print(msg);
    }

    public void println(String msg) {
        System.out.println(msg);
    }
}
