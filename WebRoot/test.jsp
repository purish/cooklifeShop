<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>YjwShopV1</title>
		
<script type="text/javascript" src="admin/js/jquery/jquery-1.7.2.js"></script>
		
<script type="text/javascript">

	function textChange(){
		alert(this.value);
	}
		
	function pageInit(){
		var myInputObj = document.getElementById("mytext");
		if(navigator.userAgent.indexOf("MSIE")>0){
			myInputObj.attachEvent("onpropertychange", textChange);
		} else{
			myInputObj.oninput = textChange;
		}
	}
</script>		
	</head>

	<body onload="pageInit();">
		YjwShopV1 <br />
		<input type="text" name="mytext" id="mytext" />
	</body>
</html>
