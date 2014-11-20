<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>参数列表 - YJW-SHOP</title>
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
		<a href="../index.html">首页</a> » 参数列表
		<span>(共<span id="pageTotal">
			<s:property value="paramPage.totalCount" />
		</span>条记录)</span>
	</div>
	<form id="listForm" method="post" action="paramAction_getParametersByCondition.action">
<!-- ********************************** 工具栏. ************************************** -->
		<div class="bar">
			<a class="iconButton" href="paramAction_initSaveParameterUi.action?opType=add">
				<span class="addIcon">&nbsp;</span>添加
			</a>
			<div class="buttonWrap">
				<a class="iconButton disabled" id="deleteButton" href="javascript:;"
						val="paramAction_deleteParameterByIds.action">
					<span class="deleteIcon">&nbsp;</span>删除
				</a>
				<a class="iconButton" id="refreshButton" href="javascript:;">
					<span class="refreshIcon">&nbsp;</span>刷新
				</a>
				<div class="menuWrap">
					<a class="button" id="pageSizeSelect" href="javascript:;">
						每页显示<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu" style="left: 217px; top: 59px; display: none;">
						<ul id="pageSizeOption">
							<li><a val="10" href="javascript:;">10</a></li>
							<li><a val="20" class="current" href="javascript:;">20</a></li>
							<li><a val="30" href="javascript:;">30</a></li>
							<li><a val="40" href="javascript:;">40</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="menuWrap">
				<div class="search">
					<span class="arrow" id="searchPropertySelect">&nbsp;</span>
					<input type="text" maxlength="200" name="searchValue" id="searchValue" 
							value="<s:property value="echoParam['searchValue']" />" />
					<button type="submit">&nbsp;</button>
				</div>
				<div class="popupMenu">
					<ul id="searchPropertyOption">
						<li><a val="all" href="javascript:;">全部</a></li>
						<li><a val="name" href="javascript:;">名称</a></li>
					</ul>
				</div>
			</div>
		</div>
		
<!-- ********************************** 参数信息列表. ************************************** -->
		<table class="list" id="listTable">
			<tbody>
				<tr>
					<th class="check"><input type="checkbox" id="selectAll"></th>
					<th><a name="name" class="sort" href="javascript:;">名称</a></th>
					<th><a name="pc.name" class="sort" href="javascript:;">绑定分类</a></th>
					<th><span>参数</span></th>
					<th><a name="order" class="sort" href="javascript:;">排序</a></th>
					<th><span>操作</span></th>
				</tr>
				
				<s:iterator value="paramPage.list" var="param">
				<tr>
					<td><input type="checkbox" value="<s:property value="#param.id" />" name="ids"></td>
					<td><s:property value="#param.name" /></td>
					<td><s:property value="#param.pc.name" /></td>
					<td>
						<s:iterator value="#param.pitems" var="pitem">
							<s:property value="#pitem.name" />&nbsp;&nbsp;
						</s:iterator>
					</td>
					<td><s:property value="#param.order" /></td>
					<td>
						<a href="paramAction_initSaveParameterUi.action?id=<s:property value="#param.id" />&opType=update">[编辑]</a>
						<a href="paramAction_initSaveParameterUi.action?id=<s:property value="#param.id" />&opType=view">[查看]</a>
					</td>
				</tr>
				</s:iterator>
				
			</tbody>
		</table>

<!-- ********************************** 分页. ************************************** -->
		<div class="pagination">
			<!-- 首页和上一页 -->
			<s:if test="paramPage.pageNumber<=1">
				<span class="firstPage"> </span>
				<span class="previousPage"> </span>
			</s:if>
			<s:else>
				<a class="firstPage" href="javascript: $.pageSkip(1);"> </a>
				<a class="previousPage" href="javascript: $.pageSkip(<s:property value="paramPage.pageNumber-1" />);"> </a>
			</s:else>
			
			<!-- 前两页 -->
			<s:if test="paramPage.pageNumber-3>0">
				<span class="pageBreak">...</span>
			</s:if>
			<s:if test="paramPage.pageNumber-2>0">
				<a href="javascript: $.pageSkip(<s:property value="paramPage.pageNumber-2" />);">
					<s:property value="paramPage.pageNumber-2" />
				</a>
			</s:if>
			<s:if test="paramPage.pageNumber-1>0">
				<a href="javascript: $.pageSkip(<s:property value="paramPage.pageNumber-1" />);">
					<s:property value="paramPage.pageNumber-1" />
				</a>
			</s:if>
			
			<!-- 当前页 -->
			<span class="currentPage">
				<s:property value="paramPage.pageNumber" />
			</span>
			
			<!-- 后两页 -->
			<s:if test="paramPage.pageNumber+1<=paramPage.totalPage">
				<a href="javascript: $.pageSkip(<s:property value="paramPage.pageNumber+1" />);">
					<s:property value="paramPage.pageNumber+1" />
				</a>
			</s:if>
			<s:if test="paramPage.pageNumber+2<=paramPage.totalPage">
				<a href="javascript: $.pageSkip(<s:property value="paramPage.pageNumber+2" />);">
					<s:property value="paramPage.pageNumber+2" />
				</a>
			</s:if>
			<s:if test="paramPage.pageNumber+3<=paramPage.totalPage">
				<span class="pageBreak">...</span>
			</s:if>
			
			<!-- 下一页和尾页 -->
			<s:if test="paramPage.pageNumber>=paramPage.totalPage">
				<span class="nextPage"> </span>
				<span class="lastPage"> </span>
			</s:if>
			<s:else>
				<a class="nextPage" href="javascript: $.pageSkip(<s:property value="paramPage.pageNumber+1" />);"> </a>
				<a class="lastPage" href="javascript: $.pageSkip(<s:property value="paramPage.totalPage" />);"> </a>
			</s:else>

			<span class="pageSkip"> 共<s:property value="paramPage.totalPage" />页 到第 
				<input id="pageNumber" onpaste="return false;" maxlength="9" 
						value="<s:property value="paramPage.pageNumber" />" name="pageNumber" /> 页 
				<button type="submit"> </button>
			</span>
		</div>

<!-- ********************************** 隐藏域. ************************************** -->
		<input type="hidden" value="<s:property value="paramPage.pageSize" />" name="pageSize" id="pageSize" />
		<input type="hidden" value="<s:property value="echoParam['searchProperty']" />" name="searchProperty" id="searchProperty" />
		<input type="hidden" value="<s:property value="echoParam['orderProperty']" />" name="orderProperty" id="orderProperty" />
		<input type="hidden" value="<s:property value="echoParam['orderDirection']" />" name="orderDirection" id="orderDirection" />
	</form>
</body>
</html>