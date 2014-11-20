<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>编辑品牌 - YJW-SHOP</title>
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
		var $logoImage = $("#logoImage");
		// 图片/文本 类型切换
		$type.change(function() {
				if ($(this).val() == "0") {
					$logoImage.val("").prop("disabled", true);
				} else {
					$logoImage.prop("disabled", false);
				}
			});
		// 表单验证
		$inputForm.validate( {
				rules : {
					name : "required",
				//	logoImage : "required",
					order : "digits"
				}
			});
		// 初始化CkEditor
		initCKEditor("introduction", "<%=request.getContextPath() %>");
	});
</script>
</head>
<body>
	<div class="path">
		<a href="../index.html">首页</a> » 添加品牌
	</div>
	<form id="inputForm" method="post" action="brandAction_saveBrand.action" 
			novalidate="novalidate" enctype="multipart/form-data">
		<input name="id" type="hidden" value="<s:property value="brand.id" />" />
		<table class="input">
			<tbody>
				<tr>
					<th><span class="requiredField">*</span> 名称:</th>
					<td>
						<input name="name" class="text" type="text" maxlength="200" style="width: 300px;" 
								value="<s:property value="brand.name" />" />
					</td>
				</tr>
				<tr>
					<th>类型:</th>
					<td>
						<select id="type" name="type" class="valid" style="width: 150px;">
							<option value="0" <s:property value="brand.type==null || brand.type==0 ? 'selected' : ''" />>文本</option>
							<option value="1" <s:property value="brand.type==1 ? 'selected' : ''" />>图片</option>
						</select>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input id="logoImage" name="logoImage" type="file" maxlength="200" 
								<s:property value="brand.type==null || brand.type==0 ? 'disabled' : ''" />
								style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<th>网址:</th>
					<td>
						<input name="url" class="text" type="text" maxlength="200" style="width: 300px;" 
								value="<s:property value="brand.url" />" />
					</td>
				</tr>
				<tr>
					<th>排序:</th>
					<td>
						<input name="order" class="text" type="text" maxlength="9" style="width: 300px;"
								value="<s:property value="brand.order" />" />
					</td>
				</tr>
				<tr>
					<th>介绍:</th>
					<td>
						<textarea id="introduction" name="introduction">
							<s:property value="brand.introduction" />
						</textarea>
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
