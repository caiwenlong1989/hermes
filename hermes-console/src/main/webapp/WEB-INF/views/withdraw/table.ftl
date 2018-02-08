<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
			<th width="10%"><@messages key="model.withdraw.name" /></th>
			<th width="10%" >卡号后四位</th>
			<th width="10%" >银行名称</th>
			<th width="10%" >所属支行</th>
			<th width="5%" ><@messages key="model.withdraw.amount" />/元</th>
			<th width="5%" ><@messages key="model.withdraw.fee" />/元</th>
			<th width="10%"><@messages key="model.withdraw.datetime" /></th>
			<th width="10%"><@messages key="model.withdraw.status" /></th>
			<th width="20%">提现流水号</th>
		</tr>
	</thead>
	<tbody>
		<#if withdraw.numberOfElements == 0>
		<tr>
			<td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list withdraw.content as wd>
		<tr>
			<td>${wd.bankAccount.name!''}</td>
			<td>******${wd.bankAccount.accountLast!''}</td>
			<td>${wd.bankAccount.bank.name!''}</td>
			<td>${wd.bankAccount.deposit!''}</td>
			<td >${wd.formatAmount}</td>
			<td >${wd.formatFee}</td>
			<td>${wd.formatDatetime}</td>
			<td>${wd.statusName}</td>
			<td>${wd.serialNo!""}</td>
		</tr>
		</#list>
		</#if>
	</tbody>
</table>

<ul class="pagination" data-number="${withdraw.number}" data-total-pages="${withdraw.totalPages}"></ul>

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
//-->
</script>
