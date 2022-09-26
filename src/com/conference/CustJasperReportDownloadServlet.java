package com.conference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;

import wt.fc.ReferenceFactory;
import wt.fc.WTReference;
import wt.log4j.LogR;
import wt.session.SessionHelper;
import com.conference.helper.*;

public class CustJasperReportDownloadServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{

	private static final long serialVersionUID = 1L;
	private static Logger logger = null;

	//Setup logging
	static 
	{
		try 
		{
			logger = LogR.getLogger(CustJasperReportDownloadServlet.class.getName());
		} 
		catch (Throwable throwable) 
		{
			logger.debug(throwable.getMessage());
			throwable.printStackTrace(System.err);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		logger.debug("Start: doGet");
		performTask(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		performTask(request, response);
	}

	private void performTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		logger.debug("Start: performTask");
		logger.debug("Context path: " + request.getContextPath());

		Map<String,String[]> inputParams = null;
		inputParams = request.getParameterMap();

		//For debugging params
		if (inputParams != null) 
		{
			logger.debug("Total number of parameters: " + inputParams.size());
			Iterator<String> iter = inputParams.keySet().iterator();

			while (iter.hasNext())
			{
				String key = (String) iter.next();
				String[] values = (String[]) inputParams.get(key);

				for (int i = 0; i < values.length; i++) 
					logger.debug("Parameter Name="+ key + " Value=" + values[i]);
			}
		} 

		//Get parameters
		String oid = request.getParameter("oid");
		String reportName = request.getParameter("reportName");
		String pdfFileName = reportName + ".pdf";

		//Set response headesr
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);

		try
		{

			ReferenceFactory rf = new ReferenceFactory();
			WTReference ref = (WTReference)rf.getReference(oid);
			String fullIda2a2 = ref.getObject().toString();
			String ida2a2 = String.valueOf(ref.getObject().getPersistInfo().getObjectIdentifier().getId());
			
			logger.debug("reportName: " + reportName);
			logger.debug("objectId: " + ida2a2);
			logger.debug("oid: " + fullIda2a2);
			
			HashMap<String,String> reportParams = new HashMap<String, String>();
			reportParams.put("reportName", reportName);
				
			reportParams.put("objectId", ida2a2);
			reportParams.put("oid", fullIda2a2);
			
			ByteArrayOutputStream bos = CustReportHelper.executeJasperReport(reportName, reportParams, false);
			response.getOutputStream().write(bos.toByteArray());
			
			createPDFResponse(response,pdfFileName,bos);
			
			try
			{
				bos.close();
			}
			catch(Exception ex)
			{
				logger.debug(ex.getMessage());
			}
			

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			createRedirectWithErrorResponse(request, response, ex.getCause().getMessage());
		}

		logger.debug("End: performTask");

	}
	
	private void createPDFResponse(HttpServletResponse response, String pdfFileName, ByteArrayOutputStream bos) throws Exception
	{
		//Set response header
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);
		response.getOutputStream().write(bos.toByteArray());
	}
	
	private void createRedirectWithErrorResponse(HttpServletRequest request, HttpServletResponse response, String errorMessage) 
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/netmarkets/jsp/conference/reportError.jsp?errorMessage=" + errorMessage);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}