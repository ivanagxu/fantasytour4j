Ext.define('productionlog.view.v_productionlog', {
	extend : 'Ext.form.Panel',

	alias : 'widget.productionlogform',

	title : '',

	id : 'productionlogform',

	initComponent : function() {
		this.layout = 'anchor';
		
		
		Ext.create('Ext.data.Store', {
			storeId : 'productionLogStore',
			model : 'ProductionLogData',
			proxy : {
				type : 'ajax',
				url : 'ReportController?action=generateProductLogReportByDateAndSection',
				reader : {
					type : 'json',
					root : 'data',
					model : 'ProductionLogData'
				}
			}
		});
		
		
		this.items = [ Ext.create('Ext.tab.Panel', {
			anchor : '100%',
			items : [{
				title : '生产记录',
				containScroll: true,
	        	autoScroll: true,
				items : [ Ext.create('Ext.grid.Panel', { 
		        	id : 'productlog-grid',
		        	height: 500,
		        	store: Ext.data.StoreManager.lookup('productionLogStore'),
		        	columns : [{
						header : '我司料名',
						dataIndex : 'product_our_name'
					}, {
						header : '完成总数',
						dataIndex : 'finished'
					}, {
						header : '废品数',
						dataIndex : 'disuse'
					}, {
						header : '返工数',
						dataIndex : 'rejected'
					}],
					tbar : [
					    {
					    	labelAlign : 'right',
							fieldLabel : '生产日期',
							editable : false,
							name : 'date',
							id : 'productlog_date',
							xtype: 'datefield',
					        maxValue: new Date(),
					        value: new Date(),
					        format: 'Y-m-d'
					    },
					    Ext.create('Ext.form.ComboBox', {
					    	labelAlign : 'right',
						    fieldLabel: '部门',
						    id : 'productlog_section',
						    editable: false,
						    store: Ext.data.StoreManager.lookup('jobTypeStore'),
						    queryMode: 'local',
						    displayField: 'name',
						    valueField: 'name',
						    name : 'job_type'
						}),
						{
					    	xtype : 'button',
					    	text : '刷新记录'
						}
		            ]
		        })]
			}]
		}) ];

		this.callParent(arguments);
	}
});
