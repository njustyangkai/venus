define(function (require, exports, module) {
	var Util = require('util/util');

	function CourseManage() {
		this.students;
		Util.dateFormat();
	};

	module.exports = CourseManage;

	//============================================================
	// init
	//============================================================
	CourseManage.prototype.init = function () {
		var courseManage = this;
		courseManage.calendar();
		courseManage.student();
		courseManage.studentToDialog();
		courseManage.datepicker();
		courseManage.save();

		$('.js-home').click(function() {
			window.location.reload();
		});
		
		$('#btn-event-copy').click(function() {
			var view = $('#calendar-courseManage').fullCalendar('getView');
			var s = new Date(view.start).Format('yyyy-MM-dd hh:mm:ss');
			var e = new Date(view.end).Format('yyyy-MM-dd hh:mm:ss');
			var st = new Date();
			var et = new Date();
			st.setTime((new Date(view.start)).getTime() - 604800000);
			et.setTime((new Date(view.end)).getTime() - 604800000);
			var sl = new Date(st).Format('yyyy-MM-dd hh:mm:ss');
			var el = new Date(et).Format('yyyy-MM-dd hh:mm:ss');
			$.ajax({
	    		url: 'course/copyEvents.do',
	    		data: {'start': s, 'end': e, 'startLast': sl, 'endLast': el},
	    		type: 'post',
	    		success: function(result){
	    			if(result.success){
	    				$('#calendar-courseManage').fullCalendar('refetchEvents');
	    			}else{
	    				Util.alertDialog(result.message);
	    			}
	    		}
	    	});
		});
	};

	//============================================================
	// datepicker
	//============================================================
	CourseManage.prototype.datepicker = function () {
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

	//============================================================
	// student
	//============================================================
	CourseManage.prototype.student = function () {

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
		
		this.students = students;

	};
	
	//============================================================
	// student to dialog
	//============================================================
	CourseManage.prototype.studentToDialog = function() {
		var courseManage = this;
		
		if(courseManage.students) {
			$.each(courseManage.students, function(i, v) {
				
				$('#student-chooser').append('<li><a class="'+ courseManage.getDiffColor(i) +'" href="javascript: void(0);" data-studentId="' + 
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
	CourseManage.prototype.getDiffColor = function(i) {
		
		var colorClass= ['text-green', 'text-blue', 'text-navy', 'text-yellow', 'text-orange', 
		                 'text-aqua', 'text-red', 'text-fuchsia', 'text-purple', 'text-light-blue',
		                 'text-olive', 'text-lime', 'text-maroon'];
		
		return colorClass[i % 13];
		
	};

	//============================================================
	// calendar
	//============================================================
	CourseManage.prototype.calendar = function () {
		var option = {
			defaultView : 'month',
			header : {
				left : 'prev,next today',
				center : 'title',
				right : 'month,agendaWeek,agendaDay'
			},
			slotLabelFormat : 'ahh:mm',
			allDaySlot : false,
			minTime : '08:00:00',
			maxTime : '22:00:00',
			slotLabelInterval : '00:60:00',
			slotDuration : '00:15:00',
			editable : false,
			selectable: false,
			selectHelper: false,
			eventLimit : true, 
			displayEventTime: false,
			viewRender: function(view, element) {
				if(view && view.type == 'month') {
					$('#btn-event-copy').attr('disabled',true);
				}
				if(view && view.type == 'agendaWeek'){
					$('#btn-event-copy').attr('disabled',false);
				}
				if(view && view.type == 'agendaDay'){
					$('#btn-event-copy').attr('disabled',true);
				}
			},
			events: function(start, end, timezone, callback) {
				var s = new Date(start).Format('yyyy-MM-dd hh:mm:ss');
				var e = new Date(end).Format('yyyy-MM-dd hh:mm:ss');
				
				$.ajax({
					url: 'course/getEvents.do',
					type: 'get',
					data: {
						start: s,
						end: e
					},
					dataType: 'json',
					success: function(result) {
						if(result.success) {
							if(result.object.events) {
								var e = result.object.events;
								var events = [];	
								$.each(e, function (i, v) {
									
									var event = {};
									
									var sTime = new Date(v.startTime.time).Format('hh:mm');
									var eTime = new Date(v.endTime.time).Format('hh:mm');
									
									event.id = v.eventId;
									event.title = v.studentName + sTime + '-' + eTime;
									event.start = new Date(v.startTime.time).Format('yyyy-MM-dd hh:mm:ss');
									event.end = new Date(v.endTime.time).Format('yyyy-MM-dd hh:mm:ss');
									event.color = v.color;
									event.allDay = false;
									event.textColor = '#fff !important';
									event.studentId = v.studentId;
									event.studentName = v.studentName;
									
									events.push(event);	
									
								});
									
								callback(events);
							}
							
						}else{
							Util.alertDialog(result.message);
						}
					}
				});
			},
			eventClick: function(event, jsEvent, view) {
				Util.confirmDialog('确认删除课程吗？', function() {
					$.ajax({
						url: 'course/removeEvent.do',
						type: 'post',
						data: {'id': event.id},
						dataType: 'json',
						success: function(result) {
							$('#dialog-confirm').modal('hide');
							if(result.success) {
								$('#calendar-courseManage').fullCalendar('removeEvents', event.id);
							}else{
								Util.alertDialog(result.message);
							}
						}
					});
				});
			},
			eventMouseover: function( event, jsEvent, view ) { 
				var studentChooser = $("#student-chooser-btn");
				var studentId = event.studentId;
				$('#student-chooser > li').each(function(i, v) {
					var a = $(v).find('a');
					if (a.attr('data-studentId') === studentId){
						studentChooser.attr('data-studentId', studentId)
		            	.attr('data-studentName', a.attr('data-studentName'))
		            	.css({"background-color": a.css('color'), "border-color": a.css('color')})
		            	.html(a.text()+' <span class="caret"></span>');
					}
				});
				$('#event-startTime').datetimepicker({value: event.start._i});
				$('#event-endTime').datetimepicker({value: event.end._i});
			}
		};

		$('#calendar-courseManage').fullCalendar(option);
	};
	
	//============================================================
	// save event
	//============================================================
	CourseManage.prototype.save = function() {
		$('#btn-event-save').click(function() {
			var studentId = $('#student-chooser-btn').attr('data-studentId');
			var start = $('#event-startTime').val().trim();
			var end = $('#event-endTime').val().trim();
			if(!studentId) {
				Util.alertDialog('未选择学生！');
				return;
			}
			if(start=='' || end=='') {
				Util.alertDialog('未选择时间！');
				return;
			}
			var startTime = new Date(start);
			var endTime = new Date(end);
			if(startTime >= endTime) {
				Util.alertDialog('结束时间必须大于开始时间！');
				return;
			}
			$.ajax({
				url: 'course/saveEvent.do',
				type: 'post',
				dataType: 'json',
				data: {
					'event.studentId': studentId,
					'event.studentName': $('#student-chooser-btn').attr('data-studentName'),
					'event.startTime': startTime.Format('yyyy-MM-dd hh:mm:ss'),
					'event.endTime': endTime.Format('yyyy-MM-dd hh:mm:ss'),
					'event.color': $('#student-chooser-btn').css('background-color')
				},
				success: function(result) {
					if(result.success) {
						$('#calendar-courseManage').fullCalendar('refetchEvents');
					}else{
						Util.alertDialog(result.message);
					}
				}
			});
		});
	};
});
