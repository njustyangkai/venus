define(function(require, exports, module) {
	
	require('../../js/tree/tree')($);
	var Util = require('util/util');
	
	function Left() {}
	
	module.exports = Left;
	
	//==========================================
	// init
	//==========================================
	Left.prototype.init = function() {
		Util.ajaxSetup();
		this.loginInit();
		$(".sidebar .treeview").tree();
		this.menu();
	};
	
	//==========================================
	// login init
	//==========================================
	Left.prototype.loginInit = function() {
		$.ajax({
			url: 'auth/isLogin.do',
			type: 'get',
			async: false,
			dataType: 'json',
			success: function(result){
				if(result.success){
					setTimeout(function() {
						$('.js-alreadyLogin').removeClass('hidden').find('span').html(result.object.userName + '<i class="caret"></i>');
					},100);
					$('#btn-logout').click(function() {
						$.ajax({
							url: 'auth/logout.do',
							type: 'post',
							dataType: 'json',
							success: function(result) {
								if(result.success) {
									window.location.reload();
								}else{
									Util.alertDialog(result.message);
								}
							}
						});
					});
					$('#btn-modifyPassword').click(function() {
						$('ul.sidebar-menu').find('li').removeClass('active');
						$('aside.right-side').load('pages/header/modifyPassword.html');
						$('li.user-menu').removeClass('open');
					});
					if(result.object.authType === 'teacher') {
						$('ul.sidebar-menu li.hidden').removeClass('hidden');
					}else{
						$('ul.sidebar-menu li.hidden').remove();
					}
				}else{
					setTimeout(function() {
						$('.js-noLogin').removeClass('hidden');
					},100);
					$('ul.sidebar-menu li.hidden').remove();
					Util.alertDialog("未登录！");
				}
			}
		});
	};
	
	//==========================================
	// menu
	//==========================================
	Left.prototype.menu = function () {
		
		var removeActive = function() {
			$('ul.sidebar-menu').find('li').removeClass('active');
		};
		
		$('.js-sidebar-home').click(function() {
			$('aside.right-side').load('pages/home/home.html');
			removeActive();
			$(this).addClass('active');
		});
		
		$('.js-sidebar-mycourse').click(function() {
			$('aside.right-side').load('pages/myCourse/myCourse.html?v=' + version);
			removeActive();
			$(this).addClass('active');
		});
		$('.js-sidebar-courseManage').click(function() {
			$('aside.right-side').load('pages/courseManage/courseManage.html?v=' + version);
			removeActive();
			$(this).addClass('active');
		});
		$('.js-sidebar-student').click(function() {
			$('aside.right-side').load('pages/student/student.html?v=' + version);
			removeActive();
			$(this).addClass('active');
		});
		$('.js-sidebar-message').click(function() {
			$('aside.right-side').load('pages/message/message.html?v=' + version);
			removeActive();
			$(this).addClass('active');
		});
		$('.js-sidebar-courseSearch').click(function() {
			$('aside.right-side').load('pages/courseSearch/courseSearch.html?v=' + version);
			removeActive();
			$(this).addClass('active');
		});
		
	};
});