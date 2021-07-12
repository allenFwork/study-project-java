package com.study.annotation;

import com.study.imports.MyImportBeanDefinitionRegistrar;
import com.study.imports.MyImportSelector;
import com.study.imports.NormalClass;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Import({NormalClass.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AopStart {

}
