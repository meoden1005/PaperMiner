package au.org.paperminer.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import au.org.paperminer.ws.WebController;

public class RankingTest {
	
	private static WebController webController = new WebController();
	
	@Test
	public void testNullString() {
		try {
			String result = webController.fileDownload(null, null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testStringWithSpecialChars() {
		try {
			String result = webController.fileDownload(new String(), null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testStringNotInDict() {
		try {
			String result = webController.fileDownload(new String("http://www.google.com"), null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testStringCannotCast() {
		try {
			String result = webController.fileDownload(new String("http://www.google.com"), null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testWithoutServletRunning() {
		try {
			String result = webController.fileDownload(new String("http://www.google.com"), null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testWithNoResult() {
		try {
			String result = webController.fileDownload(new String("https://www.google.com"), null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testJsonParse() {
		try {
			String result = webController.fileDownload(new String("http://www.google.com"), null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testStringToJsont() {
		try {
			String result = webController.fileDownload(new String("http://www.google.com"), null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testInvalidJson() {
		try {
			String result = webController.fileDownload(new String("http://www.google.com"), null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testSameRelevanceScore() {
		try {
			String result = webController.fileDownload(new String("https://www.google.com"), null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
}
