package org.ehais.epublic.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = EUniqueValidator.class) //具体的实现    
@Target( { java.lang.annotation.ElementType.METHOD,java.lang.annotation.ElementType.FIELD })    
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
public @interface EUnique {
	String message() default "存在重复性的值"; //提示信息,可以写死,可以填写国际化的key    
    String tableName();
    String fieldName();
        
    //下面这两个属性必须添加    
    Class<?>[] groups() default {};    
    Class<? extends Payload>[] payload() default {};   
    
}
