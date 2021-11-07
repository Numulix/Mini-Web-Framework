package engine;

import java.util.HashMap;

public class DContainer {

    private HashMap<String, Class> qualifiers = new HashMap<>();

    public DContainer() {  }

    public HashMap<String, Class> getQualifiers() {
        return qualifiers;
    }
}
