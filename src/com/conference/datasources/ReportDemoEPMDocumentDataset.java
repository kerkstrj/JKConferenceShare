package com.conference.datasources;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import org.apache.logging.log4j.Logger;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import com.infoengine.SAK.IeService;
import com.infoengine.SAK.Task;
import com.infoengine.object.factory.Att;
import com.infoengine.object.factory.Element;
import com.infoengine.object.factory.Group;
import com.conference.helper.CustReportHelper;

import wt.log4j.LogR;
import wt.util.*;
/*

 * */
//CH - Drawing Master
public class ReportDemoEPMDocumentDataset {
	private static Logger logger = null;

	//Setup logger
	static 
	{
		try 
		{
			logger = LogR.getLogger(ReportDemoEPMDocumentDataset.class.getName());
		} 
		catch (Throwable throwable) 
		{
			logger.debug(throwable.getMessage());
			throwable.printStackTrace(System.err);
		}
	}
	

	
	private static ArrayList<HashMap<String, String>> RunReportTemplate(String reportName,String parameters)
	{		
		logger.debug("RunReportTemplate begin--- ("+reportName + "," + parameters);
    	ArrayList<HashMap<String, String>> rows =new ArrayList<HashMap<String, String>>();
    	
    	try
    	{
    		Group outg = null;
    		IeService ie = new IeService();
    		WTProperties props = WTProperties.getLocalProperties();
    		String reportTemplateIda2a2 = CustReportHelper.getReportTemplate(reportName).toString(); //ex:  CH - Drawing Master
    		
    		Task t = new Task("/com/ptc/windchill/enterprise/report/ExecuteReportTemplate.xml", props.getProperty("wt.federation.ie.VMName"));
    		t.setService(ie);
    		t.addParam("object_ref", reportTemplateIda2a2);
    		t.addParam("supporting-adapter", props.getProperty("wt.federation.ie.VMName"));
			t.addParam("INPUT",parameters);		//ex: "objectId='8224374537';legacyDate='20140922'");
    		///t.setUsername("wcadmin");  you may need this to run
    		
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
	public ReportDemoEPMDocumentDataset()
	{
		
		
	}
    public static JRBeanCollectionDataSource getDataSource(boolean useTestData)
    {

        ArrayList<ReportDemoEPMDocumentRecord> reportDemoEPMDocumentRecords = new ArrayList<ReportDemoEPMDocumentRecord>();
        if(useTestData)
        {
        	reportDemoEPMDocumentRecords.add(new ReportDemoEPMDocumentRecord("8506951089","THINK V2"));
        	reportDemoEPMDocumentRecords.add(new ReportDemoEPMDocumentRecord("8506951090","THINK V2"));
        	reportDemoEPMDocumentRecords.add(new ReportDemoEPMDocumentRecord("8506951091","THINK V2"));

        }
        else
        {
        	// do report call here
        	ArrayList<HashMap<String, String>> rows = 
        				RunReportTemplate("ReportDemoEPMDocument","containerId='THINK V2'");// add additional parameters with semicolon ie  ;parameter2='passedparameter'
        	for(int i = 0;i<rows.size();i++)
        	{        		
        		reportDemoEPMDocumentRecords.add(new ReportDemoEPMDocumentRecord(rows.get(i).get("Persist Info.Object Identifier"),
        				rows.get(i).get("Context (containerName)")));
        	}
        }
        
        return new JRBeanCollectionDataSource(reportDemoEPMDocumentRecords);
    }
    
}


