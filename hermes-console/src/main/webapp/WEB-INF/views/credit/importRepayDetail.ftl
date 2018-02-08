<div class="panel panel-primary">
        <div class="panel-heading">查看明细</div>
    	<div class="panel-body">
            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
              <li role="presentation" class="active"><a href="#basicInfo" role="tab" data-toggle="tab">基本信息</a></li>
              <li role="presentation"><a href="#planDetail" role="tab" data-toggle="tab">还款计划及回款明细</a></li>
            </ul>
            <!-- Tab panes -->
            <div class="tab-content">
              <div role="tabpanel" class="tab-pane active" id="basicInfo">
                <h3>借款人基本信息</h3>
                <div class="row">
                    <div class="col-xs-4">
                        <div class="spanblock"><span class="wd_100">借款人：</span>${(creditInfo.borrower)!''}</div>
                        <div class="spanblock"><span class="wd_100">行业：</span>${(creditInfo.workType)!''}</div>
                    </div>
                    <div class="col-xs-4">
                        <div class="spanblock"><span class="wd_100">证件类型：</span>${(creditInfo.certType)!''}</div>
                        <div class="spanblock"><span class="wd_100">省份：</span>${(creditInfo.province)!''}</div>
                    </div>
                    <div class="col-xs-4">
                        <div class="spanblock"><span class="wd_100">证件号码：</span>${(creditInfo.certificateNo)!''}</div>
                        <div class="spanblock"><span class="wd_100">城市：</span>${(creditInfo.city)!''}</div>
                    </div>
                </div>

                <hr class="divder">

                <h3>债权人基本信息</h3>
                <div class="row">
                    <div class="col-xs-4">
                        <div class="spanblock"><span class="wd_100">债权人编号：</span>${(creditInfo.creditor.creditorNo)!''}</div>
                        <div class="spanblock"><span class="wd_100">联系人：</span>${(creditInfo.creditor.contacter)!''}</div>
                        <div class="spanblock"><span class="wd_100">可用余额：</span>${reMainCash!'0'} 元 </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="spanblock"><span class="wd_100">债权人名称：</span>${(creditInfo.creditor.creditorName)!''}</div>
                        <div class="spanblock"><span class="wd_100">电话：</span>${(creditInfo.creditor.cellphone)!''}</div>
                    </div>
                    <div class="col-xs-4">
                        <div class="spanblock"><span class="wd_100">证件号码：</span>${(creditInfo.creditor.certificateNo)!''}</div>
                        <div class="spanblock"><span class="wd_100">担保方式：</span>${(creditInfo.creditor.assoureType)!''}</div>
                    </div>
                </div>

                <hr class="divder">

                <h3>银行卡信息</h3>
                <div class="row">
                    <div class="col-xs-4">
                        <div class="spanblock"><span class="wd_100">账户名：</span>${(creditInfo.creditor.creditorName)!''}</div>
                        <div class="spanblock"><span class="wd_100">账号：</span>${(creditInfo.creditor.bankAccount)!''}</div>
                    </div>
                    <div class="col-xs-4">
                        <div class="spanblock"><span class="wd_100">银行名称：</span>${(creditInfo.creditor.bankName)!''}</div>
                        <div class="spanblock"><span class="wd_100">开户行：</span>${(creditInfo.creditor.bankBrantch)!''}</div>
                    </div>
                    <!--
                    <div class="col-xs-4">
                        <div class="spanblock"><span class="wd_100">省份：</span>${(creditInfo.creditor.bankProvince)!''}</div>
                        <div class="spanblock"><span class="wd_100">城市：</span>${(creditInfo.creditor.bankCity)!''}</div>
                    </div>-->
                </div>

              </div>
             
              <div role="tabpanel" class="tab-pane" id="planDetail">
                    <div id="" style="display:block; margin-top:20px;">
                    <table id="creditRepayPlanDetail" class="table table-bordered table-hover">
                        <thead>
                            <tr>
                                <th class="align-center">序号</th>
                                <th class="align-center">期数</th>
                                <th class="align-center">计划还款日期</th>
                                <th class="align-center">应还本金</th>
                                <th class="align-center">应还利息</th>
                                <th class="align-center">应还总额</th>
                                <th class="align-center">剩余本金</th>
                                <th class="align-center">状态</th>
                            </tr>
                        </thead>
                        <tbody>
                           <#if repayPlanDetailList??>
                             <#list repayPlanDetailList as l>
                                <tr> 
                                <td class="align-center">${l_index +1}</td>
                                <td class="align-center">${l.period!''}</td>
                                <td class="align-center">${l.repayPlanTime?string('yyyy-MM-dd')}</td> 
                                <td class="align-center">${l.repayPrincipal!''}</td> 
                                <td class="align-center">${l.repayInterest!''}</td> 
                                <td class="align-center">${l.repayAllmount!''}</td>
                                <td class="align-center">${l.remainPrincipal!''}</td>
                                <td class="align-center">${l.statusName!''}</td> 
                            </tr>
                           </#list>
                           <#else>
                                <tr>
									<td colspan="9" class="align-center"><@messages key="common.table.empty" /></td>
								</tr>
                           </#if>
                        </tbody>
                    </table>
                </div>
              </div>
                <div role="tabpanel" class="tab-pane" id="planDetail">
                   <div id="repayResult"></div>
                </div>
            </div> 
        </div>
    </div>





<!-- Modal -->
  
    
<script type="text/javascript" charset="utf-8">
<!--

   jQuery(function($) {
    $('.protocol').click(function(){
		var win = openwindow("${app}/credit/assignProtocol","",1000,800);
	});
    function openwindow(url,name,iWidth,iHeight)
	{
		var url; //转向网页的地址;
		var name; //网页名称，可为空;
		var iWidth; //弹出窗口的宽度;
		var iHeight; //弹出窗口的高度;
		var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
		return window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');
	}     
   });

   $("#creditorRight a").on("click",function(){
		$.link.html(null, {
			url: $(this).attr("data-url"),
			target: 'main'
		});
   }); 
   
   	 $('#creditorCharge').click(function() {
   	    $.link.html(null, {
			url: '${app}/loan/goCharge/'+$(this).data().id,
			target: 'main'
		});
     });
   
   	$('#creditRepayPlanDetail .btn-primary').click(function() {
   	    $.link.html(null, {
			url: '${app}/loan/repayment/'+$(this).data().id,
			target: 'main'
		});
     });
   
 
//-->
</script>