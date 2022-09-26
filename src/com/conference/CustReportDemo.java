package com.conference;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import wt.log4j.LogR;

import org.apache.logging.log4j.Logger;

import com.conference.datasources.*;

import java.util.*;

public class CustReportDemo extends CustJasperReport 
{
	private static Logger logger = null;
	static String reportJRXML = "ReportDemo.jrxml";
	
	//Setup logging
	static 
	{
		try 
		{
			logger = LogR.getLogger(CustReportDemo.class.getName());
		} 
		catch (Throwable throwable) 
		{
			logger.debug(throwable.getMessage());
			throwable.printStackTrace(System.err);
		}
	}

	public static void main(String[] args)
    {
        try
        {
        	HashMap<String,String> reportParams = new HashMap<String, String>();
			reportParams.put("objectId", "111111111");
        
			CustReportDemo cnrc = new CustReportDemo();
        	cnrc.setUseTestData(true);
        	cnrc.setReportPath("C:\\git\\Windchill\\Windchill Source\\src\\com\\conference\\jasperreports\\" + reportJRXML);
        	cnrc.setLogoPath("c:\\git\\Windchill\\Windchill Source\\src\\com\\conference\\jasperreports\\conference.png");
        	cnrc.setReportParams(reportParams);
        	cnrc.initialize();
        	
        	      
            logger.debug(cnrc.generateReportPDF("C:\\temp\\custreport.pdf"));
        }
        catch(Exception exc)
        {
        	exc.printStackTrace();
            logger.debug(exc.getMessage());
        }
    }

	public CustReportDemo() throws Exception
	{
		
	}
	
	public void initialize()  throws Exception
	{	
		//Gather all the data for the report
		this.gatherReportData();

		//Set the JasperPrint object by filling the report
		setJasperPrint(super.fillReport());
	}
	
	public CustReportDemo(HashMap<String, String> reportParams, boolean useTestData) throws Exception 
	{
		super(reportJRXML, reportParams, useTestData);	
		
		initialize();
	}

	@Override
	public void gatherReportData() throws Exception
	{
		logger.debug("gatherReportData");
		
		boolean useTestData = this.getUseTestData();
		try
		{
			JRBeanCollectionDataSource reportDemoDataContainerSource = ReportDemoContainerDataset.getDataSource(useTestData);
			JRBeanCollectionDataSource reportDemoDataPartSource = ReportDemoPartDataset.getDataSource(useTestData);
			JRBeanCollectionDataSource reportDemoDataEPMDocumentSource = ReportDemoEPMDocumentDataset.getDataSource(useTestData);
			
			Map<String, Object> parameters = new HashMap<String,Object>();
			parameters.put("ReportDemoPartTableData", reportDemoDataPartSource);
			parameters.put("ReportDemoEPMDocumentTableData", reportDemoDataEPMDocumentSource);
			
			this.setJasperParameters(parameters);
			this.setJasperDataSource(reportDemoDataContainerSource);
		}
		catch(Exception ex)
		{
			Exception ex2 = new Exception("Error getting datasources.");
			ex2.setStackTrace(ex.getStackTrace());
			throw ex2;
		}
	}
}
