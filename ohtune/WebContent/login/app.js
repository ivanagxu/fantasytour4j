//login app

Ext.application({
	name : 'login',

	appFolder : 'login/app',

	controllers : [ 'c_login' ],
	launch : function() {
		Ext.create('Ext.container.Viewport', {
			layout : 'fit',
			items : [ {
				xtype : 'panel',
				items : [ {
					xtype : 'header'
				}, {
					xtype : 'loginform'
				} ]
			} ]
		});
	}
});