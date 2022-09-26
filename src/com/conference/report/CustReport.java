package com.conference.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class CustReport implements Serializable
{
	private static final long serialVersionUID = -1922190441344665472L;

	private String name = "";
	private HashMap<String, ArrayList<String>> input;
	private String baseExportFileName = "";
	private String exportFileName = "";
	private String exportPath = "";
	private Boolean export = true;
	private String startParam = "";//param.getString("startParam");
	private String runFrequency = "";//param.getString("runFrequency");
	private String day = "";//param.getString("day");
	private String reportDate = "";//param.getString("reportDate");

	public CustReport(String name, HashMap<String, ArrayList<String>> input) 
	{
		super();
		this.input = input;
		this.name = name;
	}
	
	public CustReport(String name, String exportFileName,
			String exportPath, Boolean export, String startParam, String runFrequency,
			String day, String reportDate) {
		super();
		this.name = name;
		this.input = new HashMap<String, ArrayList<String>>();
		this.baseExportFileName = exportFileName;
		this.exportFileName = exportFileName;
		this.exportPath = exportPath;
		this.export = export;
		this.startParam = startParam;
		this.runFrequency = runFrequency;
		this.day = day;
		this.reportDate = reportDate;
	}

	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getExportFileName() 
	{
		return exportFileName;
	}
	
	public void setExportFileNamePrefix(String prefix)
	{
		this.exportFileName = prefix + baseExportFileName;
	}
	
	public void setExportFileNameSuffix(String suffix)
	{
		this.exportFileName = baseExportFileName + suffix;
	}
	
	public String getExportPath() 
	{
		return exportPath;
	}
	
	public void setExportPath(String exportPath) 
	{
		this.exportPath = exportPath;
	}
	
	public Boolean getExport() 
	{
		return export;
	}
	
	public void setExport(Boolean export) 
	{
		this.export = export;
	}
	
	public HashMap<String, ArrayList<String>> getInput() 
	{
		return input;
	}
	
	public void setInput(HashMap<String, ArrayList<String>> input) 
	{
		this.input = input;
	}

	public String getStartParam() 
	{
		return startParam;
	}

	public void setStartParam(String startParam) 
	{
		this.startParam = startParam;
	}

	public String getRunFrequency() 
	{
		return runFrequency;
	}

	public void setRunFrequency(String runFrequency) 
	{
		this.runFrequency = runFrequency;
	}

	public String getDay() 
	{
		return day;
	}

	public void setDay(String day) 
	{
		this.day = day;
	}

	public String getReportDate() 
	{
		return reportDate;
	}

	public void setReportDate(String reportDate) 
	{
		this.reportDate = reportDate;
	}

	@Override
	public int hashCode() 
	{
		return this.name.hashCode();
	}

	@Override
	public boolean equals(Object o) 
	{
		// TODO Auto-generated method stub
		//return super.equals(arg0);

		if (o == this) {
			return true;
		}

		if (!(o instanceof CustReport)) 
		{
			return false;
		}

		CustReport sr = (CustReport) o;

		if(sr.name.equals(this.name))
			return true;
		else
			return false;

	}

}
