package com.study.component;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 向容器中添加组件方法一：
 * 通过实现 org.springframework.context.annotation.ImportSelector接口,
 * 重写selectImports方法，该方法返回自定义组件的全类名
 */
public class CustomizedImportSelector implements ImportSelector {

    /**
     * 返回自定义组件的全类名路径
     * @param annotationMetadata
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.study.component.CustomizedComponent"};
    }

}
