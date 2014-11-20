<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

	<title>商品列表 - YJW-SHOP</title>
	<!-- 通知浏览器本页面的编码字符集 -->
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<!-- 设置版权信息 -->
	<meta content="yjwShop Team" name="author" />
	<meta content="yjwShop" name="copyright" />
	
	<!-- 引入CSS文件 -->
	<link type="text/css" rel="stylesheet" href="../css/common.css" />
	<!-- 引入JS文件 -->
	<script type="text/javascript" src="../js/jquery/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="../js/yjw/common.js"></script>
	<script type="text/javascript" src="../js/yjw/list.js"></script>

<style type="text/css">
.moreTable th {
	width: 80px;
	line-height: 25px;
	padding: 5px 10px 5px 0px;
	text-align: right;
	font-weight: normal;
	color: #333333;
	background-color: #f8fbff;
}

.moreTable td {
	line-height: 25px;
	padding: 5px;
	color: #666666;
}

.promotion {
	color: #cccccc;
}
</style>

<script type="text/javascript">
$().ready(function() {
	
	var $listForm = $("#listForm");
	var $moreButton = $("#moreButton");
	var $filterSelect = $("#filterSelect");
	var $filterOption = $("#filterOption a");

	var pcList = null;
	var brandList = null;

	// 根据指定的Url获取数据
	function getJsonData(url, reqData) {
		var result = null;
		$.ajax({
			url: url,
			type: "POST",
			async: false,
			cache: false,
			data: reqData,
			dataType: "json",
			success: function(jsonData){
				result = jsonData;
			}
		});
		return result;
	}
	
	// 更多选项
	$moreButton.click(function() {

		// 获取商品分类集合
		if(pcList == null) {
			pcList = getJsonData("pcAction_getAllProductCategorys.action", null);
		}
		var pcOptionHtml = "";
		$.each(pcList, function(i, pc1) {
			pcOptionHtml += '<option value="' + pc1.id + '">┣&nbsp;' + pc1.name + ' </option>';
			$.each(pc1.childs, function(j, pc2){
				pcOptionHtml += '<option value="' + pc2.id + '">&nbsp;&nbsp;&nbsp;&nbsp;┣&nbsp;' + pc2.name + '</option>';
				$.each(pc2.childs, function(k, pc3){
					pcOptionHtml += '<option value="' + pc3.id + '">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;┣&nbsp;' 
							+ pc3.name + '</option>';
				});
			});
		});

		// 获取品牌信息集合
		if(brandList == null) {
			brandList = getJsonData("brandAction_getAllBrands.action", null);
		}
		var brandOptionHtml = "";
		$.each(brandList, function(i, brand){
			brandOptionHtml += '<option value="' + brand.id + '">' + brand.name + '</option>';
		});

		$.dialog({
			title: "更多选项",
			content: ' <table id="moreTable" class="moreTable"> ' 
					+ '<tr> <th> 商品分类: <\/th> <td> <select name="productCategoryId" style="width: 300px;">' 
				//	+ '<option value="">请选择...<\/option> <option value="1"> 时尚女装 <\/option> <option value="11"> &nbsp;&nbsp; 连衣裙 <\/option> <option value="12"> &nbsp;&nbsp; 针织衫 <\/option> <option value="2"> 精品男装 <\/option> <option value="21"> &nbsp;&nbsp; 针织衫 <\/option> <option value="22"> &nbsp;&nbsp; POLO衫 <\/option> ' 
					+ pcOptionHtml 
					+ '<\/select> <\/td> <\/tr> ' 
					+ '<tr> <th> 所属品牌: <\/th> <td> <select name="brandId" style="width: 300px;"> ' 
				//	+ '<option value="">请选择...<\/option> <option value="1"> 梵希蔓 <\/option> <option value="21"> 我的品牌A <\/option> <option value="2"> 伊芙丽 <\/option> <option value="19"> 我的品牌-B <\/option> <option value="3"> 尚都比拉 <\/option> <option value="6"> 李宁 <\/option> <option value="7"> 耐克 <\/option> ' 
					+ brandOptionHtml 
					+ '<\/select> <\/td> <\/tr> ' 
				//	+ '<tr> <th> 促销: <\/th> <td> <select name="promotionId"> ' 
				//	+ '<option value="">请选择...<\/option> <option value="1"> 限时抢购 <\/option> <option value="2"> 双倍积分 <\/option> ' 
				//	+ '<\/select> <\/td> <\/tr> ' 
					+ '<tr> <th> 页面标签: <\/th> <td> <select name="tagId" style="width: 300px;"> ' 
					+ '<option value="">请选择...<\/option> <option value="hot"> 热销 <\/option> <option value="new"> 最新 <\/option> <option value="recmd"> 推荐 <\/option> ' 
					+ '<\/select> <\/td> <\/tr> <\/table>', 
			width: 470,
			modal: true,
			ok: "确&nbsp;&nbsp;定",
			cancel: "取&nbsp;&nbsp;消",
			onOk: function() {
				$("#moreTable :input").each(function() {
					var $this = $(this);
					$("#" + $this.attr("name")).val($this.val());
				});
				$listForm.submit();
			}
		});
	});
	
	// 商品筛选
	$filterSelect.mouseover(function() {
		var $this = $(this);
		var offset = $this.offset();
		var $menuWrap = $this.closest("div.menuWrap");
		var $popupMenu = $menuWrap.children("div.popupMenu");
		$popupMenu.css({left: offset.left, top: offset.top + $this.height() + 2}).show();
		$menuWrap.mouseleave(function() {
			$popupMenu.hide();
		});
	});
	
	// 筛选选项
	$filterOption.click(function() {
		var $this = $(this);
		var $dest = $("#" + $this.attr("name"));
		if ($this.hasClass("checked")) {
			$dest.val("");
		} else {
			$dest.val($this.attr("val"));
		}
		$listForm.submit();
		return false;
	});
}); 
</script>
</head>
<body>
	<div class="path">
		<a href="../index.html">首页</a> » 商品列表
		<span>(共<span id="pageTotal">
			<s:property value="productPage.totalCount" />
		</span>条记录)</span>
	</div>
	<form method="get" action="productAction_getProductsByCondition.action" id="listForm">
		<input type="hidden" value="<s:property value="echoParam['productCategoryId']" />" name="productCategoryId" id="productCategoryId" />
		<input type="hidden" value="<s:property value="echoParam['brandId']" />" name="brandId" id="brandId" />
		<%--<input type="hidden" value="" name="promotionId" id="promotionId" />--%>
		<input type="hidden" value="<s:property value="echoParam['tagId']" />" name="tagId" id="tagId" />
		<input type="hidden" value="<s:property value="echoParam['isMarketable']" />" name="isMarketable" id="isMarketable" />
		<input type="hidden" value="<s:property value="echoParam['isList']" />" name="isList" id="isList" />
		<input type="hidden" value="<s:property value="echoParam['isTop']" />" name="isTop" id="isTop" />
		<input type="hidden" value="<s:property value="echoParam['isGift']" />" name="isGift" id="isGift" />
		<input type="hidden" value="<s:property value="echoParam['isOutOfStock']" />" name="isOutOfStock" id="isOutOfStock" />
		<%--<input type="hidden" value="" name="isStockAlert" id="isStockAlert" />--%>
		
<!-- ********************************** 工具栏. ************************************** -->
		<div class="bar">
			<a class="iconButton" href="productAction_initSaveProductUi.action?opType=add">
				<span class="addIcon">&nbsp;</span>添加
			</a>
			<div class="buttonWrap">
				<a class="iconButton disabled" id="deleteButton" href="javascript:;" 
						val="productAction_deleteProductsByIds.action">
					<span class="deleteIcon">&nbsp;</span>删除
				</a>
				<a class="iconButton" id="refreshButton" href="javascript:;">
					<span class="refreshIcon">&nbsp;</span>刷新
				</a>
				<div class="menuWrap">
					<a class="button" id="filterSelect" href="javascript:;">
						商品筛选<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul class="check" id="filterOption">
							<li>
								<a val="true" name="isMarketable" href="javascript:;" 
										<s:property value="echoParam['isMarketable']=='true' ? 'class=checked' : ''" />>
									已上架
								</a>
							</li>
							<li>
								<a val="false" name="isMarketable" href="javascript:;" 
										<s:property value="echoParam['isMarketable']=='false' ? 'class=checked' : ''" />>
									未上架
								</a>
							</li>
							<li class="separator">
								<a val="true" name="isList" href="javascript:;" 
										<s:property value="echoParam['isList']=='true' ? 'class=checked' : ''" />>
									已列出
								</a>
							</li>
							<li>
								<a val="false" name="isList" href="javascript:;" 
										<s:property value="echoParam['isList']=='false' ? 'class=checked' : ''" />>
									未列出
								</a>
							</li>
							<li class="separator">
								<a val="true" name="isTop" href="javascript:;" 
										<s:property value="echoParam['isTop']=='true' ? 'class=checked' : ''" />>
									已置顶
								</a>
							</li>
							<li>
								<a val="false" name="isTop" href="javascript:;" 
										<s:property value="echoParam['isTop']=='false' ? 'class=checked' : ''" />>
									未置顶
								</a>
							</li>
							<li class="separator">
								<a val="true" name="isGift" href="javascript:;" 
										<s:property value="echoParam['isGift']=='true' ? 'class=checked' : ''" />>
									赠品
								</a>
							</li>
							<li>
								<a val="false" name="isGift" href="javascript:;" 
										<s:property value="echoParam['isGift']=='false' ? 'class=checked' : ''" />>
									非赠品
								</a>
							</li>
							<li class="separator">
								<a val="false" name="isOutOfStock" href="javascript:;" 
										<s:property value="echoParam['isOutOfStock']=='false' ? 'class=checked' : ''" />>
									有货
								</a>
							</li>
							<li>
								<a val="true" name="isOutOfStock" href="javascript:;" 
										<s:property value="echoParam['isOutOfStock']=='true' ? 'class=checked' : ''" />>
									缺货
								</a>
							</li>
							<%--
							<li class="separator"><a val="false" name="isStockAlert" href="javascript:;">库存正常</a></li>
							<li><a val="true" name="isStockAlert" href="javascript:;">库存警告</a></li>
							--%>
						</ul>
					</div>
				</div>
				<a class="button" id="moreButton" href="javascript:;">更多选项</a>
				<div class="menuWrap">
					<a class="button" id="pageSizeSelect" href="javascript:;">
						每页显示<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="pageSizeOption">
							<li><a val="10" href="javascript:;">10</a></li>
							<li><a val="20" class="current" href="javascript:;">20</a></li>
							<li><a val="30" href="javascript:;">30</a></li>
							<li><a val="40" href="javascript:;">40</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="menuWrap">
				<div class="search">
					<span class="arrow" id="searchPropertySelect">&nbsp;</span>
					<input type="text" maxlength="200" name="searchValue" id="searchValue"
							value="<s:property value="echoParam['searchValue']" default="" />" />
					<button type="submit">&nbsp;</button>
				</div>
				<div class="popupMenu">
					<ul id="searchPropertyOption">
						<li><a val="all" href="javascript:;">全部</a></li>
						<li><a val="name" href="javascript:;">名称</a></li>
						<li><a val="sn" href="javascript:;">编号</a></li>
					</ul>
				</div>
			</div>
		</div>
		
<!-- ********************************** 品牌信息列表. ************************************** -->
		<table class="list" id="listTable">
			<tbody>
				<tr>
					<th class="check"><input type="checkbox" id="selectAll"></th>
					<th><a name="sn" class="sort" href="javascript:;">编号</a></th>
					<th><a name="name" class="sort" href="javascript:;">名称</a></th>
					<th><a name="pc.name" class="sort" href="javascript:;">商品分类</a></th>
					<th><a name="price" class="sort" href="javascript:;">销售价</a></th>
					<th><a name="cost" class="sort" href="javascript:;">成本价</a></th>
					<th><a name="stock" class="sort" href="javascript:;">库存</a></th>
					<th><a name="isMarketable" class="sort" href="javascript:;">是否上架</a></th>
					<th><a name="createDate" class="sort" href="javascript:;">创建日期</a></th>
					<th><span>操作</span></th>
				</tr>
				
				<s:iterator value="productPage.list" var="product">
				<tr>
					<td><input type="checkbox" value="<s:property value="#product.id" />" name="ids"></td>
					<td><s:property value="#product.sn" /></td>
					<td>
						<span title="<s:property value="#product.name" />">
							<s:property value="#product.name" />
						</span>
					</td>
					<td><s:property value="#product.pc.name" /></td>
					<td><s:property value="#product.price" /></td>
					<td><s:property value="#product.cost" /></td>
					<td><s:property value="#product.stock" /></td>
					<td><span class="<s:property value="#product.isMarketable==true ? 'trueIcon' : 'falseIcon'" />">&nbsp;</span></td>
					<td>
						<span title="<s:date name="#product.createTime" format="yyyy-MM-dd HH:mm:ss"/>">
							<s:date name="#product.createTime" format="yyyy-MM-dd"/>
						</span>
					</td>
					<td>
						<a href="productAction_initSaveProductUi.action?id=<s:property value="#product.id" />&opType=update">[编辑]</a>
						<a href="productAction_initSaveProductUi.action?id=<s:property value="#product.id" />&opType=view">[查看]</a>
					</td>
				</tr>
				</s:iterator>
				
			</tbody>
		</table>
		
<!-- ********************************** 分页. ************************************** -->
		<div class="pagination">
			<!-- 首页和上一页 -->
			<s:if test="productPage.pageNumber<=1">
				<span class="firstPage"> </span>
				<span class="previousPage"> </span>
			</s:if>
			<s:else>
				<a class="firstPage" href="javascript: $.pageSkip(1);"> </a>
				<a class="previousPage" href="javascript: $.pageSkip(<s:property value="productPage.pageNumber-1" />);"> </a>
			</s:else>
			
			<!-- 前两页 -->
			<s:if test="productPage.pageNumber-3>0">
				<span class="pageBreak">...</span>
			</s:if>
			<s:if test="productPage.pageNumber-2>0">
				<a href="javascript: $.pageSkip(<s:property value="productPage.pageNumber-2" />);">
					<s:property value="productPage.pageNumber-2" />
				</a>
			</s:if>
			<s:if test="productPage.pageNumber-1>0">
				<a href="javascript: $.pageSkip(<s:property value="productPage.pageNumber-1" />);">
					<s:property value="productPage.pageNumber-1" />
				</a>
			</s:if>
			
			<!-- 当前页 -->
			<span class="currentPage">
				<s:property value="productPage.pageNumber" />
			</span>
			
			<!-- 后两页 -->
			<s:if test="productPage.pageNumber+1<=productPage.totalPage">
				<a href="javascript: $.pageSkip(<s:property value="productPage.pageNumber+1" />);">
					<s:property value="productPage.pageNumber+1" />
				</a>
			</s:if>
			<s:if test="productPage.pageNumber+2<=productPage.totalPage">
				<a href="javascript: $.pageSkip(<s:property value="productPage.pageNumber+2" />);">
					<s:property value="productPage.pageNumber+2" />
				</a>
			</s:if>
			<s:if test="productPage.pageNumber+3<=productPage.totalPage">
				<span class="pageBreak">...</span>
			</s:if>
			
			<!-- 下一页和尾页 -->
			<s:if test="productPage.pageNumber>=productPage.totalPage">
				<span class="nextPage"> </span>
				<span class="lastPage"> </span>
			</s:if>
			<s:else>
				<a class="nextPage" href="javascript: $.pageSkip(<s:property value="productPage.pageNumber+1" />);"> </a>
				<a class="lastPage" href="javascript: $.pageSkip(<s:property value="productPage.totalPage" />);"> </a>
			</s:else>

			<span class="pageSkip"> 共<s:property value="productPage.totalPage" />页 到第 
				<input id="pageNumber" onpaste="return false;" maxlength="9" 
						value="<s:property value="productPage.pageNumber" />" name="pageNumber" /> 页 
				<button type="submit"> </button>
			</span>
		</div>
		
<!-- ********************************** 隐藏域. ************************************** -->
		<input id="pageSize" name="pageSize" type="hidden" value="<s:property value="productPage.pageSize" />" />
		<input id="searchProperty" name="searchProperty" type="hidden" value="<s:property value="echoParam['searchProperty']" />" />
		<input id="orderProperty" name="orderProperty" type="hidden" value="<s:property value="echoParam['orderProperty']" />" />
		<input id="orderDirection" name="orderDirection" type="hidden" value="<s:property value="echoParam['orderDirection']" />" />
	</form>
</body>
</html>
