package au.org.paperminer.test;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.org.paperminer.ws.WebController;
import static org.junit.Assert.*;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("file:test-resources/applicationContext.xml")
public class SaveRawResultTest {

	private static WebController webController = new WebController();
	
	@Test
	public void testNoUrl() {
		try {
			String result = webController.fileDownload(null, null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testNoServletResponse() {
		try {
			String result = webController.fileDownload(new String(), null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testWithServletResponse() {
		try {
			String result = webController.fileDownload(new String("http://www.google.com"), null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testIncorrectUrlType() {
		try {
			String result = webController.fileDownload(new String("http://www.google.com"), null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testWithUrlServlet() {
		try {
			String result = webController.fileDownload(new String("http://www.google.com"), null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testWithSecuredUrl() {
		try {
			String result = webController.fileDownload(new String("https://www.google.com"), null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testWithServerTimeout() {
		try {
			String result = webController.fileDownload(new String("https://127.0.0.1"), null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testWithNonResposeServer() {
		try {
			String result = webController.fileDownload(new String("https://127.0.0.1"), null);
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
}
