package framework;

import annotations.GET;
import annotations.POST;
import annotations.Path;
import engine.Engine;
import framework.request.Request;
import framework.response.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MiniWebFramework {

    public static final List<Class> controllerClasses = new ArrayList<>();
    public static final HashMap<String, Method> methodGetRoutes = new HashMap<>();
    public static final HashMap<String, Method> methodPostRoutes = new HashMap<>();

    public static void addControllerClass(Class cl) {
        controllerClasses.add(cl);
        System.out.println("!- MiniWebFramework -! Dodata klasa " + cl.getName() + " u kontrolere");
        Method[] methods = cl.getDeclaredMethods();

        for (Method m: methods) {
            if (m.isAnnotationPresent(Path.class)) {
                String route = m.getAnnotation(Path.class).route();
                if (m.isAnnotationPresent(GET.class)) {
                    methodGetRoutes.put(route, m);
                    System.out.println("!- MiniWebFramework -! Metoda " + m.getName() + " dodata u GET metode");
                } else if (m.isAnnotationPresent(POST.class)) {
                    methodPostRoutes.put(route, m);
                    System.out.println("!- MiniWebFramework -! Metoda " + m.getName() + " dodata u POST metode");
                }
            }
        }
    }

    public static Response processRequest(Request req) throws InvocationTargetException, IllegalAccessException {
        switch (req.getMethod().toString()) {
            case "GET":
                Method mGet = methodGetRoutes.get(req.getLocation());
                return (Response) mGet.invoke(Engine.controllerClassMap.get(mGet.getDeclaringClass().getName()), req);
            case "POST":
                Method mPost = methodPostRoutes.get(req.getLocation());
                return (Response) mPost.invoke(Engine.controllerClassMap.get(mPost.getDeclaringClass().getName()), req);
        }
        return null;
    }

}
