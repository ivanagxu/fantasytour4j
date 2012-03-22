//order app

Ext.application({
	name : 'order',

	appFolder : 'order/app',

	controllers : [ 'c_order' ],
	launch : function() {
		Ext.create('Ext.container.Viewport', {
			items : [ {
				xtype : 'panel',
				items : [ {
					xtype : 'header'
				}, {
					xtype : 'order_table'
				} ]
			} ]
		});
	}
});