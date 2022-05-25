package nz.ac.wgtn.swen301.resthome4logs.server;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.servlet.ServletException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;


import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestGetLogs {

	
	/**
	 * Test standard working operation
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Test
	public void test_GetLogs1() throws ServletException, IOException {
		
		Persistency.logs.clear();
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		request.setParameter("level", "ALL");
		request.setParameter("limit", "3");
		
		
		LogsServlet ls = new LogsServlet();
	
		
		
		JSONObject log1 = new JSONObject();
		log1.put("id", "jeffrey");
		log1.put("message", "I am a log yo");
		log1.put("timestamp", "14-03-2022 13:30:45");
		log1.put("thread", "main");
		log1.put("logger", "loggerJeff");
		log1.put("level", "DEBUG");
		
		Persistency.logs.add(log1);
		
		JSONObject log2 = new JSONObject();
		log2.put("id", "jeffrey");
		log2.put("message", "I am also a log yo");
		log2.put("timestamp", "14-03-2022 14:35:47");
		log2.put("thread", "main");
		log2.put("logger", "loggerJeff");
		log2.put("level", "INFO");
		
		Persistency.logs.add(log2);
		
		JSONObject log3 = new JSONObject();
		log3.put("id", "jeffrey");
		log3.put("message", "I am too a log yo");
		log3.put("timestamp", "14-03-2022 15:37:48");
		log3.put("thread", "main");
		log3.put("logger", "loggerJeff");
		log3.put("level", "WARN");
		
		Persistency.logs.add(log3);
		
		JSONObject log4 = new JSONObject();
		log4.put("id", "jeffrey");
		log4.put("message", "I am three a log yo");
		log4.put("timestamp", "14-03-2022 16:38:49");
		log4.put("thread", "main");
		log4.put("logger", "loggerJeff");
		log4.put("level", "WARN");
		
		Persistency.logs.add(log4);
		
		
		ls.doGet(request, response);
		
		assertEquals(200, response.getStatus());
		assertTrue(response.getContentType().startsWith("application/json"));
		
		JSONArray ja = new JSONArray(response.getContentAsString());
		
		assertTrue(ja.length() == 3);
		assertTrue(ja.getJSONObject(0).get("message").equals("I am three a log yo"));
		

	}
	
	/**
	 * Test invalid parameter
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Test
	public void test_GetLogs2() throws ServletException, IOException {
		Persistency.logs.clear();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		request.setParameter("level", "ALLl");
		request.setParameter("limit", "3");
		
		
		LogsServlet ls = new LogsServlet();
	
		JSONObject log1 = new JSONObject();
		log1.put("id", "jeffrey");
		log1.put("message", "I am a log yo");
		log1.put("timestamp", "14-03-2022 13:30:45");
		log1.put("thread", "main");
		log1.put("logger", "loggerJeff");
		log1.put("level", "DEBUG");
		
		Persistency.logs.add(log1);
		ls.doGet(request, response);
		
		assertEquals(400, response.getStatus());
	}
	
	/**
	 * Test null parameters
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Test
	public void test_GetLogs3() throws ServletException, IOException {
		Persistency.logs.clear();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
	
		
		
		LogsServlet ls = new LogsServlet();
		
		JSONObject log1 = new JSONObject();
		log1.put("id", "jeffrey");
		log1.put("message", "I am a log yo");
		log1.put("timestamp", "14-03-2022 13:30:45");
		log1.put("thread", "main");
		log1.put("logger", "loggerJeff");
		log1.put("level", "DEBUG");
		
		Persistency.logs.add(log1);
		ls.doGet(request, response);
		
		assertEquals(400, response.getStatus());
	}
	
	/**
	 * Test level parameter works
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Test
	public void test_GetLogs4() throws ServletException, IOException {
		
		Persistency.logs.clear();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		request.setParameter("level", "INFO");
		request.setParameter("limit", "3");
		
		
		LogsServlet ls = new LogsServlet();
	
		
		
		JSONObject log1 = new JSONObject();
		log1.put("id", "jeffrey");
		log1.put("message", "I am a log yo");
		log1.put("timestamp", "14-03-2022 13:30:45");
		log1.put("thread", "main");
		log1.put("logger", "loggerJeff");
		log1.put("level", "DEBUG");
		
		Persistency.logs.add(log1);
		
		JSONObject log2 = new JSONObject();
		log2.put("id", "jeffrey");
		log2.put("message", "I am also a log yo");
		log2.put("timestamp", "14-03-2022 14:35:47");
		log2.put("thread", "main");
		log2.put("logger", "loggerJeff");
		log2.put("level", "INFO");
		
		Persistency.logs.add(log2);
		
		JSONObject log3 = new JSONObject();
		log3.put("id", "jeffrey");
		log3.put("message", "I am too a log yo");
		log3.put("timestamp", "14-03-2022 15:37:48");
		log3.put("thread", "main");
		log3.put("logger", "loggerJeff");
		log3.put("level", "FATAL");
		
		Persistency.logs.add(log3);
		
		JSONObject log4 = new JSONObject();
		log4.put("id", "jeffrey");
		log4.put("message", "I am three a log yo");
		log4.put("timestamp", "14-03-2022 16:38:49");
		log4.put("thread", "main");
		log4.put("logger", "loggerJeff");
		log4.put("level", "ERROR");
		
		Persistency.logs.add(log4);
		
		
		ls.doGet(request, response);
		
		assertEquals(200, response.getStatus());
		
		JSONArray ja = new JSONArray(response.getContentAsString());
		
		assertTrue(ja.length() == 3);
		assertTrue(ja.getJSONObject(2).get("message").equals("I am also a log yo"));
	}
	
	/**
	 * Test invalid limit parameter
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Test
	public void test_GetLogs5() throws ServletException, IOException {
		Persistency.logs.clear();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		request.setParameter("level", "INFO");
		request.setParameter("limit", "-1");
		
		
		LogsServlet ls = new LogsServlet();
		
		JSONObject log1 = new JSONObject();
		log1.put("id", "jeffrey");
		log1.put("message", "I am a log yo");
		log1.put("timestamp", "14-03-2022 13:30:45");
		log1.put("thread", "main");
		log1.put("logger", "loggerJeff");
		log1.put("level", "DEBUG");
		
		Persistency.logs.add(log1);
		ls.doGet(request, response);
		
		assertEquals(400, response.getStatus());
	}
	
	/**
	 * Test invalid limit parameter
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Test
	public void test_GetLogs6() throws ServletException, IOException {
		Persistency.logs.clear();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		request.setParameter("level", "INFO");
		request.setParameter("limit", "threePlease");
		
		
		LogsServlet ls = new LogsServlet();
	
		JSONObject log1 = new JSONObject();
		log1.put("id", "jeffrey");
		log1.put("message", "I am a log yo");
		log1.put("timestamp", "14-03-2022 13:30:45");
		log1.put("thread", "main");
		log1.put("logger", "loggerJeff");
		log1.put("level", "DEBUG");
		
		Persistency.logs.add(log1);
		ls.doGet(request, response);
		
		assertEquals(400, response.getStatus());
	}
	
	
	
	
	
	
	
}
