package test;

import annotations.*;
import framework.request.Request;
import framework.response.JsonResponse;
import framework.response.Response;
import test.beantest.Phone;

@Controller
public class TestClass {

    @Autowired(verbose = true)
    @Qualifier("samsung")
    private Phone sPhone;

    @Autowired(verbose = true)
    @Qualifier("xiaomi")
    private Phone xPhone;

    public TestClass() {

    }

    @Path(route = "/test1")
    @GET
    public Response testMethod1(Request req) {
        System.out.println("Poziv iz metode testMethod1");
        Response res = new JsonResponse(sPhone);

        return res;
    }

    @Path(route = "/test2")
    @POST
    public Response testMethod2(Request req) {
        System.out.println("Poziv iz metode testMethod2");

        Response res = new JsonResponse(xPhone);
        return res;
    }

}
