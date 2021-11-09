package test.beantest;

import annotations.Bean;
import annotations.Qualifier;

@Bean
@Qualifier(value = "samsung")
public class SamsungPhone implements Phone {

    String brand = "Samsung";
    String model = "S21";

    public SamsungPhone() {
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public String getModel() {
        return model;
    }
}
