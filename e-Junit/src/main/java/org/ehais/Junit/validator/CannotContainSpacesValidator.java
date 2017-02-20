package org.ehais.Junit.validator;

import javax.validation.ConstraintValidator;    
import javax.validation.ConstraintValidatorContext;    
    
public class CannotContainSpacesValidator implements ConstraintValidator<CannotContainSpaces, String> {    
    private int len;    
    /**  
     * 初始参数,获取注解中length的值  
     */    
    @Override    
    public void initialize(CannotContainSpaces arg0) { 
    	System.out.println("arg0.toString():"+arg0.toString());
        this.len = arg0.length();  
        System.out.println(arg0.tableName()+" -- "+arg0.fieldName());
    }    
    
    @Override    
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {    
        if(str != null){    
            if(str.indexOf(" ") < 0){    
                return true;    
            }    
        }else{    
            constraintValidatorContext.disableDefaultConstraintViolation();//禁用默认的message的值    
            //重新添加错误提示语句    
            constraintValidatorContext    
            .buildConstraintViolationWithTemplate("字符串不能为空").addConstraintViolation();    
        }    
        return false;    
    }    
    
}    
