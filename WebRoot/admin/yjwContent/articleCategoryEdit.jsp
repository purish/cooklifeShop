<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>编辑文章分类 - YJW-SHOP</title>
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

<style type="text/css">
.brands label {
	width: 150px;
	display: block;
	float: left;
	padding-right: 6px;
}
</style>

<script type="text/javascript">
$().ready(function() {
	var $inputForm = $("#inputForm");
	// 表单验证
	$inputForm.validate( {
		rules : {
			name : "required",
			order : "digits"
		}
	});
});
</script>
</head>

<body>
	<div class="path">
		<a href="../index.html">首页</a> » 编辑文章分类
	</div>
	<form method="post" id="inputForm" novalidate="novalidate" 
			action="acAction_saveArticleCategory.action">
		<input name="id" type="hidden" value="<s:property value="ac.id" />" />
		<table class="input">
			<tbody>
				<tr>
					<th><span class="requiredField">*</span>名称:</th>
					<td>
						<input id="name" name="name" type="text" maxlength="300" class="text" style="width: 300px;" 
								value="<s:property value="ac.name" />" />
					</td>
				</tr>
				<tr>
					<th>上级分类:</th>
					<td>
						<select name="ac.parent.id" style="width: 300px;">
							<option value="" <s:property value="ac.parent==null ? 'selected' : ''" />>顶级分类</option>
							<s:iterator value="acs" var="ac1">
								<option value="<s:property value="#ac1.id" />" <s:property value="ac.parent.id==#ac1.id ? 'selected' : ''" />>
									┣&nbsp;<s:property value="#ac1.name" />
								</option>
								<s:iterator value="#ac1.childs" var="ac2">
									<option value="<s:property value="#ac2.id" />" <s:property value="ac.parent.id==#ac2.id ? 'selected' : ''" />>
										&nbsp;&nbsp;&nbsp;&nbsp;┣&nbsp;<s:property value="#ac2.name" />
									</option>
									<s:iterator value="#ac2.childs" var="ac3">
										<option value="<s:property value="#ac3.id" />" <s:property value="ac.parent.id==#ac3.id ? 'selected' : ''" />>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;┣&nbsp;<s:property value="#ac3.name" />
										</option>
									</s:iterator>
								</s:iterator>
							</s:iterator>
						</select>
					</td>
				</tr>
				<tr>
					<th>页面标题:</th>
					<td>
						<input name="seoTitle" type="text" maxlength="300" class="text" style="width: 300px;" 
								value="<s:property value="ac.seoTitle" />" />
					</td>
				</tr>
				<tr>
					<th>页面关键词:</th>
					<td>
						<input name="seoKeywords" type="text" maxlength="300" class="text" style="width: 300px;" 
								value="<s:property value="ac.seoKeywords" />" />
					</td>
				</tr>
				<tr>
					<th>页面描述:</th>
					<td>
						<input name="seoDescription" type="text" maxlength="300" class="text" style="width: 300px;" 
								value="<s:property value="ac.seoDescription" />" />
					</td>
				</tr>
				<tr>
					<th>排序:</th>
					<td>
						<input name="order" type="text" maxlength="9" class="text" style="width: 300px;" 
								value="<s:property value="ac.order" />" />
					</td>
				</tr>
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