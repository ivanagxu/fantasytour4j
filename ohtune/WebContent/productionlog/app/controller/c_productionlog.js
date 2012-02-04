Ext.define('productionlog.controller.c_productionlog', {
	extend : 'Ext.app.Controller',

	views : [ 'v_productionlog' ],


	init : function() {
		this.control({
			'panel[id=productlog-grid]' : {
				render : this.onProductLogGridRender
			},
			'datefield[name=date]' : {
				select : this.getProductLog
			},
			'combobox[name=job_type]' : {
				select : this.getProductLog
			},
			'button[text=刷新记录]' : {
				click : this.getProductLog
			}
		});
	},
	
	onProductLogGridRender : function()
	{
		Ext.data.StoreManager.lookup('jobTypeStore').load();
	},
	
	getProductLog : function()
	{
		Ext.data.StoreManager.lookup('productionLogStore').proxy.url = 
			'ReportController?action=generateProductLogReportByDateAndSection&date=' + Ext.getCmp('productlog_date').getRawValue() +
			'&job_type=' + encodeURI(Ext.getCmp('productlog_section').getValue());
		Ext.data.StoreManager.lookup('productionLogStore').load();
	}
});