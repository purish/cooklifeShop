<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>编辑规格 - YJW-SHOP</title>
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
	var $specificationTable = $("#specificationTable");
	var $type = $("#type");
	var $addSpecificationValueButton = $("#addSpecificationValueButton");
	var $deleteSpecificationValue = $("a.deleteSpecificationValue");
	var specificationValueIndex = <s:property value="opType==null || opType.equals('add') ? 1 : sp.spvs.size()" />;
	
	// 修改规格类型
	$type.change(function() {
		if ($(this).val() == "0") {
			$("input.specificationValuesImage").val("").prop("disabled", true);
			$("input.browserButton").prop("disabled", true);
			$("input[name='spvImages']").val("").prop("disabled", true);
		} else {
			$("input.specificationValuesImage").prop("disabled", false);
			$("input.browserButton").prop("disabled", false);
			$("input[name='spvImages']").prop("disabled", false);
		}
	});
	
//	$("input.browserButton").browser();
	
	// 增加规格值
	$addSpecificationValueButton.click(function() {
		if ($type.val() == "0") {
			
			var trHtml = '<tr class="specificationValueTr"> <td> &nbsp; <\/td> <td> ' 
					+ '<input type="text" name="spvs[' + specificationValueIndex + '].name" maxlength="200" class="text specificationValuesName" \/> ' 
					+ '<\/td> <td> <span class="fieldSet" style="display: block; position: relative;"> ' 
					+ '<input type="text" name="spvs[' + specificationValueIndex + '].image" id="spvs[' + specificationValueIndex + '].image" maxlength="200" class="text specificationValuesImage" disabled="disabled" \/> ' 
					+ '<input type="button" class="button browserButton" value="选择文件" disabled="disabled" \/> ' 
					+ '<input type="file" name="spvImages" disabled="disabled" style="position: absolute; top: 0; left: 0; filter:alpha(opacity:0); opacity: 0; height: 28px; width: 285px;" onchange="document.getElementById(\'spvs[' + specificationValueIndex + '].image\').value=this.value" />'
					+ '<\/span> <\/td> <td> ' 
					+ '<input type="text" name="spvs[' + specificationValueIndex + '].order" maxlength="9" class="text specificationValuesOrder" style="width: 30px;" \/> ' 
					+ '<\/td> <td> <a href="javascript:;" class="deleteSpecificationValue"> [删除] <\/a> <\/td> <\/tr>';
			
		} else {
			
			var trHtml = '<tr class="specificationValueTr"> <td> &nbsp; <\/td> <td> ' 
					+ '<input type="text" name="spvs[' + specificationValueIndex + '].name" class="text specificationValuesName" maxlength="200" \/> ' 
					+ '<\/td> <td> <span class="fieldSet" style="display: block; position: relative;"> ' 
					+ '<input type="text" name="spvs[' + specificationValueIndex + '].image" id="spvs[' + specificationValueIndex + '].image" class="text specificationValuesImage" maxlength="200" \/> ' 
					+ '<input type="button" class="button browserButton" value="选择文件" \/> ' 
					+ '<input type="file" name="spvImages" style="position: absolute; top: 0; left: 0; filter:alpha(opacity:0); opacity: 0; height: 28px; width: 285px;" onchange="document.getElementById(\'spvs[' + specificationValueIndex + '].image\').value=this.value" />'
					+ '<\/span> <\/td> <td> ' 
					+ '<input type="text" name="spvs[' + specificationValueIndex + '].order" class="text specificationValuesOrder" maxlength="9" style="width: 30px;" \/> ' 
					+ '<\/td> <td> <a href="javascript:;" class="deleteSpecificationValue"> [删除] <\/a> <\/td> <\/tr>';
		}
		
		$specificationTable.append(trHtml).find("input.browserButton:last").browser();
		specificationValueIndex ++;
	});
	
	// 删除规格值
	$deleteSpecificationValue.live("click", function() {
		var $this = $(this);
		if ($specificationTable.find("tr.specificationValueTr").size() <= 1) {
			$.message("warn", "必须至少保留一个规格值");
		} else {
			$this.closest("tr").remove();
		}
	});
	
	$.validator.addClassRules({
		specificationValuesName: {
			required: true
		},
//		specificationValuesImage: {
//			required: true
//		},
		specificationValuesOrder: {
			digits: true
		}
	});
	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			order: "digits"
		}
	});
});
</script>
</head>

<body>
	<div class="path">
		<a href="../index.html">首页</a> » 添加规格
	</div>
	
	<form method="post" action="spAction_saveSpecification.action" id="inputForm" 
			novalidate="novalidate" enctype="multipart/form-data">
		<input name="id" type="hidden" value="<s:property value="sp.id" />" />
		<table class="input" id="specificationTable">
			<tbody>
				<tr class="titleTr">
					<th><span class="requiredField">*</span>名称:</th>
					<td colspan="4">
						<input name="name" type="text" maxlength="300" class="text" style="width: 300px;" 
								value="<s:property value="sp.name" />" />
					</td>
				</tr>
				<tr>
					<th>类型:</th>
					<td colspan="4">
						<select id="type" name="type" class="valid" style="width: 150px;">
							<option value="0" <s:property value="sp.type==null || sp.type==0 ? 'selected' : ''" />>文本</option>
							<option value="1" <s:property value="sp.type==1 ? 'selected' : ''" />>图片</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>备注:</th>
					<td colspan="4">
						<input name="memo" type="text" maxlength="300" class="text" style="width: 300px;" 
								value="<s:property value="sp.memo" />" />
					</td>
				</tr>
				<tr>
					<th>排序:</th>
					<td colspan="4">
						<input name="order" type="text" maxlength="9" class="text" style="width: 300px;" 
								value="<s:property value="sp.order" />" />
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td colspan="4">
						<a class="button" id="addSpecificationValueButton" href="javascript:;">增加规格值</a>
					</td>
				</tr>
				<tr class="title">
					<td>&nbsp;</td>
					<td>规格值名称</td>
					<td>规格值图片</td>
					<td>规格值排序</td>
					<td>删除</td>
				</tr>
				
				<!-- 如果是添加规格，则显示一行规格值 -->
				<s:if test="opType==null || opType.equals('add')">
				<tr class="specificationValueTr">
					<td>&nbsp;</td>
					<td>
						<input type="text" maxlength="200" class="text specificationValuesName"
								name="spvs[0].name" value="" />
					</td>
					<td>
						<span class="fieldSet" style="display: block; position: relative;">
							<input type="text" disabled="disabled" maxlength="200"
									class="text specificationValuesImage"
									id="spvs[0].image" name="spvs[0].image" value="" />
									
							<input type="button" disabled="disabled" value="选择文件"
									class="button browserButton" />
									
							<input type="file" name="spvImages" disabled="disabled" 
									style="position: absolute; top: 0; left: 0; filter:alpha(opacity:0); opacity: 0; height: 28px; width: 285px;"
									onchange="document.getElementById('spvs[0].image').value=this.value" />
						</span>
					</td>
					<td>
						<input type="text" style="width: 30px;" maxlength="9" class="text specificationValuesOrder" 
								name="spvs[0].order" value="" />
					</td>
					<td>
						<a class="deleteSpecificationValue" href="javascript:;">[删除]</a>
					</td>
				</tr>
				</s:if>
				
				<!-- 如果是更新/查看规格，则显示全部规格值 -->
				<s:else>
				<s:iterator value="sp.spvs" var="spv" status="status">
				<tr class="specificationValueTr">
					<td>&nbsp;</td>
					<td>
						<input type="hidden" name="spvs[<s:property value="#status.index" />].id" 
								value="<s:property value="#spv.id" />" />
						
						<input type="text" maxlength="200" class="text specificationValuesName"
								name="spvs[<s:property value="#status.index" />].name" 
								value="<s:property value="#spv.name" />" />
					</td>
					<td>
						<span class="fieldSet" style="display: block; position: relative;">
							<input type="text" maxlength="200" 
									<s:property value="sp.type==null || sp.type==0 ? 'disabled' : ''" /> 
									class="text specificationValuesImage" 
									id="spvs[<s:property value="#status.index" />].image" 
									name="spvs[<s:property value="#status.index" />].image" value="" />
									
							<input type="button" value="选择文件" 
									<s:property value="sp.type==null || sp.type==0 ? 'disabled' : ''" /> 
									class="button browserButton" />
									
							<input type="file" name="spvImages" 
									<s:property value="sp.type==null || sp.type==0 ? 'disabled' : ''" />
									style="position: absolute; top: 0; left: 0; filter:alpha(opacity:0); opacity: 0; height: 28px; width: 285px;"
									onchange="document.getElementById('spvs[<s:property value="#status.index" />].image').value=this.value" />
						</span>
					</td>
					<td>
						<input type="text" style="width: 30px;" maxlength="9"
								class="text specificationValuesOrder"
								name="spvs[<s:property value="#status.index" />].order" 
								value="<s:property value="#spv.order" />" />
					</td>
					<td>
						<a class="deleteSpecificationValue" href="javascript:;">[删除]</a>
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
					<td colspan="4">
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