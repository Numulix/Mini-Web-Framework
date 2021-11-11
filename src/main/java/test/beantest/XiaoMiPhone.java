package test.beantest;

import annotations.Bean;
import annotations.Qualifier;

@Bean
@Qualifier("xiaomi")
public class XiaoMiPhone implements Phone {

    String brand = "XiaoMi";
    String model = "Redmi 9";

    public XiaoMiPhone() {
    }

    @Override
    public String getBrand() {
        return null;
    }

    @Override
    public String getModel() {
        return null;
    }
}
