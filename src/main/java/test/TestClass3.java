package test;

import annotations.*;
import framework.request.Request;
import framework.response.JsonResponse;
import framework.response.Response;
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

    @Path(route = "/component")
    @GET
    public Response componentRes(Request req) {
        componentClass.setTestNum(2);
        Response res = new JsonResponse(componentClass);
        return res;
    }

    @Path(route = "/anotherClass")
    @GET
    public Response anotherPersonMethod(Request req) {
        personBean.setPrezime("Milovanovic");
        Response res = new JsonResponse(personBean);

        return res;
    }
}
