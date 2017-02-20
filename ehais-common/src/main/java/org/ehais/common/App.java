package org.ehais.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String s = "%E8%BD%AF%E4%BB%B6";    	
        try {
			System.out.println( URLDecoder.decode(s, "utf-8") );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
