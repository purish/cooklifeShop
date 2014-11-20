<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>编辑导航 - YJW-SHOP</title>
	<!-- 通知浏览器本页面的编码字符集 -->
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<!-- 设置版权信息 -->
	<meta content="yjwShop Team" name="author" />
	<meta content="yjwShop" name="copyright" />

	<!-- 引入CSS文件 -->
	<link type="text/css" rel="stylesheet" href="../css/common.css" />
	<!-- 引入JS文件 -->
	<script type="text/javascript" src="../js/jquery/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="../js/jquery/jquery.validate.js"></script>
	<script type="text/javascript" src="../js/yjw/common.js"></script>
	<script type="text/javascript" src="../js/yjw/input.js"></script>
	
<script type="text/javascript">

$().ready(function() {
	var $inputForm = $("#inputForm");

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
			name : "required",
			url : "required",
			order : "digits"
		}
	});
});
</script>
	
</head>

<body>
	<div class="path">
		<a href="../index.html">首页</a> » 编辑导航
	</div>
	<form id="inputForm" action="navAction_saveNavigation.action" method="post">
		<input name="id" type="hidden" value="<s:property value="nav.id" />" />
		<table class="input">
			<tr>
				<th><span class="requiredField">*</span>名称:</th>
				<td>
					<input type="text" id="name" name="name" class="text" maxlength="300" style="width: 300px;" 
							value="<s:property value="nav.name" />" />
				</td>
			</tr>
			<tr>
				<th>位置:</th>
				<td>
					<select name="position" style="width: 300px;">
						<option value="0" <s:property value="nav.position==null || nav.position==0 ? 'selected' : ''" />>顶部导航</option>
						<option value="1" <s:property value="nav.position==1 ? 'selected' : ''" />>底部导航</option>
					</select>
				</td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>链接地址:</th>
				<td>
					<input type="text" name="url" class="text" maxlength="300" style="width: 300px;" 
							value="<s:property value="nav.url" />" />
				</td>
			</tr>
			<tr>
				<th>设置:</th>
				<td id="checkSettingTd">
					<label>
						<input type="checkbox" name="_isBlank" value="true" <s:property value="nav.isBlank==true ? 'checked' : ''" /> />是否新窗口打开
						<input type="hidden" name="isBlank" value="<s:property value="nav.isBlank==true ? true : false" />" />
					</label>
				</td>
			</tr>
			<tr>
				<th>备注:</th>
				<td>
					<input type="text" name="memo" class="text" maxlength="300" style="width: 300px;" 
							value="<s:property value="nav.memo" />" />
				</td>
			</tr>
			<tr>
				<th>排序:</th>
				<td>
					<input type="text" name="order" class="text" maxlength="9" style="width: 300px;"
							value="<s:property value="nav.order" />" />
				</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td>
					<input type="submit" class="button" value="确&nbsp;&nbsp;定" 
							<s:property value="opType!=null && opType.equals('view') ? 'disabled' : ''" /> />
					<input type="button" class="button" value="返&nbsp;&nbsp;回" id="backButton" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
