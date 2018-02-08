<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="renderer" content="webkit">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/others.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<script type="text/javascript" src="${app.theme}/public/other/javascripts/jquery-1.10.2.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mPlugin.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mCommon.js" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#submit").click(function(){
		if(checkEqual()){
			$.ajax({
				data: $("#pwdForm").serialize(),
			    url: "${app}/userIndex/resetPwd",
			    type: "POST",
			    dataType: 'html',
			    timeout: 10000,
			    error: function(){
			     	 		
			    },
				success: function(data){
				
				}
			});
		}
	});
	
});
function checkEqual(){
	var sign=$("#signPassword").val();
	var conf=$("#configPassword").val();
	if(sign==conf){
		return true;
	}else{
		return false;
	}
}
 
</script>
</head>

<body>
<div class="_container">
<#include "/header.ftl" />
<!-- middle start-->
<div class="m_con m_fp m_fp3">
	<h3>重置密码</h3>
	<div class="m_fp_box">
		<form id="pwdForm" name="pwdForm" action="${app}/userIndex/resetPwd" method="post">
			<input name="id" type="hidden" value="${uuid}"/>
			<div class="m_fp_s1">
				<div class="m_item">
					&nbsp;&nbsp;&nbsp;新密码：
					<input id="signPassword" name="signPassword" type="password" autocomplete = "off"  class="mv_pwd"/> 
					<span class="mv_msg"></span>
				</div>
				<div class="m_item">
					确认密码：
					<input id="configPassword" name="configPassword" type="password" autocomplete = "off"  class="mv_pwdagain" />
					<span class="mv_msg"></span>
				</div>
			</div>
			<img src="${app.theme}/public/other/images/m/icon1/ico7.png" class="m_fp_box_bg2" />
		</form>
	</div>
	<a href="javascript:document.pwdForm.submit();"  class="m_btn1 m_bg1 mv_submit">提交</a>
</div>
<div class="push"><!-- not put anything here --></div>
</div>
<!-- foot start-->
<#include "/footer.ftl" />
<div class="theme-popover">
     <div class="theme-poptit">
          <a href="javascript:;" title="关闭" class="close">×</a>
          <h3>提示：</h3>
     </div>
     <div class="theme-popbod dform">
               建议您使用IE8以上版本IE浏览器或其它类型的浏览器
     </div>
</div>
<div class="theme-popover-mask"></div>
<script type="text/javascript" charset="utf-8">
  jQuery(function($){
     if (navigator.userAgent.indexOf("MSIE") > 0) { //IE
	   		if(/msie 7.0/.test(navigator.userAgent.toLowerCase()) || /msie 8.0/.test(navigator.userAgent.toLowerCase())) {
	        	$('.theme-popover-mask').fadeIn(100);
   			    $('.theme-popover').slideDown(200);
	        }
	}
    //关闭提示层
	$('.theme-poptit .close').click(function(){
		$('.theme-popover-mask').fadeOut(100);
		$('.theme-popover').slideUp(200);
	})
  });
</script>
</body>
</html>