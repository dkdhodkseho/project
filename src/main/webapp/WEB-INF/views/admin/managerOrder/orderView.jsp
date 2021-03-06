<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- Header --%>
<jsp:include page="/WEB-INF/views/admin/common/header.jsp" />

<%-- Content --%>

<section id="content-wrapper" class="clearfix">
	<%-- LEFT MENU --%>
	<jsp:include page="/WEB-INF/views/admin/common/admin-left-nav.jsp" />
	<div class="area">
		<div class="sub-menu">※ 재고관리 > <a href="/managerOrder/orderList.do">발주 리스트</a></div>

			<table class="comm-tbl">
				<colgroup>
					<col width="20%">
					<col width="80%">
				</colgroup>
				<tr>
					<th>상태</th>
					<th>
						<c:if test="${order.mOrderState eq 1}"><span class="state-reg">접수완료</span></c:if>
						<c:if test="${order.mOrderState eq 2}"><span class="state-out">출고완료</span></c:if>
						<c:if test="${order.mOrderState eq 3}"><span class="state-arr">도착</span></c:if>
						<c:if test="${order.mOrderState eq 4}"><span class="state-can">취소</span></c:if>
					</th>
				</tr>
				<tr>
					<th>발주 등록일</th><td>${order.mOrderDate }</td>
				</tr>
				<tr>
					<th>희망 배송일</th><td>${order.mOrderDelDate }</td>
				</tr>
			</table>
			<br><br><br><br><br><br>
			<p class="comm-content-tit">발주 내역</p>
			<table class="comm-tbl">
				<colgroup>
					<col width="80%">
					<col width="20%">
				</colgroup>
				<tr>
					<th>재료명</th><th>단위별 수량</th>
				</tr>
				<c:forEach items="${order.itemList }" var="item">
					<tr>
					<td>${item.mItemName }</td><td><input type="text" class="short" value="${item.mItemAmount }" readonly="readonly"></td>
				</tr>
				</c:forEach>
			</table>
			<div class="common-tbl-btn-group">
				<button class="btn-style2" type="button" onclick="history.back();">목록보기</button>
				<c:if test="${order.mOrderState eq 1}">
					<c:if test="${sessionScope.mgr.mgrLevel eq 0 }">
						<button class="btn-style3" type="button" onclick="update('${order.mOrderNo}',4)">발주취소</button><!-- 가맹점 회원만 가능 -->
					</c:if>
					<c:if test="${sessionScope.mgr.mgrLevel eq 1 }">
						<button class="btn-style1" type="button" onclick="update('${order.mOrderNo}',2);">출고처리</button><!-- 최고관리자만 가능 -->
					</c:if>
				</c:if>
			</div>
	</div>
</section>

<script>
function update(no,st){
	if(st==4){
		if(!confirm('취소하시겠습니까?')){
			return;
		}
	}
	if(st==2){
		if(!confirm('출고하시겠습니까?')){
			return;
		}
	}
	
	$.ajax({
		url : '/managerOrder/updateState.do',
		data : {no:no,st:st},
		success : function(result){
			if(result==1){
				location.reload();
			}else{
				console.log('뭐야');
			}
		},
		error : function(){
			console.log('실패');
		}
		
	});
	
	
}
</script>
<%-- Footer --%>
<jsp:include page="/WEB-INF/views/admin/common/footer.jsp" />