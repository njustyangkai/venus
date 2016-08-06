define(function (require, exports, module) {

	//====================================================
	// 日期格式化
	//====================================================
	var dateFormat = function (fmt) {
		Date.prototype.Format = function (fmt) { //author: meizz
			var o = {
				"M+" : this.getMonth() + 1, //月份
				"d+" : this.getDate(), //日
				"h+" : this.getHours(), //小时
				"m+" : this.getMinutes(), //分
				"s+" : this.getSeconds(), //秒
				"q+" : Math.floor((this.getMonth() + 3) / 3), //季度
				"S" : this.getMilliseconds() //毫秒
			};
			if (/(y+)/.test(fmt))
				fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
			for (var k in o)
				if (new RegExp("(" + k + ")").test(fmt))
					fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			return fmt;
		};
	};
	//====================================================
	// 随机背景色
	//====================================================
	var getColor = function (i) {

		var colors = ['#00aff0', '#a20025', '#647687', '#00aba9', '#d80073', '#6d8764',
			'#008a00', '#dc4fad', '#825a2c', '#60a917', '#aa00ff', '#e3c800',
			'#a4c400', '#6a00ff', '#f0a30a', '#555555', '#222222', '#63362f',
			'#128023', '#bf5a15', '#9a1616', '#9a165a', '#57169a', '#16499a'];

		return colors[i % 23];

	};
	//====================================================
	// 生成uuid
	//====================================================
	var getUuid = function () {
		var s = [];
		var hexDigits = "0123456789abcdef";
		for (var i = 0; i < 36; i++) {
			s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
		}
		s[14] = "4";
		s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);
		s[8] = s[13] = s[18] = s[23] = "-";

		var uuid = s.join("");
		return uuid;
	};
	//====================================================
	// alertDialog
	//====================================================
	var alertDialog = function(text) {
		$('#dialog-alert p').text(text);
		$('#dialog-alert').modal({
			backdrop : true,
			keyboard : true,
			show : true
		});
	};
	
	//====================================================
	// confrimDialog
	//====================================================
	var confirmDialog = function(text, callback) {
		$('#dialog-confirm p').text(text);
		$('#dialog-confirm').modal({
			backdrop : true,
			keyboard : true,
			show : true
		});
		$('#dialog-confirm .btn-confirm').unbind().click(callback);
	};
	
	//====================================================
	// ajaxSetup
	//====================================================
	var ajaxSetup = function() {
		$.ajaxSetup({
		    contentType : "application/x-www-form-urlencoded;charset=utf-8",
		    complete : function(XMLHttpRequest, textStatus) {
		    	//console.log(XMLHttpRequest);
		    	//console.log(textStatus);
		        var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
		        if (sessionstatus == "timeout") {
		            window.location.reload();
		        }
		    }
		});
	};
	
	return {
		dateFormat : dateFormat,
		getColor : getColor,
		getUuid : getUuid,
		alertDialog: alertDialog,
		confirmDialog: confirmDialog,
		ajaxSetup: ajaxSetup
	};

});
