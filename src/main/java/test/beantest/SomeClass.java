package test.beantest;

import annotations.Autowired;
import annotations.Component;

@Component
public class SomeClass {

    private int firstNumber = 1337;
    private int secondNumber = 42069;

    @Autowired(verbose = true)
    private SomeClassDependency someClassDependency;

    public SomeClass() {
    }
}
