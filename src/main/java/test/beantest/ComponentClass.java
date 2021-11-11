package test.beantest;

import annotations.Component;

@Component
public class ComponentClass {

    private int testNum = 1;

    public ComponentClass() {
    }

    public void setTestNum(int testNum) {
        this.testNum = testNum;
    }
}
