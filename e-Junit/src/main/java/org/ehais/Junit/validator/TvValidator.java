package org.ehais.Junit.validator;


import java.util.Set;    
import javax.validation.ConstraintViolation;    
import javax.validation.Validation;    
import javax.validation.Validator;    
import javax.validation.ValidatorFactory; 


public class TvValidator {
    public static void main(String[] args) {    
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();    
        Validator validator = factory.getValidator();    
    
        Entity entity = new Entity();    
        entity.setAge(12);    
        entity.setName("admin"); 
        entity.setValue("AA BB");
        Set<ConstraintViolation<Entity>> constraintViolations = validator.validate(entity);    
        for (ConstraintViolation<Entity> constraintViolation : constraintViolations) {    
            System.out.println("对象属性:"+constraintViolation.getPropertyPath());    
            System.out.println("国际化key:"+constraintViolation.getMessageTemplate());    
            System.out.println("错误信息:"+constraintViolation.getMessage());    
        }    
    
    } 
}
