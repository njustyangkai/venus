define(function (require, exports, module) {
	
	var Util = require('util/util');

	function Student() {
		Util.dateFormat();
	};

	module.exports = Student;

	//=====================================
	// init
	//=====================================
	Student.prototype.init = function () {

		var student = this;

		student.initTable();

		student.addValid();

		student.editValid();

		student.deleteStudents();

		student.lock();

		student.unlock();

		/*$("button").tooltip({
			placement : 'bottom'
		});*/

		$("[data-mask]").inputmask();
		
		$('.js-home').click(function() {
			window.location.reload();
		});
		

	};

	//========================================
	// initTable
	//========================================
	Student.prototype.initTable = function () {
		$('#auth-table').bootstrapTable({
			striped : true,
			url : 'auth/loadStudents.do',
			pagination : true,
			sidePagination : 'server',
			pageNumber : 1,
			pageSize : 10,
			pageList : [10, 25, 50, 100, 'All'],
			sortable: true,
			sortName: 'user.username',
			sortOrder: 'asc',
			search : true,
			strictSearch : false,
			searchText : '',
			showRefresh : true,
			showColumns : true,
			showToggle : true,
			paginationDetailHAlign : 'left',
			paginationHAlign : 'right',
			clickToSelect : true,
			toolbar : '#student-toolbar',
			dataField : 'list',
			columns : [{
					checkbox : true
				}, {
					field : 'user.username',
					title : '用户名',
					sortable: true
				}, {
					field : 'user.password',
					title : '密码',
					visible : false
				}, {
					field : 'student.name',
					title : '姓名',
					sortable: true
				}, {
					field : 'student.sex',
					title : '性别',
					sortable: true
				}, {
					field : 'student.grade',
					title : '年级',
					sortable: true
				}, {
					field : 'student.birthday',
					title : '生日',
					sortable: true,
					formatter : function (value, row, index) {
						return (new Date(value)).Format('yyyy-MM-dd');
					}
				}, {
					field : 'student.email',
					title : '邮箱'
				}, {
					field : 'student.phone',
					title : '电话'
				}, {
					field : 'student.parentPhone',
					title : '家长电话'
				}, {
					field : 'user.createTime',
					title : '创建时间',
					sortable: true,
					visible : false,
					formatter : function (value, row, index) {
						return (new Date(value)).Format('yyyy-MM-dd hh:mm:ss');
					}
				}, {
					field : 'user.lastLogTime',
					title : '最后登录时间',
					sortable: true,
					formatter : function (value, row, index) {
						return (new Date(value)).Format('yyyy-MM-dd hh:mm:ss');
					}
				}, {
					field : 'user.state',
					title : '账号状态',
					sortable: true,
					formatter : function (value, row, index) {
						if (value == 0) {
							return '<span class="label label-danger">已锁定</span>';
						} else if (value == 1) {
							return '<span class="label label-success">已激活</span>';
						} else {
							return value;
						}
					}
				}
			]
		});
		
	};

	//========================================
	// addValid
	//========================================
	Student.prototype.addValid = function () {

		$('.js-student-table .add').click(function () {
			$('#student-add-form').bootstrapValidator('resetForm', true);
		});

		$('#student-add-form').bootstrapValidator({
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			submitButtons : '#student-add-submit-btn',
			submitHandler : function (validator, form, submitButton) {
				$.ajax({
					url : 'auth/addStudent.do',
					type : 'post',
					data : form.serialize(),
					dataType : 'json',
					success : function (result) {
						if (result.success) {
							$('#dialog-add').modal('hide');
							$('#auth-table').bootstrapTable('refresh');
						} else {
							$('#dialog-alert p').text('新增学生失败');
							$('#dialog-alert').modal({
								backdrop : true,
								keyboard : true,
								show : true
							});
						}
					}
				});
			},
			fields : {
				'user.username' : {
					trigger : 'blur',
					validators : {
						notEmpty : {
							message : '用户名不能为空'
						},
						stringLength : {
							min : 6,
							max : 20,
							message : '用户名长度必须在6~20之间'
						},
						regexp : {
							regexp : /^[a-zA-Z0-9_]+$/,
							message : '用户名只能由字母、数字、下划线组成'
						},
						callback : {
							message : '此用户名已存在',
							callback : function (value, validator) {
								var e;
								$.ajax({
									url : 'auth/isUsernameExist.do',
									async : false,
									type : 'post',
									data : {
										'username' : value
									},
									dataType : 'json',
									success : function (result) {
										if (result.success) {
											e = false;
										} else {
											e = true;
										}
									}

								});
								return e;
							}

						}

					}
				},
				'user.password' : {
					validators : {
						notEmpty : {
							message : '密码不能为空'
						},
						stringLength : {
							min : 6,
							max : 20,
							message : '密码长度必须在6~20之间'
						}
					}
				},
				'passwordRepeat' : {
					trigger : 'blur',
					selector : '#student-add-repeatPassword',
					validators : {
						notEmpty : {
							message : '确认密码不能为空'
						},
						stringLength : {
							min : 6,
							max : 20,
							message : '确认密码长度必须在6~20之间'
						},
						callback : {
							message : '两次密码输入不一致',
							callback : function (value, validator) {
								var e = true;
								if ($('#student-add-password').val() != value) {
									e = false;
								}
								return e;
							}

						}

					}
				},
				'student.name' : {
					validators : {
						notEmpty : {
							message : '姓名不能为空'
						}
					}
				},
				'student.sex' : {
					validators : {
						notEmpty : {
							message : '性别不能为空'
						}
					}
				},
				'student.grade' : {
					validators : {
						notEmpty : {
							message : '年级不能为空'
						}
					}
				},
				'student.birthday' : {
					validators : {
						notEmpty : {
							message : '生日不能为空'
						}
					}
				},
				'student.email' : {
					validators : {
						notEmpty : {
							message : '邮箱不能为空'
						},
						emailAddress : {
							message : '邮箱格式不对'
						}
					}
				},
				'student.phone' : {
					validators : {
						regexp : {
							regexp : /^[0-9]+$/,
							message : '学生电话格式不对'
						}
					}
				},
				'student.parentPhone' : {
					validators : {
						regexp : {
							regexp : /^[0-9]+$/,
							message : '家长电话格式不对'
						}
					}
				}

			}

		});
	};

	//========================================
	// editValid
	//========================================
	Student.prototype.editValid = function () {

		$('.js-student-table .edit').click(function () {
			$('#student-edit-form').bootstrapValidator('resetForm', true);
			var selections = $('#auth-table').bootstrapTable('getSelections');

			if (selections.length == 0) {
				$('#dialog-alert p').text('未选择');
				$('#dialog-alert').modal({
					backdrop : true,
					keyboard : true,
					show : true
				});
			} else if (selections.length > 1) {
				$('#dialog-alert p').text('一次只能修改一个');
				$('#dialog-alert').modal({
					backdrop : true,
					keyboard : true,
					show : true
				});
			} else {

				var u = selections[0].user;
				var s = selections[0].student;

				$('#student-edit-username').data('uuid', s.id);
				$('#student-edit-username').val(u.username);
				$('#dialog-edit input:radio[name="student.sex"][value="' + s.sex + '"]').prop('checked', true);
				$('#student-edit-name').val(s.name);
				$('#student-edit-grade').val(s.grade);
				$('#student-edit-birthday').val((new Date(s.birthday)).Format('yyyy/MM/dd'));
				$('#student-edit-email').val(s.email);
				$('#student-edit-phone').val(s.phone);
				$('#student-edit-parentPhone').val(s.parentPhone);

				$('#dialog-edit').modal({
					backdrop : true,
					keyboard : true,
					show : true
				});

			};
		});

		$('#student-edit-form').bootstrapValidator({
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			submitButtons : '#student-edit-submit-btn',
			submitHandler : function (validator, form, submitButton) {
				$.ajax({
					url : 'auth/editStudent.do',
					type : 'POST',
					data : form.serialize() + '&student.id=' + $('#student-edit-username').data('uuid'),
					dataType : 'json',
					success : function (result) {
						if (result.success) {
							$('#dialog-edit').modal('hide');
							$('#auth-table').bootstrapTable('refresh');
						} else {
							$('#dialog-alert p').text('编辑学生失败');
							$('#dialog-alert').modal({
								backdrop : true,
								keyboard : true,
								show : true
							});
						}
					}
				});
			},
			fields : {
				'student.name' : {
					validators : {
						notEmpty : {
							message : '姓名不能为空'
						}
					}
				},
				'student.sex' : {
					validators : {
						notEmpty : {
							message : '性别不能为空'
						}
					}
				},
				'student.grade' : {
					validators : {
						notEmpty : {
							message : '年级不能为空'
						}
					}
				},
				'student.birthday' : {
					validators : {
						notEmpty : {
							message : '生日不能为空'
						}
					}
				},
				'student.email' : {
					validators : {
						notEmpty : {
							message : '邮箱不能为空'
						},
						emailAddress : {
							message : '邮箱格式不对'
						}
					}
				},
				'student.phone' : {
					validators : {
						regexp : {
							regexp : /^[0-9]+$/,
							message : '学生电话格式不对'
						}
					}
				},
				'student.parentPhone' : {
					validators : {
						regexp : {
							regexp : /^[0-9]+$/,
							message : '家长电话格式不对'
						}
					}
				}

			}

		});
	};

	//========================================
	// deleteStudents
	//========================================
	Student.prototype.deleteStudents = function () {

		$('.js-student-table .minus').click(function () {

			var selections = $('#auth-table').bootstrapTable('getSelections');

			if (selections.length == 0) {

				$('#dialog-alert p').text('未选择');
				$('#dialog-alert').modal({
					backdrop : true,
					keyboard : true,
					show : true
				});

			} else {

				$('#dialog-confirm p').text('确认删除所选学生吗？');
				$('#dialog-confirm').modal({
					backdrop : true,
					keyboard : true,
					show : true
				});

				$('#dialog-confirm .btn-confirm').unbind().click(function () {

					var ids = '';

					$.each(selections, function (i, v) {

						var s = v.student;

						ids += s.id + ',';

					});

					ids = ids.substr(0, ids.length - 1);

					$('#dialog-confirm').modal('hide');

					$.ajax({

						url : 'auth/deleteStudent.do',
						data : {
							'ids' : ids
						},
						type : 'post',
						dataType : 'json',
						success : function (result) {

							if (result.success) {

								$('#auth-table').bootstrapTable('refresh');

							} else {

								$('#dialog-confirm p').text('删除失败');
								$('#dialog-confirm').modal({
									backdrop : true,
									keyboard : true,
									show : true
								});

							}
						}

					});

				});

			}

		});

	};

	//========================================
	// lock
	//========================================
	Student.prototype.lock = function () {

		$('#student-toolbar .lock').click(function () {

			var selections = $('#auth-table').bootstrapTable('getSelections');

			if (selections.length == 0) {

				$('#dialog-alert p').text('未选择');
				$('#dialog-alert').modal({
					backdrop : true,
					keyboard : true,
					show : true
				});

			} else {

				changeState('0', selections);

			}
		});
	};

	//========================================
	// unlock
	//========================================
	Student.prototype.unlock = function () {
		$('#student-toolbar .unlock').click(function () {

			var selections = $('#auth-table').bootstrapTable('getSelections');

			if (selections.length == 0) {

				$('#dialog-alert p').text('未选择');
				$('#dialog-alert').modal({
					backdrop : true,
					keyboard : true,
					show : true
				});

			} else {
				changeState('1', selections);
			}
		});
	};

	//========================================
	// changeState
	//========================================
	var changeState = function (state, selections) {
		var type;
		switch (state) {
		case '0':
			type = '锁定';
			break;
		case '1':
			type = '激活';
			break;
		default:
			break;
		}

		$('#dialog-confirm p').text('确认' + type + '所选学生账号吗？');

		$('#dialog-confirm').modal({
			backdrop : true,
			keyboard : true,
			show : true
		});

		$('#dialog-confirm .btn-confirm').unbind().click(function () {

			var ids = '';

			$.each(selections, function (i, v) {

				var u = v.user;

				ids += u.id + ',';

			});

			ids = ids.substr(0, ids.length - 1);

			$('#dialog-confirm').modal('hide');

			$.ajax({

				url : 'auth/changeUserState.do',
				data : {
					'ids' : ids,
					'state' : state
				},
				type : 'post',
				dataType : 'json',
				success : function (result) {

					if (result.success) {

						$('#auth-table').bootstrapTable('refresh');

					} else {

						$('#dialog-confirm p').text('操作失败');
						$('#dialog-confirm').modal({
							backdrop : true,
							keyboard : true,
							show : true
						});

					}
				}

			});

		});

	};
});
