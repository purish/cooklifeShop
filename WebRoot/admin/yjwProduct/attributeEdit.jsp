<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>编辑属性 - YJW-SHOP</title>
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
	var $attributeTable = $("#attributeTable");
	var $addOption = $("#addOption");
	var $deleteOption = $("a.deleteOption");
	var attributeIndex = <s:property value="opType==null || opType.equals('add') ? 1 : attr.atopts.size()" />;
	
	// 增加可选项
	$addOption.live("click", function() {
		var $this = $(this);
		var trHtml = '<tr class="optionTr"> <td> &nbsp; <\/td> <td> ' 
				+ '<input type="text" name="atopts[' + attributeIndex + '].option" class="text atoptName" maxlength="300" style="width: 300px;" \/> ' 
				+ '<\/td> <td> <a href="javascript:;" class="deleteOption">[删除]<\/a> <\/td> <\/tr>'; 
				
		$attributeTable.append(trHtml);
		attributeIndex ++;
	});
	// 删除可选项
	$deleteOption.live("click", function() {
		var $this = $(this);
		if ($attributeTable.find("tr.optionTr").size() <= 1) {
			$.message("warn", "必须至少保留一个可选项");
		} else {
			$this.closest("tr").remove();
		}
	});

	$.validator.addClassRules({
		atoptName: {
			required: true
		}
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			productCategoryId: "required",
			name: "required",
			order: "digits",
		//	options: "required"
		}
	});
}); 
</script>

</head>
<body>
	<div class="path">
		<a href=../index.html>首页</a> » 添加属性
	</div>
	<form id="inputForm" method="post" action="attrAction_saveAttribute.action" novalidate="novalidate">
		<input name="id" type="hidden" value="<s:property value="attr.id" />" />
		<table class="input" id="attributeTable">
			<tbody>
				<tr>
					<th>绑定分类:</th>
					<td colspan="2">
						<select name="attr.pc.id" style="width: 300px;">
						<s:iterator value="pcs" var="pc1">
							<option value="<s:property value="#pc1.id" />" <s:property value="attr.pc.id==#pc1.id ? 'selected' : ''" />>
								┣&nbsp;<s:property value="#pc1.name" />
							</option>
							<s:iterator value="#pc1.childs" var="pc2">
								<option value="<s:property value="#pc2.id" />" <s:property value="attr.pc.id==#pc2.id ? 'selected' : ''" />>
									&nbsp;&nbsp;&nbsp;&nbsp;┣&nbsp;<s:property value="#pc2.name" />
								</option>
								<s:iterator value="#pc2.childs" var="pc3">
									<option value="<s:property value="#pc3.id" />" <s:property value="attr.pc.id==#pc3.id ? 'selected' : ''" />>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;┣&nbsp;<s:property value="#pc3.name" />
									</option>
								</s:iterator>
							</s:iterator>
						</s:iterator>
						</select>
					</td>
				</tr>
				<tr>
					<th><span class="requiredField">*</span>名　　称:</th>
					<td colspan="2">
						<input type="text" maxlength="300" class="text" name="name" style="width: 300px;" 
								value="<s:property value="attr.name" />" />
					</td>
				</tr>
				<tr>
					<th>排　　序:</th>
					<td colspan="2">
						<input type="text" maxlength="9" class="text" name="order" style="width: 300px;" 
								value="<s:property value="attr.order" />" />
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td colspan="2">
						<a class="button" id="addOption" href="javascript:;">增加可选项</a>
					</td>
				</tr>
				<tr class="title">
					<td>&nbsp;</td>
					<td>可选项</td>
					<td>删除</td>
				</tr>
				
				<s:if test="opType==null || opType.equals('add')">
				<tr class="optionTr">
					<td>&nbsp;</td>
					<td>
						<input type="text" maxlength="300" class="text atoptName" name="atopts[0].option" 
								style="width: 300px;" />
					</td>
					<td>
						<a class="deleteOption" href="javascript:;">[删除]</a>
					</td>
				</tr>
				</s:if>
				
				<s:else>
				<s:iterator value="attr.atopts" var="atopt" status="status">
				<tr class="optionTr">
					<td>&nbsp;</td>
					<td>
						<input type="hidden" name="atopts[<s:property value="#status.index" />].id" 
								value="<s:property value="#atopt.id" />" />
						<input type="text" maxlength="300" class="text atoptName" style="width: 300px;" 
								name="atopts[<s:property value="#status.index" />].option"
								value="<s:property value="#atopt.option" />" />
								
					</td>
					<td>
						<a class="deleteOption" href="javascript:;">[删除]</a>
					</td>
				</tr>
				</s:iterator>
				</s:else>
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