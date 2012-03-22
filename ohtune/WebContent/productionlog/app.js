//productionlog app

Ext.application({
	name : 'productionlog',

	appFolder : 'productionlog/app',

	controllers : [ 'c_productionlog'],
	
	launch : function() {
		Ext.create('Ext.container.Viewport', {
			items : [ {
				xtype : 'panel',
				items : [ {
					xtype : 'header'
				}, {
					xtype : 'productionlogform'
				} ]
			} ]
		});
	}
});