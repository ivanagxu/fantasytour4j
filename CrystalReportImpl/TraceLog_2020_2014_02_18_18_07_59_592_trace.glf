FILE_TYPE:DAAA96DE-B0FB-4c6e-AF7B-A445F5BF9BE2
ENCODING:UTF-8
RECORD_SEPARATOR:30
COLUMN_SEPARATOR:124
ESC_CHARACTER:27
COLUMNS:Location|Guid|Time|Tzone|Trace|Log|Importance|Severity|Exception|DeviceName|ProcessID|ThreadID|ThreadName|ScopeTag|MajorTick|MinorTick|MajorDepth|MinorDepth|RootName|RootID|CallerName|CallerID|CalleeName|CalleeID|ActionID|DSRRootContextID|DSRTransaction|DSRConnection|DSRCounter|User|ArchitectComponent|DeveloperComponent|Administrator|Unit|CSNComponent|Text
SEVERITY_MAP: |None| |Success|W|Warning|E|Error|A|Assertion
HEADER_END
|174E1558E6F547A1BC41DDBD96B669C20|2014 02 18 18:07:59.596|+0800|Error| |==|E| |TraceLog| 2020|   1|main            | ||||||||||||||||||||com.crystaldecisions.sdk.occa.managedreports.ras.internal.RASReportAppFactory||RASReportAppFactory(): ,aps=DCBC1.asiapacific.hpqcorp.net:6400,uri=osca:iiop://DCBC1.asiapacific.hpqcorp.net:6400;SI_SESSIONID=5888J2GF7yVpJH3poTuBYFtr7IH,SI_TENANT_ID=0
com.crystaldecisions.sdk.occa.report.lib.ReportSDKServerException: Error in File {F562CC73-9454-486B-9E1B-1AF8895402BB}.rpt:
File I/O error.
Details: error---- Error code:-2147483118 [CRSDK00000000] Error code name:failed
	at com.crystaldecisions.sdk.occa.report.lib.ReportSDKServerException.throwReportSDKServerException(ReportSDKServerException.java:109)
	at com.crystaldecisions.proxy.remoteagent.ExceptionHelper.throwResultInfoException(ExceptionHelper.java:192)
	at com.crystaldecisions.sdk.occa.report.application.ReportClientDocument.sendSyncRequest(ReportClientDocument.java:805)
	at com.crystaldecisions.sdk.occa.report.application.ReportClientDocument.openRemoteDocument(ReportClientDocument.java:1826)
	at com.crystaldecisions.sdk.occa.report.application.ReportClientDocument.doOpen(ReportClientDocument.java:743)
	at com.crystaldecisions.sdk.occa.report.application.ClientDocument.open(ClientDocument.java:1027)
	at com.crystaldecisions.sdk.occa.report.application.ReportClientDocument.open(ReportClientDocument.java:225)
	at com.crystaldecisions.sdk.occa.managedreports.ras.internal.RASReportAppFactory.openDocument(RASReportAppFactory.java:530)
	at com.crystaldecisions.sdk.occa.managedreports.ras.internal.RASReportAppFactory.openDocument(RASReportAppFactory.java:373)
	at GenerateReport.downloadReport(GenerateReport.java:118)
	at GenerateReport.main(GenerateReport.java:33)

