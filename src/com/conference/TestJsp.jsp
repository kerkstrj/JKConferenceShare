
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="wctags" %>

 <%@ page import="java.io.InputStream,
java.net.URL,
java.text.Collator,
org.apache.commons.codec.binary.Base64,
org.apache.commons.io.IOUtils,
com.ptc.windchill.enterprise.dsvcore.server.utils.PersistableHelper,
com.ptc.core.businessfield.server.businessObject.*,
com.ptc.core.businessfield.common.*,
com.ptc.core.businessfield.server.*,
wt.content.*,
javax.xml.ws.WebServiceException,
wt.content.ContentServerHelper,
wt.method.OutputStreamProxy,
 wt.fc.ObjectIdentifier,
com.ptc.windchill.enterprise.report.Report,
com.ptc.windchill.enterprise.report.ReportHelper,
 wt.fc.ObjectReference,
 wt.httpgw.URLFactory,
 wt.content.ContentHolder,
 wt.content.ContentServerHelper,
wt.content.ContentHelper,
com.ptc.core.adapter.server.impl.ObjectReferenceTranslator,
wt.fc.WTReference,
wt.fc.*,
wt.fc.collections.*,
com.ptc.windchill.enterprise.part.commands.PartDocServiceCommand,
com.steelcase.helper.SteelcaseWTPartHelper,
wt.part.WTPart,
wt.part.PartDocHelper,
wt.epm.EPMDocument,
com.ptc.core.lwc.server.*,
com.steelcase.helper.*,
wt.representation.Representation,
com.ptc.wvs.common.ui.VisualizationHelper,
java.util.*,
java.sql.Timestamp,
wt.change2.*,
wt.iba.value.*,
wt.iba.value.service.LoadValue,
wt.iba.value.litevalue.*,
wt.preference.*,
wt.org.*,
wt.session.*,
wt.effectivity.EffectivityHelper,
wt.eff.*,
wt.part.WTPartMaster,
wt.esi.*,
wt.query.QuerySpec,
wt.query.SearchCondition,
wt.pds.StatementSpec,
wt.vc.views.ViewReference,
wt.vc.views.ViewHelper,
com.ptc.core.meta.common.UpdateOperationIdentifier,
com.ptc.core.meta.common.CreateOperationIdentifier,

wt.query.*,
wt.lifecycle.Transition,
wt.lifecycle.LifeCycleHelper,
wt.lifecycle.*,
com.ptc.windchill.enterprise.history.*,
wt.inf.library.WTLibrary,
wt.team.*,
wt.part.*,
wt.filter.NavigationCriteria,
com.steelcase.helper.SteelcaseEPMDocumentHelper,
com.infoengine.SAK.Task,
com.infoengine.object.factory.Att,
com.infoengine.object.factory.Element,
com.infoengine.object.factory.Group,
com.steelcase.annotations.MethodDetail,
wt.vc.*,

com.ptc.windchill.collector.api.cad.CadCollector.*,
wt.epm.workspaces.*,
com.ptc.windchill.collector.api.cad.*,
wt.units.*,
com.ptc.core.meta.common.DataTypesUtility,
wt.doc.*,
 wt.viewmarkup.DerivedImage,
com.ptc.windchill.uwgm.common.container.*,
java.io.BufferedInputStream,
java.io.ByteArrayOutputStream,
java.io.FileInputStream,
java.io.FileNotFoundException,
java.io.FileOutputStream,
java.io.IOException,
java.io.OutputStream,				
java.io.*,
org.apache.commons.io.IOUtils,
com.fishbowl.pdmlink.linkaccess.converter.ThumbnailEngine,
com.fishbowl.pdmlink.linkaccess.converter.WatermarkEngine,
com.fishbowl.pdmlink.linkcontent.LinkViewable,
wt.content.*,
wt.epm.structure.EPMReferenceLink,
wt.vc.config.LifeCycleConfigSpec,
wt.vc.config.*,
com.ptc.windchill.enterprise.attachments.server.*,
wt.org.*,
wt.preference.*,
wt.intersvrcom.*,
wt.fv.*,
wt.workflow.work.*,
wt.project.Role,
com.ptc.core.meta.container.common.*,
com.ptc.core.meta.common.*,
com.ptc.core.lwc.server.LWCEnumerationEntryValuesFactory,
wt.meta.LocalizedValues,
wt.vc.struct.StructHelper,
wt.type.ClientTypedUtility,
wt.pom.*,
wt.queue.*,
wt.associativity.EquivalenceLink,
wt.vc.views.View,
wt.workflow.engine.WfVotingEventAudit,
wt.folder.CabinetBased,
wt.folder.FolderHelper,
java.math.BigDecimal,
com.ptc.wvs.server.schedule.ScheduleJobs,
wt.inf.container.*,
com.ptc.core.meta.type.common.*,
com.ptc.core.meta.common.impl.*,
wt.type.TypedUtilityServiceHelper,
com.ptc.core.meta.server.TypeIdentifierUtility,
com.ptc.core.lwc.server.TypeDefinitionServiceHelper,
wt.filter.NavigationCriteriaHelper,
wt.navigation.*,
wt.navigation.SimpleDTRequest,
com.ptc.core.meta.common.*,
com.ptc.core.foundation.associativity.common.*,
com.ptc.core.foundation.occurrence.common.*,
wt.pdmlink.PDMLinkProduct,
wt.workflow.engine.WfProcess,
java.net.*,
org.apache.commons.codec.binary.Base64.*,
org.json.*,
wt.epm.structure.EPMDescribeLink,
wt.enterprise.RevisionControlled,
com.ptc.core.foundation.doc.server.LatestReleasedConfigSpec,
wt.epm.structure.EPMStructureHelper,
wt.epm.structure.*,
com.ptc.windchill.uwgm.common.navigate.AssociationTracer,
com.ptc.windchill.uwgm.common.navigate.AssociatedInfo,
com.ptc.windchill.cadx.common.CADDocTypes,
wt.vc.views.ViewManageable,
wt.log4j.LogR,
com.ptc.windchill.uwgm.common.workspace.*,
com.ptc.windchill.uwgm.common.autoassociate.WTPartUtilities,
com.ptc.windchill.uwgm.common.util.PrintHelper,
wt.folder.Folder,
wt.folder.Cabinet,
wt.folder.FolderService,
wt.folder.FolderNotFoundException,
java.math.*,
com.ptc.core.businessRules.*,
com.ptc.core.businessRules.validation.*,
wt.services.applicationcontext.implementation.DefaultServiceProvider,
com.ptc.core.businessRules.relationship.*,
com.ptc.windchill.enterprise.part.PartUsageLinkHelper,
wt.queue.*,
wt.epm.workspaces.*,
wt.epm.familytable.*,
com.ptc.windchill.enterprise.data.*,
wt.introspection.WTIntrospector,
wt.epm.navigator.relationship.AssociatedCADDocs,
wt.epm.modelitems.*,
wt.epm.navigator.relationship.*,
wt.epm.navigator.Filter.Condition,
wt.epm.navigator.Filter,
wt.epm.navigator.EPMNavigateHelper,
com.ptc.windchill.uwgm.common.associate.AssociationType,
wt.representation.RepresentationHelper,
wt.representation.Representable,
org.apache.commons.io.FileUtils,
wt.identity.IdentityFactory,
com.ptc.windchill.enterprise.data.*,
com.google.common.collect.SetMultimap,
com.ptc.windchill.enterprise.data.service.EnterpriseDataService,
com.ptc.windchill.enterprise.data.bll.EnterpriseDataCreateManagerImpl,
wt.services.ServiceFactory,
com.ptc.windchill.enterprise.data.bll.EnterpriseDataCreateManager,
com.ptc.windchill.enterprise.data.bll.EnterpriseDataLinkCreationReport,
com.ptc.cat.entity.server.*,
wt.iba.value.DefaultAttributeContainer,
wt.iba.value.service.IBAValueDBService,
com.ptc.core.lwc.common.dynamicEnum.EnumerationInfoProvider,
com.ptc.core.lwc.common.dynamicEnum.*,
com.steelcase.core.lwc.common.dynamicEnum.*,
com.ptc.core.lwc.server.cache.EnumerationDefinitionManager,
com.steelcase.bom.*,
com.ptc.wvs.server.util.*,
wt.fc.collections.*,
com.ptc.core.lwc.common.DisplayUnitsService,
wt.services.ServiceFactory,
com.ptc.windchill.enterprise.note.commands.*,
com.steelcase.lwc.*,
wt.iba.definition.StringDefinition,
com.ptc.windchill.connected.plm.restcore.model.Entity,
com.ptc.qualitymanagement.nc.NCHelper,
com.ptc.qualitymanagement.nc.Nonconformance,
com.ptc.qualitymanagement.masterdata.entity.MDEntity,
com.ptc.qualitymanagement.capa.plan.*,
com.ptc.qualitymanagement.capa.request.*,
wt.folder.*,
wt.ownership.Ownership,

com.ptc.qualitymanagement.capa.plan.action.*,

com.ptc.qualitymanagement.capa.*,

com.ptc.core.components.beans.ObjectBean,

com.ptc.qualitymanagement.capa.plan.processors.CAPAChangeActivityFormProcessorHelper,

com.ptc.qualitymanagement.capa.investigation.CAPAChangeInvestigation,


com.ptc.qualitymanagement.capa.investigation.*,

com.ptc.qualitymanagement.capa.util.CAPAUtil,

wt.facade.classification.ClassificationFacade,

com.ptc.windchill.csm.common.CsmConstants,
com.steelcase.utility.*,

com.ptc.windchill.cadx.common.util.GenericUtilities,

com.ptc.core.lwc.common.view.AttributeDefinitionReadView,
com.ptc.qualitymanagement.qms.capa.CAPASiteObjectProxy,
com.ptc.core.lwc.server.cache.*,
wt.epm.EPMFamily,
wt.workflow.engine.WfActivity,
wt.workflow.definer.WfAssignedActivityTemplate,

wt.workflow.engine.WfEventHelper,
wt.workflow.engine.ProcessData,
com.ptc.windchill.cadx.common.util.*,
wt.workflow.definer.UserEventVector,
wt.inf.team.ContainerTeamManaged,
wt.fc.dynamicenum.DynamicEnumerationHelper,
wt.inf.team.ContainerTeam,
wt.inf.team.ContainerTeamHelper,
com.ptc.windchill.enterprise.team.server.TeamCCHelper,
com.steelcase.massteammaintenance.*,
com.ptc.core.query.common.CriteriaHelper,
com.ptc.core.query.command.common.BasicQueryCommand,

com.ptc.core.command.common.bean.repository.PageMode,
com.ptc.core.command.common.bean.repository.ResultContainer,
com.ptc.core.query.common.RelationalConfigSpecAttributeContainerFunction,
com.ptc.core.query.common.impl.NoOpCriteriaAugmentor,

com.infoengine.SAK.IeService,
com.infoengine.SAK.ObjectWebject,
org.joda.time.DateTime,
java.text.SimpleDateFormat,
java.time.*,
java.time.temporal.ChronoUnit,
com.infoengine.au.NamingService,
wt.epm.EPMDocumentMaster,
com.ptc.windchill.cadx.common.EPMDocumentUtilities,
java.lang.reflect.*,
com.steelcase.jasperreports.*,
com.ptc.jws.client.handler.*,
com.steelcase.ws.*,

java.net.URL,
javax.xml.namespace.QName,
javax.xml.ws.Service,
com.steelcase.jasperreports.*
"%>
										
<%@ include file="/netmarkets/jsp/util/beginPopup.jspf"%>


<%		
	String result = "";
	String reportJRXML = "ReportDemo.jrxml";
	
	HashMap<String,String> reportParams = new HashMap<String, String>();
	reportParams.put("containerId", "THINK V2");
        
	SteelcaseReportDemoReport cnrc = new SteelcaseReportDemoReport();
        	cnrc.setUseTestData(false);
        cnrc.setReportPath("/opt/ptc/WC01/Windchill_12.0/Windchill/codebase/com/steelcase/jasperreports/" + reportJRXML);
//        	cnrc.setLogoPath("c:\\git\\Windchill\\Windchill Source\\src\\com\\steelcase\\jasperreports\\steelcase1.png");
        cnrc.setReportParams(reportParams);
       	cnrc.initialize();
        cnrc.generateReportPDF("/opt/ptc/WC01/Windchill_12.0/custreport.pdf");
	
	
	
	/*
	ReferenceFactory rf = new ReferenceFactory();
	WTReference ref = rf.getReference("OR:wt.doc.WTDocument:4259347058");		
	WTDocument wtDoc = (WTDocument)ref.getObject();
	
	ReferenceFactory rf = new ReferenceFactory();
	WTReference ref = rf.getReference("OR:wt.epm.EPMDocument:4259347058");		
	EPMDocument epmDoc = (EPMDocument)ref.getObject();
	
	ReferenceFactory rf = new ReferenceFactory();
	WTReference ref = rf.getReference("OR:wt.part.WTPart:4260719771");		
	WTPart wtPart = (WTPart)ref.getObject();
	
	ReferenceFactory rf = new ReferenceFactory();
	WTReference ref = rf.getReference("OR:wt.part.WTPartUsageLink:2090049527");		
	WTPartUsageLink link = (WTPartUsageLink)ref.getObject();
	
	ReferenceFactory rf = new ReferenceFactory();
	WTReference ref = rf.getReference("OR:wt.change2.WTChangeOrder2:8223744554");		
	WTChangeOrder2 cn = (WTChangeOrder2)ref.getObject();
	
ReferenceFactory rf = new ReferenceFactory();
	WTReference ref = rf.getReference("OR:wt.epm.EPMDocument:8543965435");		
	EPMDocument epmDoc = (EPMDocument)ref.getObject();
	
	*/

	


%>

<br><br>
<%=result%>
<%@ include file="/netmarkets/jsp/util/end.jspf"%>
