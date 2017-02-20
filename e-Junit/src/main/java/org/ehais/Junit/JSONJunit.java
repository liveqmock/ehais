package org.ehais.Junit;

import org.ehais.model.ExtendEntity;

import net.sf.json.JSONObject;

public class JSONJunit {

	public static void main(String[] args) {
		ExtendEntity ent = new ExtendEntity();
		ent.setPassword("123456ok");
		
		JSONObject obj = JSONObject.fromObject(ent);
		System.out.println(obj.toString());
	}
}
