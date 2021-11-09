package test.beantest;

import annotations.Autowired;
import annotations.Bean;

@Bean(scope = "prototype")
public class BeanClassWithAutowired {

    @Autowired()
    private SomeClass someClass;

}
