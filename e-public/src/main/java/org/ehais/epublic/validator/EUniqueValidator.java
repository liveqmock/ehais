package org.ehais.epublic.validator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.ECommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EUniqueValidator implements ConstraintValidator<EUnique, String> {
	
	@Autowired
	private ECommonMapper eCommonMapper;
	@Autowired
	private HttpServletRequest request;
	
    private String tableName;
    private String fieldName;
    private String actionType;
    /**  
     * 初始参数,获取注解中length的值  
     */    
    @Override    
    public void initialize(EUnique arg0) { 
    	tableName = arg0.tableName();
    	fieldName = arg0.fieldName();
    	actionType = arg0.actionType();
    }    
    
    @Override    
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {    
        
    	System.out.println(request.getRequestURI());
    			
    	if(str != null && !str.equals("")){
        	int c = eCommonMapper.commonUniqueStore(tableName, fieldName, str,(Integer)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID) );
            if(c <= 0){    
                return true;    
            }else{
            	return false;
            }
        }
//        else{    
//            constraintValidatorContext.disableDefaultConstraintViolation();//禁用默认的message的值    
//            //重新添加错误提示语句    
//            constraintValidatorContext.buildConstraintViolationWithTemplate("字符串不能为空").addConstraintViolation();    
//        }    
        return true;    
    }
  
    
}    

