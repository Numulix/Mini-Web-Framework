package engine;


import annotations.*;
import framework.MiniWebFramework;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Predstavlja Dependency Injection Engine (DI Engine)
public class Engine {

    public static HashMap<String, Object> classMap = new HashMap<>();
    public static HashMap<String, Object> controllerClassMap = new HashMap<>();
    public static DContainer dc = new DContainer();

    public static final List<Class> getClassesFromPackage(String pkgName) {
        String path = pkgName.replaceAll("//.", File.separator);
        List<Class> classList = new ArrayList<>();

        // Lista lokacija sa .jar fajlovima i class folderom koji je u class path-u
        String[] classPathDirs = System.getProperty("java.class.path").split(System.getProperty("path.separator"));

        String name;
        for (String entry : classPathDirs) {
            // Ne ucitavamo klase sa .jar fajlova
            if (!entry.endsWith(".jar")) {
                try {
                    File base = new File(entry + File.separatorChar + path);
                    for (File file : base.listFiles()) {
                        name = file.getName();
                        if (name.endsWith(".class")) {
                            name = name.replaceFirst(".class", "");
                            pkgName = pkgName.replace("\\", ".");
                            classList.add(Class.forName(pkgName + "." + name));
                        } else {
                            String tmpPkgName = pkgName.equals("") ? name : pkgName + "\\" + name;
                            List<Class> dirSubClasses = getClassesFromPackage(tmpPkgName);

                            classList.addAll(dirSubClasses);
                        }
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }

        return classList;
    }

    public static void initAllClasses() throws Exception {
        List<Class> allClasses = getClassesFromPackage("");
        for (Class cl : allClasses) {
            if (cl.isAnnotationPresent(Controller.class)) {
                System.out.println("!- Engine -! Klasa " + cl.getName() + " je kontroler");
                MiniWebFramework.addControllerClass(cl);
                controllerClassMap.put(cl.getName(), cl.getConstructor().newInstance());
                classMap.put(cl.getName(), cl.getConstructor().newInstance());
            }

            if ((cl.isAnnotationPresent(Bean.class) && (((Bean) cl.getAnnotation(Bean.class)).scope().equals("singleton")))
                    || cl.isAnnotationPresent(Service.class)) {
                classMap.put(cl.getName(), cl.getConstructor().newInstance());
                System.out.println("!- Engine -! Napravljena nova instanca klase " + cl.getName());
            }

            if (cl.isAnnotationPresent(Qualifier.class)) {

                if (checkIfBeanServiceComponent(cl)) {
                    Qualifier q = (Qualifier) cl.getDeclaredAnnotation(Qualifier.class);
                    String value = q.value();

                    if (dc.getQualifiers().get(value) != null) {
                        try {
                            throw new Exception("Class already has qualifier");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    dc.getQualifiers().put(value, cl);
                }
            }
        }

        for (Map.Entry<String, Object> entry: controllerClassMap.entrySet()) {
            injectObjectAnnotations(entry.getValue());
        }
    }

    private static boolean checkIfBeanServiceComponent(Class cl) {
        return cl.isAnnotationPresent(Bean.class) || cl.isAnnotationPresent(Service.class) || cl.isAnnotationPresent(Component.class);
    }

    public static void injectObjectAnnotations(Object obj) throws Exception {
        Class cl = obj.getClass();
        Field[] fields = cl.getDeclaredFields();

        for (Field f: fields) {
            if (f.isAnnotationPresent(Autowired.class)) {

                if (f.getType().isInterface() && !f.isAnnotationPresent(Qualifier.class)) {
                    throw new Exception("Interface attribute is annotated with @Autowired without @Qualifier annotation");
                }

                // Gledamo da li je atribut klasa koja nije primitivna
                if (!f.getType().isInterface() && !f.getType().isPrimitive())  {
                    Boolean isVerbose = f.getAnnotation(Autowired.class).verbose();
                    Object o = null;

                    int accessor = f.getModifiers();
                    f.setAccessible(true);

                    Class type = f.getType();
                    o = getSingletonOrNewInstance(type);

                    if (o != null) {
                        f.set(obj, o);
                        if (isVerbose) {
                            System.out.println("Initialized " + type.getName() + " " + f.getName() + " " +
                                    "in " + obj.getClass().getName() + " on " + LocalDateTime.now() +
                                    " with " + f.hashCode());
                        }
                        injectObjectAnnotations(o);
                    }

                    f.setAccessible(Modifier.toString(accessor).equals("public"));
                    // Atribut koji je interfejs mora biti anotiran i sa Qualifier
                } else if (f.getType().isInterface() && f.isAnnotationPresent(Qualifier.class)) {
                    String qValue = f.getAnnotation(Qualifier.class).value();

                    if (dc.getQualifiers().get(qValue) == null) {
                        throw new Exception("No bean for qualifier");
                    }

                    Boolean isVerbose = f.getAnnotation(Autowired.class).verbose();
                    Object o = null;

                    int accessor = f.getModifiers();
                    f.setAccessible(true);

                    Class type = dc.getQualifiers().get(qValue);
                    o = getSingletonOrNewInstance(type);

                    if (o != null) {
                        f.set(obj, o);
                        if (isVerbose) {
                            System.out.println("Initialized " + type.getName() + " " + f.getName() + " " +
                                    "in " + obj.getClass().getName() + " on " + LocalDateTime.now() +
                                    " with " + f.hashCode());
                        }
                        injectObjectAnnotations(o);
                    }
                    f.setAccessible(Modifier.toString(accessor).equals("public"));
                }

            }
        }
    }

    public static Object getSingletonOrNewInstance(Class type) throws Exception {
        Object returnValue = null;

        if (type.isAnnotationPresent(Bean.class)) {
            String scope = ((Bean)type.getAnnotation(Bean.class)).scope();
            if (scope.equals("singleton")) returnValue = classMap.get(type.getName());
            else if (scope.equals("prototype")) returnValue = type.getConstructor().newInstance();
        } else if (type.isAnnotationPresent(Service.class)) {
            String scope = ((Service)type.getAnnotation(Service.class)).scope();
            if (scope.equals("singleton")) returnValue = classMap.get(type.getName());
            else if (scope.equals("prototype")) returnValue = type.getConstructor().newInstance();
        } else if (type.isAnnotationPresent(Component.class)) {
            String scope = ((Component)type.getAnnotation(Component.class)).scope();
            if (scope.equals("prototype")) returnValue = type.getConstructor().newInstance();
            else if (scope.equals("singleton")) returnValue = classMap.get(type.getName());
        }

        return returnValue;
    }

}
