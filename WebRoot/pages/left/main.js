define(function(require, exports, module) {
	
	var Left = require('./left');
	
	var init = function(){
		var left = new Left();
		left.init();
	}
	
	return {
		init: init
	}
})