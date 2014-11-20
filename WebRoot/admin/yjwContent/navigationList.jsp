<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>导航列表 - YJW-SHOP</title>
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
</head>

<body>

	<div class="path">
		<a href="../index.html">首页</a> » 导航列表
	</div>
	<form id="listForm" action="navAction_getNavigationsByCondition.action" method="get">
		<div class="bar">
			<a href="yjwContent/navAction_initSaveNavigationUi.action?opType=add" class="iconButton">
				<span class="addIcon">&nbsp;</span>添加
			</a>
			<div class="buttonWrap">
				<a href="javascript:;" id="deleteButton" class="iconButton disabled" 
						val="navAction_deleteNavigationsByIds.action">
					<span class="deleteIcon">&nbsp;</span>删除
				</a>
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>刷新 
				</a>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check"><input type="checkbox" id="selectAll" /></th>
				<th><span>名称</span></th>
				<th><span>位置</span></th>
				<th><span>是否新窗口打开</span></th>
				<th><span>备注</span></th>
				<th><span>排序</span></th>
				<th><span>操作</span></th>
			</tr>
			
			<s:set var="positionList" value="{'顶部导航','底部导航'}"></s:set>
			
			<s:iterator value="navs" var="nav">
			<tr>
				<td><input type="checkbox" name="ids" value="<s:property value="#nav.id" />" /></td>
				<td><s:property value="#nav.name" /></td>
				<td><s:property value="#positionList[#nav.position]" /></td>
				<td><s:property value="#nav.isBlank == true ? '是' : '否'" /></td>
				<td><s:property value="#nav.memo" /></td>
				<td><s:property value="#nav.order" /></td>
				<td>
					<a href="navAction_initSaveNavigationUi.action?id=<s:property value="#nav.id" />&opType=update">[编辑]</a>
					<a href="navAction_initSaveNavigationUi.action?id=<s:property value="#nav.id" />&opType=view">[查看]</a>
				</td>
			</tr>
			</s:iterator>
			
		</table>
	</form>
</body>
</html>
