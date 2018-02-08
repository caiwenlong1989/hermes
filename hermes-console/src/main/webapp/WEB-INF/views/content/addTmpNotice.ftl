 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
 <div class="panel panel-primary">
        <div class="panel-heading">新增临时公告</div>
        <div class="panel-body">
            <div id="data" style="display:block">
          <form class="form-horizontal" role="form" id="editForm" method="post">
             <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>公告标题</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="title"  name="title">
                </div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，汉字限定为8个字</span>
				</div>                
              </div>
          
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>公告内容</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="content"  name="content">
                </div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，汉字限定为8个字</span>
				</div>                
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>显示开始日期</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="startDate" name="startDate">
                </div>
		        <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，请输入正确的日期</span>
				</div>                                                        
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>显示结束日期</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="endDate" name="endDate">
                </div>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，请输入正确的日期</span>
				</div>                                
              </div>
              
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                  <div class="col-xs-3"><button type="button" class="btn btn-primary btn-block" id="addTmpNotice">新增</button></div>
                  <div class="col-xs-3"><button type="button" class="btn btn-default btn-block" id="cancelTmpNotice">取消</button></div>
                </div>
              </div>
            </form>     
        </div> 
        </div>
      </div>
<script type="text/javascript">
jQuery(function($) {      
    //点击编辑页面中保存按钮
	$("#addTmpNotice").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/handerAddTmpNotice',
			data: $("#editForm").serialize(),
			target: 'main'
		});
	});
	//点击编辑页面中取消按钮
	$("#cancelTmpNotice").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/tmpNotice',
			target: 'main'
		});
	});
	
	$("#startDate").datepicker();
	$("#endDate").datepicker();
});
</script>   