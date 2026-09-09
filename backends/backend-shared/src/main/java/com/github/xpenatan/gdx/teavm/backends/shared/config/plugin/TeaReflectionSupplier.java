package com.github.xpenatan.gdx.teavm.backends.shared.config.plugin;

import com.github.xpenatan.gdx.teavm.backends.shared.config.TeaLogHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TeaReflectionSupplier {

    public static boolean printDebugLogs = false;

    private static final List<String> DEFAULT_REFLECTION_PATTERNS = Collections.unmodifiableList(Arrays.asList(
            "com.badlogic.gdx.scenes.scene2d.**",
            "net.mgsx.gltf.data.**",
            "com.badlogic.gdx.utils.Array",
            "com.badlogic.gdx.utils.ArrayMap",
            "com.badlogic.gdx.utils.IntIntMap",
            "com.badlogic.gdx.utils.IntMap",
            "com.badlogic.gdx.utils.IntSet",
            "com.badlogic.gdx.utils.LongMap",
            "com.badlogic.gdx.utils.ObjectFloatMap",
            "com.badlogic.gdx.utils.ObjectIntMap",
            "com.badlogic.gdx.utils.ObjectMap",
            "com.badlogic.gdx.utils.ObjectSet",
            "com.badlogic.gdx.utils.Queue"
    ));

    private static ArrayList<String> clazzList = new ArrayList();

    public static List<String> getDefaultReflectionPatterns() {
        return DEFAULT_REFLECTION_PATTERNS;
    }

    public static void addReflectionClass(Class<?> type) {
        addReflectionClass(type.getName());
    }

    public static void addReflectionClass(ArrayList<String> classes) {
        addReflectionClass((Collection<String>)classes);
    }

    public static void addReflectionClass(Collection<String> classes) {
        if(printDebugLogs) {
            TeaLogHelper.logHeader("ADD REFLECTION CLASSES: " + classes.size());
        }
        for(String className : classes) {
            addReflectionClass(className);
        }
        if(printDebugLogs) {
            TeaLogHelper.logEnd();
        }
    }

    public static List<String> getReflectionClasses() {
        return clazzList;
    }

    public static boolean containsReflection(String className) {
        for(int i = 0; i < clazzList.size(); i++) {
            String reflectionClass = clazzList.get(i);
            if(className.contains(reflectionClass))
                return true;
        }
        return false;
    }

    /**
     * Full class name. Use config reflectionListener for more control.
     */
    public static void addReflectionClass(String className) {
        if(!clazzList.contains(className)) {
            clazzList.add(className);
            if(printDebugLogs) {
                TeaLogHelper.log("Added [Reflection] " + className);
            }
        }
        else if(printDebugLogs) {
            TeaLogHelper.log("Skipped duplicate [Reflection] " + className);
        }
    }

    /**
     * Must be called after {@code TeaBuilder.build}.
     */
    public static void printReflectionClasses() {
        TeaLogHelper.logHeader("REFLECTION CLASSES: " + clazzList.size());
        for(String reflectionClass : clazzList) {
            TeaLogHelper.log(reflectionClass);
        }
        TeaLogHelper.logEnd();
    }
}
