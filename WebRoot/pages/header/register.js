define(function (require, exports, module) {
	var Util = require('util/util');

	function Register() {}

	module.exports = Register;

	//==========================================
	// 初始化
	//==========================================
	Register.prototype.init = function () {
		this.addValid();
		$("[data-mask]").inputmask();
		$('.js-home').click(function() {
			window.location.reload();
		});
	};
	
	//========================================
	// addValid
	//========================================
	Register.prototype.addValid = function () {

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
							$('#dialog-alert p').text('注册信息已提交，请联系老师激活！');
							$('#dialog-alert').modal({
								backdrop : true,
								keyboard : true,
								show : true
							});
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

});
