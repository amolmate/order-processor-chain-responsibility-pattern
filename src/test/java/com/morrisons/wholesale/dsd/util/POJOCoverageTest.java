package com.morrisons.wholesale.dsd.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.NoPublicFieldsExceptStaticFinalRule;
import com.openpojo.validation.rule.impl.NoStaticExceptFinalRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

/**
 * 
 * @author surajv
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class POJOCoverageTest {

	private static final String[] POJO_PKGS = { "com.morrisons.stock.config", "com.morrisons.stock.endpoint.vo",
			"com.morrisons.stock.model.external.stock", "com.morrisons.stock.entity", "com.morrisons.stock.exception",
			"com.morrisons.stock.vo", "com.morrisons.stock.service.file" };

	private List<PojoClass> pojoClasses;
	private ValidatorBuilder validatorBuilder;

	@Before
	public void setup() {

		pojoClasses = getPojoClasses();

		validatorBuilder = ValidatorBuilder.create();
		validatorBuilder.getRules().add(new NoStaticExceptFinalRule());
		validatorBuilder.getRules().add(new NoPublicFieldsExceptStaticFinalRule());
		validatorBuilder.getTesters().add(new SetterTester());
		validatorBuilder.getTesters().add(new GetterTester());
	}

	@Test
	public void testPojoStructureAndBehavior() {

		for (PojoClass pojoClass : pojoClasses) {
			validatorBuilder.build().validate(pojoClass);
		}
	}

	private List<PojoClass> getPojoClasses() {

		List<PojoClass> pojoClasses = new ArrayList<>();

		for (int i = 0; i < POJO_PKGS.length; i++) {
			pojoClasses.addAll(PojoClassFactory.getPojoClasses(POJO_PKGS[i], new FilterPackageInfo()));
		}

		return pojoClasses;
	}
}