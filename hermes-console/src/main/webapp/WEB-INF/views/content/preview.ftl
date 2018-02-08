<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><@config key="app.title" /></title>
<style type="text/css">

.content_template{width: 750px;
border: 2px solid #e2e2e2;
height: auto;
background: #FFF;
-moz-border-radius: 5px;
-webkit-border-radius: 5px;
border-radius: 5px;}
.content_template h2.title{ text-align: center; padding: 20px 0; color: #323232; font-size: 22px; border-bottom: 1px dashed #eeeeee; font-weight: 500;}
.content_index .fl_right{ float: right;}
.content_index p{ margin: 20px;}
.tu_images{ text-align: center; margin: 20px 0;}
*{
-webkit-box-sizing: content-box;
-moz-box-sizing: content-box;
box-sizing: content-box;
}
</style>
</head>

<body>

<div class="sub_main" style="margin-top:133px;">
	<div class="account_center">
		<div class="account_nav_left">
			<div class="titleuser"></div>
			</div>
			<div class="leftbottom"></div>
		</div>
		<div class="account_content_right">
			<div class="content_template">
				<h2 class="title">${(context.articleTitle)!}</h2>	
				<div class="content_index">
					<div class="clearfix"><p class="fl_right ">【发布时间：${(context.updateTime)!}】</p>	</div>
					<p>${(context.content)!}</p>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</div>

</body>
</html>