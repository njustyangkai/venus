define(function (require, exports, module) {
	var Util = require('util/util');
	function CourseSearch() {
		Util.dateFormat();
	};

	module.exports = CourseSearch;

	//========================================
	// init
	//========================================
	CourseSearch.prototype.init = function () {
		
		$('.js-home').click(function() {
			window.location.reload();
		});
		
		this.initTable();
	};
	
	//========================================
	// init table
	//========================================
	CourseSearch.prototype.initTable = function() {
		$('#courseSearch-table').bootstrapTable({
			striped : true,
			//url : 'auth/loadStudents.do',
			/*queryParams : function (params) {
				var temp = {
					limit : params.limit,
					offset : params.offset,
					name : params.search
				};
				return temp;
			},*/
			pagination : true,
			sidePagination : 'server',
			pageNumber : 1,
			pageSize : 10,
			pageList : [10, 25, 50, 100, 'All'],
			search : true,
			strictSearch : false,
			searchText : '',
			showRefresh : true,
			showColumns : true,
			showToggle : true,
			//cardView: true,
			paginationDetailHAlign : 'left',
			paginationHAlign : 'right',
			clickToSelect : true,
			toolbar : '#courseSearch-toolbar',
			dataField : 'list',
			columns : [{
					checkbox : true
				}, {
					field : 'studentName',
					title : '学生姓名'
				}, {
					field : 'teacherName',
					title : '老师姓名'
				}, {
					field : 'startTime',
					title : '开始时间'
				}, {
					field : 'endTime',
					title : '结束时间'
				}]
		});
	};

});
