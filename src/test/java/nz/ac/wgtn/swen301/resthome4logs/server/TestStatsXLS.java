package nz.ac.wgtn.swen301.resthome4logs.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.junit.Test;


import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestStatsXLS {

	@Test
	public void test_StatsXLS1() throws ServletException, IOException, ClassNotFoundException {
		
		Persistency.DB.clear();
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		
		StatsXLSServlet sxs = new StatsXLSServlet();
	
		
		
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
		
		sxs.doGet(request, response);
		
		assertTrue(response.getStatus() == 200);
		assertTrue(response.getContentType().equals("application-vnd.ms-excel"));
		
		ByteArrayInputStream stream = new ByteArrayInputStream(response.getContentAsByteArray());
		
		XSSFWorkbook wb = new XSSFWorkbook(stream);
		assertEquals("stats", wb.getSheetName(0));
		assertTrue(wb.getNumberOfSheets() == 1);
		
		XSSFSheet sheet = wb.getSheetAt(0);
		
		String table = "";
		
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 11; col++) {
				if(sheet.getRow(row) != null && sheet.getRow(row).getCell(col) != null) {
					table += printCell(sheet.getRow(row).getCell(col));
					table += (" ");
				}
			}
			table += "\n";
		}

		assertEquals("logger ALL TRACE DEBUG INFO WARN ERROR FATAL OFF \n"
						+ "loggerJeff 0.0 2.0 0.0 0.0 0.0 0.0 0.0 0.0 \n"
						+"loggerTatum 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 \n", table);
		
		
		
		
	}
	
	/**
	 * Returns string value of a cell
	 * 
	 * https://stackoverflow.com/questions/6508203/when-getting-cell
	 * -content-using-apache-poi-library-i-get-both-cannot-get-a-num
	 * 
	 * @param cell
	 */
	public String printCell(Cell cell) {
		if(cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		
        case STRING:
            return (cell.getRichStringCellValue().getString());
            
        case NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                return ("" + cell.getDateCellValue());
            } else {
                return ( "" + cell.getNumericCellValue());
            }
           
       
        default:
            return "";
    }
	}
}
