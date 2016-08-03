define(function (require, exports, module) {
	var Util = require('util/util');

	function Header() {}

	module.exports = Header;

	//==========================================
	// 初始化
	//==========================================
	Header.prototype.init = function () {
		this.initOffcanvas();
		this.loginValid();
		
		$('.logo').click(function() {
			window.location.reload();
		});
		
		$('.js-register').click(function() {
			$('ul.sidebar-menu').find('li').removeClass('active');
			$('aside.right-side').load('pages/header/register.html');
			$('li.user-menu').removeClass('open');
		});

	};

	//==========================================
	// 初始化收缩左侧栏按钮
	//==========================================
	Header.prototype.initOffcanvas = function () {
		$("[data-toggle='offcanvas']").click(function (e) {
			e.preventDefault();

			//If window is small enough, enable sidebar push menu
			if ($(window).width() <= 992) {
				$('.row-offcanvas').toggleClass('active');
				$('.left-side').removeClass("collapse-left");
				$(".right-side").removeClass("strech");
				$('.row-offcanvas').toggleClass("relative");
			} else {
				//Else, enable content streching
				$('.left-side').toggleClass("collapse-left");
				$(".right-side").toggleClass("strech");
			}
		});
	};

	//==========================================
	// 登录校验
	//==========================================
	Header.prototype.loginValid = function () {
		$('.user-menu .dropdown-toggle').click(function() {
			$('#login-form').bootstrapValidator('resetForm', true);
		});
		
		$('#login-form').bootstrapValidator({
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			submitButtons : '#login-submit-btn',
			submitHandler : function (validator, form, submitButton) {
				$.ajax({
					url : 'auth/login.do',
					data : form.serialize(),
					dataType : 'json',
					success : function (result) {
						if(result.success) {
							window.location.reload();
						}
						else{
							Util.alertDialog(result.message);
						}
					}
				});
			},
			fields : {
				'username' : {
					validators : {
						notEmpty : {
							message : '用户名不能为空'
						},
						regexp : {
							regexp : /^[a-zA-Z0-9_]+$/,
							message : '用户名只能由字母、数字、下划线组成'
						}
					}
				},
				'password' : {
					validators : {
						notEmpty : {
							message : '密码不能为空'
						}

					}
				}
			}
		});

	};

});
