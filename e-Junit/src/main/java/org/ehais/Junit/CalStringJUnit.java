package org.ehais.Junit;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CalStringJUnit {

//    public static void main(String[] args) {  
//        String str = "(1+2)*3+9/5-5+8*3.9+7/3"; 
//        str = "((100*5)+50)*100/2";
//        ScriptEngineManager manager = new ScriptEngineManager();  
//        ScriptEngine engine = manager.getEngineByName("js");  
//        Object result = null;  
//        try {  
//            result = engine.eval(str);  
//        } catch (ScriptException e) {  
//            e.printStackTrace();  
//        }  
//  
//        System.out.println("结果类型:" + result.getClass().getName() + ",计算结果:" + result);  
//    } 
    
    
    
    public static void main(String[] args) {  
        String str = "(1+2)*3+9/5-5+8*3.9+7/3"; 
        str = "((100*5)+50)*100/2";
        ScriptEngineManager manager = new ScriptEngineManager();  
        ScriptEngine engine = manager.getEngineByName("js");  
        Double result = null;  
        try {  
            result = (Double)engine.eval(str);  
            System.out.println(result.intValue());
        } catch (ScriptException e) {  
            e.printStackTrace();  
        }  
  
        System.out.println("结果类型:" + result.getClass().getName() + ",计算结果:" + result);  
    } 
    
}
