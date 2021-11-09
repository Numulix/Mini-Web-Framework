package test.beantest;

import annotations.Autowired;
import annotations.Component;

@Component
public class SomeClass {

    @Autowired(verbose = true)
    private SomeClassDependency someClassDependency;

}
