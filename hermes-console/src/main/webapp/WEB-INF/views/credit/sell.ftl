   <div class="panel panel-primary">
        <div class="panel-heading">发售债权</div>
          <div class="panel-body">
                <form class="form-horizontal" id="creditorSellForm" >
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label"><span class="color_red">*</span>产品名称</label>
                    <div class="col-xs-2">
                      <input type="hidden" name="id" class="form-control"  value="${creditInfo.id!'0'}">
                      <input type="text" name="purpose" class="form-control" id="purpose" placeholder="系统默认为借款用途，用户可修改" value="${creditInfo.purpose!''}">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label"><span class="color_red">*</span>招标结束日期</label>
                    <div class="col-xs-2">
                        <input id="beginDate" name="bidEndTimeStr" value="" class="form-control" type="text">
                    </div>
                     <div class="col-xs-5" id="error_msg" style="color:red;"></div>
                  </div>
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label"><span class="color_red">*</span>发售价格</label>
                    <div class="col-xs-2">
                      <input type="text" name="sellAmount" readonly="true" class="form-control" id="sellAmount"  value="">
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label"><span class="color_red">*</span>年利率</label>
                    <div class="col-xs-2">
                      <input type="hidden" name="rate" class="form-control" id="rate"  value="${(creditInfo.rate)!'0'}">
                      <input type="text" disabled="true" class="form-control"   value="${(creditInfo.ratePercent)!''}">
                    </div>
                    <div class="col-xs-2"><span class="vlight">%</span></div>
                  </div>

                 
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label"><span class="color_red">*</span>剩余期限</label>
                    <div class="col-xs-2">
                      <input type="text"  readonly="true"  name="deadLine" class="form-control" id="deadLine" placeholder="系统计算，=债权到期日-招标结束日期">
                      <input type="hidden"   name="termNum"  value="${remainPeriod!'0'}">
                    </div>
                    <div class="col-sm-1"><span class="vlight">天</span></div>
                  </div>

                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">担保方式</label>
                    <div class="col-xs-2">
                      <input type="text" name="assureType" class="form-control" id="assureType" placeholder="">
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">资金用途</label>
                    <div class="col-xs-2">
                      <input type="text" name="amountAim" class="form-control" id="amountAim" >
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">产品介绍</label>
                    <div class="col-xs-2">
                      <input type="text" name="productDesc" class="form-control" id="productDesc">
                    </div>
                  </div>

                </form>   
          </div>
     
            <div class="row">
                <div class="col-sm-offset-2 col-sm-4">
                    <div class="col-xs-2"><button type="submit" id="commitSell" class="btn btn-primary btn-block">确定</button></div>
                    <div class="col-xs-2"><button type="submit" id="cancel" class="btn btn-default btn-block"  data-dismiss="modal">取消</button></div>
                </div>
         
        </div>
      </div>
    </div> 

<script type="text/javascript">
<!--
jQuery(function($) {
	$("#beginDate").datepicker();
	$("#beginDate").change(function(){
	   var _id = '${creditInfo.id!'0'}';
	   $.ajax({
				data: "bidEndTime="+$("#beginDate").val()+"&id="+_id,
			    url: "${app}/credit/checkEndBidTime",
			    type: "POST",
			    dataType: 'json',
				success: function(data){  
                    if(data.code == '00'){
                       calcuRemainAmount();
                    }else{
                        $("#error_msg").html(data.msg);
                    }				
				}
	   });	
	});
});

function calcuRemainAmount(){
  var _id = '${creditInfo.id!'0'}';
  var inputBidEndTime = $("#beginDate").val();
  var deadTime = '${creditInfo.deadTimeFormate}';
	   $.ajax({
				data: "bidEndTime="+$("#beginDate").val()+"&id="+_id,
			    url: "${app}/credit/calcuRemainAmount",
			    type: "POST",
			    dataType: 'json',
				success: function(data){  
                    if(data.code == '00'){
                       $("#sellAmount").val(data.val);
                       $("#deadLine").val(dateDiff(deadTime,inputBidEndTime));
                        $("#error_msg").html("");
                    }					
				}
	   });	
}

	 $("#commitSell").on("click",function(){
	        if($("#error_msg").val().length == 0 && $("#deadLine").val().length > 0 ){
	            $.link.html(null, {
				url: '${app}/credit/sell',
				data: $("#creditorSellForm").serialize(),
				target: 'main'
			   });
	        }else{
			    $("#error_msg").html("请选择有效的招标结束日期");
	            return false;
	        }
	 });
 
   //计算天数差  
   function  dateDiff(sDate1,  sDate2){
       var  aDate,  oDate1,  oDate2,  iDays  
       aDate  =  sDate1.split("-")  
       oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);
       aDate  =  sDate2.split("-");
       oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);
       iDays  =  parseInt(Math.abs(oDate1  -  oDate2)/1000/60/60/24);
       return  iDays  
   }  
</script>


