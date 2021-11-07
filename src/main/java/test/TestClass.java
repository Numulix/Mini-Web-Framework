package test;

import annotations.Controller;
import annotations.GET;
import annotations.POST;
import annotations.Path;

@Controller
public class TestClass {

    public TestClass() {

    }

    @Path(route = "/test1")
    @GET
    public void testMethod1() {
        System.out.println("Poziv iz metode testMethod1");
    }

    @Path(route = "/test2")
    @POST
    public void testMethod2() {
        System.out.println("Poziv iz metode testMethod2");
    }

}
