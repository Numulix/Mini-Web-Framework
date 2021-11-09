package test;

import annotations.Autowired;
import annotations.Controller;
import annotations.Qualifier;
import test.beantest.*;

@Controller
public class TestClass3 {

    @Autowired(verbose = true)
    private PersonBean personBean;

    @Autowired(verbose = true)
    @Qualifier(value = "samsung")
    private Phone samsungPhone;

    @Autowired(verbose = true)
    private ServiceClass serviceClass;

    @Autowired(verbose = true)
    private ComponentClass componentClass;

    @Autowired(verbose = true)
    private BeanClassWithAutowired beanClassWithAutowired;

    public TestClass3() {
    }
}
