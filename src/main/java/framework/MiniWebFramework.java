package framework;

import annotations.GET;
import annotations.POST;
import annotations.Path;

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

}
