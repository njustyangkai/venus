define(function (require, exports, module) {
	var Util = require('util/util');
	function CourseSearch() {
		Util.dateFormat();
	};

	module.exports = CourseSearch;

	//============================================================
	// init
	//============================================================
	CourseSearch.prototype.init = function () {

		$('.js-home').click(function() {
			window.location.reload();
		});
		
	};

});
