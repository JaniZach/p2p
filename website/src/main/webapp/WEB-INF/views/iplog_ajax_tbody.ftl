<#if result.listData?size &gt; 0 >
	<#list result.listData as item>
		<tr>
			<td>${item.username}</td>
			<td>${item.loginTime?string("yyyy-MM-dd HH:mm:ss")}</td>
			<td>${item.ip}</td>
			<td>${item.displayState}</td>
		</tr>
	</#list>
<#else>
	<tr>
		<td colspan="4" align="center">
			<p class="text-danger">目前没有符合要求的登录记录</p>
		</td>
	</tr>
</#if>
<script type="text/javascript">
	$(function(){
		$("#page_container").empty().append($('<ul id="pagination" class="pagination"></ul>'));
		$("#pagination").twbsPagination({
			totalPages:${result.totalPage},
			currentPage:${result.currentPage},
			visiblePages:5,
			startPage:${result.currentPage},
			initiateStartPageClick:false,
			onPageClick : function(event, page) {
				$("#currentPage").val(page);
				$("#searchForm").submit();
			}
		});
	});
</script>