//job app

Ext.application({
	name : 'job',

	appFolder : 'job/app',

	controllers : [ 'c_job' ],
	launch : function() {
		Ext.create('Ext.container.Viewport', {
			items : [ {
				xtype : 'panel',
				items : [ {
					xtype : 'header'
				}, {
					xtype : 'job_table'
				} ]
			} ]
		});
	}
});