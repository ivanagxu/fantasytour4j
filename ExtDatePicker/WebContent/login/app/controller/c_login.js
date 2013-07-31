var holidays = ['2013-06-26'];

Ext.define('login.controller.c_login', {
    extend: 'Ext.app.Controller',

    views: [
            'v_login'
        ],
    
    init: function() {
        this.control({
            'viewport > panel': {
                render: this.onPanelRendered
            },
            'button[text="Login"]' :{
            	click: this.onLoginBtnClick
            },
            'button[text="Cancel"]' :{
            	click: this.onCancelBtnClick
            },
            'textfield[name="login_id"]' :{
            	specialkey: this.onTxtLoginSpecialKey
            },
            'textfield[name="password"]' :{
            	specialkey: this.onTxtLoginSpecialKey
            }
        });
    },

    onPanelRendered: function() {
    	
    },
    
    onLoginBtnClick : function(btn) {
    	Ext.Msg.alert('Holiday', holidays[0]);
    },
    
    onTxtLoginSpecialKey: function(field, e){
        if (e.getKey() == e.ENTER) {
        	this.onLoginBtnClick(field);
        }
    },
    
    onCancelBtnClick : function(btn){
    	btn.up('viewport').down('form[id="loginForm"]').getForm().reset();
    }
});