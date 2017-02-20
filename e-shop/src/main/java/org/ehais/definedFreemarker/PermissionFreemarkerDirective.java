package org.ehais.definedFreemarker;

import java.io.IOException;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class PermissionFreemarkerDirective implements TemplateDirectiveModel{

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		// TODO Auto-generated method stub
//		env.getOut().write(TemplateBooleanModel.FALSE);
//		DEFAULT_WRAPPER
//		env.setStrictBeanModels(TemplateBooleanModel.FALSE);
//		env.getOut().write("");
		body.render(env.getOut());
		
		
	}

}

//public class PermissionFreemarkerDirective implements TemplateMethodModel {
//
//	@Override
//	public Object exec(List arguments) throws TemplateModelException {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//
//
//
//}

