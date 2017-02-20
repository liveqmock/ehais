package org.ehais.Junit;

import java.util.Date;

import org.ehais.model.ExtendEntity;
import org.ehais.model.SimpleEntity;
import org.ehais.utils.ValidationResult;
import org.ehais.utils.ValidationUtils;
import org.junit.Assert;
import org.junit.Test;

public class ValidationUtilsTest {

	@Test
	public void validateSimpleEntity() {
		SimpleEntity se = new SimpleEntity();
		se.setDate(new Date());
		se.setEmail("123");
		se.setName("123");
		se.setPassword("123");
		se.setValid(false);
		ValidationResult result = ValidationUtils.validateEntity(se);
		System.out.println("--------------------------");
		System.out.println(result);
		Assert.assertTrue(result.isHasErrors());
	}

	@Test
	public void validateSimpleProperty() {
		SimpleEntity se = new SimpleEntity();
		ValidationResult result = ValidationUtils.validateProperty(se, "name");
		System.out.println("--------------------------");
		System.out.println(result);
		Assert.assertTrue(result.isHasErrors());
	}

	@Test
	public void validateExtendEntity() {
		ExtendEntity ee = new ExtendEntity();
		ee.setPassword("212");
		ValidationResult result = ValidationUtils.validateEntity(ee);
		System.out.println("--------------------------");
		System.out.println(result);
		Assert.assertTrue(result.isHasErrors());
	}

}
