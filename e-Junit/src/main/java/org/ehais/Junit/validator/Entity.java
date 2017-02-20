package org.ehais.Junit.validator;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.Length;


public class Entity {
	@Max(value=3)//最大值为3    
    private int age;    
    @Length(max=1) //字符串长度最大为1,hibernate 扩展的    
    private String name;    
    @CannotContainSpaces(message="不能包含空格",tableName="hai_brand",fieldName="brand_name")
    private String value;
    
    public int getAge() {    
        return age;    
    }    
    public void setAge(int age) {    
        this.age = age;    
    }    
    public String getName() {    
        return name;    
    }    
    public void setName(String name) {    
        this.name = name;    
    }
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}  
    
}
