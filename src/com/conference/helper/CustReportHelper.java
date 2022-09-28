package com.conference.helper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.infoengine.SAK.IeService;
import com.infoengine.SAK.Task;
import com.infoengine.object.factory.Att;
import com.infoengine.object.factory.Element;
import com.infoengine.object.factory.Group;
import com.conference.CustJasperReport;
import com.conference.report.*;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.log4j.LogR;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.template.ReportTemplate;
import wt.util.WTException;
import wt.util.WTProperties;

public class CustReportHelper 
{

	private static Logger logger = null;

	//Setup logger
	static 
	{
		try 
		{
			logger = LogR.getLogger(CustReportHelper.class.getName());
		} 
		catch (Throwable throwable) 
		{
			logger.debug(throwable.getMessage());
			throwable.printStackTrace(System.err);
		}
	}

	public static ReportTemplate getReportTemplate(String reportName)
	{
		try
		{
			QuerySpec qs = new QuerySpec(ReportTemplate.class);
			qs.appendWhere(new SearchCondition(ReportTemplate.class, "name",SearchCondition.EQUAL, reportName), new int[]{0});

			QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

			if(qr.hasMoreElements())
			{
				return (ReportTemplate) qr.nextElement();
			}

		} 
		catch (WTException e)
		{
			logger.error("Exception took place during generating query for finding a report template", e);
		}

		return null;
	}

	public static void writeIEGroupToFile(Group out, String directoryPath, String fileName)
	{
		try
		{
			//Create workbook
			Workbook wb = new XSSFWorkbook();

			//Create first tab
			Sheet sheet = wb.createSheet("Default");


			for(int i=0; i<out.getElementCount(); i++)
			{
				Element elem = out.getElementAt(i);
				Enumeration<Att> atts = elem.getAtts();

				ArrayList<String> columnHeaderData = new ArrayList<String>();
				ArrayList<String> rowData = new ArrayList<String>();

				while(atts.hasMoreElements())
				{
					Att attribute = atts.nextElement();
					logger.debug(attribute.getName() + " - " + attribute.getValue());

					columnHeaderData.add(attribute.getName());
					rowData.add(attribute.getValue().toString());
				}

				//If we're on the first element, write the headers
				if(i == 0)
					writeHeaderRow(sheet, columnHeaderData);

				int nextRow = i + 1;
				Row dataRow = sheet.createRow(nextRow);

				writeCellsToRow(wb, rowData.toArray(new String[0]), dataRow);
			}

			File exportListFile = new File(directoryPath + "/" + fileName+".xlsx");
			
			//Write workbook to file
			FileOutputStream fileOut = new FileOutputStream(exportListFile);
			wb.write(fileOut);
			fileOut.close();
			
			//Change permissions so everyone can read
			if(exportListFile.exists())
			{
				boolean result = exportListFile.setReadable(true,false);
				logger.debug("Permissions changed: " + result);
			}
			else
				logger.debug("Couldn't find file: " + exportListFile.getAbsolutePath());
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public static Group executeReportTemplate(CustReport report, String userName) throws Exception
	{
		Group out = null;

		IeService ie = new IeService();
		WTProperties props = WTProperties.getLocalProperties();
		String reportTemplateIda2a2 = getReportTemplate(report.getName()).toString();

		Task t = new Task("/com/ptc/windchill/enterprise/report/ExecuteReportTemplate.xml", props.getProperty("wt.federation.ie.VMName"));
		t.setService(ie);
		t.setUsername(userName);
		
		t.addParam("object_ref", reportTemplateIda2a2);
		t.addParam("supporting-adapter", props.getProperty("wt.federation.ie.VMName"));
		
		HashMap<String, ArrayList<String>> params = report.getInput();

		//Add all the params
		for(String key : params.keySet())
		{
			ArrayList<String> paramValues = params.get(key);

			for(String s : paramValues)
			{
				t.addParam(key, s);
			}
		}
		
		logger.debug("wt.federation.ie.VMName:" + props.getProperty("wt.federation.ie.VMName"));
		logger.debug("object_ref: " + reportTemplateIda2a2);
		logger.debug("supporting-adapter:" + props.getProperty("wt.federation.ie.VMName"));
		logger.debug("Params: " + params.toString());
		logger.debug("userName: " + userName);

		try
		{
			//Make the call and get the output group
			t.invoke();
			out = t.getGroup("out");

			if(report.getExport())
			{
				writeIEGroupToFile(out,report.getExportPath(), report.getExportFileName());
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		return out;
	}

	public static void writeHeaderRow(Sheet sheet, ArrayList<String> header)
	{
		logger.debug("writeHeaderRow header: " + header.toString());

		Row headerRow = sheet.createRow(0);
		int cellIndex = 0;

		//Write the header cells
		for(String colName : header)
		{
			Cell c = headerRow.createCell(cellIndex);
			c.setCellValue(colName);
			cellIndex++;
		}
	}

	public static void writeCellsToRow(Workbook wb, String[] rowData, Row dataRow)
	{
		logger.debug("writeCellsToRow rowData: " + rowData.toString());

		int cellIndex = 0;

		for(String cellValue: rowData)
		{
			Cell c = dataRow.createCell(cellIndex);
			c.setCellValue(cellValue);

			cellIndex++;
		}
	}

	public ArrayList<HashMap<String, String>> RunReportTemplate(String reportName,String parameters)
	{		
		logger.debug("RunReportTemplate begin--- ("+reportName + "," + parameters);
    	ArrayList<HashMap<String, String>> rows =new ArrayList<HashMap<String, String>>();
    	
    	try
    	{
    		Group outg = null;
    		IeService ie = new IeService();
    		WTProperties props = WTProperties.getLocalProperties();
    		String reportTemplateIda2a2 = com.conference.helper.CustReportHelper.getReportTemplate(reportName).toString(); //ex:  CH - Drawing Master
    		
    		Task t = new Task("/com/ptc/windchill/enterprise/report/ExecuteReportTemplate.xml", props.getProperty("wt.federation.ie.VMName"));
    		t.setService(ie);
    		t.addParam("object_ref", reportTemplateIda2a2);
    		t.addParam("supporting-adapter", props.getProperty("wt.federation.ie.VMName"));
			t.addParam("INPUT",parameters);		//ex: "objectId='8224374537';legacyDate='20140922'");
//    		t.setUsername("jdoe");// set if you want to run as a specific user
    		
    		logger.debug("wt.federation.ie.VMName:" + props.getProperty("wt.federation.ie.VMName"));
    		logger.debug("object_ref: " + reportTemplateIda2a2);
    		logger.debug("supporting-adapter:" + props.getProperty("wt.federation.ie.VMName"));
    		logger.debug("Params: " + parameters.toString());
    		
    		try
    		{
    			//Make the call and get the output group
				logger.debug("RunReportTemplate about to invoke report ");
    			t.invoke();
				logger.debug("RunReportTemplate invoked");
    			outg = t.getGroup("out");

				logger.debug("RunReportTemplate found "+outg.getElementCount()+"rows");
    			for(int i=0; i<outg.getElementCount(); i++)
    			{
    				Element elem = outg.getElementAt(i);
    				Enumeration<Att> atts = elem.getAtts();
    		    	HashMap<String, String> rowcols = new HashMap<String, String>();
    				
    		    	while(atts.hasMoreElements())
    				{
    					Att attribute = atts.nextElement();
    					logger.debug(" adding column " +attribute.getName() + "," + attribute.getValue().toString());
        		    	rowcols.put(attribute.getName(), attribute.getValue().toString());    					
    				}
    		    	
    		    	rows.add(rowcols );
    			}
    		}
    		catch(Exception ex)
    		{
    			ex.printStackTrace();
    			logger.error("RunReportTemplate - error invoking IE for reporttemplate ( " +reportName +","+parameters +")" + ex.getMessage());
    		}
    	}
    	catch(Exception exc)
    	{
    		exc.printStackTrace();
			logger.error("RunReportTemplate - error setting up IE for reporttemplate ( " +reportName +","+parameters +")" + exc.getMessage());
    	}
    	
    	// always returning something even if empty  this means errors will need to be digested by logs
		return rows; 
	}

	public static ByteArrayOutputStream executeJasperReport(String reportName, HashMap<String, String> reportParams, boolean useTestData) throws Exception
	{
		
		String className = "com.conference." + reportName;
		logger.debug("Class path: " + className);
		Class<?> cl = Class.forName(className);
		
		Constructor<?> cons = cl.getConstructor(HashMap.class, boolean.class);
		
		//This is the pointer to the report class
		logger.debug("Creating instance...");
		CustJasperReport jasperReport = (CustJasperReport)cons.newInstance(reportParams, useTestData);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		jasperReport.generateReportPDFStream(baos);
		
		return baos;
	}
	
	
}
