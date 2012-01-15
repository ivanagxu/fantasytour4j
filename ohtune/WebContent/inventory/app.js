//inventory app

Ext.application({
	name : 'inventory',

	appFolder : 'inventory/app',

	controllers : [ 'c_inventory' ],
	launch : function() {
		Ext.create('Ext.container.Viewport', {
			layout : 'fit',
			items : [ {
				xtype : 'panel',
				items : [ {
					xtype : 'header'
				}, {
					xtype : 'inventory_table'
				} ]
			} ]
		});
	}
});