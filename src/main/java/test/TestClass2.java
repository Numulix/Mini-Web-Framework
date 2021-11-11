package test;

import annotations.*;
import framework.request.Request;
import framework.response.JsonResponse;
import framework.response.Response;
import test.beantest.ComponentClass;
import test.beantest.PersonBean;
import test.beantest.ServiceClass;
import test.beantest.SomeClass;

@Controller
public class TestClass2 {

    @Autowired(verbose = true)
    private PersonBean personBean;

    @Autowired
    private SomeClass someClass;

    @Autowired(verbose = true)
    private ServiceClass serviceClass;

    @Autowired
    private ComponentClass componentClass;

    public TestClass2() {

    }

    @Path(route = "/testGet1")
    @GET
    public Response testGet1(Request req) {
        System.out.println("Poziv iz metode testGet1");
        Response res = new JsonResponse(personBean);

        return res;
    }

    @Path(route = "/testGet2")
    @GET
    public Response testGet2(Request req) {
        System.out.println("Poziv iz metode testGet2");
        Response res = new JsonResponse(someClass);

        return res;
    }

    @Path(route = "/testPost")
    @POST
    public Response testPost(Request req) {
        System.out.println("Poziv iz metode testPost");
        Response res = new JsonResponse(serviceClass);

        return res;
    }

    @Path(route = "/testComponent")
    @GET
    public Response componentResponse(Request req) {
        Response res = new JsonResponse(componentClass);

        return res;
    }

    @Path(route = "/changePerson")
    @POST
    public Response changePerson(Request req) {
        personBean.setAge(69);
        personBean.setName("Mika");
        personBean.setPrezime("Mikic");
        Response res = new JsonResponse(personBean);

        return res;
    }

}
