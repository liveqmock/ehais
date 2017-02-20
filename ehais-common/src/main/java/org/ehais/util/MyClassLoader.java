package org.ehais.util;

import java.net.URI;
import java.util.Arrays;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

public class MyClassLoader extends ClassLoader {
    @Override
    public Class<?> findClass(String str) throws ClassNotFoundException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // 用于诊断源代码编译错误的对象
        DiagnosticCollector diagnostics = new DiagnosticCollector();
        // 内存中的源代码保存在一个从JavaFileObject继承的类中
        JavaFileObject file = new JavaSourceFromString("Temp", str.toString());
        // System.out.println(file);
        Iterable compilationUnits = Arrays.asList(file);
        // 关于报：Exception in thread "main" java.lang.ClassNotFoundException: Temp
        // 的解决方法：http://willam2004.iteye.com/blog/1026454
        // 需要为compiler.getTask方法指定编译路径：
        // 执行过程如下：
        // 1、定义类的字符串表示。
        // 2、编译类
        // 3、加载编译后的类
        // 4、实例化并进行调用。
        // 在eclipse下如果按照上述的方式进行调用，会在第三步中加载编译的类过程抛出“ClassNotFoundException”。
        // 因为默认的Eclipse的java工程编译后的文件是放在当前工程下的bin目录下。而第二步编译输出的路径是工程目录下,
        // 所以加载时会抛出类找不到的错误。
        String flag = "-d";
        String outDir = System.getProperty("user.dir") + "/" + "bin";
        Iterable<String> stringdir = Arrays.asList(flag, outDir); // 指定-d dir 参数
        // 建立一个编译任务
        JavaCompiler.CompilationTask task = compiler.getTask(null, null, null,
                stringdir, null, compilationUnits);
        // 编译源程序
        boolean result = task.call();
        if (result) {
            return Class.forName("Temp");
        }
        return null;
    }

}

class JavaSourceFromString extends SimpleJavaFileObject {
    private String name;
    private String code;

    public JavaSourceFromString(String name, String code) {
        super(URI.create("string:///" + name.replace('.', '/')
                + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
}

