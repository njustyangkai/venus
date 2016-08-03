define(function(require, exports, module){
	
	var Index = require('./index');
	
	var init = function() {
		var index = new Index();
		index.init();
	};
	return {
		init: init
	};
	
});