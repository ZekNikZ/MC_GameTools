package dev.mattrm.mc.gametools.annotations;

import org.atteo.classindex.IndexAnnotated;
import org.bukkit.command.CommandExecutor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@IndexAnnotated
public @interface GTCommand {
    String plugin();
    String[] commands() default {};

    record Data(String name, CommandExecutor executor) { }
}
