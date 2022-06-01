package nz.ac.wgtn.swen301.resthome4logs.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import javax.servlet.ServletException;

import org.json.JSONObject;
import org.junit.Test;


import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestStatsCSV {

	
	@Test
	public void test_StatsCSV1() throws ServletException, IOException {
		Persistency.DB.clear();
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		
		StatsCSVServlet scs = new StatsCSVServlet();
	
		
		
		JSONObject log1 = new JSONObject();
		log1.put("id", "jeffrey");
		log1.put("message", "I am a log yo");
		log1.put("timestamp", "14-03-2022 13:30:45");
		log1.put("thread", "main");
		log1.put("logger", "loggerJeff");
		log1.put("level", "TRACE");
		
		Persistency.DB.add(log1);
		
		JSONObject log2 = new JSONObject();
		log2.put("id", "jeffrey");
		log2.put("message", "Chur");
		log2.put("timestamp", "14-03-2022 13:30:45");
		log2.put("thread", "main");
		log2.put("logger", "loggerJeff");
		log2.put("level", "TRACE");
		
		Persistency.DB.add(log2);
		
		JSONObject log3 = new JSONObject();
		log3.put("id", "jeffrey");
		log3.put("message", "Chur");
		log3.put("timestamp", "14-03-2022 13:30:45");
		log3.put("thread", "main");
		log3.put("logger", "loggerTatum");
		log3.put("level", "OFF");
		
		Persistency.DB.add(log3);
		
		scs.doGet(request, response);
		
		assertTrue(response.getStatus() == 200);
		assertTrue(response.getContentType().equals("text/csv"));
		
		String content = response.getContentAsString();
		
		String[] lines = content.split("\\n");
		
		assertTrue(lines.length == 3);
		
		String[] firstLine = lines[0].split("\\t");
		assertTrue(firstLine.length == 9);
		assertEquals("logger", firstLine[0]);
		assertEquals("ALL", firstLine[1]);
		assertEquals("TRACE", firstLine[2]);
		assertEquals("DEBUG", firstLine[3]);
		assertEquals("INFO", firstLine[4]);
		assertEquals("WARN", firstLine[5]);
		assertEquals("ERROR", firstLine[6]);
		assertEquals("FATAL", firstLine[7]);
		assertEquals("OFF", firstLine[8]);
		
		String[] tatumLine = null;
		String[] jeffLine = null;
		
		if(lines[1].split("\\t")[0].equals("loggerTatum")) {
			tatumLine = lines[1].split("\\t");
			jeffLine = lines[2].split("\\t");
		} else if(lines[1].split("\\t")[0].equals("loggerJeff")) {
			tatumLine = lines[2].split("\\t");
			jeffLine = lines[1].split("\\t");
		} else {
			fail();
		}
		assertTrue(tatumLine.length == 9);
		assertTrue(jeffLine.length == 9);
		
		assertEquals(Integer.valueOf(1), Integer.valueOf(tatumLine[8]));
		assertEquals(Integer.valueOf(2), Integer.valueOf(jeffLine[2]));
		
		for(int i = 1; i < 8; i++) {
			assertEquals(Integer.valueOf(0), Integer.valueOf(tatumLine[i]));
		}
		for(int i = 3; i < 9; i++) {
			assertEquals(Integer.valueOf(0), Integer.valueOf(jeffLine[i]));
		}
		
		assertEquals(Integer.valueOf(0), Integer.valueOf(jeffLine[1]));
		
		
		
		
	}
}
