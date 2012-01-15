Ext.define('inventory.controller.c_inventory', {
    extend: 'Ext.app.Controller',

    views: [
            'v_inventory'
        ],
    
    init: function() {
        this.control({
            'viewport > panel': {
                render: this.onPanelRendered
            },
            'panel[id="inventory-grid"]' :{
            	render: this.onJobGridRendered
            }
        });
    },

    onPanelRendered: function() {
        
    },
    
    onJobGridRendered: function() {
    	Ext.data.StoreManager.lookup('allProductStore').load();
    }
});