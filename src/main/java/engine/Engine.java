package engine;


import annotations.Bean;
import annotations.Controller;
import annotations.Service;
import framework.MiniWebFramework;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public static void initAllClasses() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Class> allClasses = getClassesFromPackage("");
        for (Class cl : allClasses) {
            if (cl.isAnnotationPresent(Controller.class)) {
                System.out.println("!- Engine -! Klasa " + cl.getName() + " je kontroler");
                MiniWebFramework.addControllerClass(cl);
                controllerClassMap.put(cl.getName(), cl.getConstructor().newInstance());
            }

            if ((cl.isAnnotationPresent(Bean.class) && (((Bean) cl.getAnnotation(Bean.class)).scope().equals("singleton")))
                    || cl.isAnnotationPresent(Service.class)) {
                classMap.put(cl.getName(), cl.getConstructor().newInstance());
                System.out.println("!- Engine -! Napravljena nova instanca klase " + cl.getName());
            }
        }
    }

}
