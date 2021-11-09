package test;

import annotations.Autowired;
import annotations.Controller;
import test.beantest.PersonBean;

@Controller
public class TestClass3 {

    @Autowired(verbose = true)
    private PersonBean personBean;

    public TestClass3() {
    }
}
