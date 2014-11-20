<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>编辑参数 - YJW-SHOP</title>
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
	var $parameterTable = $("#parameterTable");
	var $addParameter = $("#addParameter");
	var $deleteParameter = $("a.deleteParameter");
	var parameterIndex = <s:property value="opType==null || opType.equals('add') ? 1 : param.pitems.size()" />;
	
	// 增加参数
	$addParameter.live("click", function() {
		var $this = $(this);
		var trHtml = '<tr class="parameterTr"> <td> &nbsp; <\/td> <td> ' 
				+ '<input type="text" name="pitems[' + parameterIndex + '].name" class="text parametersName" maxlength="300" style="width: 300px;" \/> ' 
				+ '<\/td> <td> ' 
				+ '<input type="text" name="pitems[' + parameterIndex + '].order" class="text parametersOrder" maxlength="9" style="width: 30px;" \/> ' 
				+ '<\/td> <td> <a href="javascript:;" class="deleteParameter">[删除]<\/a> <\/td> <\/tr>'; 
				
		$parameterTable.append(trHtml);
		parameterIndex ++;
	});
	
	// 删除参数
	$deleteParameter.live("click", function() {
		var $this = $(this);
		if ($parameterTable.find("tr.parameterTr").size() <= 1) {
			$.message("warn", "必须至少保留一个参数");
		} else {
			$this.closest("tr").remove();
		}
	});
	
	$.validator.addClassRules({
		parametersName: {
			required: true
		},
		parametersOrder: {
			digits: true
		}
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			productCategoryId: "required",
			name: "required",
			order: "digits"
		}
	});
}); 
</script>
</head>
<body>
	<div class="path">
		<a href="../index.html">首页</a> » 添加参数
	</div>
	<form method="post" action="paramAction_saveParameter.action" id="inputForm" novalidate="novalidate">
		<input name="id" type="hidden" value="<s:property value="param.id" />" />
		<table class="input" id="parameterTable">
			<tbody>
				<tr>
					<th>绑定分类:</th>
					<td colspan="3">
						<select name="param.pc.id" style="width: 300px;">
						<s:iterator value="pcs" var="pc1">
							<option value="<s:property value="#pc1.id" />" <s:property value="param.pc.id==#pc1.id ? 'selected' : ''" />>
								┣&nbsp;<s:property value="#pc1.name" />
							</option>
							<s:iterator value="#pc1.childs" var="pc2">
								<option value="<s:property value="#pc2.id" />" <s:property value="param.pc.id==#pc2.id ? 'selected' : ''" />>
									&nbsp;&nbsp;&nbsp;&nbsp;┣&nbsp;<s:property value="#pc2.name" />
								</option>
								<s:iterator value="#pc2.childs" var="pc3">
									<option value="<s:property value="#pc3.id" />" <s:property value="param.pc.id==#pc3.id ? 'selected' : ''" />>
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
					<td colspan="3">
						<input type="text" maxlength="300" class="text" name="name" style="width: 300px;"
								value="<s:property value="param.name" />" />
					</td>
				</tr>
				<tr>
					<th>排　　序:</th>
					<td colspan="3">
						<input type="text" maxlength="9" class="text" name="order"  style="width: 300px;"
								value="<s:property value="param.order" />" />
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td colspan="3">
						<a class="button" id="addParameter" href="javascript:;">增加参数</a>
					</td>
				</tr>
				<tr class="title">
					<td>&nbsp;</td>
					<td>名称</td>
					<td>排序</td>
					<td>删除</td>
				</tr>
				
				<s:if test="opType==null || opType.equals('add')">
				<tr class="parameterTr">
					<td>&nbsp;</td>
					<td>
						<input type="text" maxlength="300" class="text parametersName" name="pitems[0].name" 
								style="width: 300px;" />
					</td>
					<td>
						<input type="text" style="width: 30px;" maxlength="9" class="text parametersOrder" 
								name="pitems[0].order" />
					</td>
					<td>
						<a class="deleteParameter" href="javascript:;">[删除]</a>
					</td>
				</tr>
				</s:if>
				
				<s:else>
				<s:iterator value="param.pitems" var="pitem" status="status">
				<tr class="parameterTr">
					<td>&nbsp;</td>
					<td>
						<input type="hidden" name="pitems[<s:property value="#status.index" />].id" 
								value="<s:property value="#pitem.id" />" />
						
						<input type="text" maxlength="300" class="text parametersName" style="width: 300px;" 
								name="pitems[<s:property value="#status.index" />].name" 
								value="<s:property value="#pitem.name" />" />
					</td>
					<td>
						<input type="text" style="width: 30px;" maxlength="9" class="text parametersOrder" 
								name="pitems[<s:property value="#status.index" />].order"
								value="<s:property value="#pitem.order" />" />
					</td>
					<td>
						<a class="deleteParameter" href="javascript:;">[删除]</a>
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