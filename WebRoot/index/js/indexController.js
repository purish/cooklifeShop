
// 设置首页中选项卡的鼠标移动事件
yjwObj.dealTabMouseEvent = function() {
	
	// 设置"热销商品"选项卡鼠标移动事件
	var $hotGoodsTab = $("#hotGoods .title .tab");
	$hotGoodsTab.tabs("#hotGoods .content .goodsList", {
		tabs: "li",
		event: "mouseover"
	});
	
	//设置"新品上市"选项卡鼠标移动事件
	var $newGoodsTab = $("#newGoods .title .tab");
	$newGoodsTab.tabs("#newGoods .content .goodsList", {
		tabs: "li",
		event: "mouseover"
	});
	
	//设置咨询面板选项卡鼠标移动事件
	var $gcArticleTab = $("#gcArticle .tab");
	$gcArticleTab.tabs("#gcArticle .tabContent", {
		tabs: "li",
		event: "mouseover"
	});
};

//页面加载完毕后调用
$().ready(function() {
	
	// 加载（slides）幻灯片JS
	$.getScript("js/jquery_slides.js", function(){});
	
	// 设置首页选项卡鼠标移动事件
	yjwObj.dealTabMouseEvent();
	
	// 设置商品分类列表鼠标移动事件
	yjwObj.dealCategorysMouseEvent();
	
});
