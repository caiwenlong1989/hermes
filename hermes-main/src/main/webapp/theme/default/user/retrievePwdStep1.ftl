<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/others.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<script type="text/javascript" src="${app.theme}/public/other/javascripts/jquery-1.10.2.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mPlugin.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mCommon.js" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
</head>
<body>
<div class="_container" >
<#include "/header.ftl" />
<!-- middle start-->
<div class="m_con m_fp m_fp3" id="retrieve">
	<h3>找回密码</h3>
	<form id="mailForm" name="mailForm" action="${app}/userIndex/sendResetPwdEmail" method="post">
		<div class="m_fp_box">
			<div class="m_fp_s1">
				注册邮箱地址：
				<input id="email" name="email" type="text"  class="mv_email"/>
				<span class="mv_msg"></span>
				<input  name="token" type="hidden" value="${token!''}"  readonly="readonly"/>
			</div>
			<img src="${app.theme}/public/other/images/m/icon1/ico6.png" class="m_fp_box_bg1" />
		</div>
	</form>
	<a href="javascript:document.mailForm.submit();"  class="m_btn1 m_bg1 mv_submit" >提交</a>
</div>
 <div class="push"><!-- not put anything here --></div>
</div>
<!-- foot start-->
<#include "/footer.ftl" />
</body>
</html>