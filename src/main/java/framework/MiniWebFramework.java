package framework;

import annotations.Controller;
import annotations.GET;
import annotations.POST;
import annotations.Path;

import java.lang.reflect.Method;
import java.util.HashMap;

public class MiniWebFramework {

    private static final HashMap<String, Method> methodGetRoutes = new HashMap<>();
    private static final HashMap<String, Method> methodPostRoutes = new HashMap<>();

    public static void scanControllerMethods(Class cl) {
        if (cl.getAnnotation(Controller.class) != null) {

            Method[] methods = cl.getDeclaredMethods();

            for (Method m: methods) {
                if (m.isAnnotationPresent(Path.class)) {
                    if (m.isAnnotationPresent(GET.class)) {
                        methodGetRoutes.put(m.getAnnotation(Path.class).route(), m);
                    } else if (m.isAnnotationPresent(POST.class)) {
                        methodPostRoutes.put(m.getAnnotation(Path.class).route(), m);
                    }
                }
            }

        }
    }

}
