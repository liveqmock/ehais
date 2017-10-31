package org.ehais.definedFreemarker;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.cache.ERolePermissionCacheManager;
import org.ehais.common.EConstants;
import org.springframework.beans.factory.annotation.Autowired;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class PermissionFreemarkerDirective implements TemplateDirectiveModel{

	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		// TODO Auto-generated method stub
		Writer out = env.getOut();
		Object url = params.get("url");
//		System.out.println("权限地址："+url.toString());
		
		String role = (String) request.getSession().getAttribute(EConstants.SESSION_ROLE_ID_ARRAY);
//		System.out.println("role ..."+role);
		List<String> result = Arrays.asList(StringUtils.split(role,","));  
		for (String string : result) {
//			System.out.println("role : "+ string);
			if(ERolePermissionCacheManager.checkRolePermission(Integer.valueOf(string), url.toString())){
				body.render(out);
				return;
			}
		}
		
	}

	
//	@Override
//	public void execute(Environment env, Map<String,String> params, TemplateModel[] loopVars, TemplateDirectiveBody body)
//			throws TemplateException, IOException {
//		// TODO Auto-generated method stub
////		env.getOut().write(TemplateBooleanModel.FALSE);
////		DEFAULT_WRAPPER
////		env.setStrictBeanModels(TemplateBooleanModel.FALSE);
////		env.getOut().write("");
//		
//		
//		//将模版里的数字参数转化成int类型的方法，，其它类型的转换请看freemarker文档
//        TemplateModel paramValue = (TemplateModel) params.get("url");
////        String url = ((Template) paramValue).getAsNumber();
//        
//        
//		body.render(env.getOut());
//		
//		
//	}

}



