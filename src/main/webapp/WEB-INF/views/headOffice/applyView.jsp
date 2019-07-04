<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- Header --%>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
	/* 테이블 테두리 black */
	table,th,tr,td{
		border: 1px solid black; 
	}
	/* 글씨 black, 밑줄 제거 */
	a{
		text-decoration: none;
		color:black;
	}
	/* Content 세로 설정 */
	[name=contentTd]{
		height:200px;
	}
</style>

<%-- Content --%>
<section id="content-wrapper">
	<div class="area">
		<table>
			<tr>
				<th>제목</th>
				<td colspan="3"><span>${ap.applyTitle }</span></td>
			</tr>
			<tr>
				<th>이름</th>
				<td>${ap.applyName }</td>
				<th>지역</th>
				<td>${ap.applyArea }</td>
			</tr>
			<tr>
				<th>연락처</th>
				<td>${ap.applyPhone }</td>
				<th>이메일</th>
				<td>${ap.applyEmail }</td>
			</tr>
			<!-- 첨부파일이 없으면 hidden -->
			<c:choose>
				<c:when test="${ap.applyFilename }">
					<tr>
						<th>첨부파일</th>
						<td colspan="3">${ap.applyFilename }</td>
					</tr>
				</c:when>
			</c:choose>
			<tr>
				<th>내용</th>
				<td name="contentTd"colspan="3"><span>${ap.applyContent }</span></td>
			</tr>
		</table>
		<button type="button"><a href="/managerApply.do">목록으로</a></button>
	</div>
</section>

<%-- Footer --%>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />