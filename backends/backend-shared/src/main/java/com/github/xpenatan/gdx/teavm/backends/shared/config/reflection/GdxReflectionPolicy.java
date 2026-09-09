package com.github.xpenatan.gdx.teavm.backends.shared.config.reflection;

import com.github.xpenatan.gdx.teavm.backends.shared.config.plugin.TeaReflectionSupplier;
import java.util.List;
import org.teavm.extension.spi.reflection.SimpleReflectionPolicy;

/**
 * Declares the TeaVM reflection metadata for the classes registered in {@link TeaReflectionSupplier}:
 * lookup by name, instance fields and the no-arg constructor. That is the reflection surface libGDX
 * uses ({@code Json}/{@code Skin} instantiate a class through its no-arg constructor and fill it
 * field by field, and {@code Pools} instantiates the same way). Applications that reflectively call
 * methods or other constructors can declare them with their own {@link SimpleReflectionPolicy}.
 */
public class GdxReflectionPolicy extends SimpleReflectionPolicy {

    @Override
    protected void setup() {
        selectClasses(cls -> isRegisteredReflectionClass(cls.name()))
                .foundByName()
                .reflectableFields(INSTANCE)
                .reflectableMethods(withParameterCount(0).and(named("<init>")));
    }

    static boolean isRegisteredReflectionClass(String className) {
        List<String> reflectionClasses = TeaReflectionSupplier.getReflectionClasses();
        String currentClassName = className;
        while(true) {
            if(reflectionClasses.contains(currentClassName)) {
                return true;
            }
            int nestedIndex = currentClassName.lastIndexOf('$');
            if(nestedIndex < 0) {
                return false;
            }
            currentClassName = currentClassName.substring(0, nestedIndex);
        }
    }
}
