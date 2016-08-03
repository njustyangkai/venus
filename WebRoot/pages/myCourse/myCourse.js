define(function(require, exports, module) {
	var Util = require('util/util');
	
	function MyCourse() {
		Util.dateFormat();
	}
	
	module.exports = MyCourse;
	
	//===================================
	// init
	//===================================
	MyCourse.prototype.init = function() {
		$('.js-home').click(function() {
			window.location.reload();
		});
		
		this.calendar();
	};
	
	//===================================
	// calendar
	//===================================
	MyCourse.prototype.calendar = function() {
		
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
				events: function(start, end, timezone, callback) {
					$.ajax({
						url: 'auth/isLogin.do',
						type: 'get',
						dataType: 'json',
						success: function(r) {
							if(r.success) {
								var s = new Date(start).Format('yyyy-MM-dd hh:mm:ss');
								var e = new Date(end).Format('yyyy-MM-dd hh:mm:ss');
								
								$.ajax({
									url: 'course/getMyEvents.do',
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
							}else{
								Util.alertDialog(r.message);
							}
						}
					});
				}
			};

			$('#calendar-myCourse').fullCalendar(option);
	};
	
});