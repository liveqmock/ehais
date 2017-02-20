package org.ehais.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.ehais.enums.ControlEnum;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface EColumn {	
	public String control() default ControlEnum.text;
	public String name() default "";//字段名称
	public String label() default "";//中文描述名
	public int tabno() default 0;//在哪个tab里面
	public boolean listshow() default false;//是否在列表展示
	public String prompt() default "";
	
	

	
}

