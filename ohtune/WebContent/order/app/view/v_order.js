Ext.define('order.view.v_order', {
	extend : 'Ext.form.Panel',

	alias : 'widget.order_table',

	id : 'order_table',

	initComponent : function() {
		

		this.layout = 'anchor';

		Ext.create('Ext.data.Store', {
			storeId : 'orderStore',
			model : 'OrderData',
			proxy : {
				type : 'ajax',
				url : 'OrderController?action=getMyOrderList',
				reader : {
					type : 'json',
					root : 'data',
					model : 'OrderData'
				}
			},
			sortOnLoad: true ,
			sorters: { property: 'priority', direction : 'DESC' }
		});
		
		Ext.create('Ext.data.Store', {
			storeId : 'completedOrderStore',
			model : 'OrderData',
			proxy : {
				type : 'ajax',
				url : 'OrderController?action=getCompletedOrderList',
				reader : {
					type : 'json',
					root : 'data',
					model : 'OrderData'
				}
			},
			sortOnLoad: true ,
			sorters: { property: 'priority', direction : 'DESC' }
		});
		
		Ext.create('Ext.data.Store', {
			storeId : 'jobStore',
			model : 'JobData',
			proxy : {
				type : 'ajax',
				url : 'JobController?action=getJobByOrder',
				reader : {
					type : 'json',
					root : 'data',
					model : 'JobData'
				}
			}
		});
		
		Ext.create('Ext.data.Store', {
			storeId : 'productRateStore',
			model : 'ProductRate',
			proxy : {
				type : 'ajax',
				url : 'ReportController?action=generateProductRateReportByProduct',
				reader : {
					type : 'json',
					root : 'data',
					model : 'ProductRate'
				}
			}
		});
		
		var sm = Ext.create('Ext.selection.CheckboxModel',{mode : 'SINGLE'});
		var sm2 = Ext.create('Ext.selection.CheckboxModel',{mode : 'SINGLE'});
		this.items = [
			Ext.create('Ext.tab.Panel', {
				anchor : '100%',
				id : 'order-tab',
				items : [ {
					title : '在线订单',
					items : [
						Ext.create('Ext.grid.Panel', {
							id : 'order-grid',
							store : Ext.data.StoreManager.lookup('orderStore'),
							selModel : sm,
							columns : [
						    {
								header : 'Id',
								dataIndex : 'id',
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
								dataIndex : 'creator'
							}, {
								header : '客户名称',
								dataIndex : 'customer_name'
							}, {
								header : '客户代码',
								dataIndex : 'customer_code'
							}, {
								header : '他司料名',
								dataIndex : 'product_name'
							}, {
								header : '我司料名',
								dataIndex : 'product_our_name'
							}, {
								header : '电镀要求',
								dataIndex : 'requirement_1'
							}, {
								header : '特殊要求',
								dataIndex : 'requirement_2'
							}, {
								header : '订单数量',
								dataIndex : 'quantity'
							}, {
								header : '生产期限',
								dataIndex : 'deadline'
							}, {
								header : '交货日期',
								dataIndex : 'c_deadline'
							}, {
								header : '产品图片',
								dataIndex : 'product_name',
								renderer: function(val)
								{
									return '<a target="_blank" href="ProductController?action=getProductImage&name=' + val + '"><img src="resources/images/picture.png"/></a>';
								}
							}, {
								header : '产品图纸',
								dataIndex : 'product_name',
								renderer: function(val)
								{
									return '<a target="_blank" href="ProductController?action=getProductDrawing&name=' + val + '"><img src="resources/images/picture.png"/></a>';
								}
							}, {
								header : '订单状态',
								dataIndex : 'status'
							}, {
								header : '备注',
								dataIndex : 'requirement_4'
							}],
							height : 600,
							renderTo : Ext.getBody(),
							tbar : [ {
								xtype : 'button',
								text : '创建订单'
							}, {
								xtype : 'button',
								text : '查看订单'
							}, {
								xtype : 'button',
								text : '审核订单'
							}, {
								xtype : 'button',
								text : '暂停订单'
							}, {
								xtype : 'button',
								text : '恢复订单'
							}, {
								xtype : 'button',
								text : '取消订单'
							}, {
								xtype : 'button',
								text : '删除订单'
							} ],
							viewConfig:{
								getRowClass : function(rec, index)
								{
									if(rec.data.status == "审核中")
									{
										return 'green-row';
									}
									else
									{
										return '';
									}
								}
							}
						})
			        ]
				}, {
					title : '完成订单',
					items: [
						Ext.create('Ext.grid.Panel', {
							id : 'completed-order-grid',
							store : Ext.data.StoreManager.lookup('completedOrderStore'),
							selModel : sm2,
							columns : [
						    {
								header : 'Id',
								dataIndex : 'id',
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
								dataIndex : 'creator'
							}, {
								header : '客户名称',
								dataIndex : 'customer_name'
							}, {
								header : '客户代码',
								dataIndex : 'customer_code'
							}, {
								header : '他司料名',
								dataIndex : 'product_name'
							}, {
								header : '我司料名',
								dataIndex : 'product_our_name'
							}, {
								header : '电镀要求',
								dataIndex : 'requirement_1'
							}, {
								header : '特殊要求',
								dataIndex : 'requirement_2'
							}, {
								header : '订单数量',
								dataIndex : 'quantity'
							}, {
								header : '生产期限',
								dataIndex : 'deadline'
							}, {
								header : '交货日期',
								dataIndex : 'c_deadline'
							}, {
								header : '产品图片',
								dataIndex : 'product_name',
								renderer: function(val)
								{
									return '<a target="_blank" href="ProductController?action=getProductImage&name=' + val + '"><img src="resources/images/picture.png"/></a>';
								}
							}, {
								header : '产品图纸',
								dataIndex : 'product_name',
								renderer: function(val)
								{
									return '<a target="_blank" href="ProductController?action=getProductDrawing&name=' + val + '"><img src="resources/images/picture.png"/></a>';
								}
							}, {
								header : '订单状态',
								dataIndex : 'status'
							}, {
								header : '备注',
								dataIndex : 'requirement_4'
							}],
							height : 600,
							renderTo : Ext.getBody(),
							tbar : [{
								xtype : 'button',
								text : '查看订单'
							}, {
								xtype : 'button',
								text : '删除订单'
							} ]
						})
			        ]
				}
				]
			}
			)
		];

		this.callParent(arguments);
	}
});