<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>广告列表 - YJW-SHOP</title>
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
		<a href="../index.html">首页</a> » 广告列表
		<span>(共 <span id="pageTotal">
			<s:property value="adPage.totalCount" />
		</span> 条记录) </span>
	</div>
	<form id="listForm" method="post" action="adAction_getAdvertisementsByCondition.action">
<!-- ********************************** 工具栏. ************************************** -->
		<div class="bar">
			<a class="iconButton" href="adAction_initSaveAdvertisementUi.action?opType=add">
				<span class="addIcon"></span> 添加
			</a>
			<div class="buttonWrap">
				<a id="deleteButton" class="iconButton disabled" href="javascript:;" 
						val="adAction_deleteAdvertisementsByIds.action">
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
							<li><a val="20" class="current" href="javascript:;">20</a></li>
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
						<li><a val="title" href="javascript:;">标题</a></li>
					</ul>
				</div>
			</div>
		</div>
<!-- ********************************** 品牌信息列表. ************************************** -->
		<table id="listTable" class="list">
			<tbody>
				<tr>
					<th class="check"><input id="selectAll" type="checkbox" /></th>
					<th><a class="sort" name="title" href="javascript:;">标题</a></th>
					<th><a class="sort" name="position" href="javascript:;">广告位</a></th>
					<th><a class="sort " name="type" href="javascript:;">类型</a></th>
					<th><a class="sort " name="memo" href="javascript:;">备注</a></th>
					<th><a class="sort" name="createTime" href="javascript:;">创建日期</a></th>
					<th><span>操作</span></th>
				</tr>
				
				<s:set var="positionList" value="{'首页幻灯片切换广告','首页左栏咨询广告','首页底部图片列表广告','商品列表页左栏底部广告','商品详细页左栏底部广告'}"></s:set>
				<s:set var="typeList" value="{'文本-内容','图片-内容','文本-链接','图片-链接'}"></s:set>
				
				<s:iterator value="adPage.list" var="ad">
				<tr>
					<td><input type="checkbox" value="<s:property value="#ad.id" />" name="ids" /></td>
					<td><s:property value="#ad.title" /></td>
					<td><s:property value="#positionList[#ad.position]" /></td>
					<td><s:property value="#typeList[#ad.type]" /></td>
					<td><s:property value="#ad.memo" /></td>
					<td>
						<span title="<s:date name="#ad.createTime" format="yyyy-MM-dd HH:mm:ss"/>">
							<s:date name="#ad.createTime" format="yyyy-MM-dd"/>
						</span>
					</td>
					<td>
						<a href="adAction_initSaveAdvertisementUi.action?id=<s:property value="#ad.id" />&opType=update">[编辑]</a>
						<a href="adAction_initSaveAdvertisementUi.action?id=<s:property value="#ad.id" />&opType=view">[查看]</a>
					</td>
				</tr>
				</s:iterator>
				
			</tbody>
		</table>
<!-- ********************************** 分页. ************************************** -->
		<div class="pagination">
			<!-- 首页和上一页 -->
			<s:if test="adPage.pageNumber<=1">
				<span class="firstPage"> </span>
				<span class="previousPage"> </span>
			</s:if>
			<s:else>
				<a class="firstPage" href="javascript: $.pageSkip(1);"> </a>
				<a class="previousPage" href="javascript: $.pageSkip(<s:property value="adPage.pageNumber-1" />);"> </a>
			</s:else>
			
			<!-- 前两页 -->
			<s:if test="adPage.pageNumber-3>0">
				<span class="pageBreak">...</span>
			</s:if>
			<s:if test="adPage.pageNumber-2>0">
				<a href="javascript: $.pageSkip(<s:property value="adPage.pageNumber-2" />);">
					<s:property value="adPage.pageNumber-2" />
				</a>
			</s:if>
			<s:if test="adPage.pageNumber-1>0">
				<a href="javascript: $.pageSkip(<s:property value="adPage.pageNumber-1" />);">
					<s:property value="adPage.pageNumber-1" />
				</a>
			</s:if>
			
			<!-- 当前页 -->
			<span class="currentPage">
				<s:property value="adPage.pageNumber" />
			</span>
			
			<!-- 后两页 -->
			<s:if test="adPage.pageNumber+1<=adPage.totalPage">
				<a href="javascript: $.pageSkip(<s:property value="adPage.pageNumber+1" />);">
					<s:property value="adPage.pageNumber+1" />
				</a>
			</s:if>
			<s:if test="adPage.pageNumber+2<=adPage.totalPage">
				<a href="javascript: $.pageSkip(<s:property value="adPage.pageNumber+2" />);">
					<s:property value="adPage.pageNumber+2" />
				</a>
			</s:if>
			<s:if test="adPage.pageNumber+3<=adPage.totalPage">
				<span class="pageBreak">...</span>
			</s:if>
			
			<!-- 下一页和尾页 -->
			<s:if test="adPage.pageNumber>=adPage.totalPage">
				<span class="nextPage"> </span>
				<span class="lastPage"> </span>
			</s:if>
			<s:else>
				<a class="nextPage" href="javascript: $.pageSkip(<s:property value="adPage.pageNumber+1" />);"> </a>
				<a class="lastPage" href="javascript: $.pageSkip(<s:property value="adPage.totalPage" />);"> </a>
			</s:else>

			<span class="pageSkip"> 共<s:property value="adPage.totalPage" />页 到第 
				<input id="pageNumber" onpaste="return false;" maxlength="9" 
						value="<s:property value="adPage.pageNumber" />" name="pageNumber" /> 页 
				<button type="submit"> </button>
			</span>
		</div>
<!-- ********************************** 隐藏域. ************************************** -->
		<input id="pageSize" name="pageSize" type="hidden" value="<s:property value="adPage.pageSize" />" />
		<input id="searchProperty" name="searchProperty" type="hidden" value="<s:property value="echoParam['searchProperty']" />" />
		<input id="orderProperty" name="orderProperty" type="hidden" value="<s:property value="echoParam['orderProperty']" />" />
		<input id="orderDirection" name="orderDirection" type="hidden" value="<s:property value="echoParam['orderDirection']" />" />
	</form>
</body>
</html>

