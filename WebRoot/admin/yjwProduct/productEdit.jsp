<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>编辑商品 - YJW-SHOP</title>
	<!-- 通知浏览器本页面的编码字符集 -->
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<!-- 设置版权信息 -->
	<meta content="yjwShop Team" name="author" />
	<meta content="yjwShop" name="copyright" />

	<!-- 引入CSS文件 -->
	<link type="text/css" rel="stylesheet" href="../css/common.css" />
	<!-- 引入JS文件 -->
	<script type="text/javascript" src="../js/jquery/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="../js/jquery/jquery.tools.js"></script>
	<script type="text/javascript" src="../js/jquery/jquery.validate.js"></script>
	<script type="text/javascript" src="../js/yjw/common.js"></script>
	<script type="text/javascript" src="../js/yjw/input.js"></script>
	
	<script type="text/javascript" src="../js/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="../js/ckfinder/ckfinder.js"></script>
	<script type="text/javascript" src="../js/yjw/editor-me.js"></script>
		
<style type="text/css">
.specificationSelect {
	height: 100px;
	padding: 5px;
	overflow-y: scroll;
	border: 1px solid #cccccc;
}

.specificationSelect li {
	float: left;
	min-width: 150px;
	_width: 200px;
}
</style>

<script type="text/javascript">
$().ready(function() {
	
	var $inputForm = $("#inputForm");
	var $productCategoryId = $("#productCategoryId");
//	var $isMemberPrice = $("#isMemberPrice");
//	var $memberPriceTr = $("#memberPriceTr");
//	var $memberPrice = $("#memberPriceTr input");
//	var $browserButton = $("#browserButton");
	var $productImageTable = $("#productImageTable");
	var $addProductImage = $("#addProductImage");
	var $deleteProductImage = $("a.deleteProductImage");
	var $parameterTable = $("#parameterTable");
	var $attributeTable = $("#attributeTable");
	var $specificationIds = $("#specificationSelect :checkbox");
	var $specificationProductTable = $("#specificationProductTable");
	var $addSpecificationProduct = $("#addSpecificationProduct");
	var $deleteSpecificationProduct = $("a.deleteSpecificationProduct");
	var previousProductCategoryId = getCookie("previousProductCategoryId");
	
	var productImageIndex = <s:property value="opType==null || opType.equals('add') ? 0 : product.pimgs.size()" />;
	var productSpIndex = <s:property value="opType==null || opType.equals('add') ? 0 : product.psubs.size()" />;

	// YJW-获取操作类型
	var opType = '<s:property value="opType" />';
	// YJW-商品标签选择时转移值到隐藏域 
	$("#checkTagTd label :checkbox").click(function(){
		if($(this).prop("checked")){
			$(this).next(":hidden").val("true");
		} else{
			$(this).next(":hidden").val("false");
		}
	});
	// YJW-商品设置选择时转移值到隐藏域 
	$("#checkSettingTd label :checkbox").click(function(){
		if($(this).prop("checked")){
			$(this).next(":hidden").val("true");
		} else{
			$(this).next(":hidden").val("false");
		}
	});
	
	// 缓存上一次选择的商品ID 
	if (previousProductCategoryId != null) {
		$productCategoryId.val(previousProductCategoryId);
	} else {
		previousProductCategoryId = $productCategoryId.val();
	}

//	$browserButton.browser();
	
	// 会员价
/*	$isMemberPrice.click(function() {
		if ($(this).prop("checked")) {
			$memberPriceTr.show();
			$memberPrice.prop("disabled", false);
		} else {
			$memberPriceTr.hide();
			$memberPrice.prop("disabled", true);
		}
	});*/

	// 增加商品图片
	$addProductImage.click(function() {
		var trHtml = '<tr> <td> ' 
				+ '<input type="hidden" name="pimgs[' + productImageIndex + '].image" id="pimgs[' + productImageIndex + '].image" value="" \/>'
				+ '<input type="file" name="productImages" class="productImageFile" onchange="document.getElementById(\'pimgs[' + productImageIndex + '].image\').value=this.value" \/> ' 
				+ '<\/td> <td> ' 
				+ '<input type="text" name="pimgs[' + productImageIndex + '].title" class="text" maxlength="200" \/> ' 
				+ '<\/td> <td> ' 
				+ '<input type="text" name="pimgs[' + productImageIndex + '].order" class="text productImageOrder" maxlength="9" style="width: 50px;" \/> ' 
				+ '<\/td> <td> <a href="javascript:;" class="deleteProductImage">[删除]<\/a> <\/td> <\/tr>'; 
				
		$productImageTable.append(trHtml);
		productImageIndex ++;
	});
	
	// 删除商品图片
	$deleteProductImage.live("click", function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "您确定要删除吗？",
			onOk: function() {
				$this.closest("tr").remove();
			}
		});
	});
	
	// 修改商品分类
	$productCategoryId.change(function() {
		var hasValue = false;
		$parameterTable.find(":input").each(function() {
			if ($.trim($(this).val()) != "") {
				hasValue = true;
				return false;
			}
		});
		
		if (!hasValue) {
			$attributeTable.find(":input").each(function() {
				if ($.trim($(this).val()) != "") {
					hasValue = true;
					return false;
				}
			});
		}
		if (hasValue) {
			$.dialog({
				type: "warn",
				content: "修改商品分类后当前参数、属性数据将会丢失，是否继续？",
				width: 450,
				onOk: function() {
					loadParameter();
					loadAttribute();
					previousProductCategoryId = $productCategoryId.val();
				},
				onCancel: function() {
					$productCategoryId.val(previousProductCategoryId);
				}
			});
		} else {
			loadParameter();
			loadAttribute();
			previousProductCategoryId = $productCategoryId.val();
		}
	});
	
	// 加载参数
	function loadParameter() {
		$.ajax({
			url: "productAction_loadParameters.action",
			type: "GET",
			data: {pcId: $productCategoryId.val()},
			dataType: "json",
			beforeSend: function() {
				$parameterTable.empty();
			},
			success: function(data) {
				var trHtml = "";
				var paramIndex = 0;
				$.each(data, function(i, parameter) {
					trHtml += '<tr><td style="text-align: right;"><strong>' + parameter.name + ':<\/strong><\/td><td>&nbsp;<\/td><\/tr>';
					$.each(parameter.pitems, function(j, pitem) {
						trHtml += '<tr> <th>' + pitem.name + ': <\/th> <td> ' 
						trHtml += '<input type="hidden" name="ppitems[' + paramIndex + '].pitem.id" value="' + pitem.id + '" />';
						trHtml += '<input type="text" name="ppitems[' + paramIndex + '].value" class="text" maxlength="200" style="width: 300px;" \/> ' 
						trHtml += '<\/td> <\/tr>'; 
						paramIndex += 1;
					});
				});
				$parameterTable.append(trHtml);
			}
		});
	}
	
	// 加载属性
	function loadAttribute() {
		$.ajax({
			url: "productAction_loadAttributes.action",
			type: "GET",
			data: {pcId: $productCategoryId.val()},
			dataType: "json",
			beforeSend: function() {
				$attributeTable.empty();
			},
			success: function(data) {
				var trHtml = "";
				$.each(data, function(i, attribute) {
					var optionHtml = '<option value="">请选择...<\/option>';
					$.each(attribute.atopts, function(j, atopt) {
						optionHtml += '<option value="' + atopt.id + '">' + atopt.option + '<\/option>';
					});
					trHtml += '<tr> <th>' + attribute.name + ': <\/th> <td> ' 
					trHtml += '<select name="atopts[' + i + '].id" style="width: 300px;"> ' + optionHtml + ' <\/select> ' 
					trHtml += '<\/td> <\/tr>'; 
				});
				$attributeTable.append(trHtml);
			}
		});
	}
	
	// 修改商品规格
	$specificationIds.click(function() {
		if ($specificationIds.filter(":checked").size() == 0) {
			$specificationProductTable.find("tr:gt(1)").remove();
		}
		var $this = $(this);
		if ($this.prop("checked")) {
			$specificationProductTable.find("td.specification_" + $this.val()).show().find("select").prop("disabled", false);
		} else {
			$specificationProductTable.find("td.specification_" + $this.val()).hide().find("select").prop("disabled", true);
		}
	});
	
	// 增加规格商品
	$addSpecificationProduct.click(function() {
		if ($specificationIds.filter(":checked").size() == 0) {
			$.message("warn", "必须至少选择一个规格");
			return false;
		}

		var $tr = null;
		if ($specificationProductTable.find("tr:gt(1)").size() == 0) {
			$tr = $specificationProductTable.find("tr:eq(1)").clone().show().appendTo($specificationProductTable);
			$tr.find("td:first").text("当前规格");
			$tr.find("td:last").text("-");
		} else {
			$tr = $specificationProductTable.find("tr:eq(1)").clone().show().appendTo($specificationProductTable);
		}

		var $specificationSelect = $tr.find("select");
		for(i=0; i<$specificationSelect.length; i++) {
			var spSelectName = $($specificationSelect[i]).attr("name");
			spSelectName = spSelectName.replace("yjwshop", productSpIndex);
			$($specificationSelect[i]).attr("name",spSelectName);
		}
		productSpIndex ++;
	});
	
	// 删除规格商品
	$deleteSpecificationProduct.live("click", function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "您确定要删除吗？",
			onOk: function() {
				$this.closest("tr").remove();
			}
		});
	});
	
	$.validator.addClassRules({
		memberPrice: {
			min: 0,
			decimal: {
				integer: 12,
				fraction: 2
			}
		},
		productImageFile: {
		//	required: true,
			extension: "jpg,jpeg,bmp,gif,png"
		},
		productImageOrder: {
			digits: true
		}
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			productCategoryId: "required",
			name: "required",
			sn: {
				pattern: /^[0-9a-zA-Z_-]+$/
				/*				
				remote: {
					url: "check_sn.jhtml",
					cache: false
				}*/
			},
			price: {
				required: true,
				min: 0,
				decimal: {
					integer: 12,
					fraction: 2
				}
			},
			cost: {
				min: 0,
				decimal: {
					integer: 12,
					fraction: 2
				}
			},
			marketPrice: {
				min: 0,
				decimal: {
					integer: 12,
					fraction: 2
				}
			},
			weight: "digits",
			stock: "digits",
			point: "digits"
		},
		messages: {
			sn: {
				pattern: "非法字符",
				remote: "已存在"
			}
		},
		submitHandler: function(form) {
			if ($specificationIds.filter(":checked").size() > 0 
					&& $specificationProductTable.find("tr:gt(1)").size() == 0) {
				
				$.message("warn", "必须至少添加一个规格商品");
				return false;
				
			} else {
				var isRepeats = false;
				var parameters = new Array();
				$specificationProductTable.find("tr:gt(1)").each(function() {
					var parameter = $(this).find("select").serialize();
					if ($.inArray(parameter, parameters) >= 0) {
						$.message("warn", "商品规格值重复");
						isRepeats = true;
						return false;
					} else {
						parameters.push(parameter);
					}
				});
				if (!isRepeats) {
					$specificationProductTable.find("tr:eq(1)").find("select").prop("disabled", true);
					addCookie("previousProductCategoryId", $productCategoryId.val(), {expires: 24 * 60 * 60});
					form.submit();
				}
			}
		}
	});

	// 初始化CkEditor
	initCKEditor("introduction", "/YjwShopV1");
	
	// 添加商品时，自动加载商品参数和商品属性
	if(opType==null || opType=='' || opType=='add'){
		loadParameter();
		loadAttribute();
	}
	
}); 
</script>
</head>

<body>
	<div class="path">
		<a href="../index.html">首页</a> » 添加商品
	</div>
	<form method="post" id="inputForm" novalidate="novalidate" 
			action="productAction_saveProduct.action" enctype="multipart/form-data">
		<input type="hidden" name="id" value="<s:property value="product.id" />" />
		<ul class="tab" id="tab">
			<li><input type="button" value="基本信息" class="current"></li>
			<li><input type="button" value="商品介绍" class=""></li>
			<li><input type="button" value="商品图片" class=""></li>
			<li><input type="button" value="商品参数" class=""></li>
			<li><input type="button" value="商品属性" class=""></li>
			<li><input type="button" value="商品规格" class=""></li>
		</ul>
		
		
<!-- ***************************************** 基本信息 ***************************************** -->
		<table class="input tabContent" style="display: table;">
			<tbody>
				<tr>
					<th>
						商品分类:
					</th>
					<td>
						<select name="pc.id" id="productCategoryId" class="valid" style="width: 300px;">
						<s:iterator value="pcs" var="pc1">
							<option value="<s:property value="#pc1.id" />" <s:property value="product.pc.id==#pc1.id ? 'selected' : ''" />>
								┣&nbsp;<s:property value="#pc1.name" />
							</option>
							<s:iterator value="#pc1.childs" var="pc2">
								<option value="<s:property value="#pc2.id" />" <s:property value="product.pc.id==#pc2.id ? 'selected' : ''" />>
									&nbsp;&nbsp;&nbsp;&nbsp;┣&nbsp;<s:property value="#pc2.name" />
								</option>
								<s:iterator value="#pc2.childs" var="pc3">
									<option value="<s:property value="#pc3.id" />" <s:property value="product.pc.id==#pc3.id ? 'selected' : ''" />>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;┣&nbsp;<s:property value="#pc3.name" />
									</option>
								</s:iterator>
							</s:iterator>
						</s:iterator>
						</select>
					</td>
				</tr>
				<tr>
					<th><span class="requiredField">*</span>名称:</th>
					<td>
						<input type="text" maxlength="300" class="text" name="name" style="width: 300px;" 
								value="<s:property value="product.name" />" />
					</td>
				</tr>
				<tr>
					<th>编号:</th>
					<td>
						<input type="text" maxlength="300" class="text" name="sn" style="width: 300px;" 
								value="<s:property value="product.sn" />" />
					</td>
				</tr>
				<tr>
					<th><span class="requiredField">*</span>销售价:</th>
					<td>
						<input type="text" maxlength="16" class="text" name="price" style="width: 300px;" 
								value="<s:property value="product.price" />" />
					</td>
				</tr>
				
				<%--
				<tr>
					<th>会员价:</th>
					<td>
						<label>
							<input type="checkbox" value="true" name="isMemberPrice" id="isMemberPrice" />
							启用会员价
						</label>
					</td>
				</tr>
				<tr class="hidden" id="memberPriceTr">
					<th>&nbsp;</th>
					<td>
						贵宾会员:
						<input type="text" disabled="disabled" style="width: 60px; margin-right: 6px;" maxlength="16"
								class="text memberPrice" name="memberPrice_5" />
								
						白金会员:
						<input type="text" disabled="disabled" style="width: 60px; margin-right: 6px;" maxlength="16"
								class="text memberPrice" name="memberPrice_4" />
								
						金牌会员:
						<input type="text" disabled="disabled" style="width: 60px; margin-right: 6px;" maxlength="16"
								class="text memberPrice" name="memberPrice_3" />
								
						银牌会员:
						<input type="text" disabled="disabled" style="width: 60px; margin-right: 6px;" maxlength="16"
								class="text memberPrice" name="memberPrice_2" />
								
						普通会员:
						<input type="text" disabled="disabled" style="width: 60px; margin-right: 6px;" maxlength="16"
								class="text memberPrice" name="memberPrice_1" />
					</td>
				</tr>
				--%>
				
				<tr>
					<th>成本价:</th>
					<td>
						<input type="text" maxlength="16" class="text" name="cost" style="width: 300px;" 
								value="<s:property value="product.cost" />" />
					</td>
				</tr>
				<tr>
					<th>市场价:</th>
					<td>
						<input type="text" maxlength="16" class="text" name="marketPrice" style="width: 300px;" 
								value="<s:property value="product.marketPrice" />" />
					</td>
				</tr>
				<tr>
					<th>展示图片:</th>
					<td>
						<span class="fieldSet">
							<%--
							<input type="text" maxlength="300" class="text" name="image" style="width: 300px;" />
							<input type="button" value="选择文件" class="button" id="browserButton" />
							--%>
							<input type="file" name="coreImage" style="width: 300px;" />
						</span>
					</td>
				</tr>
				<tr>
					<th>单位:</th>
					<td>
						<input type="text" maxlength="300" class="text" name="unit" style="width: 300px;" 
								value="<s:property value="product.unit" />" />
					</td>
				</tr>
				<tr>
					<th>重量:</th>
					<td>
						<input type="text" maxlength="9" class="text" name="weight" style="width: 300px;" 
								value="<s:property value="product.weight" />" />
					</td>
				</tr>
				<tr>
					<th>库存:</th>
					<td>
						<input type="text" maxlength="9" class="text" name="stock" style="width: 300px;" 
								value="<s:property value="product.stock" />" />
					</td>
				</tr>
				<tr>
					<th>库存备注:</th>
					<td>
						<input type="text" maxlength="300" class="text" name="stockMemo" style="width: 300px;" 
								value="<s:property value="product.stockMemo" />" />
					</td>
				</tr>
				
				<%--
				<tr>
					<th>赠送积分:</th>
					<td>
						<input type="text" maxlength="9" class="text" name="point" style="width: 300px;" />
					</td>
				</tr>
				--%>
				
				<tr>
					<th>品牌:</th>
					<td>
						<select name="brand.id" style="width: 300px;">
							<option value="">请选择...</option>
						<s:iterator value="brands" var="brand">
							<option value="<s:property value="#brand.id" />" <s:property value="product.brand.id==#brand.id ? 'selected' : ''" />>
								<s:property value="#brand.name" />
							</option>
						</s:iterator>
						</select>
					</td>
				</tr>
				<tr>
					<th>标签:</th>
					<td id="checkTagTd">
						<label>
							<input type="checkbox" value="true" name="_isHot" <s:property value="product.isHot==true ? 'checked' : ''" /> /> 热销
							<input type="hidden" value="<s:property value="product.isHot==true ? true : false" />" name="isHot" />
						</label>
						&nbsp;&nbsp;
						<label>
							<input type="checkbox" value="true" name="_isNew" <s:property value="product.isNew==true ? 'checked' : ''" /> /> 最新
							<input type="hidden" value="<s:property value="product.isNew==true ? true : false" />" name="isNew" />
						</label>
						&nbsp;&nbsp;
						<label>
							<input type="checkbox" value="true" name="_isRecmd" <s:property value="product.isRecmd==true ? 'checked' : ''" /> />推荐
							<input type="hidden" value="<s:property value="product.isRecmd==true ? true : false" />" name="isRecmd" />
						</label>
					</td>
				</tr>
				<tr>
					<th>设置:</th>
					<td id="checkSettingTd">
						<label>
							<input type="checkbox" value="true" name="_isMarketable" <s:property value="product.isMarketable==null || product.isMarketable==true ? 'checked' : ''" /> /> 是否上架
							<input type="hidden" value="<s:property value="product.isMarketable==null || product.isMarketable==true ? true : false" />" name="isMarketable" />
						</label>
						&nbsp;&nbsp;
						<label>
							<input type="checkbox" value="true" name="_isList" <s:property value="product.isList==null || product.isList==true ? 'checked' : ''" /> /> 是否列出
							<input type="hidden" value="<s:property value="product.isList==null || product.isList==true ? true : false" />" name="isList" />
						</label>
						&nbsp;&nbsp;
						<label>
							<input type="checkbox" value="true" name="_isTop" <s:property value="product.isTop==true ? 'checked' : ''" /> /> 是否置顶
							<input type="hidden" value="<s:property value="product.isTop==true ? true : false" />" name="isTop" />
						</label>
						&nbsp;&nbsp;
						<label>
							<input type="checkbox" value="true" name="_isGift" <s:property value="product.isGift==true ? 'checked' : ''" /> /> 是否为赠品
							<input type="hidden" value="<s:property value="product.isGift==true ? true : false" />" name="isGift" />
						</label>
					</td>
				</tr>
				<tr>
					<th>备注:</th>
					<td>
						<input type="text" maxlength="300" class="text" name="memo" style="width: 300px;" 
								value="<s:property value="product.memo" />" />
					</td>
				</tr>
				<tr>
					<th>搜索关键词:</th>
					<td>
						<input type="text" maxlength="300" class="text" name="keyword" style="width: 300px;" 
								value="<s:property value="product.keyword" />" />
					</td>
				</tr>
				<tr>
					<th>页面标题:</th>
					<td>
						<input type="text" maxlength="200" class="text" name="seoTitle" style="width: 300px;" 
								value="<s:property value="product.seoTitle" />" />
					</td>
				</tr>
				<tr>
					<th>页面关键词:</th>
					<td>
						<input type="text" maxlength="200" class="text" name="seoKeywords" style="width: 300px;"
								value="<s:property value="product.seoKeywords" />" />
					</td>
				</tr>
				<tr>
					<th>页面描述:</th>
					<td>
						<input type="text" maxlength="200" class="text" name="seoDescription" style="width: 300px;" 
								value="<s:property value="product.seoDescription" />" />
					</td>
				</tr>
			</tbody>
		</table>
		
		
<!-- ***************************************** 商品介绍 ***************************************** -->
		<table class="input tabContent" style="display: none;">
			<tbody>
				<tr>
					<td>
						<textarea id="introduction" name="introduction">
							<s:property value="product.introduction" />
						</textarea>
					</td>
				</tr>
			</tbody>
		</table>
		
		
<!-- ***************************************** 商品图片 ***************************************** -->
		<table class="input tabContent" id="productImageTable" style="display: none;">
			<tbody>
				<tr>
					<td colspan="4">
						<a class="button" id="addProductImage" href="javascript:;">增加图片</a>
					</td>
				</tr>
				<tr class="title">
					<td>图片</td>
					<td>标题</td>
					<td>排序</td>
					<td>删除</td>
				</tr>
				
				<s:iterator value="product.pimgs" var="pimg" status="status">
				<tr>
					<td>
						<input type="hidden" name="pimgs[<s:property value="#status.index" />].id" 
								value="<s:property value="#pimg.id" />" />
						
						<input type="hidden" name="pimgs[<s:property value="#status.index" />].image" 
								id="pimgs[<s:property value="#status.index" />].image" value="" />
								
						<input name="productImages" class="productImageFile" type="file" 
								onchange="document.getElementById('pimgs[<s:property value="#status.index" />].image').value=this.value" />
					</td>
					<td>
						<input name="pimgs[<s:property value="#status.index" />].title" class="text" maxlength="200" type="text" 
								value="<s:property value="#pimg.title" />" />
					</td>
					<td>
						<input name="pimgs[<s:property value="#status.index" />].order" class="text productImageOrder" maxlength="9" 
								style="width: 50px;" type="text" value="<s:property value="#pimg.order" />" />
					</td>
					<td>
						<a href="javascript:;" class="deleteProductImage">[删除]</a>
					</td>
				</tr>
				</s:iterator>
				</tbody>
		</table>
		
		
<!-- ***************************************** 商品参数 ***************************************** -->
		<table class="input tabContent" id="parameterTable" style="display: none;">
			<tbody>
				<s:set var="paramIndex" value="0" scope="page" />
				<s:iterator value="product.pc.params" var="param">
				<tr>
					<td style="text-align: right;">
						<strong><s:property value="#param.name" />:</strong>
					</td>
					<td>&nbsp;</td>
				</tr>
				
				<s:iterator value="#param.pitems" var="pitem">
				<tr>
					<th><s:property value="#pitem.name" />:</th>
					<td>
						<input type="hidden" name="ppitems[<s:property value="#attr.paramIndex" />].pitem.id" 
								value="<s:property value="#pitem.id" />" />
						<input type="text" maxlength="300" class="text" style="width: 300px;" 
								name="ppitems[<s:property value="#attr.paramIndex" />].value" 
								value="<s:property value="#pitem.curPitemVal" />" />
						
						<s:set var="paramIndex" value="#attr.paramIndex + 1" scope="page" />
					</td>
				</tr>
				</s:iterator>
				</s:iterator>
				
			</tbody>
		</table>
		
		
<!-- ***************************************** 商品属性 ***************************************** -->
		<table class="input tabContent" id="attributeTable" style="display: none;">
			<tbody>
				
				<s:iterator value="product.pc.attrs" var="attr"  status="status">
				<tr>
					<th><s:property value="#attr.name" />:</th>
					<td>
						<select name="atopts[<s:property value="#status.index" />].id" style="width: 300px;">
							<option value="" <s:property value="#atopt.id==null ? 'selected' : ''" />>请选择...</option>
							
							<s:iterator value="#attr.atopts" var="atopt">
							
							<option value="<s:property value="#atopt.id" />" <s:property value="#atopt.id==#attr.curAtoptId ? 'selected' : ''" />>
								<s:property value="#atopt.option" />
							</option>
							
							</s:iterator>
							
						</select>
					</td>
				</tr>
				</s:iterator>
				
			</tbody>
		</table>
		
		
<!-- ***************************************** 商品规格 ***************************************** -->
		<table class="input tabContent" style="display: none;">
			<tbody>
				<tr class="title">
					<th>请选择商品规格:</th>
				</tr>
				<tr>
					<td>
						<div class="specificationSelect" id="specificationSelect">
							<ul>
								<s:iterator value="allSps" var="sp">
								<li>
									<label>
										<input type="checkbox" value="<s:property value="#sp.id" />" name="specificationIds" 
												<s:property value="product.sps.contains(#sp) ? 'checked' : ''" /> />
										<s:property value="#sp.name" />
										<span class="gray">[<s:property value="#sp.memo" />]</span>
									</label>
								</li>
								</s:iterator>
							</ul>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<a class="button" id="addSpecificationProduct" href="javascript:;">增加规格商品</a>
					</td>
				</tr>
				<tr>
					<td>
						<table class="input" id="specificationProductTable">
							<tbody>
								<tr class="title">
									<td width="60">&nbsp;</td>
									
									<s:iterator value="allSps" var="sp" status="status">
									<td class="specification_<s:property value="#sp.id" /> <s:property value="product.sps.contains(#sp) ? '' : 'hidden'" />">
										<s:property value="#sp.name" />
										<span class="gray">[<s:property value="#sp.memo" />]</span>
									</td>
									</s:iterator>
									
									<td>操作</td>
								</tr>
								
								<tr class="hidden">
									<td>&nbsp;</td>
									
									<s:iterator value="allSps" var="sp" status="status">
									<td class="specification_<s:property value="#sp.id" /> <s:property value="product.sps.contains(#sp) ? '' : 'hidden'" />">
										<select name="psubs[yjwshop].spvs[<s:property value="#status.index" />].id" 
												<s:property value="product.sps.contains(#sp) ? '' : 'disabled'" />>
											<option value="">无</option>
											<s:iterator value="#sp.spvs" var="spv">
											<option value="<s:property value="#spv.id" />">
												<s:property value="#spv.name" />
											</option>
											</s:iterator>
										</select>
									</td>
									</s:iterator>
									
									<td>
										<a class="deleteSpecificationProduct" href="javascript:;">[删除]</a>
									</td>
								</tr>
								
								<s:if test="opType!=null && (opType.equals('update') || opType.equals('view'))">
								<s:iterator value="product.psubs" var="psub" status="status1">
								<tr>
									<td>
										<s:if test="status1.index == 0">当前规格</s:if>
										<input type="hidden" name="psubs[<s:property value="#status1.index" />].id" 
												value="<s:property value="#psub.id" />" />
									</td>
									
									<s:iterator value="allSps" var="sp" status="status2">
									<td class="specification_<s:property value="#sp.id" /> <s:property value="product.sps.contains(#sp) ? '' : 'hidden'" />">
										<select name="psubs[<s:property value="#status1.index" />].spvs[<s:property value="#status2.index" />].id" 
												 <s:property value="product.sps.contains(#sp) ? '' : 'disabled'" />>
											<option value="">无</option>
											
											<s:iterator value="#sp.spvs" var="spv">
											<option value="<s:property value="#spv.id" />" <s:property value="#psub.spvs.contains(#spv) ? 'selected' : ''" />>
												<s:property value="#spv.name" />
											</option>
											</s:iterator>
											
										</select>
									</td>
									</s:iterator>
									
									<td>
										<a class="deleteSpecificationProduct" href="javascript:;">[删除]</a>
									</td>
								</tr>
								</s:iterator>
								</s:if>
								
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		
		
		
		<table class="input">
			<tbody>
				<tr>
					<th>&nbsp;</th>
					<td>
						<input type="submit" value="确&nbsp;&nbsp;定" class="button" 
								<s:property value="opType!=null && opType.equals('view') ? 'disabled' : ''" /> />
						<input type="button" value="返&nbsp;&nbsp;回" class="button" id="backButton" />
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>
