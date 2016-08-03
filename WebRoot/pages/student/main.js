define(function(require, exports, module) {
	
	var Student = require('./student');
	
	var init = function(){
		var student = new Student();
		student.init();
	}
	
	return {
		init: init
	}
})