Ext.define('job.view.v_job', {
	extend : 'Ext.form.Panel',

	alias : 'widget.job_table',

	title : '',

	id : 'job_table',
	
	layout: 'fit',

	initComponent : function() {

		Ext.create('Ext.data.Store', {
			storeId : 'jobStore',
			model : 'JobData',
			proxy : {
				type : 'ajax',
				url : 'JobController?action=getMyJobList',
				reader : {
					type : 'json',
					root : 'data',
					model : 'JobData'
				}
			},
			sortOnLoad: true ,
			sorters: { property: 'priority', direction : 'DESC' }
		});
		
		Ext.create('Ext.data.Store', {
			storeId : 'orderJobStore',
			model : 'JobData',
			proxy : {
				type : 'ajax',
				url : 'JobController?action=getMyJobList',
				reader : {
					type : 'json',
					root : 'data',
					model : 'JobData'
				}
			}
		});

		var sm = Ext.create('Ext.selection.CheckboxModel',{mode : 'SINGLE'});
		var sm2 = Ext.create('Ext.selection.CheckboxModel',{mode : 'SINGLE'});
		this.items = [ 
			Ext.create('Ext.tab.Panel', {
				anchor : '100%',
				items : [ {
					title : '工作列表',
					items : [
						Ext.create('Ext.grid.Panel', {
							id : 'job-grid',
							store : Ext.data.StoreManager.lookup('jobStore'),
							selModel : sm,
							columns : [ {
								header : 'Id',
								dataIndex : 'id',
								hidden : true
							}, {
								header : 'Order Id',
								dataIndex : 'order_id',
								hidden : true
							}, {
								header : '优先级',
								dataIndex : 'priority',
								renderer : function(val)
								{
									if(val == 1)
										return '紧急';
									else
										return '普通';
								}
							}, {
								header : '订单号',
								dataIndex : 'number'
							}, {
								header : '跟单人员',
								dataIndex : 'order_user'
							}, {
								header : '所在部门',
								dataIndex : 'section'
							}, {
								header : '客户名称',
								dataIndex : 'customer_name',
								hidden: true
							}, {
								header : '客户代码',
								dataIndex : 'customer_code'
							}, {
								header : '他司料名',
								dataIndex : 'product_name',
								hidden: true,
							}, {
								header : '我司料名',
								dataIndex : 'product_our_name'
							}, {
								header : '电镀要求',
								dataIndex : 'requirement1'
							}, {
								header : '特殊要求',
								dataIndex : 'requirement2'
							}, {
								header : '生产总数',
								dataIndex : 'total'
							}, {
								header : '未完成数',
								dataIndex : 'remaining'
							}, {
								header : '返工总数',
								dataIndex : 'total_rejected'
							}, {
								header : '完成总数',
								dataIndex : 'finished'
							}, {
								header : '生产期限',
								dataIndex : 'order_deadline'
							}, {
								header : '交货日期',
								dataIndex : 'order_c_deadline',
								hidden: true
							}, {
								header : '完成日期',
								dataIndex : 'complete_date',
								hidden : true
							}, {
								header : '更新日期',
								dataIndex : 'start_date'
							}, {
								header : '订单状态',
								dataIndex : 'order_status'
							}, {
								header : '备注',
								dataIndex : 'order_remark'
							}, {
								header : '产品图片',
								dataIndex : 'product_image',
								renderer: function(val)
								{
									return '<a target="_blank" onclick=DISPLAY_IMAGE_WINDOW("ProductController?action=getProductImage&name=' + val + '")><img src="resources/images/picture.png"/></a>';
								}
							}, {
								header : '产品图纸',
								dataIndex : 'product_drawing',
								renderer: function(val)
								{
									return '<a target="_blank" onclick=DISPLAY_IMAGE_WINDOW("ProductController?action=getProductDrawing&name=' + val + '")><img src="resources/images/picture.png"/></a>';
								}
							}, {
								header : '当天',
								dataIndex : 'isNew',
								hidden: true,
							}, {
								header : '工作状态',
								dataIndex : 'status'
							}, {
								header : '前负责人',
								dataIndex : 'handled_by'
							}, {
								header : '负责人',
								dataIndex : 'assigned_to'
							}],
							height : 500,
							renderTo : Ext.getBody(),
							tbar : [ {
								text : '移交工作',
								type : 'button'
							}, {
								text : '查看订单',
								type : 'button'
							} ],
							viewConfig:{
								getRowClass : function(rec, index)
								{
									if(rec.data.isNew)
									{
										return 'red-row';
									}
									else
									{
										return '';
									}
								}
							}
						}) 
					]
				},{
					title: '模具管理',
					containScroll: true,
		        	autoScroll: true,
					items : [ Ext.create('Ext.grid.Panel', { 
			        	id : 'mold-grid',
			        	height: 500,
			        	store: Ext.data.StoreManager.lookup('allMoldStore'),
			        	selModel : sm2,
			        	columns : [ {
							header : '模具号码',
							dataIndex : 'code'
						} ,{
							header : '模具名称',
							dataIndex : 'name'
						}, {
							header : '所在架号',
							dataIndex : 'stand_no'
						}],
						tbar : [
						    {
						    	text : '添加模具',
						    	xtype : 'button'
						    },
						    {
						    	text : '删除模具',
						    	xtype : 'button'
						    }
			            ]
			        })]
				}]
			})
		];
		this.callParent(arguments);
	}
});