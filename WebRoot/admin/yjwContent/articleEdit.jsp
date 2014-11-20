<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>编辑文章 - YJW-SHOP</title>
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
		// 图片/文本 类型切换
		$type.change(function() {
			if ($(this).val() == "0") {
				$("input.articleImage").val("").prop("disabled", true);
				$("input.browserButton").prop("disabled", true);
				$("input[name='atcImage']").val("").prop("disabled", true);
			} else {
				$("input.articleImage").val("").prop("disabled", false);
				$("input.browserButton").prop("disabled", false);
				$("input[name='atcImage']").val("").prop("disabled", false);
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
		<a href="../index.html">首页</a> » 添加品牌
	</div>
	<form id="inputForm" method="post" action="articleAction_saveArticle.action" 
			novalidate="novalidate" enctype="multipart/form-data">
		<input name="id" type="hidden" value="<s:property value="article.id" />" />
		<table class="input">
			<tbody>
				<tr>
					<th><span class="requiredField">*</span> 标题:</th>
					<td>
						<input name="title" class="text" type="text" maxlength="300" style="width: 300px;" 
								value="<s:property value="article.title" />" />
					</td>
				</tr>
				<tr>
					<th>文章分类:</th>
					<td>
						<select name="ac.id" style="width: 300px;">
							<s:iterator value="acs" var="ac1">
								<option value="<s:property value="#ac1.id" />" <s:property value="article.ac.id==#ac1.id ? 'selected' : ''" />>
									┣&nbsp;<s:property value="#ac1.name" />
								</option>
								<s:iterator value="#ac1.childs" var="ac2">
									<option value="<s:property value="#ac2.id" />" <s:property value="article.ac.id==#ac2.id ? 'selected' : ''" />>
										&nbsp;&nbsp;&nbsp;&nbsp;┣&nbsp;<s:property value="#ac2.name" />
									</option>
									<s:iterator value="#ac2.childs" var="ac3">
										<option value="<s:property value="#ac3.id" />" <s:property value="article.ac.id==#ac3.id ? 'selected' : ''" />>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;┣&nbsp;<s:property value="#ac3.name" />
										</option>
									</s:iterator>
								</s:iterator>
							</s:iterator>
						</select>
					</td>
				</tr>
				<tr>
					<th>类型:</th>
					<td>
						<select id="type" name="type" class="valid" style="width: 300px;">
							<option value="0" <s:property value="article.type==null || article.type==0 ? 'selected' : ''" />>文本</option>
							<option value="1" <s:property value="article.type==1 ? 'selected' : ''" />>图片</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>图片:</th>
					<td>
						<span class="fieldSet" style="display: block; position: relative;">
							<input type="text" maxlength="300" class="text articleImage" style="width: 300px;"
									<s:property value="article.type==null || article.type==0 ? 'disabled' : ''" /> 
									id="articleImage" name="articleImage" value="" />
									
							<input type="button" value="选择文件" 
									<s:property value="article.type==null || article.type==0 ? 'disabled' : ''" /> 
									class="button browserButton" />
									
							<input type="file" name="atcImage" 
									<s:property value="article.type==null || article.type==0 ? 'disabled' : ''" /> 
									style="position: absolute; top: 0; left: 0; filter:alpha(opacity:0); opacity: 0; height: 28px; width: 405px;"
									onchange="document.getElementById('articleImage').value=this.value" />
						</span>
					</td>
				</tr>
				<tr>
					<th>作者:</th>
					<td>
						<input name="author" class="text" type="text" maxlength="300" style="width: 300px;" 
								value="<s:property value="article.author" />" />
					</td>
				</tr>
				<tr>
					<th>排序:</th>
					<td>
						<input name="order" class="text" type="text" maxlength="9" style="width: 300px;"
								value="<s:property value="article.order" />" />
					</td>
				</tr>
				<tr>
					<th>设置:</th>
					<td id="checkSettingTd">
						<label>
							<input name="_isPublish" value="true" type="checkbox" <s:property value="article.isPublish==null || article.isPublish==true ? 'checked' : ''" /> />是否发布 
							<input name="isPublish" type="hidden" value="<s:property value="article.isPublish==null || article.isPublish==true ? true : false" />" />
						</label>
						<label>
							<input name="_isTop" value="true" type="checkbox" <s:property value="article.isTop==true ? 'checked' : ''" /> />是否置顶
							<input name="isTop" type="hidden" value="<s:property value="article.isTop==true ? true : false" />" />
						</label>

					</td>
				</tr>
				<tr>
					<th>内容:</th>
					<td>
						<textarea id="content" name="content">
							<s:property value="article.content" />
						</textarea>
					</td>
				</tr>
				<tr>
					<th>页面标题:</th>
					<td>
						<input name="seoTitle" class="text" type="text" maxlength="300" style="width: 300px;" 
								value="<s:property value="article.seoTitle" />" />
					</td>
				</tr>
				<tr>
					<th>页面关键词:</th>
					<td>
						<input name="seoKeywords" class="text" type="text" maxlength="300" style="width: 300px;" 
								value="<s:property value="article.seoKeywords" />" />
					</td>
				</tr>
				<tr>
					<th>页面描述:</th>
					<td>
						<input name="seoDescription" class="text" type="text" maxlength="300" style="width: 300px;" 
								value="<s:property value="article.seoDescription" />" />
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
