import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.framework.ISessionMgr;
import com.crystaldecisions.sdk.occa.infostore.CeScheduleType;
import com.crystaldecisions.sdk.occa.infostore.IContent;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import com.crystaldecisions.sdk.occa.infostore.ISchedulingInfo;
import com.crystaldecisions.sdk.occa.infostore.internal.InfoStore;
import com.crystaldecisions.sdk.occa.managedreports.IReportAppFactory;
import com.crystaldecisions.sdk.occa.report.application.OpenReportOptions;
import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportFormatOptions;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportParameter;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportParameterSingleValue;
import com.crystaldecisions.sdk.plugin.desktop.report.IReport;


public class GenerateReport {

	public static void main(String[] args) throws Exception {
		
		//generateReport();
		//downloadReport();
		downloadFile("5856");
	}
	
	private static void generateReport() throws SDKException, IOException {
		IEnterpriseSession enterpriseSession = null;
		
		try{
			
			String queryString = "SELECT * FROM CI_INFOOBJECTS WHERE SI_INSTANCE=0 AND SI_KIND='CrystalReport' AND SI_NAME='DCBC-RPT-06'";
			
			ISessionMgr sessionMgr = CrystalEnterprise.getSessionMgr();
			enterpriseSession = sessionMgr.logon("administrator", "Password", "DCBC1:6400", "secEnterprise");
			
			InfoStore enterpriseService = (InfoStore) enterpriseSession
					.getService("InfoStore");
			
			IInfoObjects results = enterpriseService.query(queryString);
			
			for (int i = 0; i < results.size(); i++) {
				//Crystal report format
				IInfoObject report = (IInfoObject)results.get(i);
				IReport oReport = (IReport)report;
				
				System.out.println("########## Kind: " + report.getKind());
				
				//Set prameter
				HashMap<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("invoiceNo", "1");
				setReportParameters(oReport, paramMap);
				
				//Schedule Report
				IReportFormatOptions reportFormat = oReport.getReportFormatOptions(); 
	        	
				//Set report format.
				//int formatType = IReportFormatOptions.CeReportFormat.EXCEL;
				//reportFormat.setFormat(formatType);
				        
				//Retrieve the ISchedulingInfo Interface for the Report object and set the schedule 
			    //time (right now) and type (run once)
			    ISchedulingInfo schedInfo = oReport.getSchedulingInfo();
			    schedInfo.setRightNow(true);
			    schedInfo.setType(CeScheduleType.ONCE);
			    
			    //Schedule the InfoObjects.
			    enterpriseService.schedule(results);
			    
			    IInfoObject rpt = oReport.getLatestInstance();
			    
			    System.out.println("###### Last Instance ID: " + rpt.getID());
			    
			    
			}
		}finally{
			if(enterpriseSession != null){
				enterpriseSession.logoff();
			}
		}
	}
	
	private static void downloadReport() throws SDKException, IOException, ReportSDKException{
		IEnterpriseSession enterpriseSession = null;
		
		try{
			//String queryString = "Select SI_LAST_SUCCESSFUL_INSTANCE_ID From CI_INFOOBJECTS Where SI_NAME='DCBC-RPT-06'";
			String queryString = "SELECT * FROM CI_INFOOBJECTS WHERE SI_ID='5856'";
			//String queryString = "SELECT * FROM CI_INFOOBJECTS WHERE SI_INSTANCE=0 AND SI_KIND='CrystalReport' AND SI_NAME='DCBC-RPT-06'";
			
			ISessionMgr sessionMgr = CrystalEnterprise.getSessionMgr();
			enterpriseSession = sessionMgr.logon("administrator", "Password", "DCBC1:6400", "secEnterprise");
			
			InfoStore enterpriseService = (InfoStore) enterpriseSession
					.getService("InfoStore");
			IReportAppFactory reportAppFactory = (IReportAppFactory) enterpriseSession.getService("RASReportFactory");
			
			IInfoObjects results = enterpriseService.query(queryString);
			
			for (int i = 0; i < results.size(); i++) {
				IInfoObject report = (IInfoObject)results.get(i);
				
				//int jobID = report.properties().getInt("SI_LAST_SUCCESSFUL_INSTANCE_ID");
				//System.out.println("########## JobID: " + jobID);
				
				System.out.println("########## Kind: " + report.getKind());
				System.out.println(results.get(i));
	
				ReportClientDocument rcd = reportAppFactory.openDocument(report, OpenReportOptions._openAsReadOnly, java.util.Locale.US);
				
				FileOutputStream fos = new FileOutputStream("C:\\Myreport.xls");
				rcd.getPrintOutputController().export(ReportExportFormat.MSExcel, fos);
				fos.close();
				rcd.close();
				
				try {
					Thread.sleep(100000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}finally{
			if(enterpriseSession != null){
				enterpriseSession.logoff();
			}
		}
	}
	
	public static void downloadFile(String rptJobId) throws SDKException, IOException {
		IEnterpriseSession enterpriseSession = null;
		try{
	        System.out.println("getPdfFile :" + rptJobId);
	        
	        ISessionMgr sessionMgr = CrystalEnterprise.getSessionMgr();
			enterpriseSession = sessionMgr.logon("administrator", "Password", "DCBC1:6400", "secEnterprise");

	        IInfoStore infoStore = (IInfoStore)enterpriseSession.getService("InfoStore");
	        System.out.println("infoStore :" + infoStore.getClass());
	        boolean success = false;

	        IInfoObjects oInfoObjects = infoStore.query("Select SI_ID, SI_SCHEDULE_STATUS,SI_FILES From CI_INFOOBJECTS Where SI_ID=" + rptJobId);
	        System.out.println("oInfoObjects.getResultSize() :" + oInfoObjects.getResultSize());
	        if (oInfoObjects.getResultSize() == 1) {
	            Object o = oInfoObjects.get(0);
	            System.out.println("oInfoObjects.get(0) :" + o.getClass());

	            IInfoObject infoObj = (IInfoObject)o;
	            switch (infoObj.getSchedulingInfo().getStatus()) {
	            case ISchedulingInfo.ScheduleStatus.COMPLETE:
	                System.out.println("ISchedulingInfo.ScheduleStatus.COMPLETE:");
	                success = true;
	                break;
	            case ISchedulingInfo.ScheduleStatus.FAILURE:
	                System.out.println("ISchedulingInfo.ScheduleStatus.FAILURE:");
	                break;
	            case ISchedulingInfo.ScheduleStatus.PENDING:
	                System.out.println("ISchedulingInfo.ScheduleStatus.PENDING:");
	                break;
	            case ISchedulingInfo.ScheduleStatus.RUNNING:
	                System.out.println("ISchedulingInfo.ScheduleStatus.RUNNING:");
	                break;
	            case ISchedulingInfo.ScheduleStatus.PAUSED:
	                System.out.println("ISchedulingInfo.ScheduleStatus.PAUSED:");
	                break;
	            }
	            if (success) {
	                IContent rpt = (IContent)o;
	                System.out.println("  rpt :" + rpt.getClass() + "," + rpt.getContentLength() + "," + rpt.getMimeType());
	                InputStream in = rpt.getInputStream();
	                
	                FileOutputStream out = new FileOutputStream("C:\\Myreport.xls");
	                int read = 0;
	        		byte[] bytes = new byte[1024];
	         
	        		while ((read = in.read(bytes)) != -1) {
	        			out.write(bytes, 0, read);
	        		}
	        		in.close();
	        		out.close();
	            }
	        }
		}
		finally{
			if(enterpriseSession != null){
				enterpriseSession.logoff();
			}
		}
    }
	
	private static void setReportParameters(IReport oReport, Map<String, String> paramMap) throws SDKException, IOException {
	    List<IReportParameter> paramList = oReport.getReportParameters();
	    
	    IReportParameterSingleValue currentValue = null;
	    IReportParameter oReportParameter;

	    for (int i=0; i < paramList.size(); i++) {
		    oReportParameter = (IReportParameter)paramList.get(i);
		    String reportName = oReportParameter.getReportName();
		    
		    System.out.print("###### Setting Report parameter: " + oReportParameter.getParameterName());
		    if (reportName.equals("")) {
				System.out.println("###### On main report");    
				
				if(paramMap.get(oReportParameter.getParameterName()) != null){
					System.out.println("###### Parameter value: " + paramMap.get(oReportParameter.getParameterName()));
			    	currentValue = oReportParameter.getCurrentValues().addSingleValue();
					currentValue.setValue(paramMap.get(oReportParameter.getParameterName()));
			    }
		    }
		    else {
				System.out.print("###### Processing Subreport" + reportName);    
		    }
		}
	}
}
