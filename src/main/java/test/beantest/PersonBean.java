package test.beantest;

import annotations.Autowired;
import annotations.Bean;

@Bean()
public class PersonBean {

    private int age = 22;
    private int height = 175;
    private String name = "Pera";
    private String prezime = "Peric";

    @Autowired(verbose = true)
    private AdressBean adresa;

    public PersonBean() {
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setAdresa(AdressBean adresa) {
        this.adresa = adresa;
    }
}
