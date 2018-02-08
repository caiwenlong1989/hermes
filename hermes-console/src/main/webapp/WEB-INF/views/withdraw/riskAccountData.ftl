<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
				<th width="20%" class="align-center">序号</th>
				<th width="30%" class="align-center">时间</th>
				<th width="10%" class="align-center">转入</th>
				<th width="10%" class="align-center">转出</th>
				<th width="10%" class="align-center">对象</th>
				<th width="30%" class="align-center">备注</th>
		</tr>
	</thead>
    <tbody>
      <#if transaction.numberOfElements == 0>
		    <tr>
			    <td colspan="5" class="align-center"><@messages key="common.table.empty" /></td>
		    </tr>
	  <#else>
		    <#list transaction.content as l>
		       <tr>
			       <td class="align-center"></td>
			       <td class="align-center">${l.datetime}</td>
			       <#if l.type="10">
			         <td class="align-center"></td>
			         <td class="align-center">${l.amount}</td>
			       <#else>
			         <td class="align-center">${l.amount}</td>
			         <td class="align-center"></td>
			       </#if>
			        <td class="align-center">${(l.targetUserAccount.user.account)!''}</td>
			       <td class="align-center"> <@risk val="${l.reference}" />   </td>
		        </tr>
		    </#list>
	 </#if>
	</tbody>
</table>
<ul class="pagination" data-number="${transaction.number}" data-total-pages="${transaction.totalPages}"></ul>

<script type="text/javascript">
<!--
jQuery(function($) {
	$('#table a').link();
	$('.pagination').pagination({
		handler: function(elem) {
			$('#page').val(elem.data().page);
			$('#searchForm').trigger('submit');
		}
	});
});
 $(function(){
         var len = $('#table tr').length;
         if(len >2){
            for(var i = 1;i<len;i++){
               $('#table tr:eq('+i+') td:first').text(i);
           }
         }
                   
    });
//-->

</script>
