package test;

import annotations.Autowired;
import annotations.Controller;
import annotations.Qualifier;
import test.beantest.PersonBean;
import test.beantest.Phone;

@Controller
public class TestClass3 {

    @Autowired(verbose = true)
    private PersonBean personBean;

    @Autowired(verbose = true)
    @Qualifier(value = "samsung")
    private Phone samsungPhone;

    public TestClass3() {
    }
}
