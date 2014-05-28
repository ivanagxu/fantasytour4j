FILE_TYPE:DAAA96DE-B0FB-4c6e-AF7B-A445F5BF9BE2
ENCODING:UTF-8
RECORD_SEPARATOR:30
COLUMN_SEPARATOR:124
ESC_CHARACTER:27
COLUMNS:Location|Guid|Time|Tzone|Trace|Log|Importance|Severity|Exception|DeviceName|ProcessID|ThreadID|ThreadName|ScopeTag|MajorTick|MinorTick|MajorDepth|MinorDepth|RootName|RootID|CallerName|CallerID|CalleeName|CalleeID|ActionID|DSRRootContextID|DSRTransaction|DSRConnection|DSRCounter|User|ArchitectComponent|DeveloperComponent|Administrator|Unit|CSNComponent|Text
SEVERITY_MAP: |None| |Success|W|Warning|E|Error|A|Assertion
HEADER_END
|318DA07417E74B5DBC17C0F314B6D7490|2014 02 19 09:44:35.879|+0800|Error| |==|E| |TraceLog| 7556|   1|main            | ||||||||||||||||||||com.crystaldecisions.sdk.occa.managedreports.ras.internal.RASReportAppFactory||getReportAppSession(): svr=,aps=DCBC1.asiapacific.hpqcorp.net:6400
com.crystaldecisions.sdk.occa.report.lib.ReportSDKServerException: Unable to connect to the server: . - Unable to find servers in CMS DCBC1.asiapacific.hpqcorp.net:6400 and cluster @DCBC1.asiapacific.hpqcorp.net:6400 with kind rptappserver and service DTSRequestor. All such servers could be down or disabled by the administrator. (FWM 01014)---- Error code:-2147217387 [CRSDK00000838] Error code name:connectServer
	at com.crystaldecisions.sdk.occa.report.lib.ReportSDKServerException.throwReportSDKServerException(ReportSDKServerException.java:115)
	at com.crystaldecisions.sdk.occa.managedreports.ras.internal.CECORBACommunicationAdapter.connect(CECORBACommunicationAdapter.java:171)
	at com.crystaldecisions.sdk.occa.managedreports.ras.internal.RASReportAppFactory.getReportAppSession(RASReportAppFactory.java:681)
	at com.crystaldecisions.sdk.occa.managedreports.ras.internal.RASReportAppFactory.openDocument(RASReportAppFactory.java:512)
	at com.crystaldecisions.sdk.occa.managedreports.ras.internal.RASReportAppFactory.openDocument(RASReportAppFactory.java:485)
	at com.crystaldecisions.sdk.occa.managedreports.ras.internal.RASReportAppFactory.openDocument(RASReportAppFactory.java:438)
	at GenerateReport.downloadReport(GenerateReport.java:117)
	at GenerateReport.main(GenerateReport.java:32)
Caused by: com.crystaldecisions.enterprise.ocaframework.OCAFrameworkException$AllServicesDown: Unable to find servers in CMS DCBC1.asiapacific.hpqcorp.net:6400 and cluster @DCBC1.asiapacific.hpqcorp.net:6400 with kind rptappserver and service DTSRequestor. All such servers could be down or disabled by the administrator. (FWM 01014)
	at com.crystaldecisions.enterprise.ocaframework.ServerController.redirectServer(ServerController.java:664)
	at com.crystaldecisions.enterprise.ocaframework.ServiceMgr.redirectServer(ServiceMgr.java:959)
	at com.crystaldecisions.enterprise.ocaframework.ManagedSession.redirectServer(ManagedSession.java:338)
	at com.crystaldecisions.enterprise.ocaframework.ManagedSession.get(ManagedSession.java:247)
	at com.crystaldecisions.enterprise.ocaframework.ManagedSessions.get(ManagedSessions.java:299)
	at com.crystaldecisions.enterprise.ocaframework.ServiceMgr.getManagedService_aroundBody4(ServiceMgr.java:520)
	at com.crystaldecisions.enterprise.ocaframework.ServiceMgr.getManagedService_aroundBody5$advice(ServiceMgr.java:512)
	at com.crystaldecisions.enterprise.ocaframework.ServiceMgr.getManagedService(ServiceMgr.java:1)
	at com.crystaldecisions.sdk.occa.managedreports.ras.internal.CECORBACommunicationAdapter.connect(CECORBACommunicationAdapter.java:147)
	... 6 more

