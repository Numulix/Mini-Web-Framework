package test;

import annotations.Controller;
import annotations.GET;
import annotations.POST;
import annotations.Path;

@Controller
public class TestClass2 {

    public TestClass2() {

    }

    @Path(route = "/testGet1")
    @GET
    public void testGet1() {
        System.out.println("Poziv iz metode testGet1");
    }

    @Path(route = "/testGet2")
    @GET
    public void testGet2() {
        System.out.println("Poziv iz metode testGet2");
    }

    @Path(route = "/testPost")
    @POST
    public void testPost() {
        System.out.println("Poziv iz metode testPost");
    }

}
