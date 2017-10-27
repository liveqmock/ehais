package org.ehais.Junit;

import java.text.DecimalFormat;

import org.junit.Test;

public class NumberJUnit {

	@Test
	public void format(){
		DecimalFormat    df   = new DecimalFormat("##0.00");   
		
		Double d = 1d;
		d = d / 100;
		
		System.out.println(df.format(d));
	}
}
