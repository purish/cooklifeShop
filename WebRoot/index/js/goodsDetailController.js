

// 商品信息选项卡鼠标点击事件
yjwObj.dealGoodsDetailTabClickEvent = function(){
	
	var $goodsDetailTab = $("#goodsDetailPanel .tabTitle .tab");
	$goodsDetailTab.tabs("#goodsDetailPanel .tabContent .p-tab-content", {
		tabs: "li",
		event: "click"
	});
}

// 页面加载完毕后调用
$().ready(function() {
	
	// 设置商品分类列表鼠标移动事件
	yjwObj.dealOtherCategorysMouseEvent();
	yjwObj.dealCategorysMouseEvent();
	
	// 商品信息选项卡鼠标点击事件
	yjwObj.dealGoodsDetailTabClickEvent();
	
});