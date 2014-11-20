<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>品牌列表 - YJW-SHOP</title>
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
		<a href="../index.html">首页</a> » 品牌列表
		<span>(共 <span id="pageTotal">
			<s:property value="brandPage.totalCount" />
		</span> 条记录) </span>
	</div>
	<form id="listForm" method="post" action="brandAction_getBrandsByCondition.action">
<!-- ********************************** 工具栏. ************************************** -->
		<div class="bar">
			<a class="iconButton" href="brandAction_initSaveBrandUi.action?opType=add">
				<span class="addIcon"></span> 添加
			</a>
			<div class="buttonWrap">
				<a id="deleteButton" class="iconButton disabled" href="javascript:;" 
						val="brandAction_deleteBrandsByIds.action">
					<span class="deleteIcon"></span> 删除
				</a>
				<a id="refreshButton" class="iconButton" href="javascript:;">
					<span class="refreshIcon"></span> 刷新
				</a>
				<div class="menuWrap">
					<a id="pageSizeSelect" class="button" href="javascript:;">
						每页显示 <span class="arrow"></span>
					</a>
					<div class="popupMenu" style="left: 217px; top: 59px; display: none;">
						<ul id="pageSizeOption">
							<li><a val="10" href="javascript:;">10</a></li>
							<li><a class="current" val="20" href="javascript:;">20</a></li>
							<li><a val="30" href="javascript:;">30</a></li>
							<li><a val="40" href="javascript:;">40</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="menuWrap">
				<div class="search">
					<span id="searchPropertySelect" class="arrow">&nbsp;</span>
					<input id="searchValue"  name="searchValue" type="text" maxlength="200" 
							value="<s:property value="echoParam['searchValue']" default="" />" />
					<button type="submit"> </button>
				</div>
				<div class="popupMenu">
					<ul id="searchPropertyOption">
						<li><a val="all" href="javascript:;">全部</a></li>
						<li><a val="name" href="javascript:;">名称</a></li>
						<li><a val="url" href="javascript:;">网址</a></li>
					</ul>
				</div>
			</div>
		</div>
<!-- ********************************** 品牌信息列表. ************************************** -->
		<table id="listTable" class="list">
			<tbody>
				<tr>
					<th class="check"><input id="selectAll" type="checkbox" /></th>
					<th><a class="sort" name="name" href="javascript:;">名称</a></th>
					<th><a class="sort" name="logo" href="javascript:;">logo</a></th>
					<th><a class="sort " name="url" href="javascript:;">网址</a></th>
					<th><a class="sort" name="order" href="javascript:;">排序</a></th>
					<th><span>操作</span></th>
				</tr>
				
				<s:iterator value="brandPage.list" var="brand">
				<tr>
					<td><input type="checkbox" value="<s:property value="#brand.id" />" name="ids" /></td>
					<td><s:property value="#brand.name" /></td>
					<td>
						<s:if test="#brand.type==1">
						<a target="_blank" href="${pageContext.request.contextPath }<s:property value="#brand.logo" />">查看</a>
						</s:if>
						<s:else>&nbsp;&nbsp;-&nbsp;&nbsp;</s:else>
					</td>
					<td><a target="_blank" href="<s:property value="#brand.url" />"><s:property value="#brand.url" /></a></td>
					<td><s:property value="#brand.order" /></td>
					<td>
						<a href="brandAction_initSaveBrandUi.action?id=<s:property value="#brand.id" />&opType=update">[编辑]</a>
						<a href="brandAction_initSaveBrandUi.action?id=<s:property value="#brand.id" />&opType=view">[查看]</a>
					</td>
				</tr>
				</s:iterator>
				
			</tbody>
		</table>
<!-- ********************************** 分页. ************************************** -->
		<div class="pagination">
			<!-- 首页和上一页 -->
			<s:if test="brandPage.pageNumber<=1">
				<span class="firstPage"> </span>
				<span class="previousPage"> </span>
			</s:if>
			<s:else>
				<a class="firstPage" href="javascript: $.pageSkip(1);"> </a>
				<a class="previousPage" href="javascript: $.pageSkip(<s:property value="brandPage.pageNumber-1" />);"> </a>
			</s:else>
			
			<!-- 前两页 -->
			<s:if test="brandPage.pageNumber-3>0">
				<span class="pageBreak">...</span>
			</s:if>
			<s:if test="brandPage.pageNumber-2>0">
				<a href="javascript: $.pageSkip(<s:property value="brandPage.pageNumber-2" />);">
					<s:property value="brandPage.pageNumber-2" />
				</a>
			</s:if>
			<s:if test="brandPage.pageNumber-1>0">
				<a href="javascript: $.pageSkip(<s:property value="brandPage.pageNumber-1" />);">
					<s:property value="brandPage.pageNumber-1" />
				</a>
			</s:if>
			
			<!-- 当前页 -->
			<span class="currentPage">
				<s:property value="brandPage.pageNumber" />
			</span>
			
			<!-- 后两页 -->
			<s:if test="brandPage.pageNumber+1<=brandPage.totalPage">
				<a href="javascript: $.pageSkip(<s:property value="brandPage.pageNumber+1" />);">
					<s:property value="brandPage.pageNumber+1" />
				</a>
			</s:if>
			<s:if test="brandPage.pageNumber+2<=brandPage.totalPage">
				<a href="javascript: $.pageSkip(<s:property value="brandPage.pageNumber+2" />);">
					<s:property value="brandPage.pageNumber+2" />
				</a>
			</s:if>
			<s:if test="brandPage.pageNumber+3<=brandPage.totalPage">
				<span class="pageBreak">...</span>
			</s:if>
			
			<!-- 下一页和尾页 -->
			<s:if test="brandPage.pageNumber>=brandPage.totalPage">
				<span class="nextPage"> </span>
				<span class="lastPage"> </span>
			</s:if>
			<s:else>
				<a class="nextPage" href="javascript: $.pageSkip(<s:property value="brandPage.pageNumber+1" />);"> </a>
				<a class="lastPage" href="javascript: $.pageSkip(<s:property value="brandPage.totalPage" />);"> </a>
			</s:else>

			<span class="pageSkip"> 共<s:property value="brandPage.totalPage" />页 到第 
				<input id="pageNumber" onpaste="return false;" maxlength="9" 
						value="<s:property value="brandPage.pageNumber" />" name="pageNumber" /> 页 
				<button type="submit"> </button>
			</span>
		</div>
<!-- ********************************** 隐藏域. ************************************** -->
		<input id="pageSize" name="pageSize" type="hidden" value="<s:property value="brandPage.pageSize" />" />
		<input id="searchProperty" name="searchProperty" type="hidden" value="<s:property value="echoParam['searchProperty']" />" />
		<input id="orderProperty" name="orderProperty" type="hidden" value="<s:property value="echoParam['orderProperty']" />" />
		<input id="orderDirection" name="orderDirection" type="hidden" value="<s:property value="echoParam['orderDirection']" />" />
	</form>
</body>
</html>

