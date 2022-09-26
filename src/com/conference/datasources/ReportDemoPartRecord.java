package com.conference.datasources;




public class ReportDemoPartRecord {
	
	String Ida2a2;
	String ContainerName;

	public String getIda2a2() {  return Ida2a2; }
	public String getContainerName() {  return ContainerName; }
	
	public ReportDemoPartRecord(
			String ida2a2,
			String containerName							)
	{
		Ida2a2 = ida2a2;
		ContainerName = containerName;
	}
}
