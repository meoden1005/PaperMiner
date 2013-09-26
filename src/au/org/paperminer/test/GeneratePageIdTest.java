package au.org.paperminer.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import au.org.paperminer.ws.WebController;

public class GeneratePageIdTest {
private static WebController webController = new WebController();
	
	@Test
	public void testNullUrl() {
		try {
			String result = webController.getPage(null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testWithCorrectUrl() {
		try {
			String result = webController.getPage(new String("http://localhost:8080"));
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testWithIncorrectUrl() {
		try {
			String result = webController.getPage(new String("http1://localhost:8080"));
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testWithTimeoutServer() {
		try {
			String result = webController.getPage(new String("http1://localhost:8080"));
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testWithProxyServer() {
		try {
			String result = webController.getPage(new String("http1://localhost:8080"));
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testWithNetworkConnection() {
		try {
			String result = webController.getPage(new String("http1://localhost:8080"));
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
}
