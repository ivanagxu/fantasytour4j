//login app

Ext.application({
	name : 'login',

	appFolder : 'login/app',

	controllers : [ 'c_login' ],
	launch : function() {
		Ext.create('Ext.container.Viewport', {
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