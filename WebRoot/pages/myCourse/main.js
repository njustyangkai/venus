define(function(require, exports, module) {
	var MyCourse = require('./myCourse');
	return {
		init: function() {
			var myCourse = new MyCourse();
			myCourse.init();
		}
	};
});