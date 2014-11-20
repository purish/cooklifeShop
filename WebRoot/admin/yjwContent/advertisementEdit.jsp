<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>编辑广告 - YJW-SHOP</title>
	<!-- 通知浏览器本页面的编码字符集 -->
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<!-- 设置版权信息 -->
	<meta content="yjwShop Team" name="author" />
	<meta content="yjwShop" name="copyright" />

	<!-- 引入CSS文件 -->
	<link type="text/css" rel="stylesheet" href="../css/common.css" />
	<!-- 引入JS文件 -->
	<script type="text/javascript" src="../js/jquery/jquery-1.7.2.js"></script>
	<%--<script type="text/javascript" src="../js/jquery/jquery.tools.js"></script>--%>
	<script type="text/javascript" src="../js/jquery/jquery.validate.js"></script>
	<script type="text/javascript" src="../js/yjw/common.js"></script>
	<script type="text/javascript" src="../js/yjw/input.js"></script>
	<script type="text/javascript" src="../js/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="../js/ckfinder/ckfinder.js"></script>
	<script type="text/javascript" src="../js/yjw/editor-me.js"></script>
		
<script type="text/javascript">
$().ready(function() {
	var $inputForm = $("#inputForm");
	var $type = $("#type");

	var $contentTr = $("#contentTr");
	var $urlTr = $("#urlTr");
	
	// 图片/文本 类型切换
	$type.change(function() {
		if ($(this).val() == "0") {

			$("input.advertisementImage").val("").prop("disabled", true);
			$("input.browserButton").prop("disabled", true);
			$("input[name='adImage']").val("").prop("disabled", true);

			$contentTr.show();
			$contentTr.find("textarea[id='content']").val("").prop("disabled", false);
			$urlTr.hide();
			$urlTr.find("input[name='url']").val("").prop("disabled", true);
			
		} else if($(this).val() == "1") {

			$("input.advertisementImage").val("").prop("disabled", false);
			$("input.browserButton").prop("disabled", false);
			$("input[name='adImage']").val("").prop("disabled", false);

			$contentTr.show();
			$contentTr.find("textarea[id='content']").val("").prop("disabled", false);
			$urlTr.hide();
			$urlTr.find("input[name='url']").val("").prop("disabled", true);
			
		} else if($(this).val() == "2") {

			$("input.advertisementImage").val("").prop("disabled", true);
			$("input.browserButton").prop("disabled", true);
			$("input[name='adImage']").val("").prop("disabled", true);

			$contentTr.hide();
			$contentTr.find("textarea[id='content']").val("").prop("disabled", true);
			$urlTr.show();
			$urlTr.find("input[name='url']").val("").prop("disabled", false);
			
		} else if($(this).val() == "3") {

			$("input.advertisementImage").val("").prop("disabled", false);
			$("input.browserButton").prop("disabled", false);
			$("input[name='adImage']").val("").prop("disabled", false);

			$contentTr.hide();
			$contentTr.find("textarea[id='content']").val("").prop("disabled", true);
			$urlTr.show();
			$urlTr.find("input[name='url']").val("").prop("disabled", false);
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
	
	// 表单验证
	$inputForm.validate( {
			rules : {
				title : "required",
			//	logoImage : "required",
				order : "digits"
			}
		});
	// 初始化CkEditor
	initCKEditor("content", "<%=request.getContextPath() %>");
});
</script>
</head>
<body>
	<div class="path">
		<a href="../index.html">首页</a> » 编辑广告
	</div>
	<form id="inputForm" method="post" action="adAction_saveAdvertisement.action" 
			novalidate="novalidate" enctype="multipart/form-data">
		<input name="id" type="hidden" value="<s:property value="ad.id" />" />
		<table class="input">
			<tbody>
				<tr>
					<th><span class="requiredField">*</span> 标题:</th>
					<td>
						<input name="title" class="text" type="text" maxlength="300" style="width: 300px;" 
								value="<s:property value="ad.title" />" />
					</td>
				</tr>
				<tr>
					<th>广告位:</th>
					<td>
						<select id="position" name="position" class="valid" style="width: 300px;">
							<option value="0" <s:property value="ad.position==null || ad.position==0 ? 'selected' : ''" />>首页幻灯片切换广告</option>
							<option value="1" <s:property value="ad.position==1 ? 'selected' : ''" />>首页左栏咨询广告</option>
							<option value="2" <s:property value="ad.position==2 ? 'selected' : ''" />>首页底部图片列表广告</option>
							<option value="3" <s:property value="ad.position==3 ? 'selected' : ''" />>商品列表页左栏底部广告</option>
							<option value="4" <s:property value="ad.position==4 ? 'selected' : ''" />>商品详细页左栏底部广告</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>类型:</th>
					<td>
						<select id="type" name="type" class="valid" style="width: 300px;">
							<option value="0" <s:property value="ad.type==null || ad.type==0 ? 'selected' : ''" />>文本-内容</option>
							<option value="1" <s:property value="ad.type==1 ? 'selected' : ''" />>图片-内容</option>
							<option value="2" <s:property value="ad.type==1 ? 'selected' : ''" />>文本-链接</option>
							<option value="3" <s:property value="ad.type==1 ? 'selected' : ''" />>图片-链接</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>图片:</th>
					<td>
						<span class="fieldSet" style="display: block; position: relative;">
							<input type="text" maxlength="300" class="text advertisementImage" style="width: 300px;"
									<s:property value="ad.type==null || ad.type==0 || ad.type==2 ? 'disabled' : ''" /> 
									id="advertisementImage" name="advertisementImage" value="" />
									
							<input type="button" value="选择文件" 
									<s:property value="ad.type==null || ad.type==0 || ad.type==2 ? 'disabled' : ''" /> 
									class="button browserButton" />
									
							<input type="file" name="adImage" 
									<s:property value="ad.type==null || ad.type==0 || ad.type==2 ? 'disabled' : ''" /> 
									style="position: absolute; top: 0; left: 0; filter:alpha(opacity:0); opacity: 0; height: 28px; width: 405px;"
									onchange="document.getElementById('advertisementImage').value=this.value" />
						</span>
					</td>
				</tr>
				<tr>
					<th>备注:</th>
					<td>
						<input name="memo" class="text" type="text" maxlength="300" style="width: 300px;" 
								value="<s:property value="ad.memo" />" />
					</td>
				</tr>
				<tr>
					<th>排序:</th>
					<td>
						<input name="order" class="text" type="text" maxlength="9" style="width: 300px;"
								value="<s:property value="ad.order" />" />
					</td>
				</tr>
				<tr id="contentTr" <s:property value="ad.type==null || ad.type==0 || ad.type==1 ? '' : 'class=hidden'" />>
					<th>内容:</th>
					<td>
						<textarea id="content" name="content" 
								<s:property value="ad.type==null || ad.type==0 || ad.type==1 ? '' : 'disabled'" />>
							<s:property value="ad.content" />
						</textarea>
					</td>
				</tr>
				<tr id="urlTr" <s:property value="ad.type==2 || ad.type==3 ? '' : 'class=hidden'" />>
					<th>链接地址:</th>
					<td>
						<input name="url" class="text" type="text" maxlength="300" style="width: 300px;" 
								<s:property value="ad.type==2 || ad.type==3 ? '' : 'disabled'" /> 
								value="<s:property value="ad.url" />" />
					</td>
				</tr>
				<tr>
					<th> </th>
					<td>
						<input class="button" type="submit" value="确  定" 
								<s:property value="opType!=null && opType.equals('view') ? 'disabled' : ''" /> />
						<input id="backButton" class="button" type="button" value="返  回" />
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>
