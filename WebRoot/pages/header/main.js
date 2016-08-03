define(function(require, exports, module){
	var Header = require('./header'); 
	
	var init = function() {
		var header = new Header();
		header.init();
		
	};
	
	return {
		init: init
	};
	
});