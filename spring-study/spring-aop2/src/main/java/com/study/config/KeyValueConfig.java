package com.study.config;

import com.study.annotation.EnableUtil;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KeyValueConfig implements ImportAware {

    private String userName;
    private Integer time;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        Map<String, Object> map = importMetadata.getAnnotationAttributes(SpringConfig.class.getName());
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(map);
        this.userName = attributes.getString("userName");
        this.time = attributes.getNumber("time");
        System.out.println(userName);
        System.out.println(time);
    }

}
