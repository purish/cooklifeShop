<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>商品分类列表 - YJW-SHOP</title>
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

<script type="text/javascript">
$().ready(function() {
	var $delete = $("#listTable a.delete");
	// 删除
	$delete.click(function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "您确定要删除吗？",
			onOk: function() {
				$.ajax({
					url: "pcAction_deleteProductCategoryById.action",
					type: "POST",
					data: {id: $this.attr("val")},
					dataType: "json",
					cache: false,
					success: function(message) {
						$.message(message);
						if (message.type == "success") {
							$this.closest("tr").remove();
						}
					}
				});
			}
		});
		return false;
	});

	// 展开
});
</script>
</head>
<body>
	<div class="path">
		<a href="../index.html">首页</a> » 商品分类列表
	</div>
	<div class="bar">
		<a class="iconButton" href="pcAction_initSaveProductCategoryUi.action?opType=add">
			<span class="addIcon">&nbsp;</span>添加
		</a>
		<a class="iconButton" id="refreshButton" href="javascript:;">
			<span class="refreshIcon">&nbsp;</span>刷新 
		</a>
	</div>
	<table class="list" id="listTable">
		<tbody>
			<tr>
				<th><span>名称</span></th>
				<th><span>排序</span></th>
				<th><span>操作</span></th>
			</tr>
			
			<s:iterator value="pcs" var="pc1">
			
			<tr>
				<td>
					<span style="margin-left: 0px; color: #000000;">
						<s:property value="#pc1.name" />
					</span>
				</td>
				<td><s:property value="#pc1.order" /></td>
				<td>
					<a href="pcAction_initSaveProductCategoryUi.action?id=<s:property value="#pc1.id" />&opType=view">[查看]</a>
					<a href="pcAction_initSaveProductCategoryUi.action?id=<s:property value="#pc1.id" />&opType=update">[编辑]</a>
					<a val="<s:property value="#pc1.id" />" class="delete" href="javascript:;">[删除]</a>
				</td>
			</tr>
			
			<s:iterator value="#pc1.childs" var="pc2">
			
			<tr>
				<td>
					<span style="margin-left: 20px;">
						<s:property value="#pc2.name" />
					</span>
				</td>
				<td><s:property value="#pc2.order" /></td>
				<td>
					<a href="pcAction_initSaveProductCategoryUi.action?id=<s:property value="#pc2.id" />&opType=view">[查看]</a>
					<a href="pcAction_initSaveProductCategoryUi.action?id=<s:property value="#pc2.id" />&opType=update">[编辑]</a>
					<a val="<s:property value="#pc2.id" />" class="delete" href="javascript:;">[删除]</a>
				</td>
			</tr>
			
			<s:iterator value="#pc2.childs" var="pc3">
				
			<tr>
				<td>
					<span style="margin-left: 40px; color: #999999;">
						<s:property value="#pc3.name" />
					</span>
				</td>
				<td><s:property value="#pc3.order" /></td>
				<td>
					<a href="pcAction_initSaveProductCategoryUi.action?id=<s:property value="#pc3.id" />&opType=view">[查看]</a>
					<a href="pcAction_initSaveProductCategoryUi.action?id=<s:property value="#pc3.id" />&opType=update">[编辑]</a>
					<a val="<s:property value="#pc3.id" />" class="delete" href="javascript:;">[删除]</a>
				</td>
			</tr>
				
			</s:iterator>
				
			</s:iterator>
				
			</s:iterator>
			
		</tbody>
	</table>
</body>
</html>