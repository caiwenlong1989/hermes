<div class="block">
	<h3 class="clearfix">
		<span><@messages key="account.fund.charge.caption" /></span>
		<span id="balance" class="sub pull-right"><@messages key="account.fund.withdraw.balance" />${account.balance?string("#,##0.00")} <@messages key="common.unit.cny" /></span>
	</h3>
	<div class="body">
		<form id="chargeForm" method="post" action="#" class="form-horizontal">
			<div class="form-group">
				<label for="channel" class="col-xs-2 u-col control-label"><@messages key="account.fund.charge.channel" /></label>
				<div class="col-xs-10 u-col">
					<input id="channel" name="channel" type="hidden">
					<p id="selectedChannel" class="form-control-static" style="display:none;"><img alt="" src="" class="img-channel"></p>
					<p id="selectChannel" class="form-control-static"><a href="#"><@messages key="account.fund.charge.channel.select" /> <i class="fa fa-angle-double-right"></i></a></p>
				</div>
			</div>
			<div class="form-group">
				<label for="amount" class="col-xs-2 u-col control-label"><@messages key="account.fund.charge.amount" /></label>
				<div class="col-xs-3 u-col">
					<input id="amount" name="amount" type="number" placeholder="<@messages key="account.fund.charge.amount.hint" />" class="form-control">
				</div>
				<div class="col-xs-1 u-col">
					<p class="form-control-static"><@messages key="common.unit.cny" /></p>
				</div>
				<p class="form-control-static" id="err_msg"></p>
			</div>
			<div class="form-group">
				<label for="fee" class="col-xs-2 u-col control-label"><@messages key="account.fund.charge.fee" /></label>
				<div class="col-xs-3 u-col">
					<p class="form-control-static"><span id="fee">0.00&nbsp;<@messages key="common.unit.cny" /></span> <a id="info" href="#"><i class="fa fa-info-circle"></i></a></p>
				</div>
			</div>
			<div class="form-group">
				<label for="sum" class="col-xs-2 u-col control-label"><@messages key="account.fund.charge.sum" /></label>
				<div class="col-xs-3 u-col">
					<p id="sum" class="form-control-static">0.00&nbsp;<@messages key="common.unit.cny" /></p>
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-2 col-xs-offset-2 u-col">
					<button id="addChargeBtn" type="button" disabled="disabled" class="btn btn-default btn-block"><@messages key="common.op.charge" /></button>
				</div>
			</div>
		</form>
	</div>
</div>
<form id="rform" action="${app}/account/charge/chargeResult" method="post">
	<input id="message" name="message" type="hidden"></input>	
	<input id="type" name="type" type="hidden"></input>	
	<input id="pAmount" name="pAmount" type="hidden"></input>
	<input id="naviFlag" name="naviFlag" type="hidden"></input>	
</form>

<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	// 格式化数字
	$('#balance, #fee, #sum').formatNumber();
	
	// 绑定选择支付方式事件
	$('#selectChannel').on('click', function() {
		var _elem = $(this);
		$.ajax('${app}/account/channels', {
			type: 'post',
			dataType: 'html',
			timeout: 5000,
			success: function(data, textStatus, xhr) {
				_elem.hide();
				$(data).hide().appendTo(_elem.parent()).show('fast');
			}
		});
	});
	
	$("#amount").on('focus',function() {
	 	$('#addChargeBtn').removeAttr("disabled");
		$("#err_msg").html('');
	});
	
	// 计算充值费用
	$('#amount').on('blur', function() {
		if(!((/^(([1-9]\d*)|\d)(\.\d{1,2})?$/)).test($("#amount").val().toString())) {
		  $('#addChargeBtn').attr("disabled",true);
		  $("#err_msg").css('color','red').html('请输入正确的金额格式!');
		  return;
		}
		  
		// 初始化
		var _elem = $(this),
			_fee = $('#fee'),
			_sum = $('#sum');
		
		// 判断输入是否有效
		if (_elem.val() === '') return;
		
		// 加载图标
		_fee.html('<i class="fa fa-spinner fa-spin"></i>');
		_sum.html('<i class="fa fa-spinner fa-spin"></i>');
		
		// 异步加载
		$.ajax('${app}/account/charge/calc', {
			data: { amount: _elem.val() },
			type: 'post',
			dataType: 'json',
			timeout: 5000,
			success: function(data, textStatus, xhr) {
				if (data.typeName === 'success') {
					_fee.html(data.messages[0] + '&nbsp;<@messages key="common.unit.cny" />').formatNumber();
					_sum.html(data.messages[1] + '&nbsp;<@messages key="common.unit.cny" />').formatNumber();
				} else {
					_fee.html('<span class="failure"><i class="fa fa-info-circle"></i> ' + data.firstMessage + '</span>');
					_sum.html('<span class="failure"><i class="fa fa-info-circle"></i> ' + data.firstMessage + '</span>');
				}
			}
		});
	});
	
	// 绑定添加充值方法
	$('#addChargeBtn').on('click', function() {
		if(!((/^(([1-9]\d*)|\d)(\.\d{1,2})?$/)).test($("#amount").val().toString())) {
		  $('#addChargeBtn').attr("disabled",true);
		  $("#err_msg").css('color','red').html('请输入正确的金额格式!');
		  return;
		} 
		
		$('#addChargeBtn').attr("disabled",true);
		$.ajax('${app}/account/charge/zjCharge', {
			data: $('#chargeForm').serialize(),
			type: 'post',
			dataType: 'json',
			timeout: 50000,
			success: function(data) {
				$("#message").val(data.messages[0]);
     			$("#type").val(data.type);
     			$("#pAmount").val(data.messages[1]);
     			$("#naviFlag").val(data.messages[2]);
     			$("#rform").submit();
			},error: function(data) {
				$("#message").val(data.messages[0]); 
     			$("#type").val(data.type);
     			$("#pAmount").val(data.messages[1]);
     			$("#naviFlag").val(data.messages[2]);
     			$("#rform").submit();
			}
		});
	});
	
	// 费用提示处理
	$('#info').popover({
		html: true,
		placement: 'right',
		trigger: 'hover', 
		content: '<span style="white-space:nowrap;"><@config key="fee.charge.desc" /></span>'
	});
});
//-->
</script>
