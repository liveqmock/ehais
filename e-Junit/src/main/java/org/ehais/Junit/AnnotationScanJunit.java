package org.ehais.Junit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;

public class AnnotationScanJunit {

    @Test  
    public void annotationScan() throws IllegalAccessException {  
//        Field field = null;  
//        try {  
//            field = ClassLoader.class.getDeclaredField("classes");  
//            field.setAccessible(true);  
//            Vector<Class> classes=(Vector<Class>) field.get(this.getClass().getClassLoader());  
//            List<Class> cl=new ArrayList<>(classes);  
//   
//            Iterator<Class> itor=cl.iterator();  
//            while(itor.hasNext()){  
//                Class c=itor.next();  
//                if(c.getAnnotation(RequestMapping.class)!=null){  
//                    RequestMapping req= (RequestMapping)c.getAnnotation(RequestMapping.class);  
//                    String[] bath=req.path().length>0?req.path():req.value();  
//                    if(bath.length==0){  
//                        continue ;  
//                    }  
//                    Method[] ms=c.getDeclaredMethods();  
//                    for(Method m : ms){  
//                        RequestMapping rm=m.getAnnotation(RequestMapping.class);  
//                        if(rm==null){  
//                            continue ;  
//                        }  
//                        String[] bath2=rm.path().length>0?rm.path():rm.value();  
//                        if(bath2.length==0){  
//                            continue ;  
//                        }  
//                        System.out.println(bath[0]+bath2[0]);  
//                    }  
//                }  
//            }  
//        } catch (NoSuchFieldException e) {  
//            e.printStackTrace();  
//        }  
    }  
    
}
