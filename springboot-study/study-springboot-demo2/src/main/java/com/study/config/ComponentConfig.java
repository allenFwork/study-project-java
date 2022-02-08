package com.study.config;

import com.study.component.CustomizedBeanDefinitionRegister;
import com.study.component.CustomizedImportSelector;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 添加的组件的配置类
 */
@Configuration
@Import(value = {CustomizedImportSelector.class, CustomizedBeanDefinitionRegister.class})
public class ComponentConfig {

}
