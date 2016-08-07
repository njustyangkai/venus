define(function (require, exports, module) {

	function Index() {
		$.datetimepicker.setLocale('ch');
	}

	module.exports = Index;

	//==========================================
	// init
	//==========================================
	Index.prototype.init = function () {
		
		$('aside.left-side').load('pages/left/left.html?v=' + version);
		$('header').load('pages/header/header.html?v=' + version);
		$('aside.right-side').load('pages/myCourse/myCourse.html?v=' + version);

		this.fixAndChangeColor();

	};
	
	//==========================================
	// 主题更换和固定头部
	//==========================================
	Index.prototype.fixAndChangeColor = function () {
		function _fix() {
			
			var height = $(window).height() - 50;
			$(".wrapper").css("min-height", height + "px");
			var content = $(".wrapper").height();
			if (content > height)
				$(".left-side, html, body").css("min-height", content + "px");
			else {
				$(".left-side, html, body").css("min-height", height + "px");
			}
		}
		function fix_sidebar() {
			if (!$("body").hasClass("fixed")) {
				return;
			}

			$(".sidebar").slimscroll({
				height : ($(window).height() - $(".header").height()) + "px",
				color : "rgba(0,0,0,0.2)"
			});
		}
		_fix();
		$(".wrapper").resize(function () {
			_fix();
			fix_sidebar();
		});

		fix_sidebar();
		/*var demo = $("<div />").css({
				position : "fixed",
				top : "150px",
				right : "0",
				background : "rgba(0, 0, 0, 0.7)",
				"border-radius" : "5px 0px 0px 5px",
				padding : "10px 15px",
				"font-size" : "16px",
				"z-index" : "999999",
				cursor : "pointer",
				color : "#ddd"
			}).html("<i class='fa fa-gear'></i>").addClass("no-print");

		var demo_settings = $("<div />").css({
				"padding" : "10px",
				position : "fixed",
				top : "130px",
				right : "-200px",
				background : "#fff",
				border : "3px solid rgba(0, 0, 0, 0.7)",
				"width" : "200px",
				"z-index" : "999999"
			}).addClass("no-print");
		demo_settings.append(
			"<h4 style='margin: 0 0 5px 0; border-bottom: 1px dashed #ddd; padding-bottom: 3px;'>显示设置</h4>"
			 + "<div class='form-group no-margin'>"
			 + "<div class='.checkbox'>"
			 + "<label>"
			 + "<input class='js-checkbox-fixHeader' type='checkbox'/> "
			 + "固定头部"
			 + "</label>"
			 + "</div>"
			 + "</div>");
		demo_settings.append(
			"<h4 style='margin: 0 0 5px 0; border-bottom: 1px dashed #ddd; padding-bottom: 3px;'>皮肤</h4>"
			 + "<div class='form-group no-margin'>"
			 + "<div class='.radio'>"
			 + "<label>"
			 + "<input name='skins' value='skin-black' type='radio' /> "
			 + "黑色"
			 + "</label>"
			 + "</div>"
			 + "</div>"
			 + "<div class='form-group no-margin'>"
			 + "<div class='.radio'>"
			 + "<label>"
			 + "<input name='skins' type='radio' value='skin-blue' checked='checked'/> "
			 + "蓝色"
			 + "</label>"
			 + "</div>"
			 + "</div>");

		demo.click(function () {
			if (!$(this).hasClass("open")) {
				$(this).css("right", "200px");
				demo_settings.css("right", "0");
				$(this).addClass("open");
			} else {
				$(this).css("right", "0");
				demo_settings.css("right", "-200px");
				$(this).removeClass("open")
			}
		});

		$("body").append(demo);
		$("body").append(demo_settings);
		
		$('.js-checkbox-fixHeader').change(function () {
			$("body").toggleClass("fixed");
			fix_sidebar();
		})
		$('input:radio[name="skins"]').change(function () {
			var cls = $(this).val();
			$("body").removeClass("skin-blue skin-black");
			$("body").addClass(cls);
		})*/
	};

});
