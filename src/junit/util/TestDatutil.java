package junit.util;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import util.Datutil;

public class TestDatutil {

	/*@Before
	public void setUp() throws Exception {
	}*/

	@Test
	public void dateToStringTest() {
		Date dateTest = new GregorianCalendar(2017, Calendar.OCTOBER, 15).getTime();
		assertEquals("15/10/17", Datutil.dateToString(dateTest));
	}
	
	/*@Test
	public void dateDuJourTest() {
		Date today = Calendar.getInstance().getTime();
		Date expectedToday = Datutil.dateDuJour();
		int diffInDays = (int) ((expectedToday.getTime() - today.getTime()) / (1000 * 60 * 60 * 24));
		assertEquals(0, diffInDays);
	}*/
	
	@Test
	public void addDateTest() {
		Date dateBegin = new GregorianCalendar(2017, Calendar.OCTOBER, 15).getTime();
 		Date dateExpected = new GregorianCalendar(2017, Calendar.OCTOBER, 20).getTime();
 		Date dateEnd = Datutil.addDate(dateBegin, 5);
 		assertEquals(0,dateExpected.compareTo(dateEnd));
	}
	
	@Test
	public void addAuJourTest() {
		Date dateAvantAdd = Datutil.dateDuJour();
		Datutil.addAuJour(11);
		Date dateApresAdd = Datutil.dateDuJour();
		
		int diffInDays = (int) ((dateApresAdd.getTime() - dateAvantAdd.getTime()) / (1000 * 60 * 60 * 24));
		
		assertEquals(11, diffInDays);
	}
	
	@Test
	public void dateToSqlValuesTest() {
		Date dateTest = new GregorianCalendar(2017, Calendar.OCTOBER, 15).getTime();
		assertEquals("15-10-2017", Datutil.dateToSqlValues(dateTest));
		Datutil.setDbLocale(Locale.CANADA);
		assertEquals("2017-10-15", Datutil.dateToSqlValues(dateTest));
	}
	

}
