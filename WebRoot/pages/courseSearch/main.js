define(function(require, exports, module) {
	var CourseSearch = require('./courseSearch');
	
	return {
		init: function() {
			var courseSearch = new CourseSearch();
			courseSearch.init();
		}
	};
	
});