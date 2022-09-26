package com.conference;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import wt.log4j.LogR;
import wt.util.WTProperties;

import org.apache.logging.log4j.Logger;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.*;

public class CustJasperReport 
{
	private static Logger logger = null;
	//private String reportPath = "";
	private HashMap<String, String> reportParams;
	private boolean useTestData = false;
	private JasperPrint jasperPrint = null;
	private Map<String, Object> jasperParameters = new HashMap<String,Object>();
	private JRBeanCollectionDataSource jasperDataSource = null;
	private HashMap<String,String> commonReportVariables = new HashMap<String,String>();
	private String conferenceLogo = "conference.png";
	private String custJaserReportHome = "codebase/com/conference";
	private static final String LOGO_LOCATION_KEY = "logoLocation";
	private static final String REPORT_PATH_KEY = "reportPath";
	
	//Setup logger
	static 
	{
		try 
		{
			logger = LogR.getLogger(CustJasperReport.class.getName());
		} 
		catch (Throwable throwable) 
		{
			logger.debug(throwable.getMessage());
			throwable.printStackTrace(System.err);
		}
	}
	
	public CustJasperReport() 
	{
	
	}

	public CustJasperReport(String reportJRXML, HashMap<String, String> reportParams, boolean useTestData) 
	{
		logger.debug("SteelcaseJasperReport...");
		
		this.reportParams = reportParams;
		this.useTestData = useTestData;
		
		String windchillBase = "";
		
		try
		{
			windchillBase = WTProperties.getLocalProperties().get("wt.home").toString();
			commonReportVariables.put(LOGO_LOCATION_KEY, windchillBase + "/" + custJaserReportHome + "/" + conferenceLogo);
			commonReportVariables.put(REPORT_PATH_KEY, windchillBase + "/" + custJaserReportHome + "/" + reportJRXML);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		
	}
	
	public HashMap<String, String> getCommonReportVariables()
	{
		return commonReportVariables;
	}
	
	public void setReportPath(String path)
	{
		commonReportVariables.put(REPORT_PATH_KEY,path);
	}
	
	public void setLogoPath(String path)
	{
		commonReportVariables.put(LOGO_LOCATION_KEY,path);
	}
	
	public String getReportPath()
	{
		return commonReportVariables.get(REPORT_PATH_KEY);
	}
	
	public String getLogoPath()
	{
		return commonReportVariables.get(LOGO_LOCATION_KEY);
	}

	public HashMap<String, String> getReportParams() 
	{
		return reportParams;
	}

	public void setReportParams(HashMap<String, String> reportParams) 
	{
		this.reportParams = reportParams;
	}

	public boolean getUseTestData() 
	{
		return useTestData;
	}

	public void setUseTestData(boolean useTestData) 
	{
		this.useTestData = useTestData;
	}


	public JasperPrint getJasperPrint()
	{
		return jasperPrint;
	}

	public void setJasperPrint(JasperPrint jasperPrint)
	{
		this.jasperPrint = jasperPrint;
	}

	public Map<String, Object> getJasperParameters()
	{
		return jasperParameters;
	}

	public void setJasperParameters(Map<String, Object> parameters)
	{
		this.jasperParameters = parameters;
	}

	public JRBeanCollectionDataSource getJasperDataSource() 
	{
		return jasperDataSource;
	}

	public void setJasperDataSource(JRBeanCollectionDataSource dataSource) 
	{
		this.jasperDataSource = dataSource;
	}
	
	public void gatherReportData() throws Exception
	{
		//This should be overridden in the child class
	}
	

	/**
	 * Generates an output stream of the report as pdf.
	 * @param outputStream - Output stream for HTTPResponse
	 * @return
	 */
	public String generateReportPDFStream(OutputStream outputStream) 
	{
		String result = "error";
		logger.debug("Starting generateReportPDFStream..");

		try
		{
			JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
			result = "success";
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		return result;	
	}


	/**
	 * Generates a pdf of the report and saves it at the specified location.
	 * @param outputPath
	 * @return
	 */
	public String generateReportPDF(String outputPath) 
	{
		String result = "error";
		logger.debug("generateReportPDF: " + outputPath);

		try
		{
			JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
			result = "success";
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		return result;	

	}
	
	public JasperPrint fillReport() throws Exception
	{
		JasperPrint jasperPrint = null;

		try
		{
			logger.debug("Getting jasper report: " + commonReportVariables.get(REPORT_PATH_KEY));
			// 1. compile template ".jrxml" file
			JasperReport jasperReport = getJasperReport(commonReportVariables.get(REPORT_PATH_KEY));

			//2. Fill
			logger.debug("Filling jasper report...");
			jasperPrint = fillJasperReport(jasperReport, jasperParameters, jasperDataSource);
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
			throw exc;
		}

		return jasperPrint;
	}

	private JasperReport getJasperReport(String reportPath) throws FileNotFoundException, JRException 
	{
		File template = ResourceUtils.getFile(reportPath);
		return JasperCompileManager.compileReport(template.getAbsolutePath());
	}

	private JasperPrint fillJasperReport(JasperReport jasperReport, Map<String, Object> parameters, JRBeanCollectionDataSource dataSource) throws Exception
	{	
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		return jasperPrint;
	}
}
