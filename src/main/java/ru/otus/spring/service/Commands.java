package ru.otus.spring.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class Commands {

    private final StudentTestService sts;
    private final ConsoleService cs;

    Commands(StudentTestService studentTestService, ConsoleService consoleService){
        sts = studentTestService;
        cs = consoleService;
    }

    @ShellMethod(value = "start-test", key = "st")
    public void startTest() {
        sts.testStart();
    }

    @ShellMethod(value = "print-result", key = "pr")
    public void printResult() {
        List<String> resultList = sts.getAnswersHistory();
        for (String myResponeToQuestion : resultList) {
            cs.println(myResponeToQuestion);
        }
    }
}
