define(function(require, exports, module) {
	var Util = require('util/util');
	
	function ModifyPassword() {}
	
	module.exports = ModifyPassword;
	
	//============================
	// init
	//============================
	ModifyPassword.prototype.init = function() {
		this.valid();
		$('.js-home').click(function() {
			window.location.reload();
		});
	};
	
	//============================
	// valid
	//============================
	ModifyPassword.prototype.valid = function() {
		$('#modifyPassword-form').bootstrapValidator({
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			submitButtons : '#modifyPassword-submit-btn',
			submitHandler : function (validator, form, submitButton) {
				$.ajax({
					url : 'auth/modifyPassword.do',
					type : 'post',
					data : form.serialize(),
					dataType : 'json',
					success : function (result) {
						if (result.success) {
							$('#dialog-alert p').text('修改成功！');
							$('#dialog-alert').modal({
								backdrop : true,
								keyboard : true,
								show : true
							});
							setTimeout(function() {
								window.location.reload();
							}, 1000);
						} else {
							$('#dialog-alert p').text('修改失败！');
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
				'originPassword' : {
					trigger : 'blur',
					validators : {
						notEmpty : {
							message : '原密码不能为空'
						},
						callback : {
							message : '原密码错误',
							callback : function (value, validator) {
								var e;
								$.ajax({
									url : 'auth/isPasswordRight.do',
									async : false,
									type : 'post',
									data : {
										'password' : value
									},
									dataType : 'json',
									success : function (result) {
										if (result.success) {
											e = true;
										} else {
											e = false;
										}
									}

								});
								return e;
							}

						}

					}
				},
				'newPassword' : {
					validators : {
						notEmpty : {
							message : '新密码不能为空'
						},
						stringLength : {
							min : 6,
							max : 20,
							message : '新密码长度必须在6~20之间'
						}
					}
				},
				'repeatPassword' : {
					trigger : 'blur',
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
								if ($('#new-password').val() != value) {
									e = false;
								}
								return e;
							}

						}

					}
				}
			}

		});
	};
});