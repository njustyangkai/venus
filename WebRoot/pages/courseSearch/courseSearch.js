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
		this.student();
		this.initTable();
		this.datepicker();
	};
	
	//============================================================
	// datepicker
	//============================================================
	CourseSearch.prototype.datepicker = function () {
		$('#event-startTime, #event-endTime').datetimepicker({
			format: 'Y-m-d H:i',
			step: 10,
			dayOfWeekStart: 1
		});
		$('#event-startTime').datetimepicker({
			onChangeDateTime: function(value) {
				$('#event-endTime').datetimepicker({value: value});
			}
		});
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
			search : false,
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
					field: 'id',
					title: '序号',
					width: 70
				}, {
					field: 'studentName',
					title: '学生姓名',
					width: 150
				}, {
					field: 'teacherName',
					title: '老师姓名',
					width: 150
				}, {
					field: 'startTime',
					title: '开始时间'
				}, {
					field: 'endTime',
					title: '结束时间'
				}, {
					field: '',
					title: '缴费信息'
				}]
		});
	};
	
	//============================================================
	// student
	//============================================================
	CourseSearch.prototype.student = function () {
		var courseSearch = this;
		var students;
		$.ajax({
			url : 'auth/loadStudentsForCourse.do',
			async : false,
			dataType : 'json',
			success : function (result) {
				if (result.success) {
					students = result.object;
				}
			}
		});
		if(students) {
			$.each(students, function(i, v) {
				$('#student-chooser').append('<li><a class="'+ courseSearch.getDiffColor(i) +'" href="javascript: void(0);" data-studentId="' + 
						v.student.id + '" data-studentName="' + v.student.name + '"><i class="fa fa-square"></i> ' + v.student.name+ '</a></li>');
			});
		}
		var studentChooser = $("#student-chooser-btn");
        $("#student-chooser > li > a").click(function(e) {
            e.preventDefault();
            
            currColor = $(this).css("color");
            
            studentChooser.attr('data-studentId', $(this).attr('data-studentId'))
            	.attr('data-studentName', $(this).attr('data-studentName'))
            	.css({"background-color": currColor, "border-color": currColor})
            	.html($(this).text()+' <span class="caret"></span>');
        });
	};
	
	//============================================================
	// get different color
	//============================================================
	CourseSearch.prototype.getDiffColor = function(i) {
		var colorClass= ['text-green', 'text-blue', 'text-navy', 'text-yellow', 'text-orange', 
		                 'text-aqua', 'text-red', 'text-fuchsia', 'text-purple', 'text-light-blue',
		                 'text-olive', 'text-lime', 'text-maroon'];
		return colorClass[i % 13];
	};

});
