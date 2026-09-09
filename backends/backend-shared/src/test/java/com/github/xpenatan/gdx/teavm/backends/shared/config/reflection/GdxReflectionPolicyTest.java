package com.github.xpenatan.gdx.teavm.backends.shared.config.reflection;

import com.github.xpenatan.gdx.teavm.backends.shared.config.plugin.TeaReflectionSupplier;
import org.junit.Assert;
import org.junit.Test;

public class GdxReflectionPolicyTest {

    @Test
    public void isRegisteredReflectionClass_matchesRegisteredClass() {
        TeaReflectionSupplier.addReflectionClass("com.example.registered.Style");

        Assert.assertTrue(GdxReflectionPolicy.isRegisteredReflectionClass("com.example.registered.Style"));
    }

    @Test
    public void isRegisteredReflectionClass_matchesNestedClassesWhenOuterClassRegistered() {
        TeaReflectionSupplier.addReflectionClass("com.example.nested.TextButton");

        Assert.assertTrue(GdxReflectionPolicy.isRegisteredReflectionClass(
                "com.example.nested.TextButton$TextButtonStyle"));
        Assert.assertTrue(GdxReflectionPolicy.isRegisteredReflectionClass(
                "com.example.nested.TextButton$TextButtonStyle$Inner"));
    }

    @Test
    public void isRegisteredReflectionClass_rejectsClassWhoseNameExtendsARegisteredName() {
        TeaReflectionSupplier.addReflectionClass("com.example.prefix.Bar");

        Assert.assertFalse(GdxReflectionPolicy.isRegisteredReflectionClass("com.example.prefix.BarBaz"));
    }

    @Test
    public void isRegisteredReflectionClass_rejectsUnregisteredClass() {
        Assert.assertFalse(GdxReflectionPolicy.isRegisteredReflectionClass("com.example.unregistered.Foo"));
    }
}
