define(function(require, exports, module) {
	var CourseManage = require('./courseManage');
	
	return {
		init: function() {
			var courseManage = new CourseManage();
			
			courseManage.init();
		}
	};
	
});