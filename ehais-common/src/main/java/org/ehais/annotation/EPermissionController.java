package org.ehais.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface EPermissionController {

	/** 
     * 功能ID，该功能ID，对应xml中的功能ID 
     */  
    String value() default ""; 
    String name() default ""; 
    String type() default "url"; 
    
}
