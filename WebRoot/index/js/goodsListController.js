// 二级页面商品子分类鼠标点击事件
yjwObj.dealSubCatMouseEvent = function() {
	$("#sortlist .item h3").bind("click", function() {
		var element = $(this).parent();
		if (element.hasClass("current")) {
			element.removeClass("current");
		} else {
			element.addClass("current");
		}
	});
};

// 页面加载完毕后调用
$().ready(function() {
	
	// 设置商品分类列表鼠标移动事件
	yjwObj.dealOtherCategorysMouseEvent();
	yjwObj.dealCategorysMouseEvent();
	
	// 二级页面商品子分类鼠标点击事件
	yjwObj.dealSubCatMouseEvent();
	
});