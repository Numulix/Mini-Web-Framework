package engine;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Predstavlja Dependency Injection Engine (DI Engine)
public class Engine {

    public static final List<Class> getClassesFromPackage(String pkgName) {
        String path = pkgName.replaceAll("//.", File.separator);
        List<Class> classList = new ArrayList<>();

        // Lista lokacija sa .jar fajlovima i class folderom koji je u class path-u
        String[] classPathDirs = System.getProperty("java.class.path").split(System.getProperty("path.separator"));

        String name;
        for (String entry: classPathDirs) {
            // Ne ucitavamo klase sa .jar fajlova
            if (!entry.endsWith(".jar")) {
                try {
                    File base = new File(entry + File.separatorChar + path);
                    for (File file: base.listFiles()) {
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

}
