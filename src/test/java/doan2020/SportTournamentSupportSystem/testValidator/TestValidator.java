package doan2020.SportTournamentSupportSystem.testValidator;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import doan2020.SportTournamentSupportSystem.validator.Validator;

public class TestValidator {

	@Test
	public void testFormatDateToString() {
		Validator validator = new Validator();
		
		Date date = new Date(2020 - 1900, 7 - 1, 12);
		String expectedStringDate = "2020-07-12";
		String actualStringDate = validator.formatDateToString(date);
		Assert.assertEquals(expectedStringDate, actualStringDate);
	}
	
	@Test
	public void testFormatStringToDate() {
		Validator validator = new Validator();
		
		String date = "2020-07-12";
		Date expectedDate = new Date(2020 - 1900, 7 - 1, 12);
		Date actualDate = validator.formatStringToDate(date);
		Assert.assertEquals(expectedDate, actualDate);
	}
	
	@Test
	public void testFormatStringToDateCaseInvalidInput() {
		Validator validator = new Validator();
		
		String date = "text";
		Date unexpectedDate = new Date(2020 - 1900, 7 - 1, 12);
		Date actualDate = validator.formatStringToDate(date);
		Assert.assertNotEquals(unexpectedDate, actualDate);
	}
	
	@Test
	public void testFormatStringToDateCaseInvalidInputFormat() {
		Validator validator = new Validator();
		
		String date = "12-07-2020";
		Date unexpectedDate = new Date(2020 - 1900, 7 - 1, 12);
		Date actualDate = validator.formatStringToDate(date);
		Assert.assertNotEquals(unexpectedDate, actualDate);
	}
	
	@Test
	public void testFormatStringToDateCaseInvalidInputFormat2() {
		Validator validator = new Validator();
		
		String date = "12/07/2020";
		Date unexpectedDate = new Date(2020 - 1900, 7 - 1, 12);
		Date actualDate = validator.formatStringToDate(date);
		Assert.assertNotEquals(unexpectedDate, actualDate);
	}
	
	@Test
	public void testFormatStringToDateCaseInvalidInputFormat3() {
		Validator validator = new Validator();
		
		String date = "2020/07/12";
		Date unexpectedDate = new Date(2020 - 1900, 7 - 1, 12);
		Date actualDate = validator.formatStringToDate(date);
		Assert.assertNotEquals(unexpectedDate, actualDate);
	}
	
	@Test
	public void testFormatStringToDateCaseInvalidInputFormat4() {
		Validator validator = new Validator();
		
		String date = "Jul-12-2020";
		Date unexpectedDate = new Date(2020 - 1900, 7 - 1, 12);
		Date actualDate = validator.formatStringToDate(date);
		Assert.assertNotEquals(unexpectedDate, actualDate);
	}
	
	@Test
	public void testConvertDobToAge() {
		Validator validator = new Validator();
		
		String dob = "1997-08-18";
		int expectedAge = 23;
		int actualAge = validator.convertDobToAge(dob);
		Assert.assertEquals(expectedAge, actualAge);
	}
	
	@Test
	public void testConvertDobToAgeCaseInvalidInput() {
		Validator validator = new Validator();
		
		String dob = "text";
		int unexpectedAge = 23;
		int actualAge = validator.convertDobToAge(dob);
		Assert.assertNotEquals(unexpectedAge, actualAge);
	}
	
	@Test
	public void testConvertDobToAgeCaseInvalidInputFormat() {
		Validator validator = new Validator();
		
		String dob = "1997/08/18";
		int unexpectedAge = 23;
		int actualAge = validator.convertDobToAge(dob);
		Assert.assertNotEquals(unexpectedAge, actualAge);
	}
	
	@Test
	public void testConvertDobToAgeCaseInvalidInputFormat2() {
		Validator validator = new Validator();
		
		String dob = "08/18/1997";
		int unexpectedAge = 23;
		int actualAge = validator.convertDobToAge(dob);
		Assert.assertNotEquals(unexpectedAge, actualAge);
	}
	
	@Test
	public void testConvertDobToAgeCaseInvalidInputFormat3() {
		Validator validator = new Validator();
		
		String dob = "08-18-1997";
		int unexpectedAge = 23;
		int actualAge = validator.convertDobToAge(dob);
		Assert.assertNotEquals(unexpectedAge, actualAge);
	}
	
	@Test
	public void testConvertDobToAgeCaseInvalidInputFormat4() {
		Validator validator = new Validator();
		
		String dob = "Aug-18-1997";
		int unexpectedAge = 23;
		int actualAge = validator.convertDobToAge(dob);
		Assert.assertNotEquals(unexpectedAge, actualAge);
	}

}
