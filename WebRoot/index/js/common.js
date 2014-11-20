// 定义通用的全局变量
var yjwObj = new Object();

// 设置导航栏背景鼠标移动事件
yjwObj.dealNavBgMouseEvent = function() {
	// 鼠标移入
	$("#navItems li[class!='hover']").mouseover(function(){
		$(this).attr("class","hover");
	});
	// 鼠标离开
	$("#navItems li[class!='hover']").mouseout(function(){
		$(this).attr("class","");
	});
};

// 设置商品分类列表鼠标移动事件
yjwObj.dealCategorysMouseEvent = function(){
	
	$("#categorysBox .list dl.displayPanel").mouseover(function(){
		$(this).attr("class","displayPanel hover");
		$(this).next("div.hiddenPanel").css("display", "block");
	});
	$("#categorysBox .list dl.displayPanel").mouseout(function(){
		$(this).attr("class","displayPanel");
		$(this).next("div.hiddenPanel").css("display", "none");
	});
	
	$("#categorysBox .list div.hiddenPanel").mouseover(function(){
		$(this).css("display", "block");
		$(this).prev("dl.displayPanel").attr("class","displayPanel hover");
	});
	$("#categorysBox .list div.hiddenPanel").mouseout(function(){
		$(this).css("display", "none");
		$(this).prev("dl.displayPanel").attr("class","displayPanel");
	});
	
};

// 设置其他页面商品分类列表鼠标移动事件
yjwObj.dealOtherCategorysMouseEvent = function(){
	
	$("#categorysLink").bind("mouseover", function(){
		$("#categorysBox").css("display", "block");
		$("#categorysLink .allLink b").css("background-position", "-65px -23px");
	});
	$("#categorysLink").bind("mouseout", function(){
		$("#categorysBox").css("display", "none");
		$("#categorysLink .allLink b").css("background-position", "-65px 0");
	});
	
	$("#categorysBox").bind("mouseover", function(){
		$(this).css("display", "block");
		$("#categorysLink .allLink b").css("background-position", "-65px -23px");
	});
	$("#categorysBox").bind("mouseout", function(){
		$(this).css("display", "none");
		$("#categorysLink .allLink b").css("background-position", "-65px 0");
	});
	
}

// 页面加载完毕后调用
$().ready(function(){
	
	// 加载（back）返回顶部JS
	$.getScript("js/backToTop.js", function(){});
	
	// 设置导航栏背景鼠标移动事件
	yjwObj.dealNavBgMouseEvent();
	
});