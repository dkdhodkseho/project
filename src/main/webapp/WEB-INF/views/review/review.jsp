<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- Header --%>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="/resources/js/notice.js"></script><!-- notice.js -->
<style>
	.move-one-review:hover{
		cursor: pointer;
		color: #ffce32;
	}
</style>
<%-- Content --%>
<section id="content-wrapper">
	<div class="area">
		<h1 class="comm-content-tit">공지사항/이벤트</h1>
		<div class="common-tbl-box">
			
			<table class="comm-tbl">
			<colgroup>
				<col width="15%">
				<col width="15%">
				<col width="/">
				<col width="15%">
			</colgroup>
				<tr>
					<th>번호</th>
					<th>작성자</th>
					<th>제목</th>
					<th>작성일</th>
				</tr>
				
			<c:forEach items="${reviewList}" var="review">
				<tr>
					<td>${review.reviewNo }</td>
					<td>${review.reviewWriter }</td>
					<td class="move-one-review">${review.reviewTitle }</td>
					<td>${review.reviewDate }</td>
				</tr>
			</c:forEach>
			
			</table>
			<button class="btn-style2 insert-review">리뷰 작성하기</button>
			<%-- <c:if test="${sessionScope.customer.customerId eq 'admin' }">
				<div class="common-tbl-btn-group">
					<button class="btn-style2 insert-notice">공지사항 작성하기</button>
				</div>
			</c:if> --%>
			<div class="paging">${pageNavi }</div>
		</div>
	</div>
</section>

<%-- Footer --%>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />