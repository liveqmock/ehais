package org.ehais.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.METHOD)
public @interface EPermissionMethod {

	/** 
     * 功能ID，该功能ID，对应xml中的功能ID 
     */  
    String value() default ""; 
    String name() default ""; //按钮名称
    String type() default "url"; 
    String intro() default "";//描述，用于系统操作日志的描述 
}
