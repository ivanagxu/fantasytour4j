Ext.define('admin.controller.c_productlog', {
	extend : 'Ext.app.Controller',

	views : [ 'v_admin' ],


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
		Ext.data.StoreManager.lookup('productLogStore').proxy.url = 
			'ReportController?action=generateProductLogReportByDateAndSection&date=' + Ext.getCmp('productlog_date').getRawValue() +
			'&job_type=' + encodeURI(Ext.getCmp('productlog_section').getValue());
		Ext.data.StoreManager.lookup('productLogStore').load();
	}
});