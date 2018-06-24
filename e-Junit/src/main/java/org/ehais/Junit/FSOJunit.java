package org.ehais.Junit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ehais.util.FSO;
import org.junit.Test;

public class FSOJunit {
	
	@Test
	public void list() throws FileNotFoundException, IOException {
		String filepath = "D:\\workspace-hbuilder\\appehais\\kindergarten";
		List<String> list = new ArrayList<String>();
		FSO.ReadfileList(list, filepath);
		for (String string : list) {
			
			System.out.println("<img src=\""+string.replace("D:\\workspace-hbuilder\\appehais\\kindergarten/", "kindergarten/")+"\"  />");
		}
	}

}
